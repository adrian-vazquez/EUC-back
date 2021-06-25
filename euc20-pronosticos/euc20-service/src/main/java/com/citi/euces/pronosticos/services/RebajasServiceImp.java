package com.citi.euces.pronosticos.services;

import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.services.api.RebajasService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@Transactional
public class RebajasServiceImp implements RebajasService {
    static final Logger log = LoggerFactory.getLogger(RebajasServiceImp.class);

    @Override
    public MensajeDTO aplicarRebajaloadFile(String file) throws GenericException {
        log.info("aplicarRebajaloadFile ::  init");
        MensajeDTO response = new MensajeDTO();
        response.setMensajeConfirm("Mensaje de Confirmacion");
        response.setMensajeInfo("Mensaje Informacion");
        return response;
    }
}
