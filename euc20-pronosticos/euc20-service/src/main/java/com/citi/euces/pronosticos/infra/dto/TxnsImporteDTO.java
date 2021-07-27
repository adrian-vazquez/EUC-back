package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class TxnsImporteDTO implements Serializable{

	private Long numCliente;
	private Long numCuenta;
	private String tipo;
	private Double sumImpTrans;
	
	public TxnsImporteDTO(Long numCliente, Long numCuenta, String tipo, Double sumImpTrans) {
		super();
		this.numCliente = numCliente;
		this.numCuenta = numCuenta;
		this.tipo = tipo;
		this.sumImpTrans = sumImpTrans;
	}
	
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
	public Double getSumImpTrans() {
		return sumImpTrans;
	}
	public void setSumImpTrans(Double sumImpTrans) {
		this.sumImpTrans = sumImpTrans;
	}
}
