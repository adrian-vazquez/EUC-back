package com.citi.euces.pronosticos.models;

import java.io.Serializable;

public class ArchivoRebajaProteccionResquest implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private String file;
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	
}
