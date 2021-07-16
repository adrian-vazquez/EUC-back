package com.citi.euces.pronosticos.services.api;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import com.citi.euces.pronosticos.infra.dto.LayoutPrevioDTO;
import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.dto.ReporteLayoutPrevioDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;

public interface PdcMapfreService {
	
	ReporteLayoutPrevioDTO ObtenerLayout(String fecCarga,	Integer dias) throws GenericException, IOException, ParseException;

	MensajeDTO GenerarTasaCero(String file, String fecha) throws GenericException, IOException, ParseException;


}
