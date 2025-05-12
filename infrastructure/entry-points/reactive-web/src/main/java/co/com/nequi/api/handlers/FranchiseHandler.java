package co.com.nequi.api.handlers;

import co.com.nequi.api.dtos.FranchiseRequest;
import co.com.nequi.api.mappers.FranchiseMapper;
import co.com.nequi.usecase.FranchiseUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
@Component
@RequiredArgsConstructor
@Slf4j
public class FranchiseHandler {

    private final FranchiseUseCase franchiseUsecase;
    public Mono<ServerResponse> createFranchise(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(FranchiseRequest.class)
                .flatMap(request -> Mono.just(FranchiseMapper.MAPPER.toDomain(request)))
                .flatMap(franchiseUsecase::save)
                .then(ServerResponse.created(serverRequest.uri()).build())
                .onErrorResume(error -> {
                    log.error("Error al crear franquicia", error);
                    return ServerResponse.status(500).bodyValue("Error interno al crear la franquicia");
                });
    }


    public Mono<ServerResponse> updateFranchiseName(ServerRequest serverRequest) {
        return null;
    }
}
