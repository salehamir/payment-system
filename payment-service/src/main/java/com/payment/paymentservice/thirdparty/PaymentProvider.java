package com.payment.paymentservice.thirdparty;

import com.payment.paymentservice.dto.TransferRequest;
import com.payment.paymentservice.model.MoneyTransfer;
import reactor.core.publisher.Mono;

public interface PaymentProvider {
    Mono<MoneyTransfer> transfer(TransferRequest request);
}
