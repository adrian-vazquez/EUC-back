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
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import com.citi.euces.pronosticos.infra.dto.RebNumProtectDTO;
import com.citi.euces.pronosticos.infra.dto.RebajaFileOndemandDTO;
import com.citi.euces.pronosticos.infra.dto.SubirRespuestaDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.infra.utils.ConstantUtils;
import com.citi.euces.pronosticos.infra.utils.FormatUtils;
import com.citi.euces.pronosticos.repositories.PerfilesJDBCRepository;
import com.citi.euces.pronosticos.repositories.SubirArchivosCobroJDBCRepository;
import com.citi.euces.pronosticos.repositories.SubirRespuestaJDBCRepository;
import com.citi.euces.pronosticos.services.api.PerfilesService;

@Service
@Transactional
public class PerfilesServiceImp implements PerfilesService{

	static final Logger log = LoggerFactory.getLogger(PdcMapfreServiceImp.class);
	
	@Autowired
	private PerfilesJDBCRepository perfilesJDBCRepository;
	@Autowired
	private SubirArchivosCobroJDBCRepository subirArchivosCobroJDBCRepository;
	@Autowired
	private SubirRespuestaJDBCRepository subirRespuestaJDBCRepository;
	
	///---------------------------SUBIR RESPUESTA DE PROTECCION---------------------------------///	
	@Override
	public MensajeDTO SubirRespuesta(String file) throws GenericException, IOException, ParseException {
		try {
			log.info("Ejecuta Subir Respuesta ");
			
			//Descomprimir ZIP
			Path testFile = Files.createTempFile("subir_resp", ".zip");
	        testFile.toFile().deleteOnExit();
	        byte[] decoder = Base64.getDecoder().decode(file);
	        Files.write(testFile, decoder);
	        ZipFile zipFile = new ZipFile(testFile.toFile());
	        Enumeration<?> enu = zipFile.entries();
	        String procesados = "";
	        while (enu.hasMoreElements()) {
	            ZipEntry zipEntry = (ZipEntry) enu.nextElement();
	            InputStream is = zipFile.getInputStream(zipEntry);
	            Path tempFile = Files.createTempFile("pruebasTXT", ".txt");
	            tempFile.toFile().deleteOnExit();
	            try (FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
	                IOUtils.copy(is, fos);
	                //Lectura TXT
	                procesados = leerArchivosCobro(tempFile);
	            }
            }
			
	        zipFile.close();
	        MensajeDTO response = new MensajeDTO();
	        response.setMensajeConfirm(procesados);
	        response.setMensajeInfo("Aviso, Importación finalizada, la información "
	        		+ "de protección ha sido completada. ");
	        return response;
			
		}catch(EntityNotFoundException ex) {
			throw new GenericException("Error ", HttpStatus.BAD_REQUEST.toString());
    	}
	}
	
