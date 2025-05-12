package co.com.nequi.usecase;

import co.com.nequi.models.product.Product;
import co.com.nequi.models.product.gateways.ProductGateways;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
@RequiredArgsConstructor
public class ProductUseCase {
    private final ProductGateways productAdapter;


    public Mono<Product> save(Product product) {
            return productAdapter.save(product);
    }
}
