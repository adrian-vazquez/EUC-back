package com.citi.euces.pronosticos.services;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.citi.euces.pronosticos.infra.dto.ImpReporteCobroDTO;
import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.dto.SlunifinalexcPerfDTO;
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
	
	///-------------------------SUBIR REBAJA----------------------------------------///
	@Override
	public MensajeDTO SubirRebajas(String file) throws GenericException, IOException, ParseException {
		try {
			Path testFile = Files.createTempFile("SubirRebaja", ".zip");
			testFile.toFile().deleteOnExit();
			byte[] decoder = Base64.getDecoder().decode(file);
			Files.write(testFile, decoder);
        
			ZipFile zipFile = new ZipFile(testFile.toFile());
			Enumeration<?> enu = zipFile.entries();
			String proceso = "";
			while (enu.hasMoreElements()) {
				ZipEntry zipEntry = (ZipEntry) enu.nextElement();

				InputStream is = zipFile.getInputStream(zipEntry);
				Path tempFile = Files.createTempFile("SubirRebaja", ".txt");
				tempFile.toFile().deleteOnExit();
				try (FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
                IOUtils.copy(is, fos);

                proceso = lecturaTxtRebaja(tempFile);
            }
        }
        zipFile.close();
        
        MensajeDTO response = new MensajeDTO();
        response.setMensajeInfo("El archivo se importo exitosamente");
        response.setMensajeConfirm(proceso);
		return response;
		}catch(EntityNotFoundException ex) {
			throw new GenericException("Error al importar registros", HttpStatus.BAD_REQUEST.toString());
		}
		
	}	
	
	public String lecturaTxtRebaja(Path tempFile) throws GenericException, IOException, ParseException {
		String validar;
        BufferedReader a = new BufferedReader(new FileReader(tempFile.toFile()));
        validar = a.readLine();
        if(!validar.equals(ConstantUtils.VALIDATXT)) {
        	a.close();
        	throw new GenericException("Layout invalido. Favor de verificar" , HttpStatus.NOT_FOUND.toString());
        }
        
		String linea;
		String[] valores;
		int ini = 1;
		FileReader f = new FileReader(tempFile.toFile());
        BufferedReader b = new BufferedReader(f);
		List<SlunifinalexcPerfDTO> lista = new ArrayList<SlunifinalexcPerfDTO>();
		
		 while ((linea= b.readLine()) != null) {
			 if (ini != 1) {
	            valores = linea.split("\t");
	            SlunifinalexcPerfDTO data = new SlunifinalexcPerfDTO();
	            
	            data.setNumCliente(Long.parseLong(valores[0].replaceAll("\\s", "")));
	            data.setNumContrato(Long.parseLong(valores[1].replaceAll("\\s", "")));
	            data.setSucursal(Long.parseLong(valores[2].equals("") ? "0" : valores[2].replaceAll("\\s", "")));
	            data.setSdoFinMes(Double.parseDouble(valores[3].equals("") ? "0" : valores[3].replaceAll("\\s", "")));
	            data.setSdoFromMes(Double.parseDouble(valores[4].equals("") ? "0" : valores[4].replaceAll("\\s", "")));
	            data.setSuc(Long.parseLong(valores[5].replaceAll("\\s", "")));
	            data.setCuenta(Long.parseLong(valores[6].replaceAll("\\s", "")));
	            data.setDiferencia(Double.parseDouble(valores[7].equals("") ? "0" : valores[7].replaceAll("\\s", "")));
	            data.setCom(Double.parseDouble(valores[8].replaceAll("\\s", "")));
	            data.setLlavePre(String.valueOf(valores[9].equals("") ? "0" : valores[9].replaceAll("\\s", "")));       
	            lista.add(data);
			 }	
			 ini = 2;
		 }
		
		 b.close();
		 try {   
             perfilesJDBCRepository.deleteSlunifinalexcPerf();
			 perfilesJDBCRepository.insertSlunifinalexcPerf(lista, 500);
			 perfilesJDBCRepository.deleteLayoutCarga();
			 perfilesJDBCRepository.deleteArmadoCuerpo();
			 perfilesJDBCRepository.deletePreparoCuerpo();
			 perfilesJDBCRepository.deleteHeader();
			 perfilesJDBCRepository.deleteTrailer();
			 perfilesJDBCRepository.deleteTablaErrores();
			 perfilesJDBCRepository.deletePreparoRespuesta();
			 perfilesJDBCRepository.deleteTmpRespuesta();
			 perfilesJDBCRepository.deleteArmadoCuerpo();
			 perfilesJDBCRepository.insertComPendOper();
		 } catch (Exception e) {
			 throw new GenericException("Error al importar registros" , HttpStatus.NOT_FOUND.toString());
		 }
	        
		 	String responseMessage = "Proceso completado";
	        return responseMessage;
	}
	
	
	
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
