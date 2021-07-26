package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class GetLayoutEstatus15DTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String mensajeInfo;
    private String file;

    public String getMensajeInfo() {
        return mensajeInfo;
    }

    public void setMensajeInfo(String mensajeInfo) {
        this.mensajeInfo = mensajeInfo;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
