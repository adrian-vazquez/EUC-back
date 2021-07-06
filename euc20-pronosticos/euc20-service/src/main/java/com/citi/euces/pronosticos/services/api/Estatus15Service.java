package com.citi.euces.pronosticos.services.api;

import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;


public interface Estatus15Service {
	
    MensajeDTO limpiarEstatus() throws GenericException;

}
