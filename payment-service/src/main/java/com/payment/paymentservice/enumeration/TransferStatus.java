package com.payment.paymentservice.enumeration;


public enum TransferStatus {
    SUCCESS(0),FAILED(1);
    private final Integer value;

    TransferStatus(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}

