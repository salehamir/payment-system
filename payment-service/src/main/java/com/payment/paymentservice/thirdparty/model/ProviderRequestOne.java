package com.payment.paymentservice.thirdparty.model;

import com.payment.paymentservice.dto.TransferRequest;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProviderRequestOne {
    private final String source;
    private final String dest;
    private final String cvv2;
    private final String expDate;
    private final String pin;
    private final BigDecimal amount;

    public ProviderRequestOne(TransferRequest request) {
        this.source=request.getSourceCardNumber();
        this.dest=request.getTargetCardNumber();
        this.cvv2=request.getCvv2();
        this.expDate=request.getExpDate();
        this.pin=request.getPin();
        this.amount=request.getAmount();
    }
}
