package com.citi.euces.pronosticos.models;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorGeneric {
    private String mensaje;
    private String code;
    private String exception;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getException() {
        return exception;
    }

    public void setException(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        this.exception = sw.toString();
    }


}


