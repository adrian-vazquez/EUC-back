package com.citi.euces.pronosticos.models;

import java.io.Serializable;

public class GetLayoutEstatus15Request implements Serializable {

    private Integer diasProteccion;
    private Integer secuencialArch;
    private Long sucursal;
    private Long cuenta;

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

    public Long getSucursal() {
        return sucursal;
    }

    public void setSucursal(Long sucursal) {
        this.sucursal = sucursal;
    }

    public Long getCuenta() {
        return cuenta;
    }

    public void setCuenta(Long cuenta) {
        this.cuenta = cuenta;
    }
}
