package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class NumProteccionDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long ID;
	private String numProteccion;

	public NumProteccionDTO(Long iD, String numProteccion) {
		ID = iD;
		this.numProteccion = numProteccion;
	}
	
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public String getNumProteccion() {
		return numProteccion;
	}
	public void setNumProteccion(String numProteccion) {
		this.numProteccion = numProteccion;
	}
	
}
