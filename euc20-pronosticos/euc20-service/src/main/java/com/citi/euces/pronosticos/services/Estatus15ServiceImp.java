package com.citi.euces.pronosticos.services;

import com.citi.euces.pronosticos.entities.MaestroDeComisiones;
import com.citi.euces.pronosticos.infra.dto.*;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.infra.utils.ConstantUtils;
import com.citi.euces.pronosticos.infra.utils.FormatUtils;
import com.citi.euces.pronosticos.repositories.Estatus15JDBCRepository;
import com.citi.euces.pronosticos.repositories.Estatus15TmpJDBCRepository;
import com.citi.euces.pronosticos.repositories.MaestroDeComisionesJDBCRepository;
import com.citi.euces.pronosticos.repositories.MaestroDeComisionesRepository;
import com.citi.euces.pronosticos.services.api.Estatus15Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class Estatus15ServiceImp implements Estatus15Service {
    static final Logger log = LoggerFactory.getLogger(Estatus15ServiceImp.class);

    @Autowired
    private Estatus15JDBCRepository estatus15JDBCRepository;
    @Autowired
    private MaestroDeComisionesJDBCRepository maestroDeComisionesJDBCRepository;
    @Autowired
    private MaestroDeComisionesRepository maestroDeComisionesRepository;
    @Autowired
    private Estatus15TmpJDBCRepository estatus15TmpJDBCRepository;

    @Override
    public MensajeDTO limpiarEstatus() throws GenericException {
        log.info("limpiarEstatus_Impl:: Init");
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
            log.info("BuscarClientes :: init");
            listaClientes = maestroDeComisionesJDBCRepository.getClientes(cliente, mes1, anio1, mes2, anio2, mes3,
                    anio3, mes4, anio4, mes5, anio5, mes6, anio6);
            if (!listaClientes.isEmpty()) {
                for (ClientesEstatus15DTO cc : listaClientes) {
                    if (cc.getSucursal() == null) {
                        cc.setSucursal("");
                    }
                    if (cc.getCuenta() == null) {
                        cc.setCuenta("");
                    }
                    if (cc.getpIva() == null) {
                        cc.setpIva(0);
                    }
                    if (cc.getmComision() == null) {
                        cc.setmComision(0.0);
                    }
                    if (cc.getmIva() == null) {
                        cc.setmIva(0.0);
                    }
                    if (cc.getmComParcial() == null) {
                        cc.setmComParcial(0.0);
                    }
                    if (cc.getmIvaParcial() == null) {
                        cc.setmIvaParcial(0.0);
                    }
                    if (cc.getIdCausaRechazo() == null) {
                        cc.setIdCausaRechazo(0);
                    }
                    if (cc.getfMovimiento() == null) {
                        cc.setfMovimiento("");
                    }
                    if (cc.getfRegistroContable() == null) {
                        cc.setfRegistroContable("");
                    }
                    if (cc.getCsi() == null) {
                        cc.setCsi("");
                    }
                    if (cc.getComEc() == null) {
                        cc.setComEc("");
                    }
                    if (cc.getComP() == null) {
                        cc.setComP("");
                    }
                    if (cc.getChequeraCargo() == null) {
                        cc.setChequeraCargo("");
                    }
                    if (cc.getNoProteccion() == null) {
                        cc.setNoProteccion(0);
                    }
                    if (cc.getEstatusProteccion() == null) {
                        cc.setEstatusProteccion(0);
                    }
                    if (cc.getContrato() == null) {
                        cc.setContrato("");
                    }
                    if (cc.getfIngreso() == null) {
                        cc.setfIngreso("");
                    }
                    if (cc.getUdemer() == null) {
                        cc.setUdemer(0);
                    }
                    if (cc.getFraqnuiciaRegistro() == null) {
                        cc.setFraqnuiciaRegistro("");
                    }
                    if (cc.getFecVencProteccion() == null) {
                        cc.setFecVencProteccion("");
                    }
                    if (cc.getContenido() == null) {
                        cc.setContenido("");
                    }
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

    @Override
    public MensajeDTO agregarALista(String listaLlaves, String sucursal, String cuenta, Integer dias, String secuencialArch) throws GenericException {
        log.info("agregarALista  ::  init  " + listaLlaves.toString());
        List<MaestroDeComisiones> listaConsultaClientes = new ArrayList<>();
        List<AgregarListaEstatus15DTO> listaClientesToAdd = new ArrayList<>();
        List<AddListFinalTmpEstatus15DTO> listaFinal = new ArrayList<>();

        try {
            String[] llaves = listaLlaves.split(",");
            List<Long> listaLlavesToAdd = new ArrayList<Long>();
            Arrays.stream(llaves).forEach(a -> {
                Integer llaveLong = Integer.parseInt(a);
                listaLlavesToAdd.add(llaveLong.longValue());
            });

            listaConsultaClientes = maestroDeComisionesRepository.findByLlaveIN(listaLlavesToAdd);
            log.info("listaConsultaClientes :: " + listaConsultaClientes);

            listaConsultaClientes.forEach(lc -> {
                AgregarListaEstatus15DTO itemCliente = new AgregarListaEstatus15DTO();
                itemCliente.setSucursal(lc.getSucursal());
                itemCliente.setCuenta(lc.getCuenta());
                itemCliente.setpIva(lc.getpIva());
                itemCliente.setmComision(lc.getmComision());
                itemCliente.setmIva(lc.getmIva());
                itemCliente.setmComParcial(lc.getmComParcial());
                itemCliente.setmIvaParcial(lc.getmIvaParcial());
                itemCliente.setIdCausaRechazo(lc.getIdCausaRechazo());
                itemCliente.setFechaMovimiento(lc.getFechaMovimiento());
                itemCliente.setFechaRegistroContable(lc.getFechaRegistroContable());
                itemCliente.setCsi(lc.getCsi());
                itemCliente.setComEc(lc.getComEc());
                itemCliente.setComP(lc.getComP());
                itemCliente.setChequeraCargo(lc.getChequeraCargo());
                itemCliente.setNoProteccion(lc.getNoProteccion());
                itemCliente.setEstatusProteccion(lc.getEstatusProteccion());
                itemCliente.setContrato(lc.getContrato());
                itemCliente.setFechaIngreso(lc.getFechaIngreso());
                itemCliente.setUdmer(lc.getUdmer());
                itemCliente.setFranquiciaRegistro(lc.getFranquiciaRegistro());
                itemCliente.setIdFranquicia(lc.getIdFranquicia());
                itemCliente.setFechaVencProteccion(lc.getFechaVencProteccion());
                itemCliente.setOpenItem(lc.getOpenItem());
                itemCliente.setLlaveTemporal(lc.getId().getLlaveTemporal());
                itemCliente.setNoCliente(lc.getId().getNoCliente());
                itemCliente.setChequera(lc.getId().getChequera());
                itemCliente.setIdServicio(lc.getId().getIdServicio());
                itemCliente.setIdOndemand(lc.getId().getIdOndemand());
                itemCliente.setMes(lc.getId().getMes());
                itemCliente.setAnio(lc.getId().getAnio());
                itemCliente.setmTotal(lc.getId().getmTotal());
                itemCliente.setIdEstatusComision(lc.getId().getIdEstatusComision());
                itemCliente.setLlave(lc.getId().getLlave());
                itemCliente.setCatalogadaGc(lc.getId().getCatalogadaGc());
                listaClientesToAdd.add(itemCliente);
            });

            if (listaClientesToAdd.size() > 0) {
                for (AgregarListaEstatus15DTO cc : listaClientesToAdd) {
                    cc.setCuenta(Integer.parseInt(cuenta));
                    cc.setSucursal(Integer.parseInt(sucursal));
                    cc.setDias(dias);
                }
            }
            log.info("listaClientesToAdd :: " + listaClientesToAdd.size());

            Integer sec_int = 2;
            Integer secint = estatus15TmpJDBCRepository.getLastSecE15();
            System.out.println("secint getLastSecE15() :: " + secint.toString());

            if (secint > 0) {
                sec_int = secint;
            }

            Integer sec_arch = 0;
            sec_arch = Integer.parseInt(secuencialArch);

            for (AgregarListaEstatus15DTO cc : listaClientesToAdd) {
                if (sec_int == 65002) {
                    sec_arch++;
                    sec_int = 2;
                }

                cc.setSEC_ARC(sec_arch);
                if (cc.getSEC_ARC() == 0) {
                    cc.setSEC_ARC(sec_arch);
                }

                cc.setSEC_INT(sec_int);
                if (cc.getSEC_INT() == 0) {
                    cc.setSEC_INT(sec_int);
                }
                sec_int++;
            }

            listaClientesToAdd.forEach(item -> {
                AddListFinalTmpEstatus15DTO cf = new AddListFinalTmpEstatus15DTO();
                cf.setLlave(item.getLlave().toString());
                cf.setSecArch(item.getSEC_ARC());
                cf.setSecInt(item.getSEC_INT());
                cf.setKeyUp(item.getSEC_ARC().toString().concat("_").concat(item.getSEC_INT().toString()));
                cf.setNoCliente(item.getSucursal().longValue());
                cf.setCtaCliente(item.getCuenta().longValue());
                listaFinal.add(cf);
            });

            try {
                estatus15TmpJDBCRepository.batchInsert(listaFinal);
            } catch (Exception e) {
                throw new GenericException("No se realizó la inserción en BD :: ", HttpStatus.NOT_FOUND.toString());
            }

        } catch (Exception e) {
            throw new GenericException("No se pudo completar la operación de carga :: ", HttpStatus.NOT_FOUND.toString());
        }

        MensajeDTO response = new MensajeDTO();
        response.setMensajeConfirm("Operación completada.");
        response.setMensajeInfo("Los clientes se cargaron correctamente.");
        return response;
    }

    @Override
    public GetLayoutEstatus15DTO generarArchivo(Integer diasProteccion, Integer secuencialArch, Integer sucursal, Integer cuenta, Integer dias) throws GenericException {
        log.info("generarArchivo  ::  init  ");
        List<Estatus15TmpDTO> est15tmp = new ArrayList<>();
        List<AgregarListaEstatus15DTO> listaFinal = new ArrayList<>();

        try {
            est15tmp = estatus15TmpJDBCRepository.findAll();

            est15tmp.forEach(le -> {
                List<MaestroDeComisiones> listaConsulta = new ArrayList<>();
                listaConsulta = maestroDeComisionesRepository.findByLlave(le.getLlave());
                listaConsulta.forEach(lc -> {
                    AgregarListaEstatus15DTO itemCliente = new AgregarListaEstatus15DTO();
                    itemCliente.setSucursal(lc.getSucursal());
                    itemCliente.setCuenta(lc.getCuenta());
                    itemCliente.setpIva(lc.getpIva());
                    itemCliente.setmComision(lc.getmComision());
                    itemCliente.setmIva(lc.getmIva());
                    itemCliente.setmComParcial(lc.getmComParcial());
                    itemCliente.setmIvaParcial(lc.getmIvaParcial());
                    itemCliente.setIdCausaRechazo(lc.getIdCausaRechazo());
                    itemCliente.setFechaMovimiento(lc.getFechaMovimiento());
                    itemCliente.setFechaRegistroContable(lc.getFechaRegistroContable());
                    itemCliente.setCsi(lc.getCsi());
                    itemCliente.setComEc(lc.getComEc());
                    itemCliente.setComP(lc.getComP());
                    itemCliente.setChequeraCargo(lc.getChequeraCargo());
                    itemCliente.setNoProteccion(lc.getNoProteccion());
                    itemCliente.setEstatusProteccion(lc.getEstatusProteccion());
                    itemCliente.setContrato(lc.getContrato());
                    itemCliente.setFechaIngreso(lc.getFechaIngreso());
                    itemCliente.setUdmer(lc.getUdmer());
                    itemCliente.setFranquiciaRegistro(lc.getFranquiciaRegistro());
                    itemCliente.setIdFranquicia(lc.getIdFranquicia());
                    itemCliente.setFechaVencProteccion(lc.getFechaVencProteccion());
                    itemCliente.setOpenItem(lc.getOpenItem());
                    itemCliente.setLlaveTemporal(lc.getId().getLlaveTemporal());
                    itemCliente.setNoCliente(lc.getId().getNoCliente());
                    itemCliente.setChequera(lc.getId().getChequera());
                    itemCliente.setIdServicio(lc.getId().getIdServicio());
                    itemCliente.setIdOndemand(lc.getId().getIdOndemand());
                    itemCliente.setMes(lc.getId().getMes());
                    itemCliente.setAnio(lc.getId().getAnio());
                    itemCliente.setmTotal(lc.getId().getmTotal());
                    itemCliente.setIdEstatusComision(lc.getId().getIdEstatusComision());
                    itemCliente.setLlave(lc.getId().getLlave());
                    itemCliente.setCatalogadaGc(lc.getId().getCatalogadaGc());
                    listaFinal.add(itemCliente);
                });
            });

            log.info("ListaFinal :: ", listaFinal.size());

            if (listaFinal.size() > 0){
                List<Estatus15TmpDTO> finalEst15tmp = est15tmp;

                listaFinal.forEach(cc -> {
                    List<Long> sucursalFinal = finalEst15tmp.stream().filter(x -> x.getLlave().equalsIgnoreCase(cc.getLlave().toString())).map(x -> x.getSucursal()).collect(Collectors.toList());
                    log.info("sucursalFinal :: " + sucursalFinal.stream().findFirst().get().intValue());
                    cc.setSucursal(sucursalFinal.stream().findFirst().get().intValue());
                    List<Long> cuentaFinal = finalEst15tmp.stream().filter(x -> x.getLlave().equalsIgnoreCase(cc.getLlave().toString())).map(x -> x.getCuenta()).collect(Collectors.toList());
                    log.info("cuentaFinal :: " + cuentaFinal.stream().findFirst().get().intValue());
                    cc.setCuenta(cuentaFinal.stream().findFirst().get().intValue());
                    cc.setDias(diasProteccion);
                });
            }



        }catch (Exception e) {
            throw new GenericException("No se pudo completar la operación de carga :: ", HttpStatus.NOT_FOUND.toString());
        }


        GetLayoutEstatus15DTO response = new GetLayoutEstatus15DTO();
        MensajeDTO mensaje = new MensajeDTO();
        mensaje.setMensajeInfo("Operación completada.");
        mensaje.setMensajeInfo("El archivo se generó exitosamente, con un total de (" ); //El archivo se generó exitosamente, con un total de (total de núm. de registros)
        response.setMensaje(mensaje);
        response.setFile("");
        return response;
    }

    public String generarLayoutProteccion(List<AgregarListaEstatus15DTO> listaCarga, Date fechaCarga, List<CatClavesProductosDTO> listaProductos, List<CatServiciosPronosticosDTO> listaCatServicios, Integer secuencialArch) throws GenericException{
        String file = "";
        String emisorPronosticos = ConstantUtils.EMISOR_PRONOSTICOS;
        String refEmisorPerfil = ConstantUtils.REF_EMISOR_PERFIL;
        String ctaAbonoPerfil = ConstantUtils.CTA_ABONO_PERFIL;
        String strFecha = FormatUtils.formatDateSinEspacios(fechaCarga);
        System.out.println("fechaXX :: " + strFecha);

        try {
            String fileName = "";
            String outTxtFile = "";
            List<Integer> listaArchivos = new ArrayList<>();
            Integer countCuentaAlterna = 0;
            Integer numChequeraFija = 0;
            Integer sec_int = 2;
            Integer sec_arch = 0;
            sec_arch = secuencialArch;
            List<AgregarListaEstatus15DTO> listaFinal = new ArrayList<>();
            Integer bloqueados = 0;
            Integer numElementosOriginal = listaCarga.size();
            listaFinal = listaCarga;

            for (AgregarListaEstatus15DTO item : listaFinal) {
                if (sec_int == 65002) {
                    sec_arch++;
                    sec_int = 2;
                }

                item.setSEC_ARC(sec_arch);
                if (item.getSEC_ARC() == 0) {
                    item.setSEC_ARC(sec_arch);
                }

                item.setSEC_INT(sec_int);
                if (item.getSEC_INT() == 0) {
                    item.setSEC_INT(sec_int);
                }
                sec_int++;
            }

        }catch (Exception e) {
            throw new GenericException("No se pudo completar la operación de carga :: ", HttpStatus.NOT_FOUND.toString());
        }

        return file;
    }

    }
