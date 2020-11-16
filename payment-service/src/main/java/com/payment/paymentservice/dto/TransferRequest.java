package com.payment.paymentservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class TransferRequest {
    private String sourceCardNumber;
    private String targetCardNumber;
    private String cvv2;
    private String expDate;
    private String pin;
    private BigDecimal amount;



}
