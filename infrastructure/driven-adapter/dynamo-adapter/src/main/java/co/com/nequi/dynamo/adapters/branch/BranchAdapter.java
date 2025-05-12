package co.com.nequi.dynamo.adapters.branch;

import co.com.nequi.dynamo.adapters.branch.mapper.BranchMapperAdapter;
import co.com.nequi.dynamo.entity.RegisterDynamo;
import co.com.nequi.dynamo.repository.RegisterRepository;
import co.com.nequi.models.branch.Branch;
import co.com.nequi.models.branch.gateways.BranchGateways;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class BranchAdapter implements BranchGateways {

    private final RegisterRepository registerRepository;
    @Override
    public Mono<Branch> save(Branch branch) {
        String now = LocalDateTime.now().toString();
        RegisterDynamo entity = BranchMapperAdapter.MAPPER.toRegisterDynamo(branch, now);
        return registerRepository.save(entity)
                .flatMap(dbResponse-> Mono.just(BranchMapperAdapter.MAPPER.toDomain(dbResponse)));
    }
}
