package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class AddListFinalTmpEstatus15DTO implements Serializable {
    private String llave;
    private Integer secArch;
    private Integer secInt;
    private String keyUp;
    private Long noCliente;
    private Long ctaCliente;

    public String getLlave() {
        return llave;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }

    public Integer getSecArch() {
        return secArch;
    }

    public void setSecArch(Integer secArch) {
        this.secArch = secArch;
    }

    public Integer getSecInt() {
        return secInt;
    }

    public void setSecInt(Integer secInt) {
        this.secInt = secInt;
    }

    public String getKeyUp() {
        return keyUp;
    }

    public void setKeyUp(String keyUp) {
        this.keyUp = keyUp;
    }

    public Long getNoCliente() {
        return noCliente;
    }

    public void setNoCliente(Long noCliente) {
        this.noCliente = noCliente;
    }

    public Long getCtaCliente() {
        return ctaCliente;
    }

    public void setCtaCliente(Long ctaCliente) {
        this.ctaCliente = ctaCliente;
    }
}
