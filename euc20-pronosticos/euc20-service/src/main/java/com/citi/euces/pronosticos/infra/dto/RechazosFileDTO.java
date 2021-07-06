package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;
import java.util.Date;

public class RechazosFileDTO implements Serializable {

	
	private Integer id;
	private Integer cliente;
	private String blanco;
	private Integer cuenta;
	private Double importe;
	private Integer iva;
	private String status; 
	private String mes;
	private Integer anio;
	private String servicio;
	private String csi;
	private Double com;
	private Double comSinIva;
	private Double ivaa;
	private Double total;
	private Integer tipoComision;
	private String llave;
	private String eje;
	private String catalogada;
	private String secuencial;
	private Date fecha;
	private String concepto;
	private String leyenda;
	private Integer dias;
	private Integer idServicio;
	private Integer idOndemand;
	private Integer evaluacionVirtual;
	private String openItem;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCliente() {
		return cliente;
	}
	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}
	public String getBlanco() {
		return blanco;
	}
	public void setBlanco(String blanco) {
		this.blanco = blanco;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public Double getCom() {
		return com;
	}
	public void setCom(Double com) {
		this.com = com;
	}
	public Double getComSinIva() {
		return comSinIva;
	}
	public void setComSinIva(Double comSinIva) {
		this.comSinIva = comSinIva;
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
	public Integer getTipoComision() {
		return tipoComision;
	}
	public void setTipoComision(Integer tipoComision) {
		this.tipoComision = tipoComision;
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
	public String getSecuencial() {
		return secuencial;
	}
	public void setSecuencial(String secuencial) {
		this.secuencial = secuencial;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getLeyenda() {
		return leyenda;
	}
	public void setLeyenda(String leyenda) {
		this.leyenda = leyenda;
	}
	public Integer getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(Integer idServicio) {
		this.idServicio = idServicio;
	}
	public Integer getIdOndemand() {
		return idOndemand;
	}
	public void setIdOndemand(Integer idOndemand) {
		this.idOndemand = idOndemand;
	}
	public Integer getEvaluacionVirtual() {
		return evaluacionVirtual;
	}
	public void setEvaluacionVirtual(Integer evaluacionVirtual) {
		this.evaluacionVirtual = evaluacionVirtual;
	}
	public String getOpenItem() {
		return openItem;
	}
	public void setOpenItem(String openItem) {
		this.openItem = openItem;
	}
	public Integer getCuenta() {
		return cuenta;
	}
	public void setCuenta(Integer cuenta) {
		this.cuenta = cuenta;
	}
	public Integer getDias() {
		return dias;
	}
	public void setDias(Integer dias) {
		this.dias = dias;
	}
	
	
}
