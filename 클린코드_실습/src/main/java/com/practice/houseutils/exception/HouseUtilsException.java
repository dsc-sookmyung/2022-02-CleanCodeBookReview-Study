package com.practice.houseutils.exception;

// Unchecked Exception을 사용하자! -> RuntimeException
public class HouseUtilsException extends RuntimeException{
    private ErrorCode errorCode;
    private String message;

    public HouseUtilsException(ErrorCode errorCode){
        this(errorCode, errorCode.getMessage());
    }
    public HouseUtilsException(ErrorCode errorCode, String customMessage){
        super(customMessage);
        this.errorCode = errorCode;
        this.message = customMessage;
    }
}
