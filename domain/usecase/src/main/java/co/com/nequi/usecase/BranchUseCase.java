package co.com.nequi.usecase;

import co.com.nequi.models.branch.Branch;
import co.com.nequi.models.branch.gateways.BranchGateways;


import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
@RequiredArgsConstructor
public class BranchUseCase {

    private final BranchGateways branchAdapter;
    public Mono<Branch> save(Branch branch) {
        return branchAdapter.save(branch);
    }
}
