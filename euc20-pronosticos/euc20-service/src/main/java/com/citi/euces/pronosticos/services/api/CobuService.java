package com.citi.euces.pronosticos.services.api;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.hibernate.exception.SQLGrammarException;

import com.citi.euces.pronosticos.infra.dto.CobuDTO;
import com.citi.euces.pronosticos.infra.dto.ReportesCobuDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;

public interface CobuService {

	public CobuDTO limpiarCobu() throws GenericException, SQLGrammarException;
	
	public CobuDTO cargaCtasCobu(String file) throws GenericException, IOException, ParseException;
	
	public CobuDTO cargaCtasVirt(String file) throws GenericException, IOException, ParseException;
	
	public CobuDTO cargaTxsCtas(String file) throws GenericException, IOException, ParseException;
	
	public CobuDTO cargaTarEspCobu(String file) throws GenericException, IOException, ParseException;

	public CobuDTO procesoCobu() throws GenericException, IOException, ParseException, SQLException;
	
	public ReportesCobuDTO reporte() throws GenericException, IOException, ParseException, SQLException, InvalidFormatException;
	
	public ReportesCobuDTO cifrasControl() throws GenericException, IOException, ParseException, SQLException, InvalidFormatException;
}
