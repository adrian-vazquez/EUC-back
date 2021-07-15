package com.citi.euces.pronosticos.restcontrollers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.models.*;
import com.citi.euces.pronosticos.services.api.PdcMapfreService;

@RestController
@RequestMapping(path= MapfreController.ORDERS_RESOURCE,
produces = MediaType.APPLICATION_JSON_VALUE)
public class MapfreController {
	
	static final Logger log = LoggerFactory.getLogger(MapfreController.class);

	static final String ORDERS_RESOURCE = "/MAPFRE";
	
	@Autowired
	private PdcMapfreService mapfreService;
	
	@GetMapping(path ="/prueba")
	public String prueba() {
		return "prueba de json";
	}	

	@PostMapping(path ="/LayoutPrevio")
	public ResponseEntity<?> LayoutPrevio(@RequestBody final LayoutPrevioRequest request){
		try {
			if(request.getFecCarga().isEmpty()){
				throw new GenericException("Fecha de comparacion es incorrecta. ", HttpStatus.BAD_REQUEST.toString());
			}else if(request.getDias()==null) {
				throw new GenericException("Campo dias es incorrecto. ", HttpStatus.BAD_REQUEST.toString());
			}
			LayoutPrevioResponse response = new LayoutPrevioResponse(mapfreService.ObtenerLayout(request.getFecCarga(),request.getDias()), "200");
	        return new ResponseEntity<LayoutPrevioResponse>(response, HttpStatus.OK);
	        
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

	
	@PostMapping(path ="/CrearLayout")
	public ResponseEntity<?> TasaCero(@RequestBody TasaCeroRequest request){
		try {
			if(request.getFile().isEmpty() || request.getFecha().isEmpty()) {
				throw new GenericException("Request incompleto :: Verifica los valores de entrada.",
						HttpStatus.BAD_REQUEST.toString());
			}
			MensajeResponse response = new MensajeResponse(
            	mapfreService.GenerarTasaCero(request.getFile(), request.getFecha()),
                HttpStatus.OK.toString());
            return new ResponseEntity<MensajeResponse>(response, HttpStatus.OK);
            
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
