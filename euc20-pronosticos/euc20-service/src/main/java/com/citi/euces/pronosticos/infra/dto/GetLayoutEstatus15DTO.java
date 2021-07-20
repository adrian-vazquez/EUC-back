package com.citi.euces.pronosticos.infra.dto;

import org.springframework.data.domain.Page;

import java.io.Serializable;

public class GetLayoutEstatus15DTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private MensajeDTO mensaje;
    private String file;

    public MensajeDTO getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajeDTO mensaje) {
        this.mensaje = mensaje;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
