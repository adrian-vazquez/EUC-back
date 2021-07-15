package com.citi.euces.pronosticos.models;

import java.io.Serializable;
import java.util.Date;

public class ArchivoProteccion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer secuArch;
	private Date fechaCarga;
	private Integer cuentaAlterna;
	private String posNoPos;
	
	public Integer getSecuArch() {
		return secuArch;
	}
	public void setSecuArch(Integer secuArch) {
		this.secuArch = secuArch;
	}
	public Date getFechaCarga() {
		return fechaCarga;
	}
	public void setFechaCarga(Date fechaCarga) {
		this.fechaCarga = fechaCarga;
	}
	public Integer getCuentaAlterna() {
		return cuentaAlterna;
	}
	public void setCuentaAlterna(Integer cuentaAlterna) {
		this.cuentaAlterna = cuentaAlterna;
	}
	public String getPosNoPos() {
		return posNoPos;
	}
	public void setPosNoPos(String posNoPos) {
		this.posNoPos = posNoPos;
	}
	
	
}
