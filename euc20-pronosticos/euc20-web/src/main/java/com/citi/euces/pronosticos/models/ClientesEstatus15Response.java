package com.citi.euces.pronosticos.models;

import java.util.List;

import org.springframework.data.domain.Page;

import com.citi.euces.pronosticos.infra.dto.ClientesEstatus15DTO;
import com.citi.euces.pronosticos.infra.dto.ReporteRebajaDTO;

public class ClientesEstatus15Response {

	private List<ClientesEstatus15DTO> clientesEstatus15DTO;
    private String code;
    private String mensaje;

    public ClientesEstatus15Response(List<ClientesEstatus15DTO> clientesEstatus15DTO, String code) {
        this.clientesEstatus15DTO = clientesEstatus15DTO;
        this.code = code;
    }

    public ClientesEstatus15Response(String mensaje, String code) {
        this.mensaje = mensaje;
        this.code = code;
    }

    public List<ClientesEstatus15DTO> getClientesEstatus15DTO() {
        return clientesEstatus15DTO;
    }

    public void setClientesEstatus15DTO(List<ClientesEstatus15DTO> clientesEstatus15DTO) {
        this.clientesEstatus15DTO = clientesEstatus15DTO;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
