package com.citi.euces.pronosticos.restcontrollers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.models.GetLayoutReponse;
import com.citi.euces.pronosticos.services.api.IRepoService;

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
	
	

}
