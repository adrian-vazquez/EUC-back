package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class CifrasControlDTO implements Serializable{

	private String consulta;
	private int cifra;
	private String proceso;
	private Integer id;
	
	
	
	public CifrasControlDTO(String consulta, int cifra) {
		super();
		this.consulta = consulta;
		this.cifra = cifra;
	}
	
	public CifrasControlDTO() {
	}

	public String getConsulta() {
		return consulta;
	}
	public void setConsulta(String consultas) {
		this.consulta = consultas;
	}
	public int getCifra() {
		return cifra;
	}
	public void setCifra(int cifras) {
		this.cifra = cifras;
	}
	public String getProceso() {
		return proceso;
	}
	
	public void setProceso(String proceso) {
		this.proceso = proceso;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
