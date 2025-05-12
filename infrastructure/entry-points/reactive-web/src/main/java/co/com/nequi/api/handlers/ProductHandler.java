package co.com.nequi.api.handlers;

import co.com.nequi.api.dtos.BranchRequest;
import co.com.nequi.api.dtos.ProductRequest;
import co.com.nequi.api.mappers.BranchMapper;
import co.com.nequi.api.mappers.ProductMapper;
import co.com.nequi.usecase.ProductUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static io.micrometer.common.util.StringUtils.isBlank;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductHandler {
    private final ProductUseCase productUseCase;

    public Mono<ServerResponse> createProduct(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(ProductRequest.class)
                .flatMap(request -> Mono.just(ProductMapper.MAPPER.toDomain(request)))
                .flatMap(productUseCase::save)
                .then(ServerResponse.created(serverRequest.uri()).build())
                .onErrorResume(error -> {
                    log.error("Error al crear franquicia", error);
                    return ServerResponse.status(500).bodyValue("Error interno al crear la franquicia");
                });
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        String franchiseId = request.pathVariable("franquiciaId");
        String branchId = request.pathVariable("sucursalId");
        String productId = request.pathVariable("productoId");

        if (isBlank(franchiseId) || isBlank(branchId) || isBlank(productId)) {
            return ServerResponse.badRequest().bodyValue("franquiciaId, sucursalId y productoId son requeridos.");
        }

        return productUseCase.deactivate(franchiseId, branchId, productId)
                .flatMap(id -> ServerResponse.ok().bodyValue("Producto desactivado: " + id))
                .onErrorResume(error -> {
                    log.error("Error al desactivar producto", error);
                    return ServerResponse.status(500).bodyValue("Error interno al desactivar el producto");
                });
    }

    public Mono<ServerResponse> updateStock(ServerRequest request) {
        String franchiseId = request.pathVariable("franquiciaId");
        String branchId = request.pathVariable("sucursalId");
        String productId = request.pathVariable("productoId");

        if (isBlank(franchiseId) ||isBlank(branchId) || isBlank(productId)) {
            return ServerResponse.badRequest().bodyValue("Todos los path variables son requeridos.");
        }

        return request.bodyToMono(ProductRequest.class)
                .flatMap(req -> {

                    boolean mismatch = !franchiseId.equals(req.getFranchiseId())
                            || !branchId.equals(req.getBranchId())
                            || !productId.equals(req.getId());

                    if (mismatch) {
                        return ServerResponse.badRequest().bodyValue("Los datos del body no coinciden con los de la URL.");
                    }

                    return productUseCase.updateStock(franchiseId, branchId, productId, req.getStock())
                            .flatMap(p -> ServerResponse.ok().bodyValue("Producto actualizado: " + p.getId()))
                            .onErrorResume(error -> {
                                log.error("Error al actualizar producto", error);
                                return ServerResponse.status(500).bodyValue("Error interno");
                            });
                });
    }


    public Mono<ServerResponse> updateProductName(ServerRequest request) {
        String franchiseId = request.pathVariable("franquiciaId");
        String branchId = request.pathVariable("sucursalId");
        String productId = request.pathVariable("productoId");

        if (isBlank(franchiseId) ||isBlank(branchId) || isBlank(productId)) {
            return ServerResponse.badRequest().bodyValue("Todos los path variables son requeridos.");
        }

        return request.bodyToMono(ProductRequest.class)
                .flatMap(req -> {

                    boolean mismatch = !franchiseId.equals(req.getFranchiseId())
                            || !branchId.equals(req.getBranchId())
                            || !productId.equals(req.getId());

                    if (mismatch) {
                        return ServerResponse.badRequest().bodyValue("Los datos del body no coinciden con los de la URL.");
                    }

                    return productUseCase.updateProductName(franchiseId, branchId, productId, req.getName())
                            .flatMap(product -> ServerResponse.ok().bodyValue("Producto actualizado: " + product.getId()))
                            .onErrorResume(error -> {
                                log.error("Error al actualizar producto", error);
                                return ServerResponse.status(500).bodyValue("Error interno");
                            });
                });
    }

    public Mono<ServerResponse> getTopProductsByStock(ServerRequest request) {
        String franchiseId = request.pathVariable("franquiciaId");

        if (isBlank(franchiseId)) {
            return ServerResponse.badRequest().bodyValue("franquiciaId es requerido.");
        }

        return productUseCase.getTopProductsByStock(franchiseId)
                .collectList()
                .flatMap(products -> ServerResponse.ok().bodyValue(products))
                .onErrorResume(error -> {
                    log.error("Error al obtener productos con mayor stock por sucursal", error);
                    return ServerResponse.status(500).bodyValue("Error interno");
                });
    }

}
