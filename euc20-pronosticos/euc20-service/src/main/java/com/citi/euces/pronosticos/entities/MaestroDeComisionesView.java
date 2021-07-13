package com.citi.euces.pronosticos.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "VW_PPC_MIS_SERCH_REPORTE_REBAJA")
public class MaestroDeComisionesView implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private MaestroDeComisionesId id;
    @Column(name = "SUCURSAL")
    private Integer sucursal;
    @Column(name = "CUENTA")
    private Integer cuenta;
    @Column(name = "P_IVA")
    private Integer pIva;
    @Column(name = "M_COMISION")
    private Integer mComision;
    @Column(name = "M_IVA")
    private Integer mIva;
    @Column(name = "M_COM_PARCIAL")
    private Integer mComParcial;
    @Column(name = "M_IVA_PARCIAL")
    private Integer mIvaParcial;
    @Column(name = "ID_CAUSA_RECHAZO")
    private Integer idCausaRechazo;
    @Column(name = "F_MOVIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaMovimiento;
    @Column(name = "F_REGISTRO_CONTABLE")
    private String fechaRegistroContable;
    @Column(name = "CSI")
    private Integer csi;
    @Column(name = "COM_EC")
    private Integer comEc;
    @Column(name = "COM_P")
    private Integer comP;
    @Column(name = "CHEQUERA_CARGO")
    private String chequeraCargo;
    @Column(name = "NO_PROTECCION")
    private String noProteccion;
    @Column(name = "ESTATUS_PROTECCION")
    private Integer estatusProteccion;
    @Column(name = "CONTRATO")
    private String contrato;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "F_INGRESO")
    private Date fechaIngreso;
    @Column(name = "UDMER")
    private Integer udmer;
    @Column(name = "FRANQUICIA_REGISTRO")
    private String franquiciaRegistro;
    @Column(name = "ID_FRANQUICIA")
    private Integer idFranquicia;
    @Column(name = "FEC_VENC_PROTECCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencProteccion;
    @Column(name = "OPEN_ITEM")
    private String openItem;
    @Column(name = "SEARCHDATA")
    private String serch;

    public MaestroDeComisionesId getId() {
        return id;
    }

    public void setId(MaestroDeComisionesId id) {
        this.id = id;
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

    public Integer getpIva() {
        return pIva;
    }

    public void setpIva(Integer pIva) {
        this.pIva = pIva;
    }

    public Integer getmComision() {
        return mComision;
    }

    public void setmComision(Integer mComision) {
        this.mComision = mComision;
    }

    public Integer getmIva() {
        return mIva;
    }

    public void setmIva(Integer mIva) {
        this.mIva = mIva;
    }

    public Integer getmComParcial() {
        return mComParcial;
    }

    public void setmComParcial(Integer mComParcial) {
        this.mComParcial = mComParcial;
    }

    public Integer getmIvaParcial() {
        return mIvaParcial;
    }

    public void setmIvaParcial(Integer mIvaParcial) {
        this.mIvaParcial = mIvaParcial;
    }

    public Integer getIdCausaRechazo() {
        return idCausaRechazo;
    }

    public void setIdCausaRechazo(Integer idCausaRechazo) {
        this.idCausaRechazo = idCausaRechazo;
    }

    public Date getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(Date fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public String getFechaRegistroContable() {
        return fechaRegistroContable;
    }

    public void setFechaRegistroContable(String fechaRegistroContable) {
        this.fechaRegistroContable = fechaRegistroContable;
    }

    public Integer getCsi() {
        return csi;
    }

    public void setCsi(Integer csi) {
        this.csi = csi;
    }

    public Integer getComEc() {
        return comEc;
    }

    public void setComEc(Integer comEc) {
        this.comEc = comEc;
    }

    public Integer getComP() {
        return comP;
    }

    public void setComP(Integer comP) {
        this.comP = comP;
    }

    public String getChequeraCargo() {
        return chequeraCargo;
    }

    public void setChequeraCargo(String chequeraCargo) {
        this.chequeraCargo = chequeraCargo;
    }

    public String getNoProteccion() {
        return noProteccion;
    }

    public void setNoProteccion(String noProteccion) {
        this.noProteccion = noProteccion;
    }

    public Integer getEstatusProteccion() {
        return estatusProteccion;
    }

    public void setEstatusProteccion(Integer estatusProteccion) {
        this.estatusProteccion = estatusProteccion;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getUdmer() {
        return udmer;
    }

    public void setUdmer(Integer udmer) {
        this.udmer = udmer;
    }

    public String getFranquiciaRegistro() {
        return franquiciaRegistro;
    }

    public void setFranquiciaRegistro(String franquiciaRegistro) {
        this.franquiciaRegistro = franquiciaRegistro;
    }

    public Integer getIdFranquicia() {
        return idFranquicia;
    }

    public void setIdFranquicia(Integer idFranquicia) {
        this.idFranquicia = idFranquicia;
    }

    public Date getFechaVencProteccion() {
        return fechaVencProteccion;
    }

    public void setFechaVencProteccion(Date fechaVencProteccion) {
        this.fechaVencProteccion = fechaVencProteccion;
    }

    public String getOpenItem() {
        return openItem;
    }

    public void setOpenItem(String openItem) {
        this.openItem = openItem;
    }

    public String getSerch() {
        return serch;
    }

    public void setSerch(String serch) {
        this.serch = serch;
    }
}
