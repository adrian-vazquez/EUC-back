package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.poi.xssf.usermodel.XSSFCell;

public class CtasVirtualesDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long numCliente;
	private Long numCuenta;
	private String fecAlta;
	private Long cuentasX;
	private String nombre;
	
	public Long getNumCliente() {
		return numCliente;
	}
	public void setNumCliente(double numCliente) {
		this.numCliente = (long) numCliente;
	}
	public Long getNumCuenta() {
		return numCuenta;
	}
	public void setNumCuenta(double numCuenta) {
		this.numCuenta = (long) numCuenta;
	}
	public String getFecAlta() {
		return fecAlta;
	}
	public void setFecAlta(String fecAlta) {
		this.fecAlta = fecAlta;
	}
	public Long getCuentasX() {
		return cuentasX;
	}
	public void setCuentasX(double cuentasX) {
		this.cuentasX = (long) cuentasX;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}
