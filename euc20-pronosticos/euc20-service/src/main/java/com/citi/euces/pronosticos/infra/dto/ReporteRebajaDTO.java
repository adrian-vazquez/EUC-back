package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class ReporteRebajaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer numeroCLiente;
    private String banco;
    private Integer cuenta;
    private String estatus;
    private Double importe;
    private Integer iva;
    private String causaRechazo;
    private String mes;
    private Integer year;
    private String servicio;
    private String csi;
    private String com;
    private Integer comisionSinIva;
    private Double ivaa;
    private Double total;
    private Integer comP;
    private String llave;
    private String numeroProteccion;
    private String franquicia;
    private String catalogada;
    private String fechaReal;
    private String openItem;
    private String fechaContable;
    private String cuentaProducto;
    private String contrato;


    public Integer getNumeroCLiente() {
        return numeroCLiente;
    }

    public void setNumeroCLiente(Integer numeroCLiente) {
        this.numeroCLiente = numeroCLiente;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Integer getIva() {
        return iva;
    }

    public void setIva(Integer iva) {
        this.iva = iva;
    }

    public String getCausaRechazo() {
        return causaRechazo;
    }

    public void setCausaRechazo(String causaRechazo) {
        this.causaRechazo = causaRechazo;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getCsi() {
        return csi;
    }

    public void setCsi(String csi) {
        this.csi = csi;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public Integer getComisionSinIva() {
        return comisionSinIva;
    }

    public void setComisionSinIva(Integer comisionSinIva) {
        this.comisionSinIva = comisionSinIva;
    }

    public Double getIvaa() {
        return ivaa;
    }

    public void setIvaa(Double ivaa) {
        this.ivaa = ivaa;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getComP() {
        return comP;
    }

    public void setComP(Integer comP) {
        this.comP = comP;
    }

    public String getLlave() {
        return llave;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }

    public String getNumeroProteccion() {
        return numeroProteccion;
    }

    public void setNumeroProteccion(String numeroProteccion) {
        this.numeroProteccion = numeroProteccion;
    }

    public String getFranquicia() {
        return franquicia;
    }

    public void setFranquicia(String franquicia) {
        this.franquicia = franquicia;
    }

    public String getCatalogada() {
        return catalogada;
    }

    public void setCatalogada(String catalogada) {
        this.catalogada = catalogada;
    }

    public String getFechaReal() {
        return fechaReal;
    }

    public void setFechaReal(String fechaReal) {
        this.fechaReal = fechaReal;
    }

    public String getOpenItem() {
        return openItem;
    }

    public void setOpenItem(String openItem) {
        this.openItem = openItem;
    }

    public String getFechaContable() {
        return fechaContable;
    }

    public void setFechaContable(String fechaContable) {
        this.fechaContable = fechaContable;
    }

    public String getCuentaProducto() {
        return cuentaProducto;
    }

    public void setCuentaProducto(String cuentaProducto) {
        this.cuentaProducto = cuentaProducto;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }
}
