package co.com.nequi.usecase;

import co.com.nequi.models.franchise.Franchise;
import co.com.nequi.models.franchise.gateways.FranchiseGateways;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FranchiseUseCase {

    private final FranchiseGateways franchiseAdapter;
    public Mono<Franchise> save(Franchise franchise) {
            return franchiseAdapter.save(franchise);
    }
}
