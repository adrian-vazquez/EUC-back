package com.citi.euces.pronosticos.models;

import java.io.Serializable;

import com.citi.euces.pronosticos.infra.dto.CobuDTO;

public class CobuResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CobuDTO mensaje;
	private String codigo;
	private String archivo;
	
	public CobuResponse(CobuDTO mensaje, String codigo) {
		this.mensaje = mensaje;
		this.codigo = codigo;
	}

	public CobuDTO getMensaje() {
		return mensaje;
	}

	public void setMensaje(CobuDTO mensaje) {
		this.mensaje = mensaje;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
	
	

}
