package com.citi.euces.pronosticos.services;

import com.citi.euces.pronosticos.entities.CatCausaRechazo;
import com.citi.euces.pronosticos.entities.MaestroDeComisiones;
import com.citi.euces.pronosticos.entities.MaestroDeComisionesView;
import com.citi.euces.pronosticos.infra.dto.*;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.infra.utils.ConstantUtils;
import com.citi.euces.pronosticos.infra.utils.FormatUtils;
import com.citi.euces.pronosticos.repositories.*;
import com.citi.euces.pronosticos.services.api.RebajasService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
@Transactional
public class RebajasServiceImp implements RebajasService {

    static final Logger log = LoggerFactory.getLogger(RebajasServiceImp.class);

    @Autowired
    private RebNumProtectRepository rebNumProtectRepository;
    @Autowired
    private RebNumProtectJDBCRepository rebNumProtectJDBCRepository;
    @Autowired
    private RebajaPronosticoJDBCRepository rebajaPronosticoJDBCRepository;
    @Autowired
    private MaestroDeComisionesRepository maestroDeComisionesRepository;
    @Autowired
    private CuentasContablesJDBCRepository cuentasContablesJDBCRepository;
    @Autowired
    private CatCausaRechazoRepository CatCausaRechazoRepository;
    @Autowired
    private CatServiciosPronosticoJDBCRepository catServiciosPronosticoRepository;
    @Autowired
    private SpRebajaMaestroDeComusionesRepository spRebajaMaestroDeComusionesRepository;
    @Autowired
    private MaestroDeComisionesViewRepository maestroDeComisionesViewRepository;

    @Override
    public MensajeDTO aplicarRebajaloadFile(String file, String fechaContable, String fechaMovimiento) throws
            GenericException, IOException, ParseException {
        log.info("aplicarRebajaloadFile ::  init");
        Path testFile = Files.createTempFile("rebajasZip", ".zip");
        testFile.toFile().deleteOnExit();
        byte[] decoder = Base64.getDecoder().decode(file);
        Files.write(testFile, decoder);
        ZipFile zipFile = new ZipFile(testFile.toFile());
        Enumeration<?> enu = zipFile.entries();
        String procesados = "";
        while (enu.hasMoreElements()) {
            ZipEntry zipEntry = (ZipEntry) enu.nextElement();
            String name = zipEntry.getName();
            long size = zipEntry.getSize();
            long compressedSize = zipEntry.getCompressedSize();
            if (name.endsWith("/") || name.startsWith("__MACOSX")) {
                continue;
            }
            InputStream is = zipFile.getInputStream(zipEntry);
            Path tempFile = Files.createTempFile("pruebasTXT", ".txt");
            tempFile.toFile().deleteOnExit();
            try (FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
                IOUtils.copy(is, fos);
                //Lectura TXT
                procesados = leerArchivo(tempFile, fechaContable, fechaMovimiento);
            }
        }
        zipFile.close();
        MensajeDTO response = new MensajeDTO();
        response.setMensajeConfirm(procesados);
        response.setMensajeInfo("Se actualizará con fecha movimiento: " + fechaMovimiento + " y fecha reg. contable: " + fechaContable);
        return response;
    }

    @Override
    public MensajeDTO aplicarRebaja() throws GenericException {
        Integer numRegCargados;
        try {
            numRegCargados = spRebajaMaestroDeComusionesRepository.spRebajaMaestroComisiones();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(
                    "Error al llamar SP :: ", HttpStatus.NOT_FOUND.toString());
        }
        MensajeDTO mensjageResponse = new MensajeDTO();
        mensjageResponse.setMensajeInfo("Confirmando, Actualizando maestro de comisiones: ".concat(numRegCargados.toString()).concat(" rebajados"));
        return mensjageResponse;
    }

