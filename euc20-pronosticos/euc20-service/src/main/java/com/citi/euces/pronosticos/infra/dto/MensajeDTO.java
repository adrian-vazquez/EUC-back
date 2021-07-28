package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class MensajeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String mensajeInfo;
    private String mensajeConfirm;

    public MensajeDTO() {
    }

    public MensajeDTO(String mensajeInfo, String mensajeConfirm) {
        this.mensajeInfo = mensajeInfo;
        this.mensajeConfirm = mensajeConfirm;
    }

    public String getMensajeInfo() {
        return mensajeInfo;
    }

    public void setMensajeInfo(String mensajeInfo) {
        this.mensajeInfo = mensajeInfo;
    }

    public String getMensajeConfirm() {
        return mensajeConfirm;
    }

    public void setMensajeConfirm(String mensajeConfirm) {
        this.mensajeConfirm = mensajeConfirm;
    }
}
