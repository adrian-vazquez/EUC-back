package com.citi.euces.pronosticos.services;

import com.citi.euces.pronosticos.entities.CatCausaRechazo;
import com.citi.euces.pronosticos.entities.MaestroDeComisiones;
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
import java.util.*;
import java.util.stream.Collectors;
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
    public ReporteRebajaDTO reporteRebaja(String fechaMovimiento, Integer page) throws GenericException, IOException {
        Pageable pageable = PageRequest.of(page, 50);
        Page<MaestroDeComisiones> listaReb = maestroDeComisionesRepository.findByFechaMovimeiento(fechaMovimiento,pageable);
        List<ReporteRebajaPageDTO> listReporteRebaja = new ArrayList<>() ;
        List<CuentasContablesDTO> listaCuentasContables = cuentasContablesJDBCRepository.findAll();
        log.info("cuentasContables size :: " + listaCuentasContables.size());
        List<CatCausaRechazo> listaRechazos = CatCausaRechazoRepository.findAll();
        log.info("listaRechazos size :: " + listaRechazos.size());
        List<CatServiciosPronosticosDTO> listaCatServicios = catServiciosPronosticoRepository.findAll();
        log.info("listaCatServicios size :: " + listaCatServicios.size());

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
            reporteRebaja.setCausaRechazo(getRechazo(lr.getIdCausaRechazo(),listaRechazos)); //GetRechazo
            reporteRebaja.setMes(FormatUtils.validFechaMes(lr.getId().getMes()));//RetornaMes
            reporteRebaja.setAnio(lr.getId().getAnio().toString());
            reporteRebaja.setServicio(getServicio(lr.getId().getIdServicio(), lr.getId().getIdOndemand(),listaCatServicios));
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
            reporteRebaja.setCuentaContable(getCuentaContable(listaCuentasContables,lr.getId().getIdServicio(), lr.getId().getIdOndemand() ));
            reporteRebaja.setContrato(lr.getContrato());
            reporteRebaja.setOpenItem(lr.getOpenItem());
            listReporteRebaja.add(reporteRebaja);
        });
        log.info("listReporteRebaja :: > "+listReporteRebaja.size());
        Page<ReporteRebajaPageDTO> pageResponse = new PageImpl<>(listReporteRebaja, pageable, listaReb.getTotalPages());
        String file ="";
        //if(listaReb.getTotalPages() <= 1200) {
            file = createFileRepRebaja(listReporteRebaja);
        //}
        ReporteRebajaDTO response = new ReporteRebajaDTO();
        response.setReporteRebajaPageDTO(pageResponse);
        response.setFile(file);
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

    private String getRechazo(Integer idRechazo, List<CatCausaRechazo> listaRechazos) {
        String resp = "";
        if(!listaRechazos.isEmpty()){
            List<CatCausaRechazo> validRechazos = listaRechazos.stream().filter(lr -> lr.getIdCausaRechazo().intValue() == idRechazo).collect(Collectors.toList());
            resp = validRechazos.get(validRechazos.size() - 1).getCausa();
        }
        return resp;
    }

    private String getServicio(Long idServicio, Long idOndemand, List<CatServiciosPronosticosDTO> listaCatServicios) {
        String resp = "";
        if(!listaCatServicios.isEmpty()){
            List<CatServiciosPronosticosDTO> servicios = listaCatServicios.stream().filter(l-> l.getIdServicio() == idServicio.longValue()
                    && l.getIdOndemand() == idOndemand.longValue()).collect(Collectors.toList());
            resp = servicios.get(servicios.size() - 1).getServicio();
        }
        return resp;
    }

    private String validFranquicia(Integer idFranquicia){
        String valid = "";
        if(!idFranquicia.equals("")) {
            valid = FormatUtils.getCatFranquicias().get(idFranquicia) != null ? FormatUtils.getCatFranquicias().get(idFranquicia) : "";
        }
        return valid;
    }

    private String getCuentaContable(List<CuentasContablesDTO> listaCuentasContables,Long idServicio, Long idOndemand) {
        String resp = "";
        if(!listaCuentasContables.isEmpty()){
            List<CuentasContablesDTO> ctaContables = listaCuentasContables.stream().filter(c -> c.getIdServicio() == idServicio.longValue()
                    && c.getIdOndemand() == idOndemand.longValue()).collect(Collectors.toList());
            for(CuentasContablesDTO cc  : ctaContables){
                 resp = (cc.getProducto() == null || cc.getProducto().equalsIgnoreCase("")) ? cc.getCuenta().toString() : cc.getCuenta().toString().concat("-").concat(cc.getProducto());
            };
        }
        return resp;
    }

    private String createFileRepRebaja(List<ReporteRebajaPageDTO> listReporteRebaja) throws IOException {
        Path fileReporteRebaja = Files.createTempFile("Reporterebaja", ".txt");
        fileReporteRebaja.toFile().deleteOnExit();
        FileOutputStream test = new FileOutputStream(fileReporteRebaja.toFile());
        String content = "";
        test.write(ConstantUtils.ENCABEZADO_REP_REBAJA.getBytes(StandardCharsets.UTF_8));
        for(ReporteRebajaPageDTO str: listReporteRebaja) {
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
        log.info("File Encoder ReporteRebaja.zip :: "+ ecoder);
        return ecoder;
    }



}
