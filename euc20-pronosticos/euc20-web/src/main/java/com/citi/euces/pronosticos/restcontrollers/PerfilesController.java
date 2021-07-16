package com.citi.euces.pronosticos.restcontrollers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.models.ErrorGeneric;
import com.citi.euces.pronosticos.models.MensajeResponse;
import com.citi.euces.pronosticos.models.TasaCeroRequest;

@RestController
@RequestMapping(path= PerfilesController.ORDERS_RESOURCE,
produces = MediaType.APPLICATION_JSON_VALUE)
public class PerfilesController {
	
	static final Logger log = LoggerFactory.getLogger(PerfilesController.class);

	static final String ORDERS_RESOURCE = "/Perfiles";
	
	@PostMapping("/ImpReporteCobro")
	public ResponseEntity<?> TasaCero(@RequestBody TasaCeroRequest request){
		try {
			if(request.getFile().isEmpty()) {
				throw new GenericException("El archivo que intenta subir no es .ZIP.",
						HttpStatus.BAD_REQUEST.toString());
			}else if (request.getFecha().isEmpty()) {
				throw new GenericException("Favor de verificar la fecha de movimiento",
						HttpStatus.BAD_REQUEST.toString());
			}
			//MensajeResponse response = new MensajeResponse(
            //	mapfreService.GenerarTasaCero(request.getFile(), request.getFecha()),
            //   HttpStatus.OK.toString());
            return null /*new ResponseEntity<MensajeResponse>(response, HttpStatus.OK)*/;
            
        } catch (GenericException ex) {
            ErrorGeneric error = new ErrorGeneric();
            error.setCode(ex.getCodeError());
            error.setMensaje(ex.getMessage());
            error.setException(ex);
            log.info(error.getException());
            return new ResponseEntity<ErrorGeneric>(error, HttpStatus.OK);
        } catch (Exception e) {
            ErrorGeneric error = new ErrorGeneric();
            error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            error.setMensaje(e.getMessage());
            error.setException(e);
            log.info(error.getException());
            return new ResponseEntity<ErrorGeneric>(error, HttpStatus.OK);
        }
	}


}
