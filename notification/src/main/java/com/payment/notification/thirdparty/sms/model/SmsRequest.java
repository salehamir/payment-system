package com.payment.notification.thirdparty.sms.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SmsRequest {
    private final String msg;
    private final String target;
}
