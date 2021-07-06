package com.citi.euces.pronosticos.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PPC_PCB_TARIFAS")
public class Tarifas implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "NUM_CLIENTE")
	private Long numCliente;
	
	@Column(name = "TARIFA_TX_BE")
	private Double tarifaTxBe;
	
	@Column(name = "TARIFA_TX_SUC")
	private Double tarifaTxSuc;
	
	@Column(name = "TARIFA_MENSUAL")
	private Double tarifaMensual;
	
	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "ID")
	private Long id;

	public Long getNumCliente() {
		return numCliente;
	}

	public void setNumCliente(Long numCliente) {
		this.numCliente = numCliente;
	}

	public Double getTarifaTxBe() {
		return tarifaTxBe;
	}

	public void setTarifaTxBe(Double tarifaTxBe) {
		this.tarifaTxBe = tarifaTxBe;
	}

	public Double getTarifaTxSuc() {
		return tarifaTxSuc;
	}

	public void setTarifaTxSuc(Double tarifaTxSuc) {
		this.tarifaTxSuc = tarifaTxSuc;
	}

	public Double getTarifaMensual() {
		return tarifaMensual;
	}

	public void setTarifaMensual(Double tarifaMensual) {
		this.tarifaMensual = tarifaMensual;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
