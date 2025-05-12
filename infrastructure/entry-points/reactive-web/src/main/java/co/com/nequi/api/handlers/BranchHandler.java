package co.com.nequi.api.handlers;

import co.com.nequi.api.dtos.BranchRequest;
import co.com.nequi.api.dtos.FranchiseRequest;
import co.com.nequi.api.mappers.BranchMapper;
import co.com.nequi.api.mappers.FranchiseMapper;
import co.com.nequi.usecase.BranchUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class BranchHandler {

    private final BranchUseCase branchUseCase;
    public Mono<ServerResponse> createBranch(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(BranchRequest.class)
                .flatMap(request -> Mono.just(BranchMapper.MAPPER.toDomain(request)))
                .flatMap(branchUseCase::save)
                .then(ServerResponse.created(serverRequest.uri()).build())
                .onErrorResume(error -> {
                    log.error("Error al crear franquicia", error);
                    return ServerResponse.status(500).bodyValue("Error interno al crear la franquicia");
                });
    }
    public Mono<ServerResponse> updateStock(ServerRequest serverRequest) {
            return null;
    }

    public Mono<ServerResponse> updateBranchName(ServerRequest serverRequest) {
            return null;
    }
}
