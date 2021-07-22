package com.citi.euces.pronosticos.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class GetLayoutReponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String code;
	private String mensaje;
	@JsonInclude(Include.NON_NULL)
	private String file;
	@JsonInclude(Include.NON_NULL)
	private String excepcion;
	
	public String getExcepcion() {
		return excepcion;
	}
	public void setExcepcion(String excepcion) {
		this.excepcion = excepcion;
	}
	public GetLayoutReponse(){
		
	}
	public GetLayoutReponse(String code, String mensaje,String file) {
		this.code=code;
		this.mensaje=mensaje;
		this.file=file;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	

}
