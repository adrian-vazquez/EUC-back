package com.citi.euces.pronosticos.services;

import com.citi.euces.pronosticos.entities.MaestroDeComisiones;
import com.citi.euces.pronosticos.infra.dto.*;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.infra.utils.ConstantUtils;
import com.citi.euces.pronosticos.infra.utils.FormatUtils;
import com.citi.euces.pronosticos.repositories.*;
import com.citi.euces.pronosticos.services.api.Estatus15Service;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

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
    @Autowired
    private CatServiciosPronosticoJDBCRepository catServiciosPronosticoJDBCRepository;
    @Autowired
    private CatClavesProductosJDBCRepository catClavesProductosJDBCRepository;

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
                                                     Integer anio2, Integer mes3, Integer anio3, Integer mes4,
                                                     Integer anio4, Integer mes5, Integer anio5, Integer mes6,
                                                     Integer anio6) throws GenericException {
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
        log.info("agregarALista  ::  init  " + listaLlaves);
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

            Integer secInt = 2;
            Integer secint = estatus15TmpJDBCRepository.getLastSecE15();
            System.out.println("secint getLastSecE15() :: " + secint.toString());

            if (secint > 0) {
                secInt = secint;
            }

            Integer secArch;
            secArch = Integer.parseInt(secuencialArch);

            for (AgregarListaEstatus15DTO cc : listaClientesToAdd) {
                if (secInt == 65002) {
                    secArch++;
                    secInt = 2;
                }

                cc.setSEC_ARC(secArch);
                if (cc.getSEC_ARC() == 0) {
                    cc.setSEC_ARC(secArch);
                }

                cc.setSEC_INT(secInt);
                if (cc.getSEC_INT() == 0) {
                    cc.setSEC_INT(secInt);
                }
                secInt++;
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
    public GetLayoutEstatus15DTO generarArchivo(Integer diasProteccion, Integer secuencialArch, Long sucursal, Long cuenta) throws GenericException {
        log.info("generarArchivo  ::  init  ");
        List<Estatus15TmpDTO> est15tmp = new ArrayList<>();
        List<AgregarListaEstatus15DTO> listaFinal = new ArrayList<>();
        List<CatClavesProductosDTO> listaProductos = new ArrayList<>();
        List<CatServiciosPronosticosDTO> listServicios = new ArrayList<>();
        Date fechaCarga = new Date();
        List<AddListFinalEstatus15DTO> listaFinalE15 = new ArrayList<>();
        GetLayoutEstatus15DTO response = new GetLayoutEstatus15DTO();

        try {
            est15tmp = estatus15TmpJDBCRepository.findAll();
            log.info("est15TMP:: ", est15tmp.toArray().length);
            log.info(est15tmp.toString());

            est15tmp.forEach(le -> {
                log.info("est15TMP foreach:: " + le.getLlave());
                List<MaestroDeComisiones> listaConsulta = new ArrayList<>();
                listaConsulta = maestroDeComisionesRepository.findByLlave(Long.parseLong(le.getLlave()));
                log.info("listaConsultaMaestroCOM consulta:: " + listaConsulta.size());
                log.info("listaConsultaMaestroCOM consulta::1 " + listaConsulta.toString());

                listaConsulta.forEach(lc -> {
                    log.info("listaConsultaMaestroCOM foreach:: ");
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

            if (listaFinal.size() > 0) {
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

            listaProductos = catClavesProductosJDBCRepository.findListProducts();
            listServicios = catServiciosPronosticoJDBCRepository.findAll();

            response = generarLayoutProteccion(listaFinal, fechaCarga, listaProductos, listServicios, secuencialArch);

            listaFinal.forEach(item -> {
                AddListFinalEstatus15DTO cf = new AddListFinalEstatus15DTO();
                cf.setLlave(item.getLlave().toString());
                cf.setSecArch(item.getSEC_ARC());
                cf.setSecInt(item.getSEC_INT());
                cf.setKeyUp(item.getSEC_ARC().toString().concat("_").concat(item.getSEC_INT().toString()));
                listaFinalE15.add(cf);
            });

            try {
                estatus15JDBCRepository.batchInsert(listaFinalE15);
            } catch (Exception e) {
                throw new GenericException("No se realizó la inserción en BD :: ", HttpStatus.NOT_FOUND.toString());
            }

        } catch (Exception e) {
            throw new GenericException("No se pudo generar el archivo :: " + e, HttpStatus.NOT_FOUND.toString());
        }

        return response;
    }

    public GetLayoutEstatus15DTO generarLayoutProteccion(List<AgregarListaEstatus15DTO> listaCarga, Date fechaCarga, List<CatClavesProductosDTO> listaProductos, List<CatServiciosPronosticosDTO> listaCatServicios, Integer secuencialArch) throws GenericException {
        log.info("fechaCarga :: " + fechaCarga);
        String ecoder = "";
        String noEmisor = ConstantUtils.EMISOR_PRONOSTICOS;
        String refEmisor = ConstantUtils.REF_EMISOR_PERFIL;
        String ctaAbono = ConstantUtils.CTA_ABONO_PERFIL;
        String strFecha = FormatUtils.formatDateSinEspacios2(fechaCarga);
        System.out.println("fechaXX :: " + strFecha);
        Integer numElementosOriginal = listaCarga.size();
        Integer countCuentaAlterna = 0;
        Integer bloqueados = 0;
        Integer numChequeraFija = 0;

        try {
            Integer secInt = 2;
            Integer secArch = 0;
            secArch = secuencialArch;
            List<AgregarListaEstatus15DTO> listaFinal = new ArrayList<>();
            listaFinal = listaCarga;

            for (AgregarListaEstatus15DTO item : listaFinal) {
                if (secInt == 65002) {
                    secArch++;
                    secInt = 2;
                }

                item.setSEC_ARC(secArch);
                if (item.getSEC_ARC() == 0) {
                    item.setSEC_ARC(secArch);
                }

                item.setSEC_INT(secInt);
                if (item.getSEC_INT() == 0) {
                    item.setSEC_INT(secInt);
                }
                secInt++;
            }

            log.info("Antes de  finalListaFinal 1 :: " );

            Set<Integer> listaArchivos = new HashSet<>();
            listaFinal.removeIf(c -> !listaArchivos.add(c.getSEC_ARC()));

            log.info("listaArchivos 1 :: " + listaArchivos.toString());
            log.info("listaArchivos.size() 2:: " + listaArchivos.size());

            for (Integer noArch : listaArchivos) {
                log.info("noArch *** :: " + noArch);

                Path fileLayoutArchivosProteccion = Files.createTempFile("Protec_ESTATUS15_" + noArch.toString() + "_" + FormatUtils.formatDateSinEspacios2(fechaCarga), ".txt");
                fileLayoutArchivosProteccion.toFile().deleteOnExit();
                FileOutputStream fileArchivosProteccion = new FileOutputStream(fileLayoutArchivosProteccion.toFile());

                List<AgregarListaEstatus15DTO> subListaArchivos = listaFinal.stream().filter(x -> x.getSEC_ARC() == noArch).collect(Collectors.toList());
                subListaArchivos.sort(Comparator.comparing(AgregarListaEstatus15DTO::getSEC_INT));
                log.info("subListaArchivos 1 :: " + subListaArchivos.toString());
                String content = "";

                /*HEADER*/
                //region HEADER
                log.info("HEADER init :: ");

                content += "01" + "0000001" + "01" + "002" + "E" + "3" + "0000000" + strFecha + "01" + "00";
                for (int i = 1; i <= 25; i++) {
                    content += ' ';
                }
                //razón social, 40 espacios= nombre+10
                content += "BANCO NACIONAL DE MEXICO, S.A.";
                for (int i = 1; i <= 10; i++) {
                    content += ' ';
                }
                //rfc + 6 espacios 18 espacios
                content += "BNM840515VB1";
                for (int i = 1; i <= 6; i++) {
                    content += ' ';
                }
                //uso futuro
                for (int i = 1; i <= 182; i++) {
                    content += ' ';
                }
                //emisor
                content += "000000" + noEmisor;
                //secuencia de archivo, el de la cabecera siempre empieza en 01 y va progresando por archivo
                content += String.format("%02d", noArch);

                for (int i = 1; i <= 226; i++) {
                    content += ' ';
                }
                content += ("\n");
                fileArchivosProteccion.write(content.getBytes(StandardCharsets.UTF_8));
                log.info("HEADER END :: ");
                //endregion

                /*DETALLE*/
                //region DETALLE
                log.info("DETALLE Init :: ");
                String strImporte = "", strIVAa = "";
                for (AgregarListaEstatus15DTO item : subListaArchivos) {
                    log.info("subListaArchivos 2 for :: " + item.toString());
                    //Bloque 1
                    log.info("Bloque 1 :: ");
                    content += "02" + String.format("%07d", item.getSEC_INT()) + "01" + "01";

                    //importe de operacion
                    log.info("importe de operacion 1 :: ");
                    Double iimporte = item.getmComision().doubleValue();
                    log.info("importe de operacion 2 :: ");
                    iimporte = Math.round(iimporte * 100.0) / 100.0;
                    log.info("importe de operacion 3 :: ");
                    String[] auxIimporte = String.valueOf(iimporte).split("\\.");
                    strImporte = auxIimporte[0];
                    log.info("importe de operacion 4 :: " + item.getmComision().toString());
                    if (item.getmComision().toString().contains(".")) {
                        log.info("importe de operacion 5 :: ");
                        String[] auxContdecim;
                        auxContdecim = item.getmComision().toString().split("\\.");
                        int contdecim = auxContdecim[1].length();
                        log.info("importe de operacion 6 :: ");
                        if (contdecim == 1) {
                            String[] auxStrImporte;
                            auxStrImporte = item.getmComision().toString().split("\\.");
                            strImporte += auxStrImporte[1].substring(0, 1) + "0";
                        }
                        if (contdecim > 1) {
                            String[] auxStrImporte;
                            auxStrImporte = item.getmComision().toString().split("\\.");
                            strImporte += auxStrImporte[1].substring(0, 2);
                        }
                    }

                    if (!item.getmComision().toString().contains(".")) {
                        strImporte += "00";
                    }

                    Double strDImporte = Double.parseDouble(strImporte);
                    log.info("importe de operacion 2 init :: ");
                    content += String.format("%015d", strDImporte.intValue());
                    log.info("importe de operacion 2 end :: ");

                    //periodo de proteccion
                    log.info("periodo de proteccion 3_1:: ");
                    content += strFecha + String.format("%03d", item.getDias());
                    log.info("periodo de proteccion 3_2:: ");

                    //numero de cliente
                    log.info("periodo de proteccion 4_1:: ");
                    content += String.format("%012d", item.getNoCliente());
                    for (int i = 1; i <= 9; i++) {
                        content += ' ';
                    }
                    log.info("numero de cliente 4_2:: ");

                    //Bloque 2
                    log.info("Bloque 2 init :: ");
                    content += "52";
                    String aux = item.getCuenta().toString();
                    Long cuenta = 0L;
                    int sucursal = 0;
                    if (aux.contains("   ")) {
                        String[] sucuen = item.getCuenta().toString().split(" ");
                        int i = 0;
                        for (String cad : sucuen) {
                            i++;
                            if (i == 1) {
                                sucursal = Integer.parseInt(cad);
                            } else {
                                if (i > 1 && !cad.equals("")) {
                                    cuenta = Long.parseLong(cad);
                                }
                            }
                        }
                        content += "00000000" + "002" + "01" + "000000000" + String.format("%04d", Long.parseLong(String.valueOf(sucursal))) + String.format("%07d", Long.parseLong(String.valueOf(cuenta)));  //item.CTA_ORIGEN.Replace("/" , "" ) );
                    } else {
                        content += "00000000" + "002" + "01" + "000000000" + String.format("%04d", Long.parseLong(String.valueOf(sucursal))) + String.format("%07d", Long.parseLong(String.valueOf(cuenta)));  //item.CTA_ORIGEN.Replace("/" , "" ) );
                    }
                    log.info("Bloque 2 end :: ");

                    //Bloque 3
                    log.info("Bloque 3 init :: ");
                    //nombre de cliente
                    for (int i = 1; i <= 40; i++) {
                        content += ' ';
                    }
                    //referencia de emisor
                    content += "0000000000000000" + refEmisor;
                    //20 uso futuro, 40 nombre de titular
                    for (int i = 1; i <= 60; i++) {
                        content += ' ';
                    }
                    log.info("Bloque 3 end :: ");

                    //Bloque 4
                    log.info("Bloque 4 init :: ");
                    //saldo de proteccion
                    content += "000000000000000";
                    //ref numerica del emisor
                    content += String.format("%07d", item.getSEC_INT() - 1);

                    String claveServicio = "";

                    String servicio = "";
                    String auxiliar = item.getComEc().toString().trim();

                    if (auxiliar.contains("DOMI") || auxiliar.contains("COBU")) {
                        claveServicio = item.getComEc().toString().trim();
                    } else {
                        List<CatServiciosPronosticosDTO> listaCatServiciosAux = new ArrayList<>();
                        listaCatServiciosAux = listaCatServicios.stream().filter(x -> x.getIdServicio() == item.getIdServicio() && x.getIdOndemand() == item.getIdOndemand()).collect(Collectors.toList());
                        for (CatServiciosPronosticosDTO itemcatser : listaCatServiciosAux) {
                            servicio = itemcatser.getServicio();
                        }
                        claveServicio = retornaClaveServicio(servicio.toString(), listaProductos);
                    }

                    content += claveServicio;//Servicio

                    if (!auxiliar.contains("DOMI") || !auxiliar.contains("COBU")) {
                        content += String.format("%02d", item.getMes());
                        content += retornaAnio(item.getAnio().toString());
                    }

                    String cade = claveServicio + String.format("%02d", item.getMes()) + retornaAnio(item.getAnio().toString());
                    int tam = 40 - cade.length();
                    for (int i = 1; i <= tam; i++) {
                        content += ' ';
                    }
                    log.info("Bloque 4 end :: ");

                    //Bloque 5
                    log.info("Bloque 5 init :: ");
                    //motivo de rechazo
                    content += "0000";
                    //uso futuro
                    for (int i = 1; i <= 7; i++) {
                        content += ' ';
                    }
                    log.info("Bloque 5 end :: ");

                    //Bloque 6
                    log.info("Bloque 6 init :: ");
                    //no de proteccion
                    content += "000000000000";
                    //uso futuro
                    for (int i = 1; i <= 11; i++) {
                        content += ' ';
                    }
                    log.info("Bloque 6 end :: ");

                    //Bloque 7
                    log.info("Bloque 7 init :: ");
                    //fecha inicio proteccion
                    content += strFecha;
                    //Secuencial del archivo
                    content += String.format("%02d", noArch);

                    // ref1 20+ ref2 20+ ref3 20+ desc rechazo 40
                    for (int i = 1; i <= 100; i++) {
                        content += ' ';
                    }
                    log.info("Bloque 7 end :: ");

                    //Bloque 8
                    log.info("Bloque 8 init :: ");
                    //contrato del cliente
                    content += "000000000000";
                    //numero de concepto
                    content += refEmisor;
                    //subconcepto lleva un 01 no más por que si (si es en protec de cobros)
                    content += "01";
                    //Número de mes de cobro
                    log.info("Bloque 8  :: Número de mes de cobro ");
                    String year = FormatUtils.formatDatedmy(fechaCarga).substring(6, 10);
                    String month = FormatUtils.formatDatedmy(fechaCarga).substring(3, 5);
                    log.info("Bloque 8  :: year " + year);
                    log.info("Bloque 8  :: year " + month);
                    content += String.format("%04d", Integer.valueOf(year)) + String.format("%02d", Integer.valueOf(month));
                    log.info("Formats of fecha:::" + String.format("%04d", Integer.valueOf(year)) +" "+ String.format("%02d", Integer.valueOf(month)));

                    //secuencial del registro original
                    log.info("Bloque 8  :: secuencial del registro original 1");
                    content += String.format("%07d", item.getSEC_INT() - 1);
                    log.info("Bloque 8  :: secuencial del registro original 2");

                    //cta concentradora
                    content += "00000" + ctaAbono;


                    //tipo de cta de abono
                    content += "01";

                    //concepto de cobro y subconcepto de cobro, el subconcepto es 01 no más
                    content += "5501" + "01";

                    Double ivaa = item.getmIva().doubleValue();
                    ivaa = Math.round(ivaa * 100.0) / 100.0;

                    //importe 15 ceros del IVA
                    log.info("Bloque 8  :: importe 15 ceros del IVA 1");
                    String[] auxStrIVAa;
                    auxStrIVAa = String.valueOf(ivaa).split("\\.");
                    strIVAa = auxStrIVAa[0];
                    log.info("Bloque 8  :: importe 15 ceros del IVA 2");

                    if (item.getmIva().toString().contains(".")) {
                        String[] auxContdecim;
                        auxContdecim = item.getmIva().toString().split("\\.");
                        int contdecim = auxContdecim[1].length();
                        if (contdecim == 1) {
                            String[] auxstrIVAa;
                            auxstrIVAa = item.getmIva().toString().split("\\.");
                            strIVAa += auxstrIVAa[1].substring(0, 1) + "0";
                        }
                        if (contdecim > 1) {
                            String[] auxstrIVAa;
                            auxstrIVAa = item.getmIva().toString().split("\\.");
                            strIVAa += auxstrIVAa[1].substring(0, 2);
                        }
                    }
                    log.info("Bloque 8  :: importe 15 ceros del IVA 3");
                    if (!item.getmIva().toString().contains(".")) {
                        strIVAa += "00";
                    }
                    log.info("Bloque 8  :: importe 15 ceros del IVA 4");

                    Double strDIVAa = Double.parseDouble(strIVAa);
                    content += String.format("%015d", strDIVAa.intValue());
                    log.info("Bloque 8  :: importe 15 ceros del IVA 5");

                    //uso futuro 49 espacios
                    for (int i = 1; i <= 49; i++) {
                        content += ' ';
                    }
                    log.info("Bloque 8 end :: ");
                    content += ("\n");
                    fileArchivosProteccion.write(content.getBytes(StandardCharsets.UTF_8));
                }
                //endregion

                /*FOOTER*/
                //region FOOTER
                log.info("FOOTER Init :: ");
                //tipo de registro
                content = "09";
                //secuencial del ultimo registro como 8001
                AgregarListaEstatus15DTO maxElement = Collections.max(subListaArchivos, Comparator.comparingInt(AgregarListaEstatus15DTO::getSEC_INT));
                content += (1 + String.format("%07d", maxElement.getSEC_INT()));
                log.info("MAX:: " + maxElement.getSEC_INT() + " FORMAT:: " + String.format("%07d", maxElement.getSEC_INT()) );
                //codigo de operacion
                content += "00";
                //numero de bloque
                content += "0000000";
                //numero de protecciones
                content += String.format("%07d", subListaArchivos.size());
                //TOTAL DE IMPORTE POR OPERACIONES
                Double total = subListaArchivos.stream().mapToDouble(x -> x.getmTotal()).sum();
                log.info("totalSSS :: " + total);
                total = Math.round(total * 100.0) / 100.0;
                String[] auxStrImporte;
                auxStrImporte = String.valueOf(total).split("\\.");
                strImporte = auxStrImporte[0];
                if (String.valueOf(total).contains(".")) {
                    String[] auxContdecim;
                    auxContdecim = String.valueOf(total).split("\\.");
                    int contdecim = auxContdecim[1].length();
                    if (contdecim == 1) {
                        String[] auxStrImporte1;
                        auxStrImporte1 = String.valueOf(total).split("\\.");
                        strImporte += auxStrImporte1[1].substring(0, 1) + "0";
                    }
                    if (contdecim > 1) {
                        String[] auxStrImporte1;
                        auxStrImporte1 = String.valueOf(total).split("\\.");
                        strImporte += auxStrImporte1[1].substring(0, 2);
                    }
                }
                if (!String.valueOf(total).contains(".")) {
                    strImporte += "00";
                }
                Double strDIVAa = Double.parseDouble(strImporte);
                content += String.format("%018d", strDIVAa.intValue());

                //uso futuro
                for (int i = 1; i <= 17; i++) {
                    content += ' ';
                }
                //uso futuro
                for (int i = 1; i <= 240; i++) {
                    content += ' ';
                }
                //uso futuro
                for (int i = 1; i <= 240; i++) {
                    content += ' ';
                }

                content += ("\n");
                fileArchivosProteccion.write(content.getBytes(StandardCharsets.UTF_8));
                log.info("FOOTER END :: ");
                //endregion
                ecoder = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(fileLayoutArchivosProteccion.toFile()));
                log.info("ecoder ::  " + ecoder);
            }
        } catch (Exception e) {
            throw new GenericException("No se completó la creación del archivo txt :: " + e, HttpStatus.NOT_FOUND.toString());
        }

        GetLayoutEstatus15DTO response = new GetLayoutEstatus15DTO();
        response.setMensajeInfo("El archivo se generó exitosamente , Total:" + numElementosOriginal.toString() + " con " + countCuentaAlterna.toString() + " cuentas alternas y " + bloqueados.toString() + " bloqueos, Chequera Fija: " + numChequeraFija.toString()); //El archivo se generó exitosamente, con un total de (total de núm. de registros)
        response.setFile(ecoder);
        return response;
    }

    public String retornaClaveServicio(String producto, List<CatClavesProductosDTO> listaProductos) {
        String clave = "";

        List<CatClavesProductosDTO> listaAux = listaProductos.stream().filter(x -> x.getProducto().toUpperCase() == producto.toUpperCase()).collect(Collectors.toList());

        for (CatClavesProductosDTO item : listaAux) {
            clave = item.getClave();
        }
        return clave;
    }

    public String retornaAnio(String cadena) {
        String aux = "";
        if (cadena.length() == 4) {
            aux = cadena.substring(2, 2);
        } else {
            aux = cadena;
        }
        return aux;
    }

    @Override
    public MensajeDTO subirRespuestas(String file) throws GenericException, IOException, ParseException {
        log.info("subirRespuestas_Estatus15 ::  init");
        Path testFile = Files.createTempFile("estatus15RespuestasZip", ".zip");
        testFile.toFile().deleteOnExit();
        byte[] decoder = Base64.getDecoder().decode(file);
        Files.write(testFile, decoder);
        ZipFile zipFile = new ZipFile(testFile.toFile());
        Enumeration<?> enu = zipFile.entries();
        String procesados = "";
        while (enu.hasMoreElements()) {
            ZipEntry zipEntry = (ZipEntry) enu.nextElement();
            String name = zipEntry.getName();
            if (name.endsWith("/") || name.startsWith("__MACOSX")) {
                continue;
            }
            InputStream inptStrm = zipFile.getInputStream(zipEntry);
            Path tempFile = Files.createTempFile("ListaDatosInsumosTempRespEst15", ".txt");
            tempFile.toFile().deleteOnExit();
            try (FileOutputStream fOS = new FileOutputStream(tempFile.toFile())) {
                IOUtils.copy(inptStrm, fOS);
                //Lectura de archivo TXT
            }
        }
        zipFile.close();
        MensajeDTO response = new MensajeDTO();
        response.setMensajeConfirm(procesados);
        response.setMensajeInfo("Se actualiza");
        return response;
    }
}