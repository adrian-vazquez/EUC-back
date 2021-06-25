package com.citi.euces.pronosticos.restcontrollers;

import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.models.ErrorGeneric;
import com.citi.euces.pronosticos.models.MensajeResponse;
import com.citi.euces.pronosticos.services.api.RebajasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/rebajas", produces = MediaType.APPLICATION_JSON_VALUE)

public class RebajasController {
    static final Logger log = LoggerFactory.getLogger(RebajasController.class);

    @Autowired
    private RebajasService rebajasService;


    @PostMapping(path = "/aplicarRebajaloadFile")
    public ResponseEntity<?> aplicarRebajaloadFile(@RequestBody final String request) {
        try {
            if (request.isEmpty() ) {
                throw new GenericException("Request incompleto :: ", HttpStatus.BAD_REQUEST.toString());
            }
            MensajeResponse response = new MensajeResponse(
                    rebajasService.aplicarRebajaloadFile(request),
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
