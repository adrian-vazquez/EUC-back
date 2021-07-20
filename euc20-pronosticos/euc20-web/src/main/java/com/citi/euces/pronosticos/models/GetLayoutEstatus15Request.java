package com.citi.euces.pronosticos.models;

import java.io.Serializable;

public class GetLayoutEstatus15Request implements Serializable {

    private Integer diasProteccion;
    private Integer secuencialArch;
    private Integer sucursal;
    private Integer cuenta;
    private Integer dias;

    public Integer getDiasProteccion() {
        return diasProteccion;
    }

    public void setDiasProteccion(Integer diasProteccion) {
        this.diasProteccion = diasProteccion;
    }

    public Integer getSecuencialArch() {
        return secuencialArch;
    }

    public void setSecuencialArch(Integer secuencialArch) {
        this.secuencialArch = secuencialArch;
    }

    public Integer getSucursal() {
        return sucursal;
    }

    public void setSucursal(Integer sucursal) {
        this.sucursal = sucursal;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }
}
