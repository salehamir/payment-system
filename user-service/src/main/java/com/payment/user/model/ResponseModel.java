package com.payment.user.model;

import com.payment.user.enumeration.ResponseCode;
import lombok.Getter;


@Getter
public class ResponseModel<T> {
    private final ResponseCode responseCode;
    private final String errorMessage;
    private final T content;

    public ResponseModel(ResponseCode responseCode, String errorMessage, T content) {
        this.responseCode = responseCode;
        this.errorMessage = errorMessage;
        this.content = content;
    }

    public ResponseModel(ResponseCode responseCode, String errorMessage) {
        this(responseCode,errorMessage,null);
    }

    public ResponseModel(ResponseCode responseCode) {
        this(responseCode,"");
    }


}
