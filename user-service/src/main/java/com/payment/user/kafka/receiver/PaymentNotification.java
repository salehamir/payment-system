package com.payment.user.kafka.receiver;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class PaymentNotification {
    private String id;
    private String sourceCardNumber;
    private String message;
}
