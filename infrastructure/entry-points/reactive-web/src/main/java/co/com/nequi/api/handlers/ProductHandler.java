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
public class ProductHandler {
    public Mono<ServerResponse> createProduct(ServerRequest serverRequest) {
        return null;
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest serverRequest) {
        return null;
    }

    public Mono<ServerResponse> updateProductName(ServerRequest serverRequest) {
            return null;
    }
}
