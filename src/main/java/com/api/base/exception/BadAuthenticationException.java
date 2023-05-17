package com.api.base.exception;

public class BadAuthenticationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String code;

    public BadAuthenticationException(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
