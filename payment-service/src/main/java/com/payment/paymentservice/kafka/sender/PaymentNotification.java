package com.payment.paymentservice.kafka.sender;

import com.payment.paymentservice.model.MoneyTransfer;
import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Getter
@Setter
public class PaymentNotification {
    private static final String DATE_PATTERN = "MM/dd/yyyy HH:mm:ss";
    private String id;
    private String sourceCardNumber;
    private String message;
    public PaymentNotification(MoneyTransfer transfer) {

        DateFormat df = new SimpleDateFormat(DATE_PATTERN);
        this.id=transfer.getId();
        this.sourceCardNumber=transfer.getSourceCardNumber();
        this.message="Money transfer from %s card to %s card in time %s was successfully completed with %s tracking code";
        this.message=String.format(this.message,sourceCardNumber,transfer.getTargetCardNumber()
                ,df.format(transfer.getTransactionTime()),transfer.getTransactionCode());
    }
}
