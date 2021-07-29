package com.citi.euces.pronosticos.restcontrollers;

import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.models.ErrorGeneric;
import com.citi.euces.pronosticos.models.GetLayoutReponse;
import com.citi.euces.pronosticos.models.LeerArchivoRequest;
import com.citi.euces.pronosticos.models.MensajeResponse;
import com.citi.euces.pronosticos.services.api.IRepoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/controlRepo")
public class RepoController {
	static final Logger log = LoggerFactory.getLogger(RepoController.class);
	IRepoService repoService;
	
	public RepoController(IRepoService repoService) {
		this.repoService=repoService;
		
	}
	
	
	@GetMapping(path="/getLayoutProteccion")
	public ResponseEntity<GetLayoutReponse> getLayoutProteccion(){
		GetLayoutReponse response=new GetLayoutReponse();
		try {
			
			response.setCode("200");
			response.setMensaje("Se genero correctamente el layout");
			response.setFile(repoService.getLayoutProteccion());
			return new ResponseEntity<GetLayoutReponse>(response,HttpStatus.OK);
		
		} catch (GenericException e) {
			response.setCode(e.getCodeError());
			response.setMensaje(e.getMessage());
			response.setExcepcion(e.toString());
			return new ResponseEntity<GetLayoutReponse>(response,HttpStatus.OK);
		}catch(RuntimeException e) {
			response.setCode(HttpStatus.BAD_REQUEST.toString());
			response.setMensaje("Error en generar layout");
			response.setExcepcion(e.getMessage());
			return new ResponseEntity<GetLayoutReponse>(response,HttpStatus.OK);
		}
		
	}
	
	@PostMapping(path = "/cargarCuentas")
	public ResponseEntity<?> cargarCuentas(@RequestBody final LeerArchivoRequest request) {
		try {
			if (request.getFecha().isEmpty()) {
				throw new GenericException("Request incompleto :: ", HttpStatus.BAD_REQUEST.toString());
			}
			MensajeResponse response = new MensajeResponse(repoService.cargarCuentas(request.getFile(), request.getFecha()), "200");
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
