package com.payment.paymentservice.thirdparty.model;

import com.payment.paymentservice.dto.TransferRequest;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProviderRequestTwo {
    private final String source;
    private final String target;
    private final String cvv2;
    private final String expire;
    private final String pin2;
    private final BigDecimal amount;

    public ProviderRequestTwo(TransferRequest request) {
        this.source=request.getSourceCardNumber();
        this.target=request.getTargetCardNumber();
        this.cvv2=request.getCvv2();
        this.expire=request.getExpDate();
        this.pin2=request.getPin();
        this.amount=request.getAmount();
    }
}
