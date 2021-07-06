package com.citi.euces.pronosticos.services.api;

import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.dto.ReporteRebajaDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.text.ParseException;

public interface RebajasService {

    MensajeDTO aplicarRebajaloadFile(String file, String fechaContable, String fechaMovimiento) throws GenericException, IOException, ParseException;

    MensajeDTO aplicarRebaja() throws GenericException;

    Page<ReporteRebajaDTO> reporteRebaja(String fechaMovimiento) throws GenericException;

}
