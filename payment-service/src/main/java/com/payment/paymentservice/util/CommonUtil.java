package com.payment.paymentservice.util;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

@Component
public class CommonUtil {

    public Mono<ClientResponse> checkError(ClientResponse clientResponse) {
        if (clientResponse.statusCode().isError()) {
            return clientResponse.bodyToMono(JsonNode.class).flatMap(res -> Mono.error(
                    new RuntimeException("rest call error status code : " + clientResponse.statusCode()
                            + " , response : " + res)));
        }
        return Mono.just(clientResponse);
    }
}
