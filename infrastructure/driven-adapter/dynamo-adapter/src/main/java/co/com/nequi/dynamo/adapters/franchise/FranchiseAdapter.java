package co.com.nequi.dynamo.adapters.franchise;

import co.com.nequi.dynamo.adapters.franchise.mapper.FranchiseToDynamoMapper;
import co.com.nequi.dynamo.entity.RegisterDynamo;
import co.com.nequi.dynamo.repository.RegisterRepository;
import co.com.nequi.models.franchise.Franchise;
import co.com.nequi.models.franchise.gateways.FranchiseGateways;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class FranchiseAdapter implements FranchiseGateways {

    private final RegisterRepository registerRepository;

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        RegisterDynamo entity = FranchiseToDynamoMapper.MAPPER.toRegisterDynamo(franchise);
        return registerRepository.save(entity)
                .flatMap(dbResponse-> Mono.just(FranchiseToDynamoMapper.MAPPER.toDomain(dbResponse)));

    }
}
