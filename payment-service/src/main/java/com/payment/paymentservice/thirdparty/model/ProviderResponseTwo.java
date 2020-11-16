package com.payment.paymentservice.thirdparty.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProviderResponseTwo {
    private String state;
    private String trackingCode;
    private Date time;
}
