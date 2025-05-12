package co.com.nequi.models.branch.gateways;

import co.com.nequi.models.branch.Branch;
import reactor.core.publisher.Mono;

public interface BranchGateways {
    Mono<Branch> save(Branch branch);
}
