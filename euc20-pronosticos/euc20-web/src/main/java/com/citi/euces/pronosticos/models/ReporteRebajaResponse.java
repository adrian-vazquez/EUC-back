package com.citi.euces.pronosticos.models;

import com.citi.euces.pronosticos.infra.dto.ReporteRebajaDTO;
import org.springframework.data.domain.Page;

import java.io.Serializable;

public class ReporteRebajaResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private Page<ReporteRebajaDTO> reporteRebajaDTO;
    private String code;

    public ReporteRebajaResponse(Page<ReporteRebajaDTO> reporteRebajaDTO, String code) {
        this.reporteRebajaDTO = reporteRebajaDTO;
        this.code = code;
    }

    public Page<ReporteRebajaDTO> getReporteRebajaDTO() {
        return reporteRebajaDTO;
    }

    public void setReporteRebajaDTO(Page<ReporteRebajaDTO> reporteRebajaDTO) {
        this.reporteRebajaDTO = reporteRebajaDTO;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
