package com.citi.euces.pronosticos.services.api;

import java.io.IOException;
import java.text.ParseException;

import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;

public interface ReintentosService {
	
	MensajeDTO limpiarTabla() throws GenericException;
	
	MensajeDTO cuentasAltloadFile(String file) throws GenericException, IOException, ParseException;

	MensajeDTO genArchPFService(Integer mes1, Integer anio1, Integer dias1, Integer mes2, Integer anio2, Integer dias2,
			Integer mes3, Integer anio3, Integer dias3) throws GenericException, IOException, ParseException;
	
	MensajeDTO genArchECService(Integer mes1, Integer anio1, Integer dias1, Integer mes2, Integer anio2, Integer dias2,
			Integer mes3, Integer anio3, Integer dias3) throws GenericException, IOException, ParseException;
	
	MensajeDTO genArchTCService() throws GenericException, IOException, ParseException;

}
