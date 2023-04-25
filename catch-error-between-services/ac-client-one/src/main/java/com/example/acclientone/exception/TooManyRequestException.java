package com.example.acclientone.exception;

public class TooManyRequestException extends RuntimeException{
    private int errorCode;
    private String errorStatus;

    public TooManyRequestException() {
    }

    public TooManyRequestException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public TooManyRequestException(String message, String errorStatus) {
        super(message);
        this.errorStatus = errorStatus;
    }

    public TooManyRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManyRequestException(Throwable cause) {
        super(cause);
    }

    protected TooManyRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
