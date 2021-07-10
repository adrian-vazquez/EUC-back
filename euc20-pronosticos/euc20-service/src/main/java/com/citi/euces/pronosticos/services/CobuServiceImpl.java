package com.citi.euces.pronosticos.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.exception.SQLGrammarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.citi.euces.pronosticos.infra.dto.CobuDTO;
import com.citi.euces.pronosticos.infra.dto.CtasVirtualesDTO;
import com.citi.euces.pronosticos.infra.dto.ProcesadoDTO;
import com.citi.euces.pronosticos.infra.dto.QueryCtosAgrupadoDTO;
import com.citi.euces.pronosticos.infra.dto.TxsCtasVirtDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.repositories.*;
import com.citi.euces.pronosticos.services.api.CobuService;



@Service
@Transactional
public class CobuServiceImpl implements CobuService{
	static final Logger log = LoggerFactory.getLogger(RebajasServiceImp.class);

	@Autowired
	private TruncateTablesCobuRepository truncateTables;
	@Autowired
	private InsertsCobuRepository insertsCobuRepository;
	
	@Override	
	public CobuDTO limpiarCobu() throws GenericException, SQLGrammarException{
		log.info("limpiarCobu ::  init");
		try {
		truncateTables.truncateCifrasControl();
		truncateTables.truncateCtasVirtualesGpos();
		truncateTables.truncateCtasVirtuales();
		truncateTables.truncateCtosUnico();
		truncateTables.truncateLayoutBe();
		truncateTables.truncateLayoutMens();
		truncateTables.truncateLayoutVent();
		truncateTables.truncateProcesado();
		truncateTables.truncateQueryCtosAgrupado();
		truncateTables.truncateQueryCtosCobu();
		truncateTables.truncateQueryCtosDuplicados();
		truncateTables.truncateTarifas();
		truncateTables.truncateTxnsImporte();
		truncateTables.trucateTxnsXTipo();
		truncateTables.truncateCtasVirt();
		}catch(EntityNotFoundException ex) {
			throw new GenericException("Error en el limpiado de tablas", HttpStatus.BAD_REQUEST.toString());
		}catch(SQLGrammarException ex) {
			throw new GenericException("Error e", HttpStatus.BAD_REQUEST.toString());
		}
		
		log.info("limpiarCobu ::  init");
		CobuDTO response = new CobuDTO();
		response.setMensajeConfirm("Tablas COBU sin datos");
		response.setProcesoCompletado("Proceso Completado");
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
	                Path tempFile = Files.createTempFile("Query_Ctas_COBU", ".xls");
	                tempFile.toFile().deleteOnExit();
	                try (FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
	                    IOUtils.copy(is, fos);
	                    procesados = leerExcelcargaCtasCobu(tempFile);
	                 
	                }
	            }
	            zipFile.close();
	       CobuDTO response = new CobuDTO();
	       response.setMensajeConfirm(procesados);
	    
