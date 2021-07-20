package com.citi.euces.pronosticos.models;

import java.io.Serializable;

public class ImpReporteCobroRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String fechaCobro;

	public String getFechaCobro() {
		return fechaCobro;
	}

	public void setFechaCobro(String fechaCobro) {
		this.fechaCobro = fechaCobro;
	}
}