package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class CifrasControlDTO implements Serializable{

	private String consulta;
	private String cifra;
	private final String proceso = "COBU";
	private Integer id;
	
	public String getConsulta() {
		return consulta;
	}
	public void setConsulta(String consultas) {
		this.consulta = consultas;
	}
	public String getCifra() {
		return cifra;
	}
	public void setCifra(String cifras) {
		this.cifra = cifras;
	}
	public String getProceso() {
		return proceso;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
