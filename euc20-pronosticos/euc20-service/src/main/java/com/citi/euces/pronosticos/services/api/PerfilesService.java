package com.citi.euces.pronosticos.services.api;

import java.io.IOException;
import java.text.ParseException;

import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;

public interface PerfilesService {

	MensajeDTO ImpReporteCobro(String fechaCobro) throws GenericException, IOException, ParseException;

}
