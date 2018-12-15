package com.objectfrontier.training.java.services.exception;

public class AppException extends RuntimeException{

    private static final long serialVersionUID = -4535673690222114178L;

    private int errorCode;
    private String errorMessage;

    public AppException(ExceptionCodes code) {
        this.errorCode = code.getId();
        this.errorMessage = code.getMessage();
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
