package com.payment.user.enumeration;

public enum ResponseCode {
    OK(0),ERROR(1);


    private final Integer value;

    ResponseCode(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
