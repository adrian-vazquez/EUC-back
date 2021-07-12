package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class CtasVirtualesDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer numCliente;
	private Integer numCuenta;
	private String fecAlta;
	private Integer cuentasX;
	private String nombre;
	private Integer id;
	
	public Integer getNumCliente() {
		return numCliente;
	}
	public void setNumCliente(Integer numCliente) {
		this.numCliente = numCliente;
	}
	public Integer getNumCuenta() {
		return numCuenta;
	}
	public void setNumCuenta(Integer numCuenta) {
		this.numCuenta = numCuenta;
	}
	public String getFecAlta() {
		return fecAlta;
	}
	public void setFecAlta(String fecAlta) {
		this.fecAlta = fecAlta;
	}
	public Integer getCuentasX() {
		return cuentasX;
	}
	public void setCuentasX(Integer cuentasX) {
		this.cuentasX = cuentasX;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
