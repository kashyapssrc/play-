package com.objectfrontier.training.filter.exception;

import java.util.ArrayList;
import java.util.List;

public class AppException extends RuntimeException{

    private static final long serialVersionUID = -4535673690222114178L;

    private List<ExceptionCodes> errorList;

    public AppException(ExceptionCodes code, Throwable cause) {

        super(cause);
        this.errorList= new ArrayList<>();
        errorList.add(code);
    }

    public AppException(ExceptionCodes code) {

        super();
        this.errorList= new ArrayList<>();
        errorList.add(code);
    }

    public AppException(List<ExceptionCodes> errorList) {

        super();
        this.errorList = errorList;
    }

    public List<ExceptionCodes> getErrorList() {
        return errorList;
    }
}
