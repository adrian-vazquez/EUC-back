package com.citi.euces.pronosticos.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class MaestroDeComisionesId implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "LLAVE_TEMPORAL")
    private Long llaveTemporal;
    @NotNull
    @Column(name = "NO_CLIENTE")
    private Long noCliente;
    @NotNull
    @Column(name = "CHEQUERA")
    private String chequera;
    @NotNull
    @Column(name = "ID_SERVICIO")
    private Long idServicio;
    @NotNull
    @Column(name = "ID_ONDEMAND")
    private Long idOndemand;
    @NotNull
    @Column(name = "MES")
    private Integer mes;
    @NotNull
    @Column(name = "ANIO")
    private Integer anio;
    @NotNull
    @Column(name = "M_TOTAL")
    private Double mTotal;
    @NotNull
    @Column(name = "ID_ESTATUS_COMISION")
    private Long idEstatusComision;
    @NotNull
    @Column(name = "LLAVE")
    private Long llave;
    @NotNull
    @Column(name = "CATALOGADA_GC")
    private String catalogadaGc;

    public Long getLlaveTemporal() {
        return llaveTemporal;
    }

    public void setLlaveTemporal(Long llaveTemporal) {
        this.llaveTemporal = llaveTemporal;
    }

    public Long getNoCliente() {
        return noCliente;
    }

    public void setNoCliente(Long noCliente) {
        this.noCliente = noCliente;
    }

    public String getChequera() {
        return chequera;
    }

    public void setChequera(String chequera) {
        this.chequera = chequera;
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

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Double getmTotal() {
        return mTotal;
    }

    public void setmTotal(Double mTotal) {
        this.mTotal = mTotal;
    }

    public Long getIdEstatusComision() {
        return idEstatusComision;
    }

    public void setIdEstatusComision(Long idEstatusComision) {
        this.idEstatusComision = idEstatusComision;
    }

    public Long getLlave() {
        return llave;
    }

    public void setLlave(Long llave) {
        this.llave = llave;
    }

    public String getCatalogadaGc() {
        return catalogadaGc;
    }

    public void setCatalogadaGc(String catalogadaGc) {
        this.catalogadaGc = catalogadaGc;
    }
}
