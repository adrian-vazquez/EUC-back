package com.citi.euces.pronosticos.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "PPC_PCB_LAYOUT_MENS")
public class LayoutMens implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "NUM_CLIENTE")
	private Long numCliente;
	
	@Size(min = 1, max = 55)
	@Column(name = "NOMBRE")
	private String nombre;

	@Column(name = "SUC")
	private Integer suc;
	
	@Column(name = "CTA")
	private Long cta;
	
	@Column(name = "COM_MENS")
	private Double comMens;
	
	@Column(name = "MONTO_IVA")
	private Double montoIva;
	
	@Column(name = "MONTO_TOTAL")
	private Double montoTotal;
	
	@Column(name = "ANIO")
	private Integer anio;
	
	@Column(name = "MES")
	private Integer mes;
	
	@Column(name = "PRODUCTO")
	private String producto;
	
	@Column(name = "IVA")
	private Double iva;
	
	@Column(name = "MONEDA")
	private Double moneda;
	
	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "ID")
	private Long id;

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

	public Integer getSuc() {
		return suc;
	}

	public void setSuc(Integer suc) {
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

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}









