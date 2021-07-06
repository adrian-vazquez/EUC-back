package com.citi.euces.pronosticos.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PPC_PCB_PROCESADO")
public class Procesado implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "NO_CLIENTE")
	private Long noCliente;
	
	@Column(name = "BE")
	private Double be;
	
	@Column(name = "VENTANILLA")
	private Double ventanilla;
	
	@Column(name = "MENSUALIDAD")
	private Double mensualidad;
	
	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "ID")
	private Long id;

	public Long getNoCliente() {
		return noCliente;
	}

	public void setNoCliente(Long noCliente) {
		this.noCliente = noCliente;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
