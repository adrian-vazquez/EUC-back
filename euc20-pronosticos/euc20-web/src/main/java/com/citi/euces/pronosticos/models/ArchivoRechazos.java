package com.citi.euces.pronosticos.models;

import java.io.Serializable;

public class ArchivoRechazos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String file;
	private Integer diasProteccion;
	private boolean extraCont;
	private boolean cobEspe;

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Integer getDiasProteccion() {
		return diasProteccion;
	}

	public void setDiasProteccion(Integer diasProteccion) {
		this.diasProteccion = diasProteccion;
	}

	public boolean isExtraCont() {
		return extraCont;
	}

	public void setExtraCont(boolean extraCont) {
		this.extraCont = extraCont;
	}

	public boolean isCobEspe() {
		return cobEspe;
	}

	public void setCobEspe(boolean cobEspe) {
		this.cobEspe = cobEspe;
	}
	
	
	
}
