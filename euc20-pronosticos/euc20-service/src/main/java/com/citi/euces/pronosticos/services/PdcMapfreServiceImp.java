package com.citi.euces.pronosticos.services;

import com.citi.euces.pronosticos.infra.dto.*;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.infra.utils.ConstantUtils;
import com.citi.euces.pronosticos.infra.utils.FormatUtils;
import com.citi.euces.pronosticos.repositories.PdcMapfreJDBCRepository;
import com.citi.euces.pronosticos.services.api.PdcMapfreService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
@Transactional
public class PdcMapfreServiceImp implements PdcMapfreService{

	static final Logger log = LoggerFactory.getLogger(PdcMapfreServiceImp.class);
	
	@Autowired
	private PdcMapfreJDBCRepository pdcMapfreJdbcRepository;
	
	
	///----------------------------------LAYOUT PREVIO----------------------------------------------///
	@Override
	public ReporteLayoutPrevioDTO ObtenerLayout(String fecCarga, Integer dias) throws GenericException, IOException, ParseException {
		try {
			log.info("Ejecuta LayoutPrevio ");
				
			//Consulta de Layout Previo
			List<LayoutPrevioDTO> res =  pdcMapfreJdbcRepository.ObtenerConsulta(fecCarga);
			List<RepoLayoutPrevioDTO> datosSalida = new ArrayList<>();
			
			for(LayoutPrevioDTO dato : res) {
				RepoLayoutPrevioDTO salida = new RepoLayoutPrevioDTO();
				
				salida.setFecCarga(dato.getFecCarga());
				salida.setCtratoTelCta(dato.getCtratoTelCta());
				salida.setCtaOrigen(dato.getCtaOrigen());
				salida.setCantidad(dato.getCantidad().toString());
				salida.setEmisor("100011");
				salida.setDias(dias.toString());
				salida.setCtaAbono("02680933270");
				salida.setConcepto("9002");
				salida.setRefEmisor("9002");
				salida.setLeyenda("MAS910614BR6 Domi Asistencia Familiar 10");
				
				datosSalida.add(salida);	            
			}
			log.info("Se descargo Layout Previo "+datosSalida);
	        Page<RepoLayoutPrevioDTO> pageResponse = new PageImpl<>(datosSalida);
			String file="";
			file = createFileRepLayout(datosSalida);
			
			ReporteLayoutPrevioDTO response = new ReporteLayoutPrevioDTO();
			response.setRepoLayoutPrevioDTO(pageResponse);
			response.setFile(file);
	        return response;
		}catch(EntityNotFoundException ex) {
			throw new GenericException("Error al generar archivo. ", HttpStatus.BAD_REQUEST.toString());
			
		}
	}
	
