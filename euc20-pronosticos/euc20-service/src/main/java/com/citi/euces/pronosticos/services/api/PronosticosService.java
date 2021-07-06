package com.citi.euces.pronosticos.services.api;

import java.io.IOException;
import java.text.ParseException;

import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;

public interface PronosticosService {

	MensajeDTO limpiarPronosticos() throws GenericException;
	
	MensajeDTO cargarRechazos(String file, Integer diasProteccion, boolean extraCont, boolean cobEspecial) throws GenericException, IOException, ParseException;
	
}