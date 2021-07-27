package com.citi.euces.pronosticos.restcontrollers;

import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.models.*;
import com.citi.euces.pronosticos.services.api.Estatus15Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/estatus15", produces = MediaType.APPLICATION_JSON_VALUE)
public class Estatus15Controller {
    static final Logger log = LoggerFactory.getLogger(Estatus15Controller.class);

    @Autowired
    private Estatus15Service estatusService;

    @GetMapping(path = "/limpiarEstatus")
    public ResponseEntity<?> limpiarEstatus() {
        try {
            MensajeResponse response = new MensajeResponse(estatusService.limpiarEstatus(), "200");
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

    @PostMapping(path = "/buscarCliente")
    public ResponseEntity<?> buscarCliente(@RequestBody final ClientesEstatus15Request request) {
        try {
            log.info("Request_BuscarClientes:: ", request);

            if (request.getCliente() == null || request.getMes1() == null || request.getAnio1() == null
                    || request.getMes2() == null || request.getAnio2() == null || request.getMes3() == null
                    || request.getAnio3() == null || request.getMes4() == null || request.getAnio4() == null
                    || request.getMes5() == null || request.getAnio5() == null || request.getMes6() == null
                    || request.getAnio6() == null) {
                throw new GenericException("Request incompleto :: ", HttpStatus.BAD_REQUEST.toString());
            }
            ClientesEstatus15Response response = new ClientesEstatus15Response(estatusService.buscarClientes(
                    request.getCliente(), request.getMes1(), request.getAnio1(), request.getMes2(), request.getAnio2(),
                    request.getMes3(), request.getAnio3(), request.getMes4(), request.getAnio4(), request.getMes5(),
                    request.getAnio5(), request.getMes6(), request.getAnio6()), "200");

            return new ResponseEntity<ClientesEstatus15Response>(response, HttpStatus.OK);
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

    @PostMapping(path = "/agregarALista")
    public ResponseEntity<?> agregarALista(@RequestBody final AgregarListaEstatus15Request request) {
        try {
            log.info("agregarALista:: ", request.toString());
            if (request.getListaLlaves() == null || request.getCuenta() == null || request.getDias() == null || request.getSucursal() == null || request.getSecuencialArch() == null) {
                throw new GenericException("Request incompleto :: ", HttpStatus.BAD_REQUEST.toString());
            }

            MensajeResponse response = new MensajeResponse(estatusService.agregarALista(request.getListaLlaves(), request.getSucursal(), request.getCuenta(), request.getDias(), request.getSecuencialArch()), "200");
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

    @PostMapping(path = "/generarArchivo")
    public ResponseEntity<?> generarArchivo(@RequestBody final GetLayoutEstatus15Request request) {
        try {
            log.info("generarArchivo:: ", request.toString());
            if (request.getDiasProteccion() == null || request.getSecuencialArch() == null || request.getSucursal() == null || request.getCuenta() == null) {
                throw new GenericException("Request incompleto :: ", HttpStatus.BAD_REQUEST.toString());
            }

            GetLayoutEstatus15Response response = new GetLayoutEstatus15Response(estatusService.generarArchivo(request.getDiasProteccion(), request.getSecuencialArch(),
                    request.getSucursal(), request.getCuenta()), "200");
            return new ResponseEntity<GetLayoutEstatus15Response>(response, HttpStatus.OK);
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

    @PostMapping(path = "/subirRespuestas")
    public ResponseEntity<?> subirRespuestas(@RequestBody final SubirRespEstatus15Request request) {
        try {
            log.info("generarArchivo:: ", request.toString());
            if (request.getFile() == null || request.getFile().isEmpty()) {
                throw new GenericException("Request incompleto :: ", HttpStatus.BAD_REQUEST.toString());
            }

            MensajeResponse response = new MensajeResponse(estatusService.subirRespuestas(request.getFile()), "200");
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