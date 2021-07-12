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
import com.citi.euces.pronosticos.models.CobuRequest;
import com.citi.euces.pronosticos.models.CobuResponse;
import com.citi.euces.pronosticos.models.ErrorGeneric;
import com.citi.euces.pronosticos.services.api.CobuService;

@RestController
@RequestMapping(path = "/cobu", produces = MediaType.APPLICATION_JSON_VALUE)
public class CobuController {
	
	static final Logger log = LoggerFactory.getLogger(CobuController.class);

    @Autowired
    private CobuService cobuService;
    

	@GetMapping(path = "/limpiarCobu")
	public ResponseEntity<?> limpiarCobu() throws GenericException {
		try {
            CobuResponse response = new CobuResponse(
            		cobuService.limpiarCobu(),
                    HttpStatus.OK.toString());
            return new ResponseEntity<CobuResponse>(response, HttpStatus.OK);
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
	
	@PostMapping(path = "/cargaCtasCobu")
	public ResponseEntity<?>cargaCtasCobu(@RequestBody final CobuRequest request) {
		try {
            if (request.getFile().isEmpty()) {
                throw new GenericException("Favor de seleccionar un archivo", HttpStatus.BAD_REQUEST.toString());
            }
            CobuResponse response = new CobuResponse(
            	cobuService.cargaCtasCobu(request.getFile()),
            	HttpStatus.OK.toString());
            	return new ResponseEntity<CobuResponse>(response, HttpStatus.OK);
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
	
	@PostMapping(path = "/cargaCtasVirt")
	public ResponseEntity<?>cargaCtasVirt(@RequestBody final CobuRequest request) {
		try {
            if (request.getFile().isEmpty() ) {
                throw new GenericException("Favor de seleccionar un archivo", HttpStatus.BAD_REQUEST.toString());
            }
            CobuResponse response = new CobuResponse(
            	cobuService.cargaCtasVirt(request.getFile()),
                HttpStatus.OK.toString());
            	return new ResponseEntity<CobuResponse>(response, HttpStatus.OK);
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
	
	@PostMapping(path = "/cargaTxsCtas")
	public ResponseEntity<?>cargaTxsCtas(@RequestBody final CobuRequest request) {
		try {
            if (request.getFile().isEmpty() ) {
                throw new GenericException("Favor de seleccionar un archivo", HttpStatus.BAD_REQUEST.toString());
            }
            CobuResponse response = new CobuResponse(
            	cobuService.cargaTxsCtas(request.getFile()),
                HttpStatus.OK.toString());
            	return new ResponseEntity<CobuResponse>(response, HttpStatus.OK);
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
	
	@PostMapping(path = "/cargaTarEspCobu")
	public ResponseEntity<?>cargaTarEspCobu(@RequestBody final CobuRequest request) {
		try {
            if (request.getFile().isEmpty() ) {
                throw new GenericException("Favor de seleccionar un archivo", HttpStatus.BAD_REQUEST.toString());
            }
            CobuResponse response = new CobuResponse(
            	cobuService.cargaTarEspCobu(request.getFile()),
                HttpStatus.OK.toString());
            	return new ResponseEntity<CobuResponse>(response, HttpStatus.OK);
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
	
	@GetMapping(path = "/procesoCobu")
	public ResponseEntity<?> procesoCobu() throws GenericException{
		try {
            CobuResponse response = new CobuResponse(
            	cobuService.procesoCobu(),
                HttpStatus.OK.toString());
            return new ResponseEntity<CobuResponse>(response, HttpStatus.OK);
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
	
	/*@GetMapping(path = "/reporte")
	public ResponseEntity<?>reporte(@RequestBody final String request) {
		try {
            if (request.isEmpty() ) {
                throw new GenericException("Request incompleto :: ", HttpStatus.BAD_REQUEST.toString());
            }
            CobuResponse response = new CobuResponse(
            	cobuService.reporte(),
                HttpStatus.OK.toString());
            	return new ResponseEntity<CobuResponse>(response, HttpStatus.OK);
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
	
	@GetMapping(path = "/cifrasControl")
	public ResponseEntity<?>cifrasControl(@RequestBody final String request) {
		try {
            if (request.isEmpty() ) {
                throw new GenericException("Request incompleto :: ", HttpStatus.BAD_REQUEST.toString());
            }
            CobuResponse response = new CobuResponse(
            	cobuService.cifrasControl(),
                HttpStatus.OK.toString());
            	return new ResponseEntity<CobuResponse>(response, HttpStatus.OK);
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
	}*/
}
