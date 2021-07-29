package com.citi.euces.pronosticos.models;

import java.io.Serializable;

public class InsertarPerfilesRequest implements Serializable{
	
	private String dias;
	private String secuencial;
	
	public String getDias() {
		return dias;
	}
	public void setDias(String dias) {
		this.dias = dias;
	}
	public String getSecuencial() {
		return secuencial;
	}
	public void setSecuencial(String secuencial) {
		this.secuencial = secuencial;
	}
}
