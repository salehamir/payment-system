package com.payment.paymentservice.model;

import com.payment.paymentservice.enumeration.TransferStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;
@Document
@Getter
@Setter
@NoArgsConstructor
public class MoneyTransfer {
    @Id
    private String id;
    @Indexed
    private String sourceCardNumber;
    private String targetCardNumber;
    private BigDecimal amount;
    private TransferStatus status;
    private Date transactionTime;
    private String transactionCode;
}