    @Override
    public List<ReporteCuadreDTO> reporteCuadre() throws GenericException, IOException, ParseException {
        Integer mes1 = FormatUtils.obtenerMes(3);
        Integer mes2 = FormatUtils.obtenerMes(2);
        Integer mes3 = FormatUtils.obtenerMes(1);
        Integer anio1 = FormatUtils.obtenerYear(3);
        Integer anio2 = FormatUtils.obtenerYear(2);
        Integer anio3 = FormatUtils.obtenerYear(1);
        LocalDate now = LocalDate.now();
        Integer mesActual = now.getYear();
        List<ReporteCuadreDTO> responseQuery1 = new ArrayList<>();
        List<ReporteCuadreDTO> responseQuery2 = new ArrayList<>();
        List<ReporteCuadreDTO> responseQuery3 = new ArrayList<>();
        List<ReporteCuadreDTO> responseQuery4 = new ArrayList<>();
        try {
            responseQuery1 = maestroDeComisionesRepository.getFunctionRepCuadre1(mes1, mes2, mes3, anio1);
            responseQuery2 = maestroDeComisionesRepository.getFunctionRepCuadre2(mes1, mes2, mes3, anio1, anio2, anio3);
            responseQuery3 = maestroDeComisionesRepository.getFunctionRepCuadre3(mesActual);
            responseQuery4 = maestroDeComisionesRepository.getFunctionRepCuadre4(mesActual);

        } catch (Exception e) {
            throw new GenericException(
                    "Error al llamar Funciones :: ", HttpStatus.NOT_FOUND.toString());
        }
        log.info("responseQuery1 :: " + responseQuery1.size() + "  responseQuery2 :: " + responseQuery2.size());
        log.info("responseQuery3 :: " + responseQuery3.size() + "  responseQuery4 :: " + responseQuery4.size());
        List<ReporteCuadreDTO> response = new ArrayList<>();
        response = Stream.of(responseQuery1, responseQuery2, responseQuery3, responseQuery4)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        log.info("response  :: " + response.size());
        return response;
    }

    @Override
    public ReporteRebajaDTO reporteRebaja(String fechaMovimiento, Integer page) throws GenericException, IOException {
        Pageable pageable = PageRequest.of(page, 50);
        Page<MaestroDeComisiones> listaReb = maestroDeComisionesRepository.findByFechaMovimeiento(fechaMovimiento, pageable);
        if (listaReb.isEmpty()) {
            throw new GenericException(
                    "No hay registros que coincidan con fecha Movimiento   :: " + fechaMovimiento, HttpStatus.NOT_FOUND.toString());
        }
        List<ReporteRebajaPageDTO> listReporteRebaja = new ArrayList<>();
        listaReb.forEach(lr -> {
            log.info("requestData listaReb ::> " + lr.getId().getmTotal());
            ReporteRebajaPageDTO reporteRebaja = new ReporteRebajaPageDTO();
            reporteRebaja.setNumeroCliente(lr.getId().getNoCliente().toString());
            reporteRebaja.setBlanco("");
            reporteRebaja.setChequera(lr.getId().getChequera());
            reporteRebaja.setChequeraCargo(lr.getChequeraCargo());
            reporteRebaja.setCobrado(validaChequera(reporteRebaja.getChequera(), reporteRebaja.getChequeraCargo()));//ValidaChequera
            reporteRebaja.setmTotal(lr.getId().getmTotal().toString());
            reporteRebaja.setpIva(lr.getpIva().toString());
            reporteRebaja.setCausaRechazo(getRechazo(lr.getIdCausaRechazo())); //GetRechazo
            reporteRebaja.setMes(FormatUtils.validFechaMes(lr.getId().getMes()));//RetornaMes
            reporteRebaja.setAnio(lr.getId().getAnio().toString());
            reporteRebaja.setServicio(getServicio(lr.getId().getIdServicio(), lr.getId().getIdOndemand()));
            reporteRebaja.setCsi(lr.getCsi().toString());
            reporteRebaja.setComEc(lr.getComEc().toString());
            reporteRebaja.setmComision(lr.getmComision().toString());
            reporteRebaja.setmIva(lr.getmIva().toString());
            reporteRebaja.setTotal(lr.getId().getmTotal().toString());
            reporteRebaja.setComP(lr.getComP().toString());
            reporteRebaja.setLlave(lr.getId().getLlave().toString());
            reporteRebaja.setNoProteccion(lr.getNoProteccion());
            reporteRebaja.setFranquicia(validFranquicia(lr.getIdFranquicia()));//getNombreFranquicia
            reporteRebaja.setCatalogadaGc(FormatUtils.validCatalogadaGc(Integer.valueOf(lr.getId().getCatalogadaGc())));//GetCatalogadaGc
            reporteRebaja.setfMovimiento(FormatUtils.formatDatedmy(lr.getFechaMovimiento()));
            reporteRebaja.setFecha(lr.getFechaRegistroContable());//f_registro_contable
            reporteRebaja.setCuentaContable(getCuentaContable(lr.getId().getIdServicio(), lr.getId().getIdOndemand()));
            reporteRebaja.setContrato(lr.getContrato());
            reporteRebaja.setOpenItem(lr.getOpenItem());
            listReporteRebaja.add(reporteRebaja);
        });
        log.info("listReporteRebaja :: > " + listReporteRebaja.size());
        Page<ReporteRebajaPageDTO> pageResponse = new PageImpl<>(listReporteRebaja, pageable, listaReb.getTotalPages());
        String file = "";
        if (listaReb.getTotalPages() <= 1200 && page == 0) {
            file = createFileTXTRepRebaja(listReporteRebaja);
        }
        ReporteRebajaDTO response = new ReporteRebajaDTO();
        response.setReporteRebajaPageDTO(pageResponse);
        response.setFile(file);
        return response;
    }

