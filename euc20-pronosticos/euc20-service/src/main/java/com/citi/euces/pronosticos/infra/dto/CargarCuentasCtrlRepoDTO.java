package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class CargarCuentasCtrlRepoDTO implements Serializable {

    private Long consecutivo;
    private Long noCliente;
    private String blanco;
    private String cuenta;
    private Double importe;
    private Double iva;
    private String CausaRechazo;
    private String mes;
    private Integer anio;
    private String servicio;
    private String csi;
    private Float com;
    private Float comisionSinIva;
    private Float ivaa;
    private Float total;
    private Float comP;
    private String llave;
    private String eje;
    private String catalogada;
    private String etiqueta;
    
    public Long getConsecutivo() {
        return consecutivo;
    }
    
    public void setConsecutivo(Long consecutivo) {
        this.consecutivo = consecutivo;
    }
    
    public Long getNoCliente() {
        return noCliente;
    }
    
    public void setNoCliente(Long noCliente) {
        this.noCliente = noCliente;
    }
    
    public String getBlanco() {
        return blanco;
    }
    
    public void setBlanco(String blanco) {
        this.blanco = blanco;
    }
    
    public String getCuenta() {
        return cuenta;
    }
    
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }
    
    public Double getImporte() {
        return importe;
    }
    
    public void setImporte(Double importe) {
        this.importe = importe;
    }
    
    public Double getIva() {
        return iva;
    }
    
    public void setIva(Double iva) {
        this.iva = iva;
    }
    
    public String getCausaRechazo() {
        return CausaRechazo;
    }
    
    public void setCausaRechazo(String causaRechazo) {
        CausaRechazo = causaRechazo;
    }
    
    public String getMes() {
        return mes;
    }
    
    public void setMes(String mes) {
        this.mes = mes;
    }
    
    public Integer getAnio() {
        return anio;
    }
    
    public void setAnio(Integer anio) {
        this.anio = anio;
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
    
    public Float getCom() {
        return com;
    }
    
    public void setCom(Float com) {
        this.com = com;
    }
    
    public Float getComisionSinIva() {
        return comisionSinIva;
    }
    
    public void setComisionSinIva(Float comisionSinIva) {
        this.comisionSinIva = comisionSinIva;
    }
    
    public Float getIvaa() {
        return ivaa;
    }
    
    public void setIvaa(Float ivaa) {
        this.ivaa = ivaa;
    }
    
    public Float getTotal() {
        return total;
    }
    
    public void setTotal(Float total) {
        this.total = total;
    }
    
    public Float getComP() {
        return comP;
    }
    
    public void setComP(Float comP) {
        this.comP = comP;
    }
    
    public String getLlave() {
        return llave;
    }
    
    public void setLlave(String llave) {
        this.llave = llave;
    }
    
    public String getEje() {
        return eje;
    }
    
    public void setEje(String eje) {
        this.eje = eje;
    }
    
    public String getCatalogada() {
        return catalogada;
    }
    
    public void setCatalogada(String catalogada) {
        this.catalogada = catalogada;
    }
    
    public String getEtiqueta() {
        return etiqueta;
    }
    
    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
}
