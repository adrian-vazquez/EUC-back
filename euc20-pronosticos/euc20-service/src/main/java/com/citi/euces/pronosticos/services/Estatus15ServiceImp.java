package com.citi.euces.pronosticos.services;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.services.api.Estatus15Service;

@Service
@Transactional
public class Estatus15ServiceImp implements Estatus15Service{
	 static final Logger log = LoggerFactory.getLogger(Estatus15ServiceImp.class);

	    @Override
	    public MensajeDTO limpiarEstatus() throws GenericException {
	        log.info("limpiarEstatus:: ");
	        MensajeDTO response = new MensajeDTO();
	        response.setMensajeConfirm("Mensaje de Confirmacion");
	        response.setMensajeInfo("Mensaje Informacion");
	        return response;
	    }
}
