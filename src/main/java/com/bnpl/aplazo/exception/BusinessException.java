package com.bnpl.aplazo.exception;

public class BusinessException extends RuntimeException {

    private final String code;
    private final String error;

    public BusinessException(String message, String code, String error) {
        super(message);
        this.code = code;
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }
}

