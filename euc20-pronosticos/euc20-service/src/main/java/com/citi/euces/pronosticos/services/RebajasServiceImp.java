package com.citi.euces.pronosticos.services;

import com.citi.euces.pronosticos.entities.RebNumProtect;
import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.dto.RebajaFileOndemandDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.repositories.RebNumProtectRepository;
import com.citi.euces.pronosticos.services.api.RebajasService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
@Transactional
public class RebajasServiceImp implements RebajasService {
    static final Logger log = LoggerFactory.getLogger(RebajasServiceImp.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RebNumProtectRepository rebNumProtectRepository;


    @Override
    public MensajeDTO aplicarRebajaloadFile(String file) throws GenericException, IOException {
        log.info("aplicarRebajaloadFile ::  init");
        log.info("File :: " + file);
        Path testFile = Files.createTempFile(Paths.get("/home/bruce/Documentos/Marlene/IDS/Pruebas/PruebasEUC-20"), "rebajasZip", ".zip");
        testFile.toFile().deleteOnExit();
        byte[] decoder = Base64.getDecoder().decode(file);
        Files.write(testFile, decoder);
        System.out.println(testFile.toFile().getAbsoluteFile());
        System.out.println("ZIP File Saved");

        ZipFile zipFile = new ZipFile(testFile.toFile());
        Enumeration<?> enu = zipFile.entries();
        Integer procesados = 0;
        try {
            while (enu.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) enu.nextElement();

                String name = zipEntry.getName();
                long size = zipEntry.getSize();
                long compressedSize = zipEntry.getCompressedSize();

                // Do we need to create a directory ?
                if (name.endsWith("/") || name.startsWith("__MACOSX")) {
                    continue;
                }
                System.out.printf("name: %-20s | size: %6d | compressed size: %6d\n",
                        name, size, compressedSize);
                // Extract the file
                InputStream is = zipFile.getInputStream(zipEntry);
                Path tempFile = Files.createTempFile(Paths.get("/home/bruce/Documentos/Marlene/IDS/Pruebas/PruebasEUC-20"), "pruebasTXT", ".txt");
                tempFile.toFile().deleteOnExit();
                try (FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
                    IOUtils.copy(is, fos);
                    //Lectura TXT
                    procesados = leerArchivo(tempFile);
                }
            }
            zipFile.close();

        } catch (IOException  e) {
            e.printStackTrace();
        }

        MensajeDTO response = new MensajeDTO();
        response.setMensajeConfirm("Elementos totales a cargar: " + procesados + " de un total de: " + procesados + " elementos.");
        response.setMensajeInfo("Mensaje Informacion");

        return response;
    }

    @Override
    public MensajeDTO aplicarRebajaloadFileM(MultipartFile file) throws GenericException, IOException {
        log.info("file::.." + file.getInputStream());
        log.info("fileN::.." + file.getName());
        return null;
    }

