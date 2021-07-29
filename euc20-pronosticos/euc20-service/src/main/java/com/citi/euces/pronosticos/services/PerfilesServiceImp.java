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
import com.citi.euces.pronosticos.infra.dto.SubirRespuestaDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.infra.utils.ConstantUtils;
import com.citi.euces.pronosticos.infra.utils.FormatUtils;
import com.citi.euces.pronosticos.repositories.PerfilesJDBCRepository;
import com.citi.euces.pronosticos.repositories.ProcesoInsertarRepository;
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
	@Autowired
	private ProcesoInsertarRepository procesoInsertarRepository;
	
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
	
	///-------------------------SUBIR REBAJA----------------------------------------///
	@Override
	public MensajeDTO subirRebaja(String file) throws GenericException, IOException, ParseException {
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
        response.setMensajeInfo("Se cargó el archivo de forma correcta, favor de proseguir");
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
        a.close();
        
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
	
	///--------------------------------INSERTAR-------------------------------------///
	
	public MensajeDTO insertar(String dias, String secuencial) throws GenericException, IOException, ParseException{
		int[] cifras = new int[11];
        String[] consulta = new String[11];
		
        cifras[1] = procesoInsertarRepository.cargaLayout();
        consulta[1] = "Inseta en PPC_PCB_LAYOUT_CARGA";
        if (cifras[1] == -1) {
        	throw new GenericException("Error cargaLayout. La información de protección ha sido completada", HttpStatus.NOT_FOUND.toString());}
		
		cifras[2] = procesoInsertarRepository.updateLayout();
        consulta[2] = "Actualizacion PPC_PCB_LAYOUT_CARGA";
        if (cifras[2] == -1) {
        	throw new GenericException("Error updateLayout. La información de protección ha sido completada", HttpStatus.NOT_FOUND.toString());}
		
        cifras[3] = procesoInsertarRepository.insertaArmadoCuerpo();
        consulta[3] = "Inserta en PPC_PCB_ARMADO_CUERPO";
        if (cifras[3] == -1) {
        	throw new GenericException("Error al insertar armado cuerpo. La información de protección ha sido completada", HttpStatus.NOT_FOUND.toString());}
		
        cifras[4] = procesoInsertarRepository.llamaProcedimiento();
        consulta[4] = "Arma cuerpo";
        if (cifras[4] == -1) {
        	throw new GenericException("Error durante el procedimiento. La información de protección ha sido completada", HttpStatus.NOT_FOUND.toString());}
		
        cifras[5] = procesoInsertarRepository.updateArmadoCuerpo(Integer.parseInt(dias), Integer.parseInt(secuencial));
        consulta[5] = "Actualiza cuerpo con dias y secuencial ";
        if (cifras[5] == -1) {
        	throw new GenericException("Error updateArmadoCuerpo. La información de protección ha sido completada", HttpStatus.NOT_FOUND.toString());}
		
        cifras[6] = procesoInsertarRepository.insertaPreparoCuerpo();
        consulta[6] = "Inserta en PPC_PCB_PREPARO_CUERPO";
        if (cifras[6] == -1) {
        	throw new GenericException("Error insertaPreparoCuerpo. La información de protección ha sido completada", HttpStatus.NOT_FOUND.toString());}
		
        cifras[7] = procesoInsertarRepository.updatePreparoCuerpo();
        consulta[7] = "Actualiza en PPC_PCB_PREPARO_CUERPO";
        if (cifras[7] == -1) {
        	throw new GenericException("Error updatePreparoCuerpo. La información de protección ha sido completada", HttpStatus.NOT_FOUND.toString());}
		
        cifras[8] = procesoInsertarRepository.armaHeader(Integer.parseInt(secuencial));
        consulta[8] = "Arma Header";
        if (cifras[8] == -1) {
        	throw new GenericException("Error armaHeader. La información de protección ha sido completada", HttpStatus.NOT_FOUND.toString());}
		
        cifras[9] = procesoInsertarRepository.armaTrailer();
        consulta[9] = "Arma Trailer";
        if (cifras[9] == -1) {
        	throw new GenericException("Error armaTrailer. La información de protección ha sido completada", HttpStatus.NOT_FOUND.toString());}
		
        cifras[10] = procesoInsertarRepository.confirmaPerfiles(Integer.parseInt(secuencial));
        consulta[10] = "Inserta en PPC_PCB_BASEPERFILES";
        if (cifras[10] == -1) {
        	throw new GenericException("Error confirmaPerfiles. La información de protección ha sido completada", HttpStatus.NOT_FOUND.toString());}
		
        MensajeDTO response = new MensajeDTO();
        response.setMensajeInfo("Proceso finalizado");
        response.setMensajeConfirm("Completo");
		return response;
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
