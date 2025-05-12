package co.com.nequi.dynamo.adapters.product;

import co.com.nequi.dynamo.adapters.product.mapper.ProductAdapterMapper;
import co.com.nequi.dynamo.entity.RegisterDynamo;
import co.com.nequi.dynamo.repository.RegisterRepository;
import co.com.nequi.models.enums.RegisterState;
import co.com.nequi.models.product.Product;
import co.com.nequi.models.product.gateways.ProductGateways;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProductAdapter implements ProductGateways {

    private final RegisterRepository registerRepository;
    @Override
    public Mono<Product> save(Product product) {
        String now = LocalDateTime.now().toString();
        Product productTobeSaved = product.toBuilder()
                .createdAt(now)
                .updatedAt(now)
                .state(RegisterState.ACTV.getValue())
                .build();
        RegisterDynamo entity = ProductAdapterMapper.MAPPER.toRegisterDynamo(productTobeSaved);
        return registerRepository.save(entity)
                .flatMap(dbResult -> Mono.just(ProductAdapterMapper.MAPPER.toDomain(dbResult)));
    }

    @Override
    public Mono<Product> findById(String franchiseId, String productId) {
        String partitionKey = "franquicia#" + franchiseId;
        String sortKey = "PRODUCT#" + productId;
        return registerRepository.findByKey(partitionKey, sortKey)
                .flatMap(dbResult -> Mono.just(ProductAdapterMapper.MAPPER.toDomain(dbResult)));
    }
    @Override
    public Mono<Product> update(Product product) {
        RegisterDynamo entity = ProductAdapterMapper.MAPPER.toRegisterDynamo(product);
        return registerRepository.save(entity)
                .thenReturn(product);
    }


}
