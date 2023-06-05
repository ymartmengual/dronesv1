package com.example.drones.exceptions;

public class GenericErrorException extends RuntimeException{

    private String message;
    private String codeError;
    private String nameCodeError;



    public GenericErrorException(String message, String codeError, String nameCodeError){
        super(message);
        this.message=message;
        this.codeError=codeError;
        this.nameCodeError=nameCodeError;
    }

    public GenericErrorException() {}

    public String getCodeError() {
        return codeError;
    }

    public String getNameCodeError() {
        return nameCodeError;
    }
}
