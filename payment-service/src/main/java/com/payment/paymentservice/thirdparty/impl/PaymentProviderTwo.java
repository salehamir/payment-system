package com.payment.paymentservice.thirdparty.impl;

import com.payment.paymentservice.dto.TransferRequest;
import com.payment.paymentservice.enumeration.TransferStatus;
import com.payment.paymentservice.model.MoneyTransfer;
import com.payment.paymentservice.thirdparty.PaymentProvider;
import com.payment.paymentservice.thirdparty.model.ProviderRequestTwo;
import com.payment.paymentservice.thirdparty.model.ProviderResponseTwo;
import com.payment.paymentservice.util.CommonUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;

@Component
@Qualifier("providerTwo")
public class PaymentProviderTwo implements PaymentProvider {
    private final WebClient webClient;
    private final CommonUtil util;
    @Value("${thirdparty.provider.two.url}")
    private String providerUrl;

    public PaymentProviderTwo(CommonUtil util) {
        this.util = util;
        this.webClient = WebClient.builder().build();
    }


    @Override
    public Mono<MoneyTransfer> transfer(TransferRequest request) {

        return webClient.post()
                .uri(URI.create(providerUrl))
                .body(BodyInserters.fromValue(new ProviderRequestTwo(request)))
                .exchangeToMono(clientResponse -> util.checkError(clientResponse)
                        .flatMap(res -> res.bodyToMono(ProviderResponseTwo.class)))
                .map(res -> {
                    MoneyTransfer transfer = new MoneyTransfer();
                    transfer.setSourceCardNumber(request.getSourceCardNumber());
                    transfer.setTargetCardNumber(request.getTargetCardNumber());
                    transfer.setAmount(request.getAmount());
                    if (res.getState() != null && res.getState().equalsIgnoreCase("success")) {
                        transfer.setStatus(TransferStatus.SUCCESS);
                        transfer.setTransactionCode(res.getTrackingCode());
                        transfer.setTransactionTime(res.getTime());
                    } else {
                        transfer.setStatus(TransferStatus.FAILED);
                        transfer.setTransactionTime(new Date());
                    }
                    return transfer;
                });
    }
}