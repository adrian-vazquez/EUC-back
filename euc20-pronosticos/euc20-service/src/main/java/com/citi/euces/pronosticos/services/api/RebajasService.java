package com.citi.euces.pronosticos.services.api;

import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.dto.ReporteCuadreDTO;
import com.citi.euces.pronosticos.infra.dto.ReporteRebajaDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface RebajasService {

    MensajeDTO aplicarRebajaloadFile(String file, String fechaContable, String fechaMovimiento) throws GenericException, IOException, ParseException;

    MensajeDTO aplicarRebaja() throws GenericException;

    List<ReporteCuadreDTO> reporteCuadre() throws GenericException, IOException, ParseException;

    ReporteRebajaDTO reporteRebaja(String fechaMovimiento, Integer page) throws GenericException, IOException;

    ReporteRebajaDTO reporteRebajaSearch(String fechaMovimiento, Integer page, String search) throws GenericException, IOException;

    ReporteRebajaDTO reporteRebajaFile(String fechaMovimiento) throws GenericException, IOException;

    MensajeDTO addMora(String fechaMovimiento) throws GenericException;

}
