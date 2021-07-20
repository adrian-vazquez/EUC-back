package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class Estatus15TmpDTO implements Serializable {

    private String llave;
    private Long secInt;
    private Long sucursal;
    private Long cuenta;

    public Estatus15TmpDTO(String llave, Long secInt, Long sucursal, Long cuenta){
        this.llave = llave;
        this.secInt = secInt;
        this.sucursal = sucursal;
        this.cuenta = cuenta;
    }

    public String getLlave() {
        return llave;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }

    public Long getSecInt() {
        return secInt;
    }

    public void setSecInt(Long secInt) {
        this.secInt = secInt;
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
