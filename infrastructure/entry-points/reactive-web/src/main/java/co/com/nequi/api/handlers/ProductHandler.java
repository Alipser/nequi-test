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

    public Mono<ServerResponse> updateProductName(ServerRequest serverRequest) {
            return null;
    }
}