	        return response;
		}catch(EntityNotFoundException ex) {
			throw new GenericException("Error al importar registros", HttpStatus.BAD_REQUEST.toString());
		}
		
	}

	public String leerExcelcargaCtasCobu(Path tempFile) throws GenericException, FileNotFoundException, IOException, ParseException, OfficeXmlFileException{
		String responseMessage = "";
		List<QueryCtosAgrupadoDTO> contenido1 = new ArrayList<QueryCtosAgrupadoDTO>();
		
		XSSFWorkbook workbook1 = new XSSFWorkbook(new FileInputStream(tempFile.toFile()));
		XSSFSheet sheet1 = workbook1.getSheetAt(0);
		
		int numFilas1 = sheet1.getLastRowNum();	
		for(int i = 1; i <= numFilas1; i++) {
			XSSFRow fila1 = sheet1.getRow(i);		
			QueryCtosAgrupadoDTO data1 = new QueryCtosAgrupadoDTO();
			 
			data1.setCuenta((long) fila1.getCell(0).getNumericCellValue());
			data1.setPrefmda((int) fila1.getCell(1).getNumericCellValue());
			data1.setCuentamda((int) fila1.getCell(2).getNumericCellValue());
			data1.setCveEstatus((int) fila1.getCell(3).getNumericCellValue());
			data1.setNombre(fila1.getCell(4).getStringCellValue());
			data1.setUso((int) fila1.getCell(5).getNumericCellValue());
			data1.setMon((int) fila1.getCell(6).getNumericCellValue());
			data1.setFranquicia((int) fila1.getCell(7).getNumericCellValue());
			data1.setDuplicado((int) fila1.getCell(8).getNumericCellValue());
			data1.setId(i);
            
            contenido1.add(data1);
            
           
		}
		workbook1.close();
		Integer contentInint = contenido1.size();	
		
		 try {
			insertsCobuRepository.insertCtasCobu(contenido1, 500);
	        } catch (Exception e) {
	            throw new GenericException(
	                    "Error al importar registros :: " , HttpStatus.NOT_FOUND.toString());
	        }
		
	  
	   responseMessage = "Carga de registros completa. Se han cargado un total de: " + contentInint + " elemento";
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
	                Path tempFile = Files.createTempFile("TXS_CTAS_VIRT", ".xls");
	                tempFile.toFile().deleteOnExit();
	                try (FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
	                    IOUtils.copy(is, fos);
	                    procesados = leerExcelCtasVirt(tempFile);
	                 
	                }
	            }
	            zipFile.close();
	       CobuDTO response = new CobuDTO();
	       response.setMensajeConfirm(procesados);
	    
	        return response;
		}catch(EntityNotFoundException ex) {
			throw new GenericException("Error al importar registros", HttpStatus.BAD_REQUEST.toString());
		}
		
	}
	
	public String leerExcelCtasVirt(Path tempFile) throws GenericException, FileNotFoundException, IOException, ParseException, OfficeXmlFileException{
		String responseMessage = "";
		List<CtasVirtualesDTO> contenido2 = new ArrayList<CtasVirtualesDTO>();
		
		XSSFWorkbook workbook2 = new XSSFWorkbook(new FileInputStream(tempFile.toFile()));
		XSSFSheet sheet2 = workbook2.getSheetAt(0);
		
		
		int numFilas2 = sheet2.getLastRowNum();
		for(int i = 1; i <= numFilas2; i++) {
			XSSFRow fila2 = sheet2.getRow(i);		
			CtasVirtualesDTO data2 = new CtasVirtualesDTO();
			 
			data2.setNumCliente((int) fila2.getCell(0).getNumericCellValue());
			data2.setNumCuenta((int) fila2.getCell(1).getNumericCellValue());
			data2.setFecAlta(fila2.getCell(2).getStringCellValue());
			data2.setCuentasX((int) fila2.getCell(3).getNumericCellValue());
			data2.setNombre(fila2.getCell(5).getStringCellValue());
            data2.setId(i);
            contenido2.add(data2);
           
		}
		workbook2.close();
		Integer contentInint = contenido2.size();	
		
		 try {
			insertsCobuRepository.insertCtasVirtuales(contenido2, 500);
	        } catch (Exception e) {
	            throw new GenericException(
	                    "Error al importar registros :: " , HttpStatus.NOT_FOUND.toString());
	        }
		
	  
	   responseMessage = "Carga de registros completa. Se han cargado un total de: " + contentInint + " elemento";
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
	                Path tempFile = Files.createTempFile("Obtiene_TXS_CTAS", ".xls");
	                tempFile.toFile().deleteOnExit();
	                try (FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
	                    IOUtils.copy(is, fos);
	                    procesados = leerExcelTxsCtas(tempFile);
	                 
	                }
	            }
	            zipFile.close();
	       CobuDTO response = new CobuDTO();
	       response.setMensajeConfirm(procesados);
	    
	        return response;
		}catch(EntityNotFoundException ex) {
			throw new GenericException("Error al importar registros", HttpStatus.BAD_REQUEST.toString());
		}
	}

	public String leerExcelTxsCtas(Path tempFile) throws GenericException, FileNotFoundException, IOException, ParseException, OfficeXmlFileException{
		String responseMessage = "";
		List<TxsCtasVirtDTO> contenido3 = new ArrayList<TxsCtasVirtDTO>();
		
		XSSFWorkbook workbook3 = new XSSFWorkbook(new FileInputStream(tempFile.toFile()));
		XSSFSheet sheet3 = workbook3.getSheetAt(0);
		
		
		int numFilas3 = sheet3.getLastRowNum();
		for(int i = 1; i <= numFilas3; i++) {
			XSSFRow fila3 = sheet3.getRow(i);		
			TxsCtasVirtDTO data3 = new TxsCtasVirtDTO();
			 
			data3.setNumCliente((long) fila3.getCell(0).getNumericCellValue());
			data3.setNumCta((long) fila3.getCell(1).getNumericCellValue());
			data3.setCteAlias(fila3.getCell(2).getStringCellValue());
			data3.setNombre(fila3.getCell(3).getStringCellValue());
			data3.setCveMonSstema((int) fila3.getCell(4).getNumericCellValue());
			data3.setFecInformacion(fila3.getCell(5).getDateCellValue());
			data3.setNumMedAcceso(fila3.getCell(6).getNumericCellValue());
			data3.setCveTxnSistema((int) fila3.getCell(7).getNumericCellValue());
			data3.setNumSucPromtormda((int) fila3.getCell(8).getNumericCellValue());
			data3.setImpTransaccion(fila3.getCell(9).getNumericCellValue());
			data3.setNumAutTrans(fila3.getCell(10).getNumericCellValue());
			data3.setNumSucOperadora((int) fila3.getCell(11).getNumericCellValue());
			data3.setTipo((int) fila3.getCell(12).getNumericCellValue());
			data3.setId(i);
			
             contenido3.add(data3);
           
		}
		workbook3.close();
		Integer contentInint = contenido3.size();	
		
		 try {
			insertsCobuRepository.insertTxsCtas(contenido3, 500);
	        } catch (Exception e) {
	            throw new GenericException(
	                    "Error al importar registros :: " , HttpStatus.NOT_FOUND.toString());
	        }
		
	  
	   responseMessage = "Carga de registros completa. Se han cargado un total de: " + contentInint + " elemento";
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
	                Path tempFile = Files.createTempFile("TAR_ESP_COBU", ".xls");
	                tempFile.toFile().deleteOnExit();
	                try (FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
	                    IOUtils.copy(is, fos);
	                    procesados = leerExcelTarEspCobu(tempFile);
	                 
	                }
	            }
	            zipFile.close();
	       CobuDTO response = new CobuDTO();
	       response.setMensajeConfirm(procesados);
	    
	        return response;
		}catch(EntityNotFoundException ex) {
			throw new GenericException("Error al importar registros", HttpStatus.BAD_REQUEST.toString());
		}
	}

	public String leerExcelTarEspCobu(Path tempFile) throws GenericException, FileNotFoundException, IOException, ParseException, OfficeXmlFileException{
		String responseMessage = "";
		List<ProcesadoDTO> contenido4 = new ArrayList<ProcesadoDTO>();
		
		XSSFWorkbook workbook4 = new XSSFWorkbook(new FileInputStream(tempFile.toFile()));
		XSSFSheet sheet4 = workbook4.getSheetAt(0);
		
		
		int numFilas4 = sheet4.getLastRowNum();
		for(int i = 1; i <= numFilas4; i++) {
			XSSFRow fila4 = sheet4.getRow(i);		
			ProcesadoDTO data4 = new ProcesadoDTO();
			
			data4.setNumCielnte((long) fila4.getCell(0).getNumericCellValue());
			data4.setBe((double) fila4.getCell(1).getNumericCellValue());
			data4.setVentanilla((double) fila4.getCell(2).getNumericCellValue());
			data4.setMensualidad((double) fila4.getCell(3).getNumericCellValue());
			data4.setId(i);
			
			contenido4.add(data4);
		}
		workbook4.close();
		Integer contentInint = contenido4.size();	
		
		 try {
			insertsCobuRepository.insertTarEspCobu(contenido4, 500);
	        } catch (Exception e) {
	            throw new GenericException(
	                    "Error al importar registros :: " , HttpStatus.NOT_FOUND.toString());
	        }
		
	  
	   responseMessage = "Carga de registros completa. Se han cargado un total de: " + contentInint + " elemento";
       return responseMessage;
	}

	/*@Override
	public CobuDTO procesoCobu() throws GenericException {
		try {
			
		}catch(EntityNotFoundException ex) {
			throw new GenericException("Error al importar registros", HttpStatus.BAD_REQUEST.toString());
		}
		log.info("procesoCobu ::  init");
		CobuDTO response = new CobuDTO();
        response.setProcesoCompletado("Proceso Completo");
        return response;
	}


	@Override
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
	}


	@Override
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
