package com.citi.euces.pronosticos.models;

import java.io.Serializable;

import com.citi.euces.pronosticos.infra.dto.LayoutPrevioDTO;
import com.citi.euces.pronosticos.infra.dto.ReporteLayoutPrevioDTO;


public class LayoutPrevioResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private ReporteLayoutPrevioDTO reporteLayoutPrevioDTO;
    private String code;
    
    
	
    public LayoutPrevioResponse(ReporteLayoutPrevioDTO reporteLayoutPrevioDTO, String code) {
		this.reporteLayoutPrevioDTO=reporteLayoutPrevioDTO;
		this.code=code;		
	}

    public ReporteLayoutPrevioDTO getReporteLayoutPrevioDTO() {
		return reporteLayoutPrevioDTO;
	}


	public void setReporteLayoutPrevioDTO(ReporteLayoutPrevioDTO reporteLayoutPrevioDTO) {
		this.reporteLayoutPrevioDTO = reporteLayoutPrevioDTO;
	}

	public String getCode() {
		return code;
	}
	
    public void setCode(String code) {
		this.code = code;
	}   
}