package com.payment.paymentservice.thirdparty.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProviderResponseOne {
    private String status;
    private String code;
    private Date time;
}
