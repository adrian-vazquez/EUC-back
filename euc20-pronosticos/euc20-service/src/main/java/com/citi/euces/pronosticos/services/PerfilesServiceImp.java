package com.citi.euces.pronosticos.services;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.citi.euces.pronosticos.infra.dto.ImpReporteCobroDTO;
import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.infra.utils.ConstantUtils;
import com.citi.euces.pronosticos.infra.utils.FormatUtils;
import com.citi.euces.pronosticos.repositories.PerfilesJDBCRepository;
import com.citi.euces.pronosticos.services.api.PerfilesService;

@Service
@Transactional
public class PerfilesServiceImp implements PerfilesService{

	static final Logger log = LoggerFactory.getLogger(PdcMapfreServiceImp.class);
	
	@Autowired
	private PerfilesJDBCRepository perfilesJDBCRepository;
	
	
	///---------------------------IMPRIMIR REPORTE---------------------------------///
	@Override
	public MensajeDTO ImpReporteCobro(String fechaCobro) throws GenericException, IOException, ParseException {
    	try {
    		log.info("Ejecuta Consulta Reporte Cobro");
    		
    		//Obtenemos consulta.
    		List<ImpReporteCobroDTO> res =  perfilesJDBCRepository.ObtenerConsulta(fechaCobro);
    		
    		if (res.isEmpty()) {
                throw new GenericException(
                        "No hay registros que coincidan con fecha  :: " + fechaCobro, HttpStatus.NOT_FOUND.toString());
            }
    		
    		List<List<String>> datosSalida = new ArrayList<>();
    		
    		res.forEach(ld -> {
    			List<String> salida = new ArrayList<String>();
    			
    			salida.add(FormatUtils.validaString(ld.getNumCliente().toString())); 		//NUM_CLIENTE
    			salida.add("");																//BLANCO
    			salida.add(FormatUtils.validaString(ld.getCtaEje()));						//CUENTA_EJE
    			salida.add(FormatUtils.validaString(ld.getNumCtaResp()));					//NUM_CTA_RESP
    			salida.add("EJE"); 															//EJE
    			salida.add(FormatUtils.validaString(ld.getTotal().toString()));				//M_TOTAL
    			salida.add("16");															//IVA("16");
    			salida.add("");																//CAUSA_RECHAZO
    			salida.add(FormatUtils.validaString(ld.getMes()));							//MES
    			salida.add(FormatUtils.validaString(ld.getAnio().toString()));				//ANIO
    			salida.add("PERFILES");														//PERFILES
    			salida.add("");																//CSI
    			salida.add(FormatUtils.validaString(ld.getServicio()));						//CONCEPTO.SERVICIO
    			salida.add(FormatUtils.validaString(ld.getComisionSinIva().toString()));	//COMISION_SIN_IVA
    			salida.add(FormatUtils.validaString(ld.getIvaa().toString()));				//IVAA
    			salida.add(FormatUtils.validaString(ld.getTotal().toString()));				//TOTAL
    			salida.add("");																//COMP
    			salida.add(FormatUtils.validaString(ld.getLlave()));						//LLAVE
    			salida.add(FormatUtils.validaString(ld.getNoProteccion()));					//NOPROTECCION
    			salida.add("COMERCIAL");													//Franquicia("COMERCIAL");
    			salida.add("PERFILES");														//Catalogada("PERFILES");
    			salida.add(FormatUtils.validaString(ld.getFechaCobro()));					//FECHA_COBRO
    			salida.add(FormatUtils.validaString(ld.getFechaContable()));				//FECHA_CONTABLE
    			salida.add("556053-");														//CUENTA_PRODUCTO
    			salida.add(FormatUtils.validaString(ld.getCntrClienteUsuario()));			//NUM_CONTRATO
    			
    			datosSalida.add(salida);
    		});
    			
			log.info("Empieza la descarga del archivo :: "+datosSalida);
			String nomArchivo ="repperfiles "+FormatUtils.formatFecCompActual();
			List<String> titles = Arrays.asList(ConstantUtils.TITLE_REP_COBROS_EXCEL);
			Path fileRepoCobro = FormatUtils.createExcelRepoCobro(titles, datosSalida, nomArchivo);
			String ecoder = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(fileRepoCobro.toFile()));
    		
	        MensajeDTO response = new MensajeDTO();
	        response.setMensajeConfirm(ecoder);
	        response.setMensajeInfo("Se genero Correctamente. " + nomArchivo);
			return response;
    		
    		
    	}catch(EntityNotFoundException ex) {
			throw new GenericException("Error ", HttpStatus.BAD_REQUEST.toString());
    	}
	}	
}
