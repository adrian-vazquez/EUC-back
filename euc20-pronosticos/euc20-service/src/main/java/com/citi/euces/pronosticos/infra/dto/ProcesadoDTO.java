package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class ProcesadoDTO implements Serializable{

	private Long numCielnte;
	private Double be;
	private Double ventanilla;
	private Double mensualidad;
	private Integer id;
	
	public Long getNumCielnte() {
		return numCielnte;
	}
	public void setNumCielnte(Long numCielnte) {
		this.numCielnte = numCielnte;
	}
	public Double getBe() {
		return be;
	}
	public void setBe(Double be) {
		this.be = be;
	}
	public Double getVentanilla() {
		return ventanilla;
	}
	public void setVentanilla(Double ventanilla) {
		this.ventanilla = ventanilla;
	}
	public Double getMensualidad() {
		return mensualidad;
	}
	public void setMensualidad(Double mensualidad) {
		this.mensualidad = mensualidad;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