	 public String leerArchivosCobro(Path tempFile) throws IOException, GenericException, ParseException {
	        String responseMessage = "";
	        String cadena;
	        FileReader f = new FileReader(tempFile.toFile());
	        BufferedReader b = new BufferedReader(f);
	        List<SubirRespuestaDTO> content = new ArrayList<SubirRespuestaDTO>();
	        String vacio=""+"";
	        
	        //Ejecutamos borrado de tmprespuesta.			
	        subirArchivosCobroJDBCRepository.truncateTmpRespuesta();
	        
	        while ((cadena = b.readLine()) != null) {
	            SubirRespuestaDTO data = new SubirRespuestaDTO();
	            data.setTipoRegistro(cadena.substring(0,2));
	            data.setNumSecuencia(Long.parseLong(cadena.substring(2,7)));
	            data.setCodOperacion(cadena.substring(9,11));
	            data.setCodDivisa(cadena.substring(11,13));
	            data.setImporteOperacion(Double.parseDouble(cadena.substring(13,28)));
	            data.setFecAplicacion(FormatUtils.DateCobros(cadena.substring(28,36)));
	            data.setPeriodoProteccion(cadena.substring(36,39));
	            data.setNumCliente(cadena.substring(39,51));
	            data.setTipoOperacion(cadena.substring(60,62));
	            data.setFecVencimiento(FormatUtils.DateCobros(cadena.substring(62,70)));
	            data.setBancoReceptor(cadena.substring(70,73));
	            data.setTipoCtaCliente(cadena.substring(73,75));
	            data.setCtaCliente(cadena.substring(75,95));
	            data.setNomCliente(vacio);
	            data.setRefEmisor(cadena.substring(137,155));
	            data.setUsoFuturo1(vacio);
	            data.setUsoFuturo2(vacio);
	            data.setUsoFuturo3(vacio);
	            data.setUsoFuturo4(vacio);
	            data.setUsoFuturo5(vacio);
	            data.setTitularServicio(vacio);
	            data.setSaldoProteccion(Double.parseDouble(cadena.substring(215,230)));
	            data.setRefEmisor(cadena.substring(230,237));
	            data.setRefLeyendaEmisor(cadena.substring(237,249));
	            data.setMotivoRechazo(cadena.substring(277,281));
	            data.setNumProteccion(cadena.substring(288,300));;
	            data.setInicioProteccion(cadena.substring(311,319));
	            data.setSecArcOriginal(cadena.substring(319,321));
	            data.setReferencia1(vacio);
	            data.setReferencia2(vacio);
	            data.setReferencia3UsuFuturo(vacio);
	            data.setDescRechazo(cadena.substring(381,421));
	            data.setCntrClienteUsr(cadena.substring(421,433));
	            data.setConcepto(cadena.substring(433,437));
	            data.setSubConcepto(cadena.substring(437,439));
	            data.setNumMesCobro(Long.parseLong(cadena.substring(439,445)));
	            data.setSecRegOriginal(Long.parseLong(cadena.substring(445,452)));
	            data.setCtaAbono(cadena.substring(452,468));
	            data.setTipoCtaAbono(cadena.substring(468,470));
	            data.setConceptoCobro2(cadena.substring(470,474));
	            data.setSubconceptoCobro2(cadena.substring(474,476));
	            data.setImporte2(Double.parseDouble(cadena.substring(476,491)));            
	            
	            content.add(data);
	        }
	        b.close();
	        Integer contentInint = content.size();
	        log.info("SubirRespuestaDTO content Final  ::  " + content.size()+" ::: "+ contentInint);
        	if(content.size()>0) {
        		//Se inserta info en la tabla PPC_PCB_RESPUESTA
        		subirArchivosCobroJDBCRepository.batchInsert(content, 500);
        		//Se inserta info en la tabla PPC_PCB_PREPARO_RESPUESTA
        		subirArchivosCobroJDBCRepository.InsertPreparoResp();
	        	subirArchivosCobroJDBCRepository.UpdatePreparoResp();
        		subirArchivosCobroJDBCRepository.DeleteTipoRegistro();
	        	subirArchivosCobroJDBCRepository.UpdateNumSecuencia();
	        	subirArchivosCobroJDBCRepository.UpdateCambioNumero();
        	
        	}else {
        		throw new GenericException(
                        "Error al leer archivo" + HttpStatus.INTERNAL_SERVER_ERROR.toString());
        	}
	        responseMessage = "Se actualizaron :: "+contentInint+" Registros.";
	        return responseMessage;
	    }
	 
		
		///-----------------------------------ACTUALIZAR ARCHIVOS COBRO------------------------------------------------///
		@Override
		public MensajeDTO ArchivoCobros(String file) throws GenericException, IOException, ParseException {
			try {
				log.info("Ejecuta Archivos Cobro");
				
				//Descomprimir ZIP
				Path testFile = Files.createTempFile("archivosCobro", ".zip");
		        testFile.toFile().deleteOnExit();
		        byte[] decoder = Base64.getDecoder().decode(file);
		        Files.write(testFile, decoder);
		        ZipFile zipFile = new ZipFile(testFile.toFile());
		        Enumeration<?> enu = zipFile.entries();
		        String procesados = "";
		        while (enu.hasMoreElements()) {
		            ZipEntry zipEntry = (ZipEntry) enu.nextElement();
		            InputStream is = zipFile.getInputStream(zipEntry);
		            Path tempFile = Files.createTempFile("pruebasTXT", ".txt");
		            tempFile.toFile().deleteOnExit();
		            try (FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
		                IOUtils.copy(is, fos);
		                //Lectura TXT
		                procesados = leerArchivos(tempFile);
		            }
	            }
				
		        zipFile.close();
		        MensajeDTO response = new MensajeDTO();
		        response.setMensajeConfirm(procesados);
		        response.setMensajeInfo("El proceso ha sido completado.");
		        return response;
				
			}catch(EntityNotFoundException ex) {
				throw new GenericException("Error ", HttpStatus.BAD_REQUEST.toString());
	    	}
		}
		
		public String leerArchivos(Path tempFile) throws IOException, GenericException, ParseException {
			String responseMessage = "";
	        String cadena;
	        FileReader f = new FileReader(tempFile.toFile());
	        BufferedReader b = new BufferedReader(f);
	        List<SubirRespuestaDTO> content = new ArrayList<SubirRespuestaDTO>();
	        long cliente = 0;
	        //Ejecutamos borrado de tmprespuesta.			
	        subirRespuestaJDBCRepository.truncateTmpRespuesta();
	        
	        while ((cadena = b.readLine()) != null) {
	            SubirRespuestaDTO data = new SubirRespuestaDTO();
	            if (cadena.length() > 130) {
		            
		            data.setConceptoCobro2(cadena.substring(470,474));
		            data.setSubconceptoCobro2(cadena.substring(474,476));
		            data.setImporte2(Double.parseDouble(cadena.substring(476,491)));            
		            
		            content.add(data);
	        	}
	        }
	        b.close();
	        Integer contentInint = content.size();
	        log.info("SubirRespuestaDTO content Final  ::  " + content.size()+" ::: "+ contentInint);
	        if(content.size()>0) {
	 		//Se inserta info en la tabla PPC_PCB_RESPUESTA
	        
	        }else {
	        	throw new GenericException(
	                 "Error al leer archivo" + HttpStatus.INTERNAL_SERVER_ERROR.toString());
	        }
	        responseMessage = "Aviso, El archivo se procesó exitosamente, se actualizaron :: "+contentInint;
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
