package com.citi.euces.pronosticos.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PPC_MIS_CUENTAS_CONTABLES")
public class CuentasContables implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="ROWID")
    String rowid;
    @Column(name = "CUENTA")
    private Long cuenta;
    @Column(name = "PRODUCTO")
    private String producto;
    @Column(name = "MODELO_GESTION")
    private String modeloGestion;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "SERVICIO")
    private String servicio;
    @Column(name = "ID_SERVICIO")
    private Long idServicio;
    @Column(name = "ID_ONDEMAND")
    private Long idOndemand;

    public String getRowid() {
        return rowid;
    }

    public void setRowid(String rowid) {
        this.rowid = rowid;
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
