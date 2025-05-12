package co.com.nequi.models.franchise.gateways;

import co.com.nequi.models.franchise.Franchise;
import reactor.core.publisher.Mono;

public interface FranchiseGateways {
    Mono<Franchise> save(Franchise franchise);
}
