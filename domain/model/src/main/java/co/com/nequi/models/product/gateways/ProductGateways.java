package co.com.nequi.models.product.gateways;

import co.com.nequi.models.product.Product;
import reactor.core.publisher.Mono;

public interface ProductGateways {
    Mono<Product> save(Product product);
}
