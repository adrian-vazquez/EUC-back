package com.citi.euces.pronosticos.services;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.repositories.ReintentosJDBCRepository;
import com.citi.euces.pronosticos.services.api.ReintentosService;

@Service
@Transactional
public class ReintentosServiceImpl implements ReintentosService {
	static final Logger log = LoggerFactory.getLogger(ReintentosServiceImpl.class);

	@Autowired
	private ReintentosJDBCRepository reintentosJDBCRepository;

	@Override
	public MensajeDTO limpiarTabla() throws GenericException {
		// TODO Auto-generated method stub
		log.info("limpiarReintentos:: ");
		try {
			reintentosJDBCRepository.truncateTableReintentos();
		} catch (Exception e) {
			throw new GenericException("Error al limpiar tabla Reintentos :: ", HttpStatus.NOT_FOUND.toString());
		}

		MensajeDTO response = new MensajeDTO();
		response.setMensajeConfirm("Tabla vacía.");
		response.setMensajeInfo("La tabla Reintentos se encuentra sin registros.");
		return response;
	}

}
