package com.citi.euces.pronosticos.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.citi.euces.pronosticos.infra.dto.ClientesEstatus15DTO;
import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.repositories.Estatus15JDBCRepository;
import com.citi.euces.pronosticos.repositories.MaestroDeComisionesJDBCRepository;
import com.citi.euces.pronosticos.services.api.Estatus15Service;

@Service
@Transactional
public class Estatus15ServiceImp implements Estatus15Service {
    static final Logger log = LoggerFactory.getLogger(Estatus15ServiceImp.class);

    @Autowired
    private Estatus15JDBCRepository estatus15JDBCRepository;
    @Autowired
    private MaestroDeComisionesJDBCRepository maestroDeComisionesJDBCRepository;

    @Override
    public MensajeDTO limpiarEstatus() throws GenericException {
        log.info("limpiarEstatus_Impl:: ");
        try {
            estatus15JDBCRepository.truncateTableEstatus15();
        } catch (Exception e) {
            throw new GenericException("Error al limpiar tabla Estatus15 :: ", HttpStatus.NOT_FOUND.toString());
        }

        try {
            estatus15JDBCRepository.truncateTableEstatus15tmp();
        } catch (Exception e) {
            throw new GenericException("Error al limpiar tabla Estatus15TMP :: ", HttpStatus.NOT_FOUND.toString());
        }

        MensajeDTO response = new MensajeDTO();
        response.setMensajeConfirm("Tabla vacía.");
        response.setMensajeInfo("La tabla Estatus15 se encuentra sin registros.");
        return response;
    }

    @Override
    public List<ClientesEstatus15DTO> buscarClientes(Long cliente, Integer mes1, Integer anio1, Integer mes2,
                                                     Integer anio2, Integer mes3, Integer anio3, Integer mes4, Integer anio4, Integer mes5, Integer anio5,
                                                     Integer mes6, Integer anio6) throws GenericException {
        List<ClientesEstatus15DTO> listaClientes = new ArrayList<>();

        try {
            log.info("Entra a buscar clientes :: ");
            listaClientes = maestroDeComisionesJDBCRepository.getClientes(cliente, mes1, anio1, mes2, anio2, mes3,
                    anio3, mes4, anio4, mes5, anio5, mes6, anio6);
            if (!listaClientes.isEmpty()) {
                for (ClientesEstatus15DTO cc : listaClientes) {
                    if (cc.getSucursal() == null){cc.setSucursal("");}
                    if(cc.getCuenta() == null){cc.setCuenta("");}
                    if(cc.getpIva() == null){cc.setpIva(0);}
                    if(cc.getmComision() == null){cc.setmComision(0.0);}
                    if(cc.getmIva() == null){cc.setmIva(0.0);}
                    if(cc.getmComParcial() == null){cc.setmComParcial(0.0);}
                    if(cc.getmIvaParcial() == null){cc.setmIvaParcial(0.0);}
                    if(cc.getIdCausaRechazo() == null){cc.setIdCausaRechazo(0);}
                    if(cc.getfMovimiento() == null){cc.setfMovimiento("");};
                    if(cc.getfRegistroContable() == null){cc.setfRegistroContable("");}
                    if(cc.getCsi() == null){cc.setCsi("");}
                    if(cc.getComEc() == null){cc.setComEc("");}
                    if(cc.getComP() == null){cc.setComP("");}
                    if(cc.getChequeraCargo() == null){cc.setChequeraCargo("");}
                    if(cc.getNoProteccion() == null){cc.setNoProteccion(0);}
                    if(cc.getEstatusProteccion() == null){cc.setEstatusProteccion(0);}
                    if(cc.getContrato() == null){cc.setContrato("");}
                    if(cc.getfIngreso() == null){cc.setfIngreso("");}
                    if(cc.getUdemer() == null){cc.setUdemer(0);}
                    if(cc.getFraqnuiciaRegistro() == null){cc.setFraqnuiciaRegistro("");}
                    if(cc.getFecVencProteccion() == null){cc.setFecVencProteccion("");}
                    if(cc.getContenido() == null){cc.setContenido("");}
                }
            }
        } catch (Exception e) {
            throw new GenericException("Error al buscar Clientes :: " + e, HttpStatus.NOT_FOUND.toString());
        }

        if (listaClientes.isEmpty()) {
            throw new GenericException(
                    "Cliente no encontrado, introduzca otro número de cliente.", HttpStatus.NOT_FOUND.toString());
        }
        return listaClientes;
    }
}
