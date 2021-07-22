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
import com.citi.euces.pronosticos.models.ArchivoProteccion;
import com.citi.euces.pronosticos.models.ArchivoRebajaProteccionResquest;
import com.citi.euces.pronosticos.models.ArchivoRechazos;
import com.citi.euces.pronosticos.models.ArchivoRespuestaRequest;
import com.citi.euces.pronosticos.models.ErrorGeneric;
import com.citi.euces.pronosticos.models.MensajeResponse;
import com.citi.euces.pronosticos.services.api.PronosticosService;


 
@RestController
@RequestMapping(path = "/pronosticos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PronosticosController {

	static final Logger log = LoggerFactory.getLogger(PronosticosController.class);
	
	@Autowired
	private PronosticosService pronosticosService;
	
	@GetMapping(path = "/limpiarPronosticos")
	public ResponseEntity<?> deletePronosticos() 
	{
		try 
		{
			MensajeResponse response = new MensajeResponse(pronosticosService.limpiarPronosticos(), HttpStatus.OK.toString());
			return new ResponseEntity<MensajeResponse>(response, HttpStatus.OK);
		} catch (GenericException ex) 
		{
            ErrorGeneric error = new ErrorGeneric();
            error.setCode(ex.getCodeError());
            error.setMensaje(ex.getMessage());
            error.setException(ex);
            log.info(error.getException());
            return new ResponseEntity<ErrorGeneric>(error, HttpStatus.OK);
        } catch (Exception e) 
		{
            ErrorGeneric error = new ErrorGeneric();
            error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            error.setMensaje(e.getMessage());
            error.setException(e);
            log.info(error.getException());
            return new ResponseEntity<ErrorGeneric>(error, HttpStatus.OK);
        }
	}
	
	@PostMapping(path = "/cargaRechazos")
	public ResponseEntity<?> cargaRechazos(@RequestBody final ArchivoRechazos request)
	{
		try {
			
			if (request.getFile().isEmpty()) 
			{
                throw new GenericException("Request incompleto :: ", HttpStatus.BAD_REQUEST.toString());
            }
			
			MensajeResponse response = new MensajeResponse(pronosticosService.cargarRechazos(request.getFile(), 
														request.getDiasProteccion(), request.isExtraCont(), 
														request.isCobEspe()), HttpStatus.OK.toString());
			return new ResponseEntity<MensajeResponse>(response, HttpStatus.OK);
			
		} catch (GenericException ex) 
		{
            ErrorGeneric error = new ErrorGeneric();
            error.setCode(ex.getCodeError());
            error.setMensaje(ex.getMessage());
            error.setException(ex);
            log.info(error.getException());
            return new ResponseEntity<ErrorGeneric>(error, HttpStatus.OK);
        } catch (Exception e) 
		{
            ErrorGeneric error = new ErrorGeneric();
            error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            error.setMensaje(e.getMessage());
            error.setException(e);
            log.info(error.getException());
            return new ResponseEntity<ErrorGeneric>(error, HttpStatus.OK);
        }
	}
	
	@PostMapping(path = "/cargaRespuestas")
	public ResponseEntity<?> cargarRespuestas (@RequestBody final ArchivoRespuestaRequest request){
		try {
			if (request.getFile().isEmpty()) 
			{
                throw new GenericException("Request incompleto :: ", HttpStatus.BAD_REQUEST.toString());
            }
			
			MensajeResponse response = new MensajeResponse(pronosticosService.cargarRespuestas(request.getFile()),HttpStatus.OK.toString());
			return new ResponseEntity<MensajeResponse>(response, HttpStatus.OK);
		} catch (GenericException ex) 
		{
            ErrorGeneric error = new ErrorGeneric();
            error.setCode(ex.getCodeError());
            error.setMensaje(ex.getMessage());
            error.setException(ex);
            log.info(error.getException());
            return new ResponseEntity<ErrorGeneric>(error, HttpStatus.OK);
        } catch (Exception e) 
		{
            ErrorGeneric error = new ErrorGeneric();
            error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            error.setMensaje(e.getMessage());
            error.setException(e);
            log.info(error.getException());
            return new ResponseEntity<ErrorGeneric>(error, HttpStatus.OK);
        }
	}
	
	@PostMapping(path = "/generaArchivoProteccion")
	public ResponseEntity<?> generarArchivoProteccion (@RequestBody final ArchivoProteccion request){

		try {
			MensajeResponse response = new MensajeResponse(pronosticosService.limpiarPronosticos(),HttpStatus.OK.toString());
			return new ResponseEntity<MensajeResponse>(response, HttpStatus.OK);
		} catch (GenericException ex) 
		{
            ErrorGeneric error = new ErrorGeneric();
            error.setCode(ex.getCodeError());
            error.setMensaje(ex.getMessage());
            error.setException(ex);
            log.info(error.getException());
            return new ResponseEntity<ErrorGeneric>(error, HttpStatus.OK);
        } catch (Exception e) 
		{
            ErrorGeneric error = new ErrorGeneric();
            error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            error.setMensaje(e.getMessage());
            error.setException(e);
            log.info(error.getException());
            return new ResponseEntity<ErrorGeneric>(error, HttpStatus.OK);
        }
	}

	@PostMapping(path = "/cargaArchivoRebaja")
	public ResponseEntity<?> cargaArchivoRebaja(@RequestBody final ArchivoRebajaProteccionResquest request){
		try {
			if (request.getFile().isEmpty()) 
			{
                throw new GenericException("Request incompleto :: ", HttpStatus.BAD_REQUEST.toString());
            }
			
			MensajeResponse response = new MensajeResponse(pronosticosService.cargaArchivoRebaja(request.getFile()),HttpStatus.OK.toString());
			return new ResponseEntity<MensajeResponse>(response, HttpStatus.OK);
		} catch (GenericException ex) 
		{
            ErrorGeneric error = new ErrorGeneric();
            error.setCode(ex.getCodeError());
            error.setMensaje(ex.getMessage());
            error.setException(ex);
            log.info(error.getException());
            return new ResponseEntity<ErrorGeneric>(error, HttpStatus.OK);
        } catch (Exception e) 
		{
            ErrorGeneric error = new ErrorGeneric();
            error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            error.setMensaje(e.getMessage());
            error.setException(e);
            log.info(error.getException());
            return new ResponseEntity<ErrorGeneric>(error, HttpStatus.OK);
        }
	}
	
}
