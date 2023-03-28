package com.example.acclientone.exception;

public class DCServiceCallerException extends RuntimeException {
    private int errorCode;

    public DCServiceCallerException() {
    }

    public DCServiceCallerException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public DCServiceCallerException(Throwable cause, String message) {
        super(message, cause);
    }

    public DCServiceCallerException(String message, Throwable cause) {
        super(message, cause);
    }

    public DCServiceCallerException(Throwable cause) {
        super(cause);
    }

    protected DCServiceCallerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}