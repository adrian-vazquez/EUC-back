package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class BancaDTO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer noCliente;
	private Integer banca;
	
	
	
	public BancaDTO(Integer noCliente, Integer banca) {
		super();
		this.noCliente = noCliente;
		this.banca = banca;
	}
	public Integer getNoCliente() {
		return noCliente;
	}
	public void setNoCliente(Integer noCliente) {
		this.noCliente = noCliente;
	}
	public Integer getBanca() {
		return banca;
	}
	public void setBanca(Integer banca) {
		this.banca = banca;
	}
	
	
}
