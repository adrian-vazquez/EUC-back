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
import com.citi.euces.pronosticos.models.BtnSaveRequest;
import com.citi.euces.pronosticos.models.ErrorGeneric;
import com.citi.euces.pronosticos.models.GenArchECRequest;
import com.citi.euces.pronosticos.models.GenArchPFRequest;
import com.citi.euces.pronosticos.models.LeerArchivoCuentaAlt;
import com.citi.euces.pronosticos.models.LeerArchivoRequest;
import com.citi.euces.pronosticos.models.MensajeResponse;
import com.citi.euces.pronosticos.services.api.ReintentosService;

@RestController
@RequestMapping(path = "/reintentos", produces = MediaType.APPLICATION_JSON_VALUE)

public class ReintentosController {
	static final Logger log = LoggerFactory.getLogger(ReintentosController.class);

	@Autowired
	private ReintentosService reintentosService;

	@GetMapping(path = "/limpiarTabla")
	public ResponseEntity<?> limpiarEstatus() {
		try {
			MensajeResponse response = new MensajeResponse(reintentosService.limpiarTabla(), "200");
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

	@PostMapping(path = "/subirArchivo")
	public ResponseEntity<?> aplicarRebajaloadFile(@RequestBody final LeerArchivoCuentaAlt request) {
		try {
			if (request.getFile().isEmpty()) {
				throw new GenericException("Request incompleto :: ", HttpStatus.BAD_REQUEST.toString());
			}
			MensajeResponse response = new MensajeResponse(reintentosService.cuentasAltloadFile(request.getFile()), "200");
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
	

	@PostMapping(path = "/btnGenArchPF")
	public ResponseEntity<?> btnGenArchPF(@RequestBody final GenArchPFRequest request) {
		try {
			if (request == null) {
				throw new GenericException("Request incompleto :: ", HttpStatus.BAD_REQUEST.toString());
			}
			MensajeResponse response = new MensajeResponse(reintentosService.genArchPFService(request.getMes1(), request.getAnio1(), request.getDias1(), request.getMes2(), request.getAnio2(), request.getDias2(),
					request.getMes3(), request.getAnio3(), request.getDias3()), "200");
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
	
	@PostMapping(path = "/btnGenArchEC")
	public ResponseEntity<?> btnGenArchEC(@RequestBody final GenArchECRequest request) {
		try {
			if (request == null) {
				throw new GenericException("Request incompleto :: ", HttpStatus.BAD_REQUEST.toString());
			}
			MensajeResponse response = new MensajeResponse(reintentosService.genArchECService(request.getMes1(), request.getAnio1(), request.getDias1(), request.getMes2(), request.getAnio2(), request.getDias2(),
					request.getMes3(), request.getAnio3(), request.getDias3()), "200");
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
	
	@GetMapping(path = "/btnGenArchTC")
	public ResponseEntity<?> btnGenArchTC() {
		try {
			log.info("entrando btnGenArchTC");
			MensajeResponse response = new MensajeResponse(reintentosService.genArchTCService(), "200");
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
	
	@PostMapping(path = "/btnSave")
	public ResponseEntity<?> btnSave(@RequestBody final BtnSaveRequest request) {
		try {
			MensajeResponse response = new MensajeResponse(reintentosService.BtnSave(request.getMes1(), request.getAnio1(), request.getDias1(), request.getMes2(), request.getAnio2(), request.getDias2(),
					request.getMes3(), request.getAnio3(), request.getDias3(), null, null, null), "200");
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
