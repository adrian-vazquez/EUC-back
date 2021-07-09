package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class CobuDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String mensajeConfirm;
	private String procesoResultado;

	public String getMensajeConfirm() {
		return mensajeConfirm;
	}

	public void setMensajeConfirm(String mensajeConfirm) {
		this.mensajeConfirm = mensajeConfirm;
	}
	
	public String getProcesoResultado() {
		return procesoResultado;
	}

	public void setProcesoResultado(String procesoResultado) {
		this.procesoResultado = procesoResultado;
	}

	
}
