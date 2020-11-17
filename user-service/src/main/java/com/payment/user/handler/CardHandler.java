package com.payment.user.handler;

import com.payment.user.enumeration.ResponseCode;
import com.payment.user.model.Card;
import com.payment.user.model.ResponseModel;
import com.payment.user.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CardHandler {

    private final CardService service;

    public Mono<ServerResponse> addCard(ServerRequest request) {
        return request.bodyToMono(Card.class)
                .flatMap(body -> service.getByCardNumber(body.getCardNumber())
                        .flatMap(res -> ServerResponse.badRequest()
                                .bodyValue(new ResponseModel<Card>
                                        (ResponseCode.ERROR, "duplicate card number")))
                        .switchIfEmpty(service.addCard(body).
                                flatMap(card -> ServerResponse.ok()
                                        .bodyValue(new ResponseModel<Card>
                                                (ResponseCode.OK, "", card)))));


    }

    public Mono<ServerResponse> getAllCardByUser(ServerRequest request) {
        return Flux.just(request.pathVariable("username"))
                .flatMap(service::getAllByUser)
                .collectList()
                .flatMap(res -> ServerResponse.ok()
                        .bodyValue(new ResponseModel<List<Card>>
                                (ResponseCode.OK, "", res)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> removeCard(ServerRequest request) {
        return Mono.just(request.pathVariable("id"))
                .flatMap(service::removeCard)
                .flatMap(t -> ServerResponse.ok()
                        .bodyValue(new ResponseModel<Card>(ResponseCode.OK, "")))
                .onErrorResume(throwable ->
                        ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .bodyValue(new ResponseModel<Card>(ResponseCode.ERROR, throwable.getMessage()))
                );
    }
}
