package com.citi.euces.pronosticos.services;

import javax.transaction.Transactional;

import com.citi.euces.pronosticos.repositories.Estatus15JDBCRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.services.api.Estatus15Service;

@Service
@Transactional
public class Estatus15ServiceImp implements Estatus15Service{
	 static final Logger log = LoggerFactory.getLogger(Estatus15ServiceImp.class);

	@Autowired
	private Estatus15JDBCRepository estatus15JDBCRepository;

	    @Override
	    public MensajeDTO limpiarEstatus() throws GenericException {
	        log.info("limpiarEstatus_Impl:: ");
			try {
				estatus15JDBCRepository.truncateTableEstatus15();
			} catch (Exception e) {
				throw new GenericException(
						"Error al limpiar tabla Estatus15 :: " , HttpStatus.NOT_FOUND.toString());
			}

			try {
				estatus15JDBCRepository.truncateTableEstatus15tmp();
			} catch (Exception e) {
				throw new GenericException(
						"Error al limpiar tabla Estatus15TMP :: " , HttpStatus.NOT_FOUND.toString());
			}

			MensajeDTO response = new MensajeDTO();
	        response.setMensajeConfirm("Tabla vacía.");
	        response.setMensajeInfo("La tabla Estatus15 se encuentra sin registros.");
	        return response;
	    }
}
