package com.example.acclientone.exception;

public class BadRequestException extends RuntimeException{
    private int errorCode;
    private String errorStatus;

    public BadRequestException() {
    }

    public BadRequestException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BadRequestException(String message, String errorStatus) {
        super(message);
        this.errorStatus = errorStatus;
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

    protected BadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
