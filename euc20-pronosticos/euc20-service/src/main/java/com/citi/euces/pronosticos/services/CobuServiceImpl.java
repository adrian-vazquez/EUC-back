package com.citi.euces.pronosticos.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hpsf.Date;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.citi.euces.pronosticos.infra.dto.CifrasControlDTO;
import com.citi.euces.pronosticos.infra.dto.CobuDTO;
import com.citi.euces.pronosticos.infra.dto.CtasVirtualesDTO;
import com.citi.euces.pronosticos.infra.dto.ProcesadoDTO;
import com.citi.euces.pronosticos.infra.dto.QueryCtosAgrupadoDTO;
import com.citi.euces.pronosticos.infra.dto.TxsCtasVirtDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.infra.utils.FormatUtils;
import com.citi.euces.pronosticos.repositories.*;
import com.citi.euces.pronosticos.services.api.CobuService;



@Service
//@Transactional
public class CobuServiceImpl implements CobuService{
	static final Logger log = LoggerFactory.getLogger(RebajasServiceImp.class);

	@Autowired
	private TruncateTablesCobuRepository deleteTables;
	@Autowired
	private InsertsCobuRepository insertsCobuRepository;
	@Autowired
	private ProcesoCobuRepository procesoCobuRepository;
	
	@Override	
	public CobuDTO limpiarCobu() throws GenericException{
		log.info("limpiarCobu ::  init");
		try {
			deleteTables.deleteCifrasControl();		
			deleteTables.deleteCtasVirtualesGpos();
			deleteTables.deleteCtasVirtuales();
			deleteTables.deleteCtosUnico();
			deleteTables.deleteLayoutBe();
			deleteTables.deleteLayoutMens();
			deleteTables.deleteLayoutVent();
			deleteTables.deleteProcesado();
			deleteTables.deleteQueryCtosAgrupado();
			deleteTables.deleteQueryCtosCobu();
			deleteTables.deleteQueryCtosDuplicados();
			deleteTables.deleteTarifas();
			deleteTables.deleteTxnsImporte();
			deleteTables.deleteTxnsXTipo();
			deleteTables.deleteTxsCtasVirt();
		}catch(Exception ex) {
			throw new GenericException("Error al borrar tablas", HttpStatus.BAD_REQUEST.toString());
		}
		log.info("limpiarCobu ::  init");
		CobuDTO response = new CobuDTO();
		response.setMensajeConfirm("Tablas COBU sin datos");
		response.setProcesoResultado("Proceso Completado");
		return response;
		
	}

	/************************************************************************************************/
	/************************************************************************************************/

