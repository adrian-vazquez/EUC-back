package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class CuentasContablesDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long cuenta;
    private String producto;
    private String modeloGestion;
    private String descripcion;
    private String servicio;
    private Long idServicio;
    private Long idOndemand;

    public CuentasContablesDTO() { }

    public CuentasContablesDTO(Long cuenta, String producto, String modeloGestion, String descripcion, String servicio, Long idServicio, Long idOndemand) {
        this.cuenta = cuenta;
        this.producto = producto;
        this.modeloGestion = modeloGestion;
        this.descripcion = descripcion;
        this.servicio = servicio;
        this.idServicio = idServicio;
        this.idOndemand = idOndemand;
    }

    public Long getCuenta() {
        return cuenta;
    }

    public void setCuenta(Long cuenta) {
        this.cuenta = cuenta;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getModeloGestion() {
        return modeloGestion;
    }

    public void setModeloGestion(String modeloGestion) {
        this.modeloGestion = modeloGestion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public Long getIdOndemand() {
        return idOndemand;
    }

    public void setIdOndemand(Long idOndemand) {
        this.idOndemand = idOndemand;
    }
}
