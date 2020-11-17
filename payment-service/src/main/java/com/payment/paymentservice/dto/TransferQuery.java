package com.payment.paymentservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TransferQuery {
    private List<String> sourceCardNumber;
    private Date from;
    private Date to;
    private int page;
    private int size;
}
