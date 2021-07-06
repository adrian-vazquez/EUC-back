package com.citi.euces.pronosticos.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "PPC_PCB_CIFRAS_CONTROL")
public class CifrasControl implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Size(min = 1, max = 255)
	@Column(name = "CONSULTA")
	private String consulta;

	@Size(min = 1, max = 10)
	@Column(name = "CIFRA")
	private String cifra;
	
	@Size(min = 1, max = 45)
	@Column(name = "PROCESO")
	private String proceso;
	
	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "ID")
	private Long id;

	
	public String getConsulta() {
		return consulta;
	}

	public void setConsulta(String consulta) {
		this.consulta = consulta;
	}

	public String getCifra() {
		return cifra;
	}

	public void setCifra(String cifra) {
		this.cifra = cifra;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
