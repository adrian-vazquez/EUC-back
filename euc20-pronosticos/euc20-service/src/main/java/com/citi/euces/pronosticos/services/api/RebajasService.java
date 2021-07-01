package com.citi.euces.pronosticos.services.api;

import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface RebajasService {

    MensajeDTO aplicarRebajaloadFile(String file) throws GenericException, IOException;

    MensajeDTO aplicarRebajaloadFileM(MultipartFile file) throws GenericException, IOException;

}
