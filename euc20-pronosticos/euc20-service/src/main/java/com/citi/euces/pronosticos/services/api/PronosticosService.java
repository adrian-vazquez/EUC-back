package com.citi.euces.pronosticos.services.api;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.hibernate.exception.SQLGrammarException;

import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;

public interface PronosticosService {

	MensajeDTO limpiarPronosticos() throws GenericException, SQLGrammarException;
	
	MensajeDTO cargarRechazos(String file, Integer diasProteccion, boolean extraCont, boolean cobEspecial) throws GenericException, IOException, ParseException;
	
	MensajeDTO cargarRespuestas(String file) throws GenericException, IOException, ParseException;
	
	MensajeDTO cargaArchivoRebaja(String file) throws GenericException, IOException, ParseException;
	
	public MensajeDTO generaArchivoProteccion(Integer secuArch, Date fechaCarga, Integer cuentaAlterna, String posNopos) throws GenericException;
	
	MensajeDTO borrarArchivoProteccion () throws GenericException; 
}