    @Override
    public ReporteRebajaDTO reporteRebajaSearch(String fechaMovimiento, Integer page, String search) throws GenericException, IOException {
        Pageable pageable = PageRequest.of(page, 50);
        Page<MaestroDeComisionesView> listaReb = maestroDeComisionesViewRepository.searchData(fechaMovimiento, search, pageable);
        log.info("listaRebSize:: > " + listaReb.getSize());
        if (listaReb.isEmpty()) {
            throw new GenericException(
                    "No hay registros que coincidan con fecha  :: " + fechaMovimiento + " y busqueda  :: " + search, HttpStatus.NOT_FOUND.toString());
        }
        List<ReporteRebajaPageDTO> listReporteRebaja = new ArrayList<>();
        listaReb.forEach(lr -> {
            log.info("requestData listaReb ::> " + lr.getId().getmTotal());
            ReporteRebajaPageDTO reporteRebaja = new ReporteRebajaPageDTO();
            reporteRebaja.setNumeroCliente(lr.getId().getNoCliente().toString());
            reporteRebaja.setBlanco("");
            reporteRebaja.setChequera(lr.getId().getChequera());
            reporteRebaja.setChequeraCargo(lr.getChequeraCargo());
            reporteRebaja.setCobrado(validaChequera(reporteRebaja.getChequera(), reporteRebaja.getChequeraCargo()));//ValidaChequera
            reporteRebaja.setmTotal(lr.getId().getmTotal().toString());
            reporteRebaja.setpIva(lr.getpIva().toString());
            reporteRebaja.setCausaRechazo(getRechazo(lr.getIdCausaRechazo())); //GetRechazo
            reporteRebaja.setMes(FormatUtils.validFechaMes(lr.getId().getMes()));//RetornaMes
            reporteRebaja.setAnio(lr.getId().getAnio().toString());
            reporteRebaja.setServicio(getServicio(lr.getId().getIdServicio(), lr.getId().getIdOndemand()));
            reporteRebaja.setCsi(lr.getCsi().toString());
            reporteRebaja.setComEc(lr.getComEc().toString());
            reporteRebaja.setmComision(lr.getmComision().toString());
            reporteRebaja.setmIva(lr.getmIva().toString());
            reporteRebaja.setTotal(lr.getId().getmTotal().toString());
            reporteRebaja.setComP(lr.getComP().toString());
            reporteRebaja.setLlave(lr.getId().getLlave().toString());
            reporteRebaja.setNoProteccion(lr.getNoProteccion());
            reporteRebaja.setFranquicia(validFranquicia(lr.getIdFranquicia()));//getNombreFranquicia
            reporteRebaja.setCatalogadaGc(FormatUtils.validCatalogadaGc(Integer.valueOf(lr.getId().getCatalogadaGc())));//GetCatalogadaGc
            reporteRebaja.setfMovimiento(FormatUtils.formatDatedmy(lr.getFechaMovimiento()));
            reporteRebaja.setFecha(lr.getFechaRegistroContable());//f_registro_contable
            reporteRebaja.setCuentaContable(getCuentaContable(lr.getId().getIdServicio(), lr.getId().getIdOndemand()));
            reporteRebaja.setContrato(lr.getContrato());
            reporteRebaja.setOpenItem(lr.getOpenItem());
            listReporteRebaja.add(reporteRebaja);
        });
        log.info("listReporteRebajaSearch:: > " + listReporteRebaja.size());
        Page<ReporteRebajaPageDTO> pageResponse = new PageImpl<>(listReporteRebaja, pageable, listaReb.getTotalPages());
        ReporteRebajaDTO response = new ReporteRebajaDTO();
        response.setReporteRebajaPageDTO(pageResponse);
        return response;
    }

