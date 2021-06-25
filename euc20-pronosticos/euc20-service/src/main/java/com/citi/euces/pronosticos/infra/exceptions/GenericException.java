package com.citi.euces.pronosticos.infra.exceptions;

public class GenericException  extends Exception {

    private static final long serialVersionUID = 1L;

    private String codeError;

    public GenericException() {
        super();
    }

    public GenericException(String message, String codeError ) {
        super(message);
        this.codeError = codeError;
    }

    public GenericException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenericException(String message) {
        super(message);
    }

    public GenericException(Throwable cause) {
        super(cause);
    }

    public String getCodeError() {
        return codeError;
    }

    public void setCodeError(String codeError) {
        this.codeError = codeError;
    }


    @Override
    public String getMessage() {
        return (super.getMessage() == null ? "null" : super.getMessage());
    }



}

