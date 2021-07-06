package com.citi.euces.pronosticos.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PPC_PCB_TXNS_IMPORTE")
public class TxnsImporte implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "NUM_CLIENTE")
	private Long numCliente;
	
	@Column(name = "NUM_CUENTA")
	private Long numCuenta;
	
	@Column(name = "TIPO")
	private String tipo;
	
	@Column(name = "TXNS")
	private Long txns;
	
	@Column(name = "SUM_IMP_TRANS")
	private Double sumImpTrans;
	
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

	public Long getTxns() {
		return txns;
	}

	public void setTxns(Long txns) {
		this.txns = txns;
	}

	public Double getSumImpTrans() {
		return sumImpTrans;
	}

	public void setSumImpTrans(Double sumImpTrans) {
		this.sumImpTrans = sumImpTrans;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
