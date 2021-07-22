package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public final class LayoutMensDTO implements Serializable{

	private Long numCliente;
	private String nombre;
	private int suc;
	private Long cta;
	private Double comMens;
	private Double montoIva;
	private Double montoTotal;
	private int anio;
	private int mes;
	private String producto;
	private Double iva;
	private Double moneda;
	
	public LayoutMensDTO(Long numCliente, String nombre, int suc, Long cta, Double comMens, Double montoIva,
			Double montoTotal, int anio, int mes, String producto, Double iva, Double moneda) {
		super();
		this.numCliente = numCliente;
		this.nombre = nombre;
		this.suc = suc;
		this.cta = cta;
		this.comMens = comMens;
		this.montoIva = montoIva;
		this.montoTotal = montoTotal;
		this.anio = anio;
		this.mes = mes;
		this.producto = producto;
		this.iva = iva;
		this.moneda = moneda;
	}
	public Long getNumCliente() {
		return numCliente;
	}
	public void setNumCliente(Long numCliente) {
		this.numCliente = numCliente;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getSuc() {
		return suc;
	}
	public void setSuc(int suc) {
		this.suc = suc;
	}
	public Long getCta() {
		return cta;
	}
	public void setCta(Long cta) {
		this.cta = cta;
	}
	public Double getComMens() {
		return comMens;
	}
	public void setComMens(Double comMens) {
		this.comMens = comMens;
	}
	public Double getMontoIva() {
		return montoIva;
	}
	public void setMontoIva(Double montoIva) {
		this.montoIva = montoIva;
	}
	public Double getMontoTotal() {
		return montoTotal;
	}
	public void setMontoTotal(Double montoTotal) {
		this.montoTotal = montoTotal;
	}
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public Double getIva() {
		return iva;
	}
	public void setIva(Double iva) {
		this.iva = iva;
	}
	public Double getMoneda() {
		return moneda;
	}
	public void setMoneda(Double moneda) {
		this.moneda = moneda;
	}
}
