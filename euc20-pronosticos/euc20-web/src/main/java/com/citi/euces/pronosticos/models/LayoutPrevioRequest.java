package com.citi.euces.pronosticos.models;

import java.io.Serializable;

public class LayoutPrevioRequest implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String fecCarga;
	private Integer dias;
	
	public String getFecCarga() {
		return fecCarga;
	}
	public void setFecCarga(String fecCarga) {
		this.fecCarga = fecCarga;
	}
	public Integer getDias() {
		return dias;
	}
	public void setDias(Integer dias) {
		this.dias = dias;
	}
	
}