    @Override
    public ReporteRebajaDTO reporteRebajaFile(String fechaMovimiento) throws GenericException, IOException {
        List<MaestroDeComisiones> listaReb = maestroDeComisionesRepository.findByAllFechaMovimeiento(fechaMovimiento);
        if (listaReb.isEmpty()) {
            throw new GenericException(
                    "No hay registros que coincidan con fecha Movimiento   :: " + fechaMovimiento, HttpStatus.NOT_FOUND.toString());
        }

        List<List<String>> renglones = new ArrayList<>();
        listaReb.forEach(ld -> {
            List<String> renglon = new ArrayList<String>();
            renglon.add(FormatUtils.validaString(ld.getId().getNoCliente().toString()));
            renglon.add("");
            renglon.add(FormatUtils.validaString(ld.getId().getChequera())); //chequera
            renglon.add(FormatUtils.validaString(ld.getChequeraCargo())); //chequera_cargo
            renglon.add(validaChequera(ld.getId().getChequera(), ld.getChequeraCargo())); //ValidaChequera
            renglon.add(FormatUtils.validaString(ld.getId().getmTotal().toString()));
            renglon.add(FormatUtils.validaString(ld.getpIva().toString()));
            renglon.add(getRechazo(ld.getIdCausaRechazo())); //GetRechazo
            renglon.add(FormatUtils.validFechaMes(ld.getId().getMes()));//RetornaMes
            renglon.add(FormatUtils.validaString(ld.getId().getAnio().toString()));
            renglon.add(getServicio(ld.getId().getIdServicio(), ld.getId().getIdOndemand()));//servicio
            renglon.add(FormatUtils.validaString(ld.getCsi().toString()));
            renglon.add(FormatUtils.validaString(ld.getComEc().toString()));
            renglon.add(FormatUtils.validaString(ld.getmComision().toString()));
            renglon.add(FormatUtils.validaString(ld.getmIva().toString()));
            renglon.add(FormatUtils.validaString(ld.getId().getmTotal().toString()));
            renglon.add(FormatUtils.validaString(ld.getComP().toString()));
            renglon.add(FormatUtils.validaString(ld.getId().getLlave().toString()));
            renglon.add(FormatUtils.validaString(ld.getNoProteccion()));
            renglon.add(validFranquicia(ld.getIdFranquicia()));//getNombreFranquicia
            renglon.add(FormatUtils.validCatalogadaGc(Integer.valueOf(ld.getId().getCatalogadaGc())));//GetCatalogadaGc
            renglon.add(FormatUtils.formatDatedmy(ld.getFechaMovimiento()));
            renglon.add(ld.getFechaRegistroContable());//f_registro_contable
            renglon.add(getCuentaContable(ld.getId().getIdServicio(), ld.getId().getIdOndemand()));
            renglon.add(FormatUtils.validaString(ld.getContrato()));
            renglon.add(FormatUtils.validaString(ld.getOpenItem()));
            renglones.add(renglon);
        });
        List<String> titles = Arrays.asList(ConstantUtils.TITLE_REP_REBAJA_EXCEL);
        Path fileReporteRebajaZip = FormatUtils.convertZip(FormatUtils.createExcel(titles, renglones));
        String ecoder = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(fileReporteRebajaZip.toFile()));
        log.info("File Encoder ReporteRebaja.zip :: " + ecoder);
        ReporteRebajaDTO response = new ReporteRebajaDTO();
        response.setFile(ecoder);
        return response;
    }

    @Override
    public MensajeDTO addMora(String fechaMovimiento) throws GenericException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localFecha = LocalDate.parse(fechaMovimiento, formatter);
        Integer mes = new Integer(localFecha.getMonth().getValue());
        Integer anio = new Integer(localFecha.getYear());
        int updateMaestroComisiones = 0;
        try {
            updateMaestroComisiones = maestroDeComisionesRepository.updateCatalogadaGc(anio, mes);

        } catch (Exception e) {
            throw new GenericException(
                    "Error al Actualizar Maestro de Comisiones  addMora :: " + fechaMovimiento, HttpStatus.NOT_FOUND.toString());
        }
        MensajeDTO response = new MensajeDTO();
        response.setMensajeConfirm("Confirmando, Actualizando maestro de comisiones: " + updateMaestroComisiones + " rebajados");
        return response;
    }

    public String leerArchivo(Path tempFile, String fechaContable, String fechaMovimiento) throws IOException, GenericException, ParseException {
        String responseMessage = "";
        String cadena;
        FileReader f = new FileReader(tempFile.toFile());
        BufferedReader b = new BufferedReader(f);
        List<RebajaFileOndemandDTO> listaCargo = new ArrayList<RebajaFileOndemandDTO>();
        List<RebNumProtectDTO> content = new ArrayList<RebNumProtectDTO>();
        Double sumaImporte = 0.0;
        rebajaPronosticoJDBCRepository.truncateTable();
        while ((cadena = b.readLine()) != null) {
            if (cadena.contains("120983/") || cadena.contains("120984/")) {
                if (cadena.substring(132, 135).contains("CGO")) {
                    Double suma = Double.parseDouble(cadena.substring(95, 108).replace(",", ""));
                    sumaImporte = sumaImporte + suma;
                    RebNumProtectDTO data = new RebNumProtectDTO();
                    data.setNumProteccion(Long.parseLong(cadena.substring(14, 26)));
                    data.setFechaMovimiento(FormatUtils.stringToDate(fechaMovimiento));
                    data.setFechaRegistroContable(FormatUtils.stringToDate(fechaContable));
                    content.add(data);
                }
            }
        }
        b.close();
        Integer contentInint = content.size();
        log.info("RebNumProtectDTO content init  ::  " + content.size());
        Set<Long> contentSet = new HashSet<>();
        content.removeIf(c -> !contentSet.add(c.getNumProteccion()));
        log.info("RebNumProtectDTO content Final  ::  " + content.size());
        log.info("RebNumProtectDTO sumaImporte  ::  " + sumaImporte);
        content.sort(Comparator.comparing(RebNumProtectDTO::getNumProteccion));
        try {
            rebNumProtectJDBCRepository.batchInsert(content, 500);
        } catch (Exception e) {
            throw new GenericException(
                    "Error al guardar datos :: ", HttpStatus.NOT_FOUND.toString());
        }
        responseMessage = " Abono Total: " + sumaImporte.toString() + " Elementos totales a cargar:" + content.size() + " de un total de:"
                + contentInint + " elementos.";
        return responseMessage;
    }


    private String validaChequera(String chequera1, String chequera2) {
        String validString = "";
        if ((chequera1 != null && chequera2 != null) && (!chequera1.equalsIgnoreCase("")
                || !chequera2.equalsIgnoreCase(""))) {
            validString = "ALTERNA";
            if (chequera1.equalsIgnoreCase(chequera2)) {
                validString = "EJE";
            }
        }
        return validString;
    }

    private String getRechazo(Integer idRechazo) {
        String resp = "";
        List<CatCausaRechazo> listaRechazos = CatCausaRechazoRepository.findAll();
        log.info("listaRechazos size :: " + listaRechazos.size());
        if (!listaRechazos.isEmpty()) {
            List<CatCausaRechazo> validRechazos = listaRechazos.stream().filter(lr -> lr.getIdCausaRechazo().intValue() == idRechazo).collect(Collectors.toList());
            resp = validRechazos.get(validRechazos.size() - 1).getCausa();
        }
        return resp;
    }

    private String getServicio(Long idServicio, Long idOndemand) {
        String resp = "";
        List<CatServiciosPronosticosDTO> listaCatServicios = catServiciosPronosticoRepository.findAll();
        log.info("listaCatServicios size :: " + listaCatServicios.size());
        if (!listaCatServicios.isEmpty()) {
            List<CatServiciosPronosticosDTO> servicios = listaCatServicios.stream().filter(l -> l.getIdServicio() == idServicio.longValue()
                    && l.getIdOndemand() == idOndemand.longValue()).collect(Collectors.toList());
            resp = servicios.get(servicios.size() - 1).getServicio();
        }
        return resp;
    }

    private String validFranquicia(Integer idFranquicia) {
        String valid = "";
        if (!idFranquicia.equals("")) {
            valid = FormatUtils.getCatFranquicias().get(idFranquicia) != null ? FormatUtils.getCatFranquicias().get(idFranquicia) : "";
        }
        return valid;
    }

    private String getCuentaContable(Long idServicio, Long idOndemand) {
        String resp = "";
        List<CuentasContablesDTO> listaCuentasContables = cuentasContablesJDBCRepository.findAll();
        log.info("cuentasContables size :: " + listaCuentasContables.size());
        if (!listaCuentasContables.isEmpty()) {
            List<CuentasContablesDTO> ctaContables = listaCuentasContables.stream().filter(c -> c.getIdServicio() == idServicio.longValue()
                    && c.getIdOndemand() == idOndemand.longValue()).collect(Collectors.toList());
            for (CuentasContablesDTO cc : ctaContables) {
                resp = (cc.getProducto() == null || cc.getProducto().equalsIgnoreCase("")) ? cc.getCuenta().toString() : cc.getCuenta().toString().concat("-").concat(cc.getProducto());
            }
            ;
        }
        return resp;
    }

    private String createFileTXTRepRebaja(List<ReporteRebajaPageDTO> listReporteRebaja) throws IOException {
        Path fileReporteRebaja = Files.createTempFile("Reporterebaja", ".txt");
        fileReporteRebaja.toFile().deleteOnExit();
        FileOutputStream test = new FileOutputStream(fileReporteRebaja.toFile());
        String content = "";
        test.write(ConstantUtils.ENCABEZADO_REP_REBAJA.getBytes(StandardCharsets.UTF_8));
        for (ReporteRebajaPageDTO str : listReporteRebaja) {
            content = "";
            content += str.getNumeroCliente().concat("\t").concat(str.getBlanco()).concat("\t").concat(str.getChequera()).concat("\t");
            content += str.getChequeraCargo().concat("\t").concat(str.getCobrado()).concat("\t").concat(str.getmTotal()).concat("\t");
            content += str.getpIva().concat("\t").concat(str.getCausaRechazo()).concat("\t").concat(str.getMes()).concat("\t");
            content += str.getAnio().concat("\t").concat(str.getServicio()).concat("\t").concat(str.getCsi()).concat("\t");
            content += str.getComEc().concat("\t").concat(str.getmComision()).concat("\t").concat(str.getmIva()).concat("\t");
            content += str.getTotal().concat("\t").concat(str.getComP()).concat("\t").concat(str.getLlave()).concat("\t");
            content += str.getNoProteccion().concat("\t").concat(str.getFranquicia()).concat("\t").concat(str.getCatalogadaGc()).concat("\t");
            content += str.getfMovimiento().concat("\t").concat(str.getFecha()).concat("\t").concat(str.getCuentaContable()).concat("\t");
            content += str.getContrato().concat("\n");
            test.write(content.getBytes(StandardCharsets.UTF_8));
        }
        Path fileReporteRebajaZip = FormatUtils.convertZip(fileReporteRebaja);
        String ecoder = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(fileReporteRebajaZip.toFile()));
        log.info("File Encoder ReporteRebaja.zip :: " + ecoder);
        return ecoder;
    }


}
