package com.citi.euces.pronosticos.services;

import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.dto.RebNumProtectDTO;
import com.citi.euces.pronosticos.infra.dto.RebajaFileOndemandDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.infra.utils.FormatUtils;
import com.citi.euces.pronosticos.repositories.RebNumProtectJDBCRepository;
import com.citi.euces.pronosticos.repositories.RebNumProtectRepository;
import com.citi.euces.pronosticos.repositories.RebajaPronosticoJDBCRepository;
import com.citi.euces.pronosticos.services.api.RebajasService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.*;
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

    @Override
    public MensajeDTO aplicarRebajaloadFile(String file, String fechaContable, String fechaMovimiento) throws
            GenericException, IOException, ParseException {
        log.info("aplicarRebajaloadFile ::  init");
        Path testFile = Files.createTempFile( "rebajasZip", ".zip");
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
        String p_numRegCargados = "";
        try {
            p_numRegCargados = rebNumProtectJDBCRepository.updateMaestoComisionesSp();
       }catch (Exception e){
           throw new GenericException(
                   "Error al llamar SP :: " , HttpStatus.NOT_FOUND.toString());
       }
        MensajeDTO mensjageResponse = new MensajeDTO();
        mensjageResponse.setMensajeInfo("Confirmando, Actualizando maestro de comisiones: ".concat(p_numRegCargados).concat(" rebajados"));
        return mensjageResponse;
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
                    Double suma = Double.parseDouble(cadena.substring(95, 108).replace(",",""));
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
                    "Error al guardar datos :: " , HttpStatus.NOT_FOUND.toString());
        }
        responseMessage =" Abono Total: " + sumaImporte.toString() + " Elementos totales a cargar:" + content.size() + " de un total de:"
                + contentInint + " elementos.";
        return responseMessage;
    }

}
