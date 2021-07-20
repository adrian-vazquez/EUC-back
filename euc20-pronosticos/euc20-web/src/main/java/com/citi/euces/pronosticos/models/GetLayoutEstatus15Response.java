package com.citi.euces.pronosticos.models;

import com.citi.euces.pronosticos.infra.dto.GetLayoutEstatus15DTO;
import com.citi.euces.pronosticos.infra.dto.MensajeDTO;

import java.io.Serializable;

public class GetLayoutEstatus15Response implements Serializable {

    private static final long serialVersionUID = 1L;
    private GetLayoutEstatus15DTO getLayoutEstatus15DTO;
    private String code;

    public GetLayoutEstatus15Response(GetLayoutEstatus15DTO getLayoutEstatus15DTO, String code) {
        this.getLayoutEstatus15DTO = getLayoutEstatus15DTO;
        this.code = code;
    }

    public GetLayoutEstatus15DTO getGetLayoutEstatus15DTO() {
        return getLayoutEstatus15DTO;
    }

    public void setGetLayoutEstatus15DTO(GetLayoutEstatus15DTO getLayoutEstatus15DTO) {
        this.getLayoutEstatus15DTO = getLayoutEstatus15DTO;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
