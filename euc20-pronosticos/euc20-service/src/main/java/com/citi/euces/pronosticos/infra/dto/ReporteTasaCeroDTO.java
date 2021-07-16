package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;
import java.util.Date;

public class ReporteTasaCeroDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String areaOrigen;	 
	private Float costoOperativo;
	private Float iva;
	private String sucursal;
	private String cuenta;
	private String numCliente;
	private String nombreNegocio;
	private String claveCA;
	private String concepto;
	private String fechadeTX;
	private String fechaAplicacion;
	private String status;
	private String proceso;
	private String tipodeMoneda;
	private Integer sirh;
	private Integer clienteAcreedorComision;
	private String producto;
	private Date inicio;
	private Date fin;
	private String periodoCobro;
	
	public String getAreaOrigen() {
		return areaOrigen;
	}
	public void setAreaOrigen(String areaOrigen) {
		this.areaOrigen = areaOrigen;
	}
	public Float getCostoOperativo() {
		return costoOperativo;
	}
	public void setCostoOperativo(Float costoOperativo) {
		this.costoOperativo = costoOperativo;
	}
	public Float getIva() {
		return iva;
	}
	public void setIva(Float iva) {
		this.iva = iva;
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
	public String getNumCliente() {
		return numCliente;
	}
	public void setNumCliente(String numCliente) {
		this.numCliente = numCliente;
	}
	public String getNombreNegocio() {
		return nombreNegocio;
	}
	public void setNombreNegocio(String nombreNegocio) {
		this.nombreNegocio = nombreNegocio;
	}
	public String getClaveCA() {
		return claveCA;
	}
	public void setClaveCA(String claveCA) {
		this.claveCA = claveCA;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getFechadeTX() {
		return fechadeTX;
	}
	public void setFechadeTX(String fechadeTX) {
		this.fechadeTX = fechadeTX;
	}
	public String getFechaAplicacion() {
		return fechaAplicacion;
	}
	public void setFechaAplicacion(String fechaAplicacion) {
		this.fechaAplicacion = fechaAplicacion;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProceso() {
		return proceso;
	}
	public void setProceso(String proceso) {
		this.proceso = proceso;
	}
	public String getTipodeMoneda() {
		return tipodeMoneda;
	}
	public void setTipodeMoneda(String tipodeMoneda) {
		this.tipodeMoneda = tipodeMoneda;
	}
	public Integer getSirh() {
		return sirh;
	}
	public void setSirh(Integer sirh) {
		this.sirh = sirh;
	}
	public Integer getClienteAcreedorComision() {
		return clienteAcreedorComision;
	}
	public void setClienteAcreedorComision(Integer clienteAcreedorComision) {
		this.clienteAcreedorComision = clienteAcreedorComision;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public Date getInicio() {
		return inicio;
	}
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}
	public Date getFin() {
		return fin;
	}
	public void setFin(Date fin) {
		this.fin = fin;
	}
	public String getPeriodoCobro() {
		return periodoCobro;
	}
	public void setPeriodoCobro(String periodoCobro) {
		this.periodoCobro = periodoCobro;
	}
}