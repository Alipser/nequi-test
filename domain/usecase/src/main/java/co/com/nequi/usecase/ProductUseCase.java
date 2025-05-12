package co.com.nequi.usecase;

import co.com.nequi.models.enums.RegisterState;
import co.com.nequi.models.exceptions.TechnicalException;
import co.com.nequi.models.product.Product;
import co.com.nequi.models.product.gateways.ProductGateways;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ProductUseCase {
    private final ProductGateways productAdapter;
    public Mono<Product> save(Product product) {
            return productAdapter.save(product);
    }

    public Mono<Product> deactivate(String franchiseId, String branchId, String productId) {
        String now = LocalDateTime.now().toString();
        return productAdapter.findById(franchiseId, productId)
                .switchIfEmpty(Mono.error(new TechnicalException("Producto no encontrado")))
                .flatMap(existing -> productAdapter.update(existing.toBuilder()
                                .state(RegisterState.DELETD.getValue())
                                .updatedAt(now)
                        .build()));
    }
}
