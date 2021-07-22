package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class TarifasDTO implements Serializable{

	private Long numCliente;
	private Double tarifaTxBe;
	private Double tarifaTxSuc;
	private Double tarifaMensual;
	
	public TarifasDTO(Long numCliente, Double tarifaTxBe, Double tarifaTxSuc, Double tarifaMensual) {
		super();
		this.numCliente = numCliente;
		this.tarifaTxBe = tarifaTxBe;
		this.tarifaTxSuc = tarifaTxSuc;
		this.tarifaMensual = tarifaMensual;
	}
	
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
}