	private String createFileRepLayout(List<RepoLayoutPrevioDTO> datosSalida) throws IOException {
        Path fileLayoutPrevio = Files.createTempFile("LayoutPrevio", ".txt");
        fileLayoutPrevio.toFile().deleteOnExit();
        FileOutputStream test = new FileOutputStream(fileLayoutPrevio.toFile());
        String content = "";
        test.write(ConstantUtils.ENCABEZADO_LAYOUT_PREVIO.getBytes(StandardCharsets.UTF_8));
        for(RepoLayoutPrevioDTO str: datosSalida) {
            content = "";
            content += str.getFecCarga().concat("\t");
            content += str.getCtratoTelCta().concat("\t");
            content += str.getCtaOrigen().concat("\t");
            content += str.getCantidad().concat("\t");
            content += str.getEmisor().concat("\t");
            content += str.getDias().concat("\t");
            content += str.getCtaAbono().concat("\t");
            content += str.getConcepto().concat("\t");
            content += str.getRefEmisor().concat("\t");
            content += str.getLeyenda().concat("\n");
            test.write(content.getBytes(StandardCharsets.UTF_8));
        }
        Path fileLayoutPrevioZip = FormatUtils.convertZip(fileLayoutPrevio);
        String ecoder = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(fileLayoutPrevioZip.toFile()));
        log.info("File Encoder LayoutPrevio.zip :: "+ ecoder);
        return ecoder;
    }

	
	///------------------------------------TASA CERO--------------------------------///
	
	@Override
	public MensajeDTO GenerarTasaCero(String file, String fecha) throws GenericException, IOException, ParseException {
		try {
			log.info("Tasa Cero, crear layout :: init");
	        
			//Descomprime ZIP
			Path testFile = Files.createTempFile("tasa_Cero", ".zip");
	        testFile.toFile().deleteOnExit();
	        byte[] decoder = Base64.getDecoder().decode(file);
	        Files.write(testFile, decoder);
	        ZipFile zipFile = new ZipFile(testFile.toFile());
	        Enumeration<?> enu = zipFile.entries();
	        String procesados = "";
	        
	        while (enu.hasMoreElements()) {
	            ZipEntry zipEntry = (ZipEntry) enu.nextElement();
	         
	            InputStream is = zipFile.getInputStream(zipEntry);
	            Path tempFile = Files.createTempFile("tasa_Cero", ".xlsx");
	            tempFile.toFile().deleteOnExit();
	            try (FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
	                IOUtils.copy(is, fos);
	                //procesados = leerExcelTasaCero(tempFile);
	                List<ReporteTasaCeroDTO> contenido = new ArrayList<ReporteTasaCeroDTO>();
	        		
	        		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(tempFile.toFile()));
	        		XSSFSheet sheet = workbook.getSheetAt(0);
	        	
	        		int numFilas2 = sheet.getLastRowNum();
	        		for(int i = 1; i <= numFilas2; i++) {
	        			XSSFRow fila = sheet.getRow(i);		
	        			ReporteTasaCeroDTO data = new ReporteTasaCeroDTO();
	        			
	        			data.setAreaOrigen(fila.getCell(0).getStringCellValue());	 
	        			data.setCostoOperativo((float) fila.getCell(1).getNumericCellValue());
	        			data.setIva((float) fila.getCell(2).getNumericCellValue());
	        			data.setSucursal(fila.getCell(3).getStringCellValue());
	        			data.setCuenta(fila.getCell(4).getStringCellValue());
	        			data.setNumCliente(fila.getCell(5).getStringCellValue());
	        			data.setNombreNegocio(fila.getCell(6).getStringCellValue());
	        			data.setClaveCA(fila.getCell(7).getStringCellValue());
	        			data.setConcepto(fila.getCell(8).getStringCellValue());
	        			data.setFechadeTX(fila.getCell(9).getStringCellValue());
	        			data.setFechaAplicacion(fila.getCell(10).getStringCellValue());
	        			data.setStatus(fila.getCell(11).getStringCellValue());
	        			data.setProceso(fila.getCell(12).getStringCellValue());
	        			data.setTipodeMoneda(fila.getCell(13).getStringCellValue());
	        			data.setSirh((int)fila.getCell(14).getNumericCellValue());
	        			data.setClienteAcreedorComision((int) fila.getCell(15).getNumericCellValue());
	        			data.setProducto(fila.getCell(16).getStringCellValue());
	        			data.setInicio((Date) fila.getCell(17).getDateCellValue());
	        			data.setFin((Date)fila.getCell(18).getDateCellValue());
	        			data.setPeriodoCobro(fila.getCell(19).getStringCellValue());
	        			
	                    contenido.add(data);
	                   
	        		}
	        		
	        		procesados = createFileTasaCero(contenido, fecha);
	        		
	        		workbook.close();
	            }
	        }
	        
	        zipFile.close();
	        
	        MensajeDTO response = new MensajeDTO();
	        response.setMensajeConfirm(procesados);
	        response.setMensajeInfo("Se genero Correctamente. ");
			return response;

		}catch(EntityNotFoundException ex) {
			throw new GenericException("Error al generar archivo, Favor de verificar la fecha de carga", HttpStatus.BAD_REQUEST.toString());
		}
	}
	
	private String createFileTasaCero(List<ReporteTasaCeroDTO> TasaCeroLayout, String fecha) throws IOException, ParseException {
		//Genera Archivo TXT
		 Path fileCrearfile = Files.createTempFile("TasaCero", ".txt");
		 fileCrearfile.toFile().deleteOnExit();
		 FileOutputStream test = new FileOutputStream(fileCrearfile.toFile());
		 String content = "";
		 
   		 // Crear fecha del dia que se hizo la creacion de el layout
		 String fechaActual = FormatUtils.formatFecActual();
		 Date formatFecha = FormatUtils.stringToDate(fecha);
		 String fechaMes = FormatUtils.formatMes(formatFecha);
		 String fechaAnio = FormatUtils.formatAnio(formatFecha);
		 
		 for(ReporteTasaCeroDTO str: TasaCeroLayout) {
			float opera = str.getCostoOperativo().floatValue()+str.getIva().floatValue();
			DecimalFormat format = new DecimalFormat("#.00");
			String total = format.format(opera);
			
	        content = "";
	        
			content += "1".concat("\t"); 													//Consecutivo
			content += str.getNumCliente().concat("\t"); 									//Cliente
			content += "".concat("\t"); 													//Blanco
			content += str.getSucursal().concat(" ").concat(str.getCuenta()).concat("\t"); 	//Sucursal + Cuenta
			content += total.concat("\t"); 								 					//Importe Total
			content += "16".concat("\t"); 													//Iva
			content += "".concat("\t"); 													//Causa Rechazo
			content += fechaMes.concat("\t"); 												//Mes
			content += fechaAnio.concat("\t"); 												//Año
			content += "TASA CERO".concat("\t"); 											//Servicio - TasaCero
			content += "".concat("\t"); 													//CSI 
			content += "TASA CERO".concat("\t"); 											//COM
			content += str.getCostoOperativo().toString().concat("\t"); 					//Comision sin IVA "CostoOperativo + ivaa";
			content += str.getIva().toString().concat("\t"); 								// IVA
			content += total.concat("\t"); 													// TOTAL
			content += "".concat("\t"); 													//COM_P
			content += "".concat("\t"); 													//LLAVE
			content += str.getSucursal().concat(" ").concat(str.getCuenta()).concat("\t"); 	//EJE
			content += "Cobro Especial".concat("\t"); 										//Catalogada
			content += fechaActual.concat("\t"); 											//Fecha rechazo
			content += "".concat("\t"); 													//Espacio 1 
			content += "".concat("\t"); 													//Espacio 2
			content += "Cobro Especial".concat("\t"); 										//Base Pendiente
			content += str.getNombreNegocio().toString().concat("\n"); 						//Open Item
			
			test.write(content.getBytes(StandardCharsets.UTF_8));
		 }
		 
		 Path fileCrearTasaCeroZIP = FormatUtils.convertZip(fileCrearfile);
		 String ecoder = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(fileCrearTasaCeroZIP.toFile()));
		 log.info("File Encoder LayoutPrevio.zip :: "+ ecoder);
		 return ecoder;
    }	
}
