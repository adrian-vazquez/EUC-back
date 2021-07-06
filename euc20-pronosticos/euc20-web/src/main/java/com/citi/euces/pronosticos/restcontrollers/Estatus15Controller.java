package com.citi.euces.pronosticos.restcontrollers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.models.ErrorGeneric;
import com.citi.euces.pronosticos.models.MensajeResponse;
import com.citi.euces.pronosticos.services.api.Estatus15Service;


@RestController
@RequestMapping(path = "/estatus15", produces = MediaType.APPLICATION_JSON_VALUE)
public class Estatus15Controller {
	static final Logger log = LoggerFactory.getLogger(Estatus15Controller.class);

    @Autowired
    private Estatus15Service estatusService;

    @GetMapping(path = "/limpiarEstatus")
    public ResponseEntity<?> limpiarEstatus() {
        try {
            MensajeResponse response = new MensajeResponse(
            		estatusService.limpiarEstatus(),
                    "200");
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
