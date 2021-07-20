package com.citi.euces.pronosticos.services.api;

import com.citi.euces.pronosticos.infra.dto.ClientesEstatus15DTO;
import com.citi.euces.pronosticos.infra.dto.GetLayoutEstatus15DTO;
import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;

import java.util.List;


public interface Estatus15Service {
	
    MensajeDTO limpiarEstatus() throws GenericException;

    List<ClientesEstatus15DTO> buscarClientes(Long cliente, Integer mes1, Integer anio1, Integer mes2, Integer anio2, Integer mes3, Integer anio3, Integer mes4, Integer anio4, Integer mes5, Integer anio5, Integer mes6, Integer anio6 ) throws GenericException;

    MensajeDTO agregarALista(String listaLlaves, String sucursal, String cuentas, Integer dias, String secuencialArch) throws GenericException;

    GetLayoutEstatus15DTO generarArchivo(Integer diasProteccion, Integer secuencialArch, Integer sucursal, Integer cuenta, Integer dias) throws GenericException;
}
