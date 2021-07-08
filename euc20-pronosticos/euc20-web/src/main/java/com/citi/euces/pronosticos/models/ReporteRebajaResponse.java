package com.citi.euces.pronosticos.models;

import com.citi.euces.pronosticos.infra.dto.ReporteRebajaDTO;

import java.io.Serializable;

public class ReporteRebajaResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private ReporteRebajaDTO reporteRebajaDTO;
    private String code;

    public ReporteRebajaResponse(ReporteRebajaDTO reporteRebajaDTO, String code) {
        this.reporteRebajaDTO = reporteRebajaDTO;
        this.code = code;
    }

    public ReporteRebajaDTO getReporteRebajaDTO() {
        return reporteRebajaDTO;
    }

    public void setReporteRebajaDTO(ReporteRebajaDTO reporteRebajaDTO) {
        this.reporteRebajaDTO = reporteRebajaDTO;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
