package com.citi.euces.pronosticos.restcontrollers;

import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.models.*;
import com.citi.euces.pronosticos.services.api.RebajasService;
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

@RestController
@RequestMapping(path = "/rebajas", produces = MediaType.APPLICATION_JSON_VALUE)

public class RebajasController {
    static final Logger log = LoggerFactory.getLogger(RebajasController.class);

    @Autowired
    private RebajasService rebajasService;


    @PostMapping(path = "/aplicarRebajaloadFile")
    public ResponseEntity<?> aplicarRebajaloadFile(@RequestBody final LeerArchivoRequest request) {
        try {
            if (request.getFile().isEmpty() ) {
                throw new GenericException("Request incompleto :: ", HttpStatus.BAD_REQUEST.toString());
            }
            MensajeResponse response = new MensajeResponse(
                    rebajasService.aplicarRebajaloadFile(request.getFile(), request.getFechaContable(),
                            request.getFechaMovimiento()), "200");
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

    @PostMapping(path = "/aplicarRebaja")
    public ResponseEntity<?> aplicarRebaja() {
        try {
            MensajeResponse response = new MensajeResponse(rebajasService.aplicarRebaja(), "200");
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

    @PostMapping(path = "/reporteCuadre")
    public ResponseEntity<?> reporteCuadre() {
        try {
            ReporteCuadreResponse response = new ReporteCuadreResponse(rebajasService.reporteCuadre(), "200");
            return new ResponseEntity<ReporteCuadreResponse>(response, HttpStatus.OK);
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

    @PostMapping(path = "/reporteRebaja")
    public ResponseEntity<?> reporteRebaja(@RequestBody final ReporteRebajaRequest request) {
        try {
            if (request.getFecha().isEmpty() || request.getPage() == null) {
                throw new GenericException("Request incompleto :: ", HttpStatus.BAD_REQUEST.toString());
            }
            ReporteRebajaResponse response = new ReporteRebajaResponse(rebajasService.reporteRebaja(request.getFecha(),request.getPage()), "200");
            return new ResponseEntity<ReporteRebajaResponse>(response, HttpStatus.OK);
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
