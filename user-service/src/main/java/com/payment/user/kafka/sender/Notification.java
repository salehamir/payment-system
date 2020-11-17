package com.payment.user.kafka.sender;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Notification {
    private String id;
    private String message;
    private String mobile;
    private String email;
}
