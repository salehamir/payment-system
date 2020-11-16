package com.payment.user.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Card {
    @Id
    private String cardNumber;
    @Indexed
    private UserProfileRef user;
    private String bankName;
    private String expDate;
}
