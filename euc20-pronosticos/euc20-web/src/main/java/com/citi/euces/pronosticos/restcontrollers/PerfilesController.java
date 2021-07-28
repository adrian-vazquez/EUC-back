package com.citi.euces.pronosticos.restcontrollers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.models.CobuRequest;
import com.citi.euces.pronosticos.models.ErrorGeneric;
import com.citi.euces.pronosticos.models.ImpReporteCobroRequest;
import com.citi.euces.pronosticos.models.MensajeResponse;
import com.citi.euces.pronosticos.services.api.PerfilesService;

@RestController
@RequestMapping(path= PerfilesController.ORDERS_RESOURCE,
produces = MediaType.APPLICATION_JSON_VALUE)
public class PerfilesController {
	
	static final Logger log = LoggerFactory.getLogger(PerfilesController.class);

	static final String ORDERS_RESOURCE = "/Perfiles";
	
	@Autowired
	private PerfilesService perfilesService;
	
	@PostMapping(path = "/subirRebaja")
	public ResponseEntity<?>cargaCtasCobu(@RequestBody final CobuRequest request) {
		try {
            if (request.getFile().isEmpty()) {
                throw new GenericException("Favor de seleccionar un archivo", HttpStatus.BAD_REQUEST.toString());
            }
            MensajeResponse response = new MensajeResponse(
            	perfilesService.SubirRebajas(request.getFile()),
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
	
	@PostMapping("/ImpReporteCobro")
	public ResponseEntity<?> ImpReporteCobro(@RequestBody final ImpReporteCobroRequest request){
		try {
			if (request.getFechaCobro().isEmpty()) {
				throw new GenericException("Fecha de Busqueda incorrecta, Favor de verificar la fecha de cobro.",
						HttpStatus.BAD_REQUEST.toString());
			}
			MensajeResponse response = new MensajeResponse(
            	perfilesService.ImpReporteCobro(request.getFechaCobro()),HttpStatus.OK.toString());
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
	
	@PostMapping("/UploadRespuesta")
	public ResponseEntity<?> SubirRespuesta(@RequestBody final CobuRequest request){
		try {
			if (request.getFile().isEmpty()) {
				throw new GenericException("Alerta, ",
						HttpStatus.BAD_REQUEST.toString());
			}
			MensajeResponse response = new MensajeResponse(
            	perfilesService.SubirRespuesta(request.getFile()),HttpStatus.OK.toString());
            return new ResponseEntity<MensajeResponse>(response, HttpStatus.OK);
		}catch (GenericException ex) {
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
	
	@PostMapping("/ReporteCobros")
	public ResponseEntity<?> ReporteCobros(@RequestBody final CobuRequest request){
		try {
			if (request.getFile().isEmpty()) {
				throw new GenericException("Aviso, Archivo no valido ",
						HttpStatus.BAD_REQUEST.toString());
			}
			MensajeResponse response = new MensajeResponse(
            	perfilesService.ArchivoCobros(request.getFile()),HttpStatus.OK.toString());
            return new ResponseEntity<MensajeResponse>(response, HttpStatus.OK);
		}catch (GenericException ex) {
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