    public Integer  leerArchivo(Path tempFile) throws IOException {
        String cadena;
        FileReader f = new FileReader(tempFile.toFile());
        BufferedReader b = new BufferedReader(f);
        List<RebajaFileOndemandDTO> listaCargo = new ArrayList<RebajaFileOndemandDTO>();
        Double sumaImporte = 0.0;
        while ((cadena = b.readLine()) != null) {
            log.info(cadena);
            if (cadena.contains("120983/") || cadena.contains("120984/")) {
                log.info("Contiene 120983/  o  120984/ :: >> " + cadena);
                RebajaFileOndemandDTO dataFile = new RebajaFileOndemandDTO();
                dataFile.setNumProteccion(Long.parseLong(cadena.substring(14, 26)));
                dataFile.setEmisor(Long.parseLong(cadena.substring(27, 33)));
                dataFile.setImporte(Double.parseDouble(cadena.substring(95, 108)));
                dataFile.setMovimiento(cadena.substring(132, 135));
                if (dataFile.getMovimiento().equalsIgnoreCase("CGO")) {
                    listaCargo.add(dataFile);
                    sumaImporte = sumaImporte + dataFile.getImporte();
                }

            }
        }
        b.close();
        List<Long> listCargo = listaCargo.stream().map(RebajaFileOndemandDTO::getNumProteccion).distinct().collect(Collectors.toList());
        log.info("NumProteccion ::  " + listCargo);
        Collections.sort(listCargo);
        log.info("NumProteccion Orden ::  " + listCargo);

        //VALIDAR creacion de archivo
      /*  Path pruebaInsert = Files.createTempFile(Paths.get("/home/bruce/Documentos/Marlene/IDS/Pruebas/PruebasEUC-20"), "pruebaInsert", ".txt");
        pruebaInsert.toFile().deleteOnExit();

        for (Long elemento : listCargo) {
            List<String> content = Arrays.asList(elemento.toString() + "," + FormatUtils.formatDateymd(new Date()) + "," + FormatUtils.formatDateymd(new Date()));
            Files.write(pruebaInsert, content, StandardOpenOption.APPEND);
        }
        //Lectura TXT
        String cadena1;
        FileReader f1 = new FileReader(pruebaInsert.toFile());
        BufferedReader b1 = new BufferedReader(f1);
        while ((cadena1 = b1.readLine()) != null) {
            System.out.println("File :: " + cadena1);
        }
        b.close();
*/
       /* long startTime = System.currentTimeMillis();
        SQLServerBulkCSVFileRecord fileRecord = null;
*/
       /* fileRecord = new SQLServerBulkCSVFileRecord(pruebaInsert.toFile().getAbsoluteFile().toString(),  StandardCharsets.UTF_8.name(), "\\,", true);
        fileRecord.addColumnMetadata(1, "NUM_PROTECCION", Types.VARCHAR, 0, 0);
        fileRecord.addColumnMetadata(2, "FEC_MOVIMIENTO", Types.DATE, 0, 0);
        fileRecord.addColumnMetadata(3, "FEC_REGISTRO_CONTABLE", Types.DATE, 0, 0);
*/
        //SQLServerBulkCopyOptions copyOptions = new SQLServerBulkCopyOptions();

        // Depending on the size of the data being uploaded, and the amount of RAM, an optimum can be found here. Play around with this to improve performance.
        //copyOptions.setBatchSize(300000);

        // This is crucial to get good performance
       /* copyOptions.setTableLock(true);
        Session session = entityManager.unwrap(Session.class);
        Connection destinationConnection = session.disconnect();
        SQLServerBulkCopy bulkCopy =  new SQLServerBulkCopy(destinationConnection);
        bulkCopy.setBulkCopyOptions(copyOptions);
        bulkCopy.setDestinationTableName("PPC_MIS_REB_NUMPROTEC");
        bulkCopy.writeToServer(fileRecord);

        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime + "ms");
*/




        List<RebNumProtect> content = null;
        Long id = 0L;
        for (Long elemento : listCargo) {
            id++;
            RebNumProtect data = new RebNumProtect();
            data.setNumProteccion(elemento.longValue());
            data.setFechaMovimiento(new Date());
            data.setFechaRegistroContable(new Date());
            //rebNumProtectRepository.insertNumProteccion(data.getNumProteccion(),data.getFechaMovimiento(),data.getFechaRegistroContable());
            entityManager.createNativeQuery("INSERT INTO PPC_MIS_REB_NUMPROTEC (NUM_PROTECCION, FEC_MOVIMIENTO, FEC_REGISTRO_CONTABLE)" +
                            " VALUES (?,?,?)").setParameter(1, elemento.longValue())
                            .setParameter(2, new Date())
                            .setParameter(3, new Date())
                            .executeUpdate();
        }
        return listCargo.size();
    }


    public FileSystemResource getFile(String file, String nameFile) throws IOException {
        byte[] decoder = Base64.getDecoder().decode(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(decoder);
        Path testFile = Files.createTempFile(nameFile + "-", ".zip");
        System.out.println("Creating and Uploading Seccion File: " + testFile);
        Files.write(testFile, bos.toByteArray());
        return new FileSystemResource(testFile.toFile());
    }
}
