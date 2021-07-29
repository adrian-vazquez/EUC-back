package com.citi.euces.pronosticos.services.api;

import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;

import java.io.IOException;
import java.text.ParseException;

public interface IRepoService {
	
   String getLayoutProteccion()throws  GenericException;
   
   MensajeDTO cargarCuentas(String file, String fecha) throws GenericException, IOException, ParseException;
   
   
   
}
