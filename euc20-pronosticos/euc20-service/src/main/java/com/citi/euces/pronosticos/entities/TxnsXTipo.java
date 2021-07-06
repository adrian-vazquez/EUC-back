package com.citi.euces.pronosticos.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "PPC_PCB_TXNS_X_TIPO")
public class TxnsXTipo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "NUM_CLIENTE")
	private Long numCliente;
	
	@Column(name = "NUM_CUENTA")
	private Long numCuenta;
	
	@Size(min = 1, max = 20)
	@Column(name = "TIPO")
	private String tipo;
	
	@Column(name = "CTA_NUM_AUT_TRANS")
	private Long ctaNumAutTrans;

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

	public Long getNumCuenta() {
		return numCuenta;
	}

	public void setNumCuenta(Long numCuenta) {
		this.numCuenta = numCuenta;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getCtaNumAutTrans() {
		return ctaNumAutTrans;
	}

	public void setCtaNumAutTrans(Long ctaNumAutTrans) {
		this.ctaNumAutTrans = ctaNumAutTrans;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
