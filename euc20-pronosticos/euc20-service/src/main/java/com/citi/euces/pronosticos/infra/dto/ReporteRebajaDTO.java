package com.citi.euces.pronosticos.infra.dto;

import org.springframework.data.domain.Page;

import java.io.Serializable;

public class ReporteRebajaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Page<ReporteRebajaPageDTO> reporteRebajaPageDTO;
    private String file;

    public Page<ReporteRebajaPageDTO> getReporteRebajaPageDTO() {
        return reporteRebajaPageDTO;
    }

    public void setReporteRebajaPageDTO(Page<ReporteRebajaPageDTO> reporteRebajaPageDTO) {
        this.reporteRebajaPageDTO = reporteRebajaPageDTO;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
