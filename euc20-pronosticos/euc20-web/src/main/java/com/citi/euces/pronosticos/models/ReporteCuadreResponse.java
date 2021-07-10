package com.citi.euces.pronosticos.models;

import com.citi.euces.pronosticos.infra.dto.ReporteCuadreDTO;

import java.io.Serializable;
import java.util.List;

public class ReporteCuadreResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<ReporteCuadreDTO> reporteCuadreDTO;
    private String code;

    public ReporteCuadreResponse(List<ReporteCuadreDTO> reporteCuadreDTO, String code) {
        this.reporteCuadreDTO = reporteCuadreDTO;
        this.code = code;
    }

    public List<ReporteCuadreDTO> getReporteCuadreDTO() {
        return reporteCuadreDTO;
    }

    public void setReporteCuadreDTO(List<ReporteCuadreDTO> reporteCuadreDTO) {
        this.reporteCuadreDTO = reporteCuadreDTO;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
