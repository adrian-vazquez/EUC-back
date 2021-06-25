package com.citi.euces.pronosticos.services;

import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.services.api.RebajasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@Transactional
public class RebajasServiceImp implements RebajasService {
    static final Logger log = LoggerFactory.getLogger(RebajasServiceImp.class);

    @Override
    public MensajeDTO aplicarRebajaloadFile(String file) throws GenericException {
        log.info("aplicarRebajaloadFile ::  init");

        byte[] decodedBytes = Base64.getDecoder().decode(file);
        try {
            FileSystemResource file1 =getFile(decodedBytes, "rebajas.zip");

            log.info("file1 :: " + file1.getFilename());
            log.info("file2 :: " + file1.getFile());
            log.info("file3 :: " + file1.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
        MensajeDTO response = new MensajeDTO();
        response.setMensajeConfirm("Mensaje de Confirmacion");
        response.setMensajeInfo("Mensaje Informacion");

        return response;
    }


    public FileSystemResource getFile(byte[] bos, String nameFile) throws IOException {
        Path testFile = Files.createTempFile(nameFile + "-", ".xlsx");
        System.out.println("Creating and Uploading Seccion File: " + testFile);
        Files.write(testFile, bos);
        return new FileSystemResource(testFile.toFile());
    }
}
