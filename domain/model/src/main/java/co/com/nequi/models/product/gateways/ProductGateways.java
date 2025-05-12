package co.com.nequi.models.product.gateways;

import co.com.nequi.models.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductGateways {
    Mono<Product> save(Product product);

    Mono<Product>findById(String franchiseId, String productId);

    Mono<Product>update(Product product);

    Flux<Product> findTopStockProductsByFranchise(String franchiseId);
}
