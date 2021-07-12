package com.citi.euces.pronosticos.services.api;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import org.hibernate.exception.SQLGrammarException;

import com.citi.euces.pronosticos.infra.dto.CobuDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;

public interface CobuService {

	public CobuDTO limpiarCobu() throws GenericException;
	
	public CobuDTO cargaCtasCobu(String file) throws GenericException, IOException, ParseException;
	
	public CobuDTO cargaCtasVirt(String file) throws GenericException, IOException, ParseException;
	
	public CobuDTO cargaTxsCtas(String file) throws GenericException, IOException, ParseException;
	
	public CobuDTO cargaTarEspCobu(String file) throws GenericException, IOException, ParseException;
	
	public CobuDTO procesoCobu() throws GenericException, IOException, ParseException,SQLException;
	
	/*public CobuDTO reporte() throws GenericException;
	
	public CobuDTO cifrasControl() throws GenericException;**/
}
