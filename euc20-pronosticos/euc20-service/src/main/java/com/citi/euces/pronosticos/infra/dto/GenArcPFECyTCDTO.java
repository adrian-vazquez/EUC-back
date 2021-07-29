package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class GenArcPFECyTCDTO  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer conteo;

	public GenArcPFECyTCDTO(Integer conteo) {
		super();
		this.conteo = conteo;
	}

	/**
	 * @return the conteo
	 */
	public Integer getConteo() {
		return conteo;
	}

	/**
	 * @param conteo the conteo to set
	 */
	public void setConteo(Integer conteo) {
		this.conteo = conteo;
	}
	
	
}
