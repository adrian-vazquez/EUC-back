package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;
import java.util.Date;

public class CtasVirtualesDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long numCliente;
	private Long numCuenta;
	private Date fecAlta;
	private Integer cuentasX;
	private String nombre;
	private Integer id;
	
	public Long getNumCliente() {
		return numCliente;
	}
	public void setNumCliente(Long numCliente) {
		this.numCliente = numCliente;
	}
	public Long getNumCuenta() {
		return numCuenta;
	}
	public void setNumCuenta(Long numCuenta) {
		this.numCuenta = numCuenta;
	}
	public Date getFecAlta() {
		return fecAlta;
	}
	public void setFecAlta(Date fecAlta) {
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
