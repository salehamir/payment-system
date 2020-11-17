package com.payment.paymentservice.Handler;

import com.payment.paymentservice.dto.TransferQuery;
import com.payment.paymentservice.dto.TransferRequest;
import com.payment.paymentservice.model.MoneyTransfer;
import com.payment.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PaymentHandler {
    private PaymentService service;

    public Mono<ServerResponse> transfer(ServerRequest request){
        return request.bodyToMono(TransferRequest.class)
                .flatMap(service::transfer)
                .flatMap(res->ServerResponse.ok().body(BodyInserters.fromValue(res)));
    }
    public Mono<ServerResponse> getTransferByQuery(ServerRequest request){
        return request.bodyToMono(TransferQuery.class)
                .flatMap(query->ServerResponse.ok().body(service.findTransferByQuery(query), MoneyTransfer.class));
    }
}
