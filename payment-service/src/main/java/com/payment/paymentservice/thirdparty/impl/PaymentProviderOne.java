package com.payment.paymentservice.thirdparty.impl;

import com.payment.paymentservice.dto.TransferRequest;
import com.payment.paymentservice.enumeration.TransferStatus;
import com.payment.paymentservice.model.MoneyTransfer;
import com.payment.paymentservice.thirdparty.PaymentProvider;
import com.payment.paymentservice.thirdparty.model.ProviderRequestOne;
import com.payment.paymentservice.thirdparty.model.ProviderResponseOne;
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
@Qualifier("providerOne")
public class PaymentProviderOne implements PaymentProvider {

    private final WebClient webClient;
    private final CommonUtil util;
    @Value("${thirdparty.provider.one.url}")
    private String providerUrl;

    public PaymentProviderOne(CommonUtil util) {
        this.util = util;
        this.webClient = WebClient.builder().build();
    }


    @Override
    public Mono<MoneyTransfer> transfer(TransferRequest request) {

        return webClient.post()
                .uri(URI.create(providerUrl + "/transfer"))
                .body(BodyInserters.fromValue(new ProviderRequestOne(request)))
                .exchangeToMono(clientResponse -> util.checkError(clientResponse)
                        .flatMap(res -> res.bodyToMono(ProviderResponseOne.class)))
                .map(res -> {
                    MoneyTransfer transfer = new MoneyTransfer();
                    transfer.setSourceCardNumber(request.getSourceCardNumber());
                    transfer.setTargetCardNumber(request.getTargetCardNumber());
                    transfer.setAmount(request.getAmount());
                    if (res.getStatus() != null && res.getStatus().equalsIgnoreCase("ok")) {
                        transfer.setStatus(TransferStatus.SUCCESS);
                        transfer.setTransactionCode(res.getCode());
                        transfer.setTransactionTime(new Date());
                    } else {
                        transfer.setStatus(TransferStatus.FAILED);
                        transfer.setTransactionTime(new Date());
                    }
                    return transfer;
                });
    }
}