	@Override
	public CobuDTO cargaCtasCobu(String file) throws GenericException, IOException, ParseException{
		try {
			log.info("Query_Ctas_COBU :: init");
	        Path testFile = Files.createTempFile("cargaQueryCtasCobu", ".zip");
	        testFile.toFile().deleteOnExit();
	        byte[] decoder = Base64.getDecoder().decode(file);
	        Files.write(testFile, decoder);
	        
	        ZipFile zipFile = new ZipFile(testFile.toFile());
	        Enumeration<?> enu = zipFile.entries();
	        String procesados = "";
	            while (enu.hasMoreElements()) {
	                ZipEntry zipEntry = (ZipEntry) enu.nextElement();
	                
	               /* String name = zipEntry.getName();
	                long size = zipEntry.getSize();
	                long compressedSize = zipEntry.getCompressedSize();*/
	              
	                InputStream is = zipFile.getInputStream(zipEntry);
	                Path tempFile = Files.createTempFile("Query_Ctas_COBU", ".csv");
	                tempFile.toFile().deleteOnExit();
	                try (FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
	                    IOUtils.copy(is, fos);
	                    procesados = leerCsvCtasCobu(tempFile);
	                 
	                }
	            }
	            zipFile.close();
	       CobuDTO response = new CobuDTO();
	       response.setMensajeConfirm("Proceso completado");
	       response.setProcesoResultado(procesados);
	        return response;
		}catch(EntityNotFoundException ex) {
			throw new GenericException("Error al importar registros", HttpStatus.BAD_REQUEST.toString());
		}
		
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String leerCsvCtasCobu(Path tempFile) throws GenericException, IOException, ParseException {
		log.info("inicia ecel:: init");
		List<QueryCtosAgrupadoDTO> contenido1 = new ArrayList<QueryCtosAgrupadoDTO>();
		String responseMessage = "";
		
		FileReader f = new FileReader(tempFile.toFile());
        BufferedReader b = new BufferedReader(f);
        
        CSVParser parse = new CSVParser(b, CSVFormat.DEFAULT);
        
        int max = 0;
        for(CSVRecord registro:parse) {
        	if(registro.getRecordNumber() != 1) {
        	max++;
        	QueryCtosAgrupadoDTO data1 = new QueryCtosAgrupadoDTO();
        	
        	data1.setCuenta(Long.parseLong(registro.get(0)));
			data1.setPrefmda(Integer.parseInt(registro.get(1)));
			data1.setCuentamda(Integer.parseInt(registro.get(2)));
			data1.setCveEstatus(Integer.parseInt(registro.get(3)));
			data1.setNombre(registro.get(4));
			data1.setUso(Integer.parseInt(registro.get(5)));
			data1.setMon(Integer.parseInt(registro.get(6)));
			data1.setFranquicia(Integer.parseInt(registro.get(10)));
			data1.setId(max); 
			
            contenido1.add(data1);   
        	}
        }    
        f.close();
        parse.close();
        try {
			insertsCobuRepository.insertCtasCobu(contenido1, 500);
	        } catch (Exception e) {
	        	deleteTables.deleteTxsCtasVirt();
	            throw new GenericException(
	                    "Error al importar registros :: " , HttpStatus.NOT_FOUND.toString());
	        }
        
        responseMessage = "Se han cargado un total de: " + max + " elementos";
        return responseMessage;
	}
	
	/************************************************************************************************/
	/************************************************************************************************/
	
	@Override
	public CobuDTO cargaCtasVirt(String file) throws GenericException, IOException, ParseException{
		try {
			log.info("TXS_CTAS_VIRT :: init");
	        Path testFile = Files.createTempFile("cargaTxsCtasVirt", ".zip");
	        testFile.toFile().deleteOnExit();
	        byte[] decoder = Base64.getDecoder().decode(file);
	        Files.write(testFile, decoder);
	        
	        ZipFile zipFile = new ZipFile(testFile.toFile());
	        Enumeration<?> enu = zipFile.entries();
	        String procesados = "";
	            while (enu.hasMoreElements()) {
	                ZipEntry zipEntry = (ZipEntry) enu.nextElement();
	                
	               /* String name = zipEntry.getName();
	                long size = zipEntry.getSize();
	                long compressedSize = zipEntry.getCompressedSize();*/
	              
	                InputStream is = zipFile.getInputStream(zipEntry);
	                Path tempFile = Files.createTempFile("TXS_CTAS_VIRT", ".csv");
	                tempFile.toFile().deleteOnExit();
	                try (FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
	                    IOUtils.copy(is, fos);
	                    procesados = leerCsvCtasVirt(tempFile);
	                 
	                }
	            }
	            zipFile.close();
	       CobuDTO response = new CobuDTO();
	       response.setMensajeConfirm("Proceso completado");
	       response.setProcesoResultado(procesados);

	        return response;
		}catch(EntityNotFoundException ex) {
			throw new GenericException("Error al importar registros", HttpStatus.BAD_REQUEST.toString());
		}
		
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String leerCsvCtasVirt(Path tempFile) throws GenericException, IOException, ParseException {
		log.info("inicia ecel:: init");
		List<CtasVirtualesDTO> contenido2 = new ArrayList<CtasVirtualesDTO>();
		String responseMessage = "";
		
		FileReader f = new FileReader(tempFile.toFile());
        BufferedReader b = new BufferedReader(f);
        
        CSVParser parse = new CSVParser(b, CSVFormat.DEFAULT);
        
        int max = 0;
        for(CSVRecord registro:parse) {
        	if(registro.getRecordNumber() != 1) {
        	max++;
        	CtasVirtualesDTO data2 = new CtasVirtualesDTO();
        	
        	data2.setNumCliente(Long.parseLong(registro.get(0)));
			data2.setNumCuenta(Long.parseLong(registro.get(1)));
		    String date1= "01/".concat(registro.get(2).replaceAll("\\s",""));
	        date1 = date1.substring(0, date1.length()-4) + "/" +date1.substring(date1.length() - 4) ;
		    data2.setFecAlta(FormatUtils.stringToDate(date1));
			data2.setCuentasX(Integer.parseInt(registro.get(3)));
			data2.setNombre(registro.get(5));
            data2.setId(max);
            
            contenido2.add(data2); 
        	}
        }    
        f.close();
        parse.close();
        try {
			insertsCobuRepository.insertCtasVirtuales(contenido2, 500);
	        } catch (Exception e) {
	        	deleteTables.deleteTxsCtasVirt();
	            throw new GenericException(
	                    "Error al importar registros :: " , HttpStatus.NOT_FOUND.toString());
	        }
        
        responseMessage = "Se han cargado un total de: " + max + " elementos";
        return responseMessage;
	}
	
	/************************************************************************************************/
	/************************************************************************************************/
	
	@Override
	public CobuDTO cargaTxsCtas(String file) throws GenericException, IOException, ParseException{
		try {
			log.info("Obtiene_TXS_CTAS :: init");
	        Path testFile = Files.createTempFile("cargaObtieneTxsCtas", ".zip");
	        testFile.toFile().deleteOnExit();
	        byte[] decoder = Base64.getDecoder().decode(file);
	        Files.write(testFile, decoder);
	        
	        ZipFile zipFile = new ZipFile(testFile.toFile());
	        Enumeration<?> enu = zipFile.entries();
	        String procesados = "";
	            while (enu.hasMoreElements()) {
	                ZipEntry zipEntry = (ZipEntry) enu.nextElement();
	                
	               /* String name = zipEntry.getName();
	                long size = zipEntry.getSize();
	                long compressedSize = zipEntry.getCompressedSize();*/
	              
	                InputStream is = zipFile.getInputStream(zipEntry);
	                Path tempFile = Files.createTempFile("Obtiene_TXS_CTAS", ".csv");
	                tempFile.toFile().deleteOnExit();
	                log.info("empieza ioutils :: init");
	                try (FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
	                    IOUtils.copy(is, fos);
	                    log.info("termina:: init");
	                    procesados = leerCsvTxsCtas(tempFile);
	                 
	                }
	                log.info("termina:: init");
	            }
	            zipFile.close();
	       CobuDTO response = new CobuDTO();
	       response.setMensajeConfirm("Proceso completado");
	       response.setProcesoResultado(procesados);

	        return response;
		}catch(EntityNotFoundException ex) {
			throw new GenericException("Error al importar registros", HttpStatus.BAD_REQUEST.toString());
		}
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String leerCsvTxsCtas(Path tempFile) throws GenericException, IOException, ParseException {
		log.info("inicia ecel:: init");
		List<TxsCtasVirtDTO> contenido3 = new ArrayList<TxsCtasVirtDTO>();
		String responseMessage = "";
		
		FileReader f = new FileReader(tempFile.toFile());
        BufferedReader b = new BufferedReader(f);
        
        CSVParser parse = new CSVParser(b, CSVFormat.DEFAULT);
        
        int max = 0;
        for(CSVRecord registro:parse) {
        	if(registro.getRecordNumber() != 1) {
        	max++;
        	TxsCtasVirtDTO data3 = new TxsCtasVirtDTO();
        	
        	data3.setNumCliente(Long.parseLong(registro.get(0)));
			data3.setNumCta(Long.parseLong(registro.get(1)));
			data3.setCteAlias((registro.get(2)));
			data3.setNombre(registro.get(3));
			data3.setCveMonSistema(Integer.parseInt(registro.get(4)));
			data3.setFecInformacion(FormatUtils.stringToDate(registro.get(5)));
			data3.setNumMedAcceso(Double.parseDouble(registro.get(6)));
			data3.setCveTxnSistema(Integer.parseInt(registro.get(7)));
			data3.setNumSucPromtormda(Integer.parseInt(registro.get(8)));
			data3.setImpTransaccion(Double.parseDouble(registro.get(9)));
			data3.setNumAutTrans(Double.parseDouble(registro.get(10)));
			data3.setNumSucOperadora(Integer.parseInt(registro.get(11)));
			data3.setTipo(Integer.parseInt(registro.get(12)));
			data3.setId(max);
			
            contenido3.add(data3);    
        	}
        }    
        f.close();
        parse.close();
        try {
			insertsCobuRepository.insertTxsCtas(contenido3, 500);
	        } catch (Exception e) {
	        	deleteTables.deleteTxsCtasVirt();
	            throw new GenericException(
	                    "Error al importar registros :: " , HttpStatus.NOT_FOUND.toString());
	        }

        responseMessage = "Se han cargado un total de: " + max + " elementos";
        return responseMessage;
	}
	

	/************************************************************************************************/
	/************************************************************************************************/

	@Override
	public CobuDTO cargaTarEspCobu(String file) throws GenericException, IOException, ParseException{
		try {
			log.info("TAR_ESP_COBU :: init");
	        Path testFile = Files.createTempFile("cargaTarEspCobu", ".zip");
	        testFile.toFile().deleteOnExit();
	        byte[] decoder = Base64.getDecoder().decode(file);
	        Files.write(testFile, decoder);
	        
	        ZipFile zipFile = new ZipFile(testFile.toFile());
	        Enumeration<?> enu = zipFile.entries();
	        String procesados = "";
	            while (enu.hasMoreElements()) {
	                ZipEntry zipEntry = (ZipEntry) enu.nextElement();
	                
	               /* String name = zipEntry.getName();
	                long size = zipEntry.getSize();
	                long compressedSize = zipEntry.getCompressedSize();*/
	              
	                InputStream is = zipFile.getInputStream(zipEntry);
	                Path tempFile = Files.createTempFile("TAR_ESP_COBU", ".csv");
	                tempFile.toFile().deleteOnExit();
	                try (FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
	                    IOUtils.copy(is, fos);
	                    procesados = leerCsvTarEspCobu(tempFile);
	                 
	                }
	            }
	            zipFile.close();
	       CobuDTO response = new CobuDTO();
	       response.setMensajeConfirm("Proceso completado");
	       response.setProcesoResultado(procesados);
	       
	        return response;
		}catch(EntityNotFoundException ex) {
			throw new GenericException("Error al importar registros", HttpStatus.BAD_REQUEST.toString());
		}
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String leerCsvTarEspCobu(Path tempFile) throws GenericException, IOException, ParseException {
		log.info("inicia ecel:: init");
		String responseMessage = "";
		List<ProcesadoDTO> contenido4 = new ArrayList<ProcesadoDTO>();
		
		FileReader f = new FileReader(tempFile.toFile());
        BufferedReader b = new BufferedReader(f);
        
        CSVParser parse = new CSVParser(b, CSVFormat.DEFAULT);
        
        int max = 0;
        for(CSVRecord registro:parse) {
        	if(registro.getRecordNumber() != 1) {
        	max++;
        	ProcesadoDTO data4 = new ProcesadoDTO();
        	
        	data4.setNumCielnte(Long.parseLong(registro.get(0)));
			data4.setBe(Double.parseDouble(registro.get(1).replace("$", "")));
			data4.setVentanilla(Double.parseDouble(registro.get(2).replace("$", "")));
			data4.setMensualidad(Double.parseDouble(registro.get(3).replace("$", "")));
			data4.setId(max);	
			
			contenido4.add(data4);   
        	}
        }    
        f.close();
        parse.close();
        try {
			insertsCobuRepository.insertTarEspCobu(contenido4, 500);
	        } catch (Exception e) {
	        	deleteTables.deleteTxsCtasVirt();
	            throw new GenericException(
	                    "Error al importar registros :: " , HttpStatus.NOT_FOUND.toString());
	        }
        
        responseMessage = "Se han cargado un total de: " + max + " elementos";
        return responseMessage;
	}

	/************************************************************************************************/
	/************************************************************************************************/

	@Override
	public CobuDTO procesoCobu() throws GenericException, IOException, ParseException, SQLException{
			 int[] cifras = new int[22];
	         String[] consultas = new String[22];

	            cifras[0] = procesoCobuRepository.insertaTarifas();
	            consultas[0] = "inserta en TARIFAS";

	            cifras[1] = procesoCobuRepository.insertaQueryCtosDuplicado();
	            consultas[1] = "inserta en query_ctos_duplicados";

	            cifras[2] = procesoCobuRepository.actualizaQueryCtosDuplicados();
	            consultas[2] = "Actualiza Query_Ctos_Duplicados";

	            cifras[3] = procesoCobuRepository.insertaCtosUnicos();
	            consultas[3] = "inserta en Ctos_Unicos";

	            cifras[4] = procesoCobuRepository.actualizaCtasVirtalesMesyAnio();
	            consultas[4] = "Actualiza en Ctas_Virtales mes y año";

	            cifras[5] = procesoCobuRepository.insertaCtasVirtualesGpos();
	            consultas[5] = "inserta en Ctas_Virtuales_Gpos";

	            cifras[6] = procesoCobuRepository.actualizaTxsCtasVirtNunSucOperadora();
	            consultas[6] = "Actualiza Txs_Ctas_Virt  NUM_SUCOPERADORA";

	            cifras[7] = procesoCobuRepository.insertaTxnsXTipo();
	            consultas[7] = "inserta en into txns_x_tipo";

	            cifras[8] = procesoCobuRepository.actualizaCtasVirtualesGposTxnsBe();
	            consultas[8] = "Actualiza Ctas_Virtuales_Gpos TXNS_BE";

	            cifras[9] = procesoCobuRepository.actualizaCtasVirtualesGposTxnsVent();
	            consultas[9] = "Actualiza Ctas_Virtuales_Gpos TXNS_VENT";

	            cifras[10] = procesoCobuRepository.actualizaANull();
	            consultas[10] = "Actualiza Ctas_Virtuales_Gpos TARIFA_BE,TARIFA_VENT,TARIFA_MENS a nulo";

	            cifras[11] = procesoCobuRepository.actualizaCtasVirtualesGposconTablaTarifas();
	            consultas[11] = "Actualiza Ctas_Virtuales_Gpos con Tabla Tarifas";

	            cifras[12] = procesoCobuRepository.actualizaAUnaTarifaPredefinida();
	            consultas[12] = "Actualiza Ctas_Virtuales_Gpos TARIFA_BE,TARIFA_VENT,TARIFA_MENS a una tarifa predefinida";

	            cifras[13] = procesoCobuRepository.actualizaCtasVirtualesGposBeVentMens();
	            consultas[13] = "Actualiza Ctas_Virtuales_Gpos COM_BE,COM_VENT,COM_MENS";

	            cifras[14] = procesoCobuRepository.actualizaCtosUnicos();
	            consultas[14] = "Actualiza Ctos_Unicos";

	            cifras[15] = procesoCobuRepository.actualizaCtosUnicosUso11();
	            consultas[15] = "Actualiza Ctos_Unicos USO11";

	            cifras[16] = procesoCobuRepository.actualizaCtasVirtualesGposComTotal();
	            consultas[16] = "Actualiza Ctas_Virtuales_Gpos COM_TOTAL";

	            cifras[17] = procesoCobuRepository.actualizaCtasVirtualesGposIva();
	            consultas[17] = "Actualiza Ctas_Virtuales_Gpos Iva";

	            cifras[18] = procesoCobuRepository.insertaEnLayoutBe();
	            consultas[18] = "inserta en Layout_BE";

	            cifras[19] = procesoCobuRepository.insertaEnLayoutVent();
	            consultas[19] = "inserta en Layout_VENT";

	            cifras[20] = procesoCobuRepository.insertaEnLayoutMens();
	            consultas[20] = "inserta en Layout_MENS";

	            cifras[21] = procesoCobuRepository.insertaEnLayoutTxnsConImporte();
	            consultas[21] = "inserta en Layout_Txns_Con_Importe";
	
	            procesarlistas(cifras, consultas);

	            CobuDTO response = new CobuDTO();
	            response.setMensajeConfirm("Proceso Completado");
	            response.setProcesoResultado("Insert en cifras control");
				return response;
	            
	}
	
	public String procesarlistas(int[] cifras, String[] consultas) throws GenericException, FileNotFoundException, IOException, ParseException, OfficeXmlFileException{
		String responseMessage = "";
		List<CifrasControlDTO> lista = new ArrayList<CifrasControlDTO>();
		
		for(int i = 0; i <= 21; i ++) {
			CifrasControlDTO datos = new CifrasControlDTO();
			
			datos.setCifra(cifras[i]);
			datos.setConsulta(consultas[i]);
			datos.setProceso("COBU");
			datos.setId(i);
			
			lista.add(datos);
		}
		
		 try {
			 procesoCobuRepository.insertCifrasControl(lista, 500);
		        } catch (Exception e) {
		            throw new GenericException(
		                    "Error al importar registros :: " , HttpStatus.NOT_FOUND.toString());
		        }
			  
		   responseMessage = "Proceso Completado";
	       return responseMessage;
	}
	
	/************************************************************************************************/
	/************************************************************************************************/

	/*@Override
	public CobuDTO reporte() throws GenericException {
		try {
			List<LayoutBe>dt1 = layoutBe.getLayoutBe();
			List<LayoutMens> dt2 = layoutMens.getLayoutmens();
			List<LayoutVent> dt3 = layoutVent.getlayoutVent();
			List<Tarifas> dt4 = tarifas.getTarifas();
			List<CtasVirtualesGpos> dt5 = ctasVirtualesGpos.getCtasVirtualesGpos();
			List<TxnsImporte> dt6 = txnsImporte.getTxnsImporte();
			
			HSSFWorkbook reporteCobu = new HSSFWorkbook();
			
			HSSFSheet sheet1 = reporteCobu.createSheet();
			reporteCobu.setSheetName(0, "layout_be");

			HSSFRow filaDatos1 = sheet1.createRow(0);			
			String encabezado1[] = new String[] {"NUM_CLIENTE", "NOMBRE", "SUC", "CTA", "COM_BE", "MONTO_IVA", "MONTO_TOTAL", "AÑO", "MES", "PRODUCTO", "IVA", "MONEDA", "USO11"};
			
			for(int i = 0; i < encabezado1.length; i++) {
				Cell celdaEncabezado1 = filaDatos1.createCell(i);
				celdaEncabezado1.setCellValue(encabezado1[i]);
			}
			
			int numFila = 1;			
			for(LayoutBe lista1:dt1) {
				filaDatos1 = sheet1.createRow(numFila);
			
				filaDatos1.createCell(0).setCellValue(lista1.getNumCliente());
				filaDatos1.createCell(1).setCellValue(lista1.getNombre());
				filaDatos1.createCell(2).setCellValue(lista1.getSuc());
				filaDatos1.createCell(3).setCellValue(lista1.getCta());
				filaDatos1.createCell(4).setCellValue(lista1.getComBe());
				filaDatos1.createCell(5).setCellValue(lista1.getMontoIva());
				filaDatos1.createCell(6).setCellValue(lista1.getMontoTotal());
				filaDatos1.createCell(7).setCellValue(lista1.getAnio());
				filaDatos1.createCell(8).setCellValue(lista1.getMes());
				filaDatos1.createCell(9).setCellValue(lista1.getProducto());
				filaDatos1.createCell(10).setCellValue(lista1.getIva());
				filaDatos1.createCell(11).setCellValue(lista1.getMoneda());
				filaDatos1.createCell(12).setCellValue(lista1.getUso11());
				
				numFila++;
			}
			
			HSSFSheet sheet2 = reporteCobu.createSheet();
			reporteCobu.setSheetName(1, "layout_mens");
			
			HSSFRow filaDatos2 = sheet2.createRow(0);
			String encabezado2[] = new String[] {"NUM_CLIENTE", "NOMBRE", "SUC", "CTA", "COM_MENS", "MONTO_IVA", "MONTO_TOTAL", "AÑO", "MES", "PRODUCTO", "IVA", "MONEDA"};
			
			for(int i = 0; i < encabezado2.length; i++) {
				Cell celdaEncabezado2 = filaDatos2.createCell(i);
				celdaEncabezado2.setCellValue(encabezado2[i]);
			}
			
			numFila = 1;
			for(LayoutMens lista2:dt2) {
				filaDatos2 = sheet2.createRow(numFila);
				
				filaDatos2.createCell(0).setCellValue(lista2.getNumCliente());
				filaDatos2.createCell(1).setCellValue(lista2.getNombre());
				filaDatos2.createCell(2).setCellValue(lista2.getSuc());
				filaDatos2.createCell(3).setCellValue(lista2.getCta());
				filaDatos2.createCell(4).setCellValue(lista2.getComMens());
				filaDatos2.createCell(5).setCellValue(lista2.getMontoIva());
				filaDatos2.createCell(6).setCellValue(lista2.getMontoTotal());
				filaDatos2.createCell(7).setCellValue(lista2.getAnio());
				filaDatos2.createCell(8).setCellValue(lista2.getMes());
				filaDatos2.createCell(9).setCellValue(lista2.getProducto());
				filaDatos2.createCell(10).setCellValue(lista2.getIva());
				filaDatos2.createCell(10).setCellValue(lista2.getMoneda());
				
				numFila++;
			}
			
			HSSFSheet sheet3 = reporteCobu.createSheet();
			reporteCobu.setSheetName(2, "layout_vent");
			
			HSSFRow filaDatos3 = sheet3.createRow(0);
			String encabezado3[] = new String[] {"NUM_CLIENTE", "NOMBRE", "SUC", "CTA", "COM_VENT", "MONTO_IVA", "MONTO_TOTAL", "AÑO", "MES", "PRODUCTO", "IVA", "MONEDA", "USO11"};
			
			for(int i = 0; i < encabezado3.length; i++) {
				Cell celdaEncabezado3 = filaDatos3.createCell(i);
				celdaEncabezado3.setCellValue(encabezado3[i]);
			}
			
			numFila = 1;
			for(LayoutVent lista3:dt3){
				filaDatos3 = sheet3.createRow(numFila);
				
				filaDatos3.createCell(0).setCellValue(lista3.getNumCliente());
				filaDatos3.createCell(1).setCellValue(lista3.getNombre());
				filaDatos3.createCell(2).setCellValue(lista3.getSuc());
				filaDatos3.createCell(3).setCellValue(lista3.getCta());
				filaDatos3.createCell(4).setCellValue(lista3.getComVent());
				filaDatos3.createCell(5).setCellValue(lista3.getMontoIva());
				filaDatos3.createCell(6).setCellValue(lista3.getMontoTotal());
				filaDatos3.createCell(7).setCellValue(lista3.getAnio());
				filaDatos3.createCell(8).setCellValue(lista3.getMes());
				filaDatos3.createCell(9).setCellValue(lista3.getProducto());
				filaDatos3.createCell(10).setCellValue(lista3.getIva());
				filaDatos3.createCell(11).setCellValue(lista3.getMoneda());
				filaDatos3.createCell(12).setCellValue(lista3.getUso11());
				
				numFila++;
			}
			
			HSSFSheet sheet4 = reporteCobu.createSheet();
			reporteCobu.setSheetName(3, "Tarifas");
			
			HSSFRow filaDatos4 = sheet4.createRow(0);
			String encabezado4[] = new String[] {"NUM_CLIENTE", "TARIFA_BE", "TAFIRA_VENT", "TARIFA_MENS"};
			
			for(int i = 0; i < encabezado4.length; i++) {
				Cell celdaEncabezado4 = filaDatos4.createCell(i);
				celdaEncabezado4.setCellValue(encabezado4[i]);
			}
			
			numFila = 1;
			for(Tarifas lista4:dt4) {
				filaDatos4 = sheet4.createRow(numFila);
				
				filaDatos4.createCell(0).setCellValue(lista4.getNumCliente());
				filaDatos4.createCell(1).setCellValue(lista4.getTarifaTxBe());				
				filaDatos4.createCell(2).setCellValue(lista4.getTarifaTxSuc());				
				filaDatos4.createCell(3).setCellValue(lista4.getTarifaMensual());
				
				numFila++;
			}
			
			HSSFSheet sheet5 = reporteCobu.createSheet();
			reporteCobu.setSheetName(4, "Detalle_TXS_X_CTA");
			
			HSSFRow filaDatos5 = sheet5.createRow(0);
			String encabezado5[] = new String[] {"NUM_CLIENTE", "NUM_CUENTA", "NOMBRE", "CTAS_V", "TXNS_BE", "TXNS_VENT", "TARIFA_BE", "TARIFA_VENT", "TARIFA_MENS", "COM_BE", "COM_VENT", "COM_MENS", "USO11", "COM_TOTAL", "SUC", "CTA", "FRANQUICIA", "MONEDA", "IVA"};			
			
			for(int i = 0; i < encabezado5.length; i++) {
				Cell celdaEncabezado5 = filaDatos5.createCell(i);
				celdaEncabezado5.setCellValue(encabezado5[i]);
			}
			
			numFila = 1;
			for(CtasVirtualesGpos lista5:dt5) {
				filaDatos5 = sheet5.createRow(numFila);
				
				filaDatos5.createCell(0).setCellValue(lista5.getNumCliente());
				filaDatos5.createCell(1).setCellValue(lista5.getNumCuenta());
				filaDatos5.createCell(2).setCellValue(lista5.getNombre());
				filaDatos5.createCell(3).setCellValue(lista5.getCtasV());
				filaDatos5.createCell(4).setCellValue(lista5.getTxnsBe());
				filaDatos5.createCell(5).setCellValue(lista5.getTxnsVent());
				filaDatos5.createCell(6).setCellValue(lista5.getTarifaBe());
				filaDatos5.createCell(7).setCellValue(lista5.getTarifaVent());
				filaDatos5.createCell(8).setCellValue(lista5.getTarifaMens());
				filaDatos5.createCell(9).setCellValue(lista5.getComBe());
				filaDatos5.createCell(10).setCellValue(lista5.getComVent());
				filaDatos5.createCell(11).setCellValue(lista5.getComMens());
				filaDatos5.createCell(12).setCellValue(lista5.getUso11());
				filaDatos5.createCell(13).setCellValue(lista5.getComTotal());
				filaDatos5.createCell(14).setCellValue(lista5.getSuc());
				filaDatos5.createCell(15).setCellValue(lista5.getCuenta());
				filaDatos5.createCell(16).setCellValue(lista5.getFranquicia());
				filaDatos5.createCell(17).setCellValue(lista5.getMoneda());
				filaDatos5.createCell(18).setCellValue(lista5.getIva());
				
				numFila++;
			}
			
			HSSFSheet sheet6 = reporteCobu.createSheet();
			reporteCobu.setSheetName(5, "Txns_Con_Importe");
			
			HSSFRow filaDatos6 = sheet6.createRow(0);
			String encabezado6[] = new String[] {"NUM_CLIENTE", "NUM_CUENTA", "TIPO", "TXNS", "SumaDeIMP_TRANSACCION"};
			
			for(int i = 0; i < encabezado6.length; i++) {
				Cell celdaEncabezado6 = filaDatos6.createCell(i);
				celdaEncabezado6.setCellValue(encabezado6[i]);
			}
			
			numFila = 1;
			for(TxnsImporte lista6:dt6) {
				filaDatos6 = sheet6.createRow(numFila);
				
				filaDatos6.createCell(0).setCellValue(lista6.getNumCliente());
				filaDatos6.createCell(2).setCellValue(lista6.getNumCuenta());
				filaDatos6.createCell(3).setCellValue(lista6.getTipo());
				filaDatos6.createCell(4).setCellValue(lista6.getTxns());
				filaDatos6.createCell(5).setCellValue(lista6.getSumImpTrans());
				
				numFila++;
			}
			
			
			
			/////////////////////////////////
			////////aqui me quede
			////////////////////////////////
			
		}catch(EntityNotFoundException ex) {
			throw new GenericException("Error al generar reporte", HttpStatus.BAD_REQUEST.toString());
		}
		
		log.info("reporte ::  init");
		CobuDTO response = new CobuDTO();
        response.setProcesoCompletado("Proceso Completo");
        return response;
	}*/


	/*@Override
	public CobuDTO cifrasControl() throws GenericException {
		try {
			List<CifrasControl> consultaCifras = cifrasControl.getCifrasControl();
			
			HSSFWorkbook cifras = new HSSFWorkbook();
		
			HSSFSheet sheet = cifras.createSheet();
			cifras.setSheetName(0, "Resumen");
			
			HSSFRow filaCifras = sheet.createRow(0);
			String encabezadoCifras[] = new String[] {"Consulta", "Cifra"};
			
			for(int i = 0; i < encabezadoCifras.length; i++) {
				Cell celdaEncabezadoCifras = filaCifras.createCell(i);
				celdaEncabezadoCifras.setCellValue(encabezadoCifras[i]);
			}
			
			int numFilaCifras = 1;
			for(CifrasControl listaCifras:consultaCifras) {
				filaCifras = sheet.createRow(numFilaCifras);
				
				filaCifras.createCell(0).setCellValue(listaCifras.getConsulta());
				filaCifras.createCell(1).setCellValue(listaCifras.getCifra());
				
				numFilaCifras++;
			}
			
			//////////////
			//aqui me quede
			//////////////
			
		}catch(EntityNotFoundException ex) {
			throw new GenericException("Error al generar cifras control", HttpStatus.BAD_REQUEST.toString());
		}
		log.info("reporte ::  init");
		CobuDTO response = new CobuDTO();
        response.setProcesoCompletado("Proceso Completo");
        return response;
	}*/


	


}
