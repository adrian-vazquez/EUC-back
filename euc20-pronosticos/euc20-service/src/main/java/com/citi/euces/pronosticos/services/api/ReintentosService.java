package com.citi.euces.pronosticos.services.api;

import java.io.IOException;
import java.text.ParseException;

import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;

public interface ReintentosService {
	
	MensajeDTO limpiarTabla() throws GenericException;
	
	MensajeDTO cuentasAltloadFile(String file) throws GenericException, IOException, ParseException;

}
