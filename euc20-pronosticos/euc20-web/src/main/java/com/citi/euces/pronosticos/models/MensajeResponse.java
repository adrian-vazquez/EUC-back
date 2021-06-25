package com.citi.euces.pronosticos.models;

import com.citi.euces.pronosticos.infra.dto.MensajeDTO;

import java.io.Serializable;

public class MensajeResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private MensajeDTO mensaje;
    private String code;

    public MensajeResponse(MensajeDTO mensaje, String code) {
        this.mensaje = mensaje;
        this.code = code;
    }

    public MensajeDTO getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajeDTO mensaje) {
        this.mensaje = mensaje;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
