package co.com.nequi.api.handlers;

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
    public Mono<ServerResponse> createBranch(ServerRequest serverRequest) {
            return null;
    }

    public Mono<ServerResponse> updateStock(ServerRequest serverRequest) {
            return null;
    }

    public Mono<ServerResponse> updateBranchName(ServerRequest serverRequest) {
            return null;
    }
}
