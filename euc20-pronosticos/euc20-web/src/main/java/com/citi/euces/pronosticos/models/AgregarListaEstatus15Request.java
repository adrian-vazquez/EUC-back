package com.citi.euces.pronosticos.models;

public class AgregarListaEstatus15Request {
    private String listaLlaves;
    private String sucursal;
    private String cuenta;
    private Integer dias;
    private String secuencialArch;

    public String getSecuencialArch() {
        return secuencialArch;
    }

    public void setSecuencialArch(String secuencialArch) {
        this.secuencialArch = secuencialArch;
    }

    public String getListaLlaves() {
        return listaLlaves;
    }

    public void setListaLlaves(String listaLlaves) {
        this.listaLlaves = listaLlaves;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }
}
