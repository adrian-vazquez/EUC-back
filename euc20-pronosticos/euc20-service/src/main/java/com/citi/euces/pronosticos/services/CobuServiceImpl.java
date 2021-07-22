package com.citi.euces.pronosticos.services;

import java.io.BufferedReader;
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
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.citi.euces.pronosticos.infra.dto.CifrasControlDTO;
import com.citi.euces.pronosticos.infra.dto.CobuDTO;
import com.citi.euces.pronosticos.infra.dto.CtasVirtualesDTO;
import com.citi.euces.pronosticos.infra.dto.CuentasVirtulesGposDTO;
import com.citi.euces.pronosticos.infra.dto.LayoutBeDTO;
import com.citi.euces.pronosticos.infra.dto.LayoutMensDTO;
import com.citi.euces.pronosticos.infra.dto.LayoutVentDTO;
import com.citi.euces.pronosticos.infra.dto.ProcesadoDTO;
import com.citi.euces.pronosticos.infra.dto.QueryCtosAgrupadoDTO;
import com.citi.euces.pronosticos.infra.dto.ReportesCobuDTO;
import com.citi.euces.pronosticos.infra.dto.TarifasDTO;
import com.citi.euces.pronosticos.infra.dto.TxnsImporteDTO;
import com.citi.euces.pronosticos.infra.dto.TxsCtasVirtDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.infra.utils.FormatUtils;
import com.citi.euces.pronosticos.repositories.*;
import com.citi.euces.pronosticos.services.api.CobuService;



@Service
@Transactional
public class CobuServiceImpl implements CobuService{
	static final Logger log = LoggerFactory.getLogger(RebajasServiceImp.class);

	@Autowired
	private TruncateTablesCobuRepository deleteTables;
	@Autowired
	private InsertsCobuRepository insertsCobuRepository;
	@Autowired
	private ProcesoCobuRepository procesoCobuRepository;
	@Autowired
	private ConsultasCobuRepository consultasCobuRepository;
	
	@Override	
	public CobuDTO limpiarCobu() throws GenericException{
		log.info("limpiarCobu");
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
		log.info("limpiarCobu");
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
			log.info("Query_Ctas_COBU");
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
	
	public String leerCsvCtasCobu(Path tempFile) throws GenericException, IOException, ParseException {	
		BufferedReader br = new BufferedReader(new FileReader(tempFile.toFile()));
		CSVParser parser = CSVParser.parse(br, CSVFormat.DEFAULT.withFirstRecordAsHeader());
		List<String> headerCtasCobu = parser.getHeaderNames();

		if(!headerCtasCobu.equals(FormatUtils.validaCtasCobu())) {
			throw new GenericException("Layout invalido. Favor de verificar" , HttpStatus.NOT_FOUND.toString());
		}

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
	            throw new GenericException("Error al importar registros :: " , HttpStatus.NOT_FOUND.toString());
	        }
        
        responseMessage = "Se han cargado un total de: " + max + " elementos";
        return responseMessage;
	}
	
	/************************************************************************************************/
	/************************************************************************************************/
	
	@Override
	public CobuDTO cargaCtasVirt(String file) throws GenericException, IOException, ParseException{
		try {
			log.info("TXS_CTAS_VIRT");
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
	
	public String leerCsvCtasVirt(Path tempFile) throws GenericException, IOException, ParseException {
		BufferedReader br = new BufferedReader(new FileReader(tempFile.toFile()));
		CSVParser parser = CSVParser.parse(br, CSVFormat.DEFAULT.withFirstRecordAsHeader());
		List<String> headerCtasVirt = parser.getHeaderNames();
		
		if(!headerCtasVirt.equals(FormatUtils.validaCtasVirt())) {
			throw new GenericException("Layout invalido. Favor de verificar" , HttpStatus.NOT_FOUND.toString());
		}
		
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
	            throw new GenericException("Error al importar registros :: " , HttpStatus.NOT_FOUND.toString());
	        }
        
        responseMessage = "Se han cargado un total de: " + max + " elementos";
        return responseMessage;
	}
	
	/************************************************************************************************/
	/************************************************************************************************/
	
	@Override
	public CobuDTO cargaTxsCtas(String file) throws GenericException, IOException, ParseException{
		try {
			log.info("Obtiene_TXS_CTAS");
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
	                try (FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
	                    IOUtils.copy(is, fos);
	                    log.info("termina:: init");
	                    procesados = leerCsvTxsCtas(tempFile);
	                 
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
	
	public String leerCsvTxsCtas(Path tempFile) throws GenericException, IOException, ParseException {
		BufferedReader br = new BufferedReader(new FileReader(tempFile.toFile()));
		CSVParser parser = CSVParser.parse(br, CSVFormat.DEFAULT.withFirstRecordAsHeader());
		List<String> headerTxsCtas = parser.getHeaderNames();
		
		if(!headerTxsCtas.equals(FormatUtils.validaCsvTxsCtas())) {
			throw new GenericException("Layout invalido. Favor de verificar" , HttpStatus.NOT_FOUND.toString());
		}
		
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
	            throw new GenericException("Error al importar registros :: " , HttpStatus.NOT_FOUND.toString());
	        }

        responseMessage = "Se han cargado un total de: " + max + " elementos";
        return responseMessage;
	}
	

	/************************************************************************************************/
	/************************************************************************************************/

	@Override
	public CobuDTO cargaTarEspCobu(String file) throws GenericException, IOException, ParseException{
		try {
			log.info("TAR_ESP_COBU");
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
	
	public String leerCsvTarEspCobu(Path tempFile) throws GenericException, IOException, ParseException {
		BufferedReader br = new BufferedReader(new FileReader(tempFile.toFile()));
		CSVParser parser = CSVParser.parse(br, CSVFormat.DEFAULT.withFirstRecordAsHeader());
		List<String> headerTarEspCobu = parser.getHeaderNames();

		if(!headerTarEspCobu.equals(FormatUtils.validaTarEspCobu())) {
			throw new GenericException("Layout invalido. Favor de verificar" , HttpStatus.NOT_FOUND.toString());
		}
		
		List<ProcesadoDTO> contenido4 = new ArrayList<ProcesadoDTO>();	
		String responseMessage = "";
		
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
	            throw new GenericException("Error al importar registros :: " , HttpStatus.NOT_FOUND.toString());
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
		                    "Error al importar registros en Cifras Control" , HttpStatus.NOT_FOUND.toString());
		        }
			  
		   responseMessage = "Proceso Completado";
	       return responseMessage;
	}
	
	/************************************************************************************************/
	/************************************************************************************************/

	@Override
	public ReportesCobuDTO reporte() throws GenericException, IOException, ParseException, SQLException, InvalidFormatException {
			
		/*if (consultasCobuRepository.countLayoutBe() > 0){
			throw new GenericException("Error existen duplicados en el Layout_Be. Favor de verificar" , HttpStatus.NOT_FOUND.toString());
        }

        if (consultasCobuRepository.countLayouMens() > 0){
        	throw new GenericException("Error existen duplicados en el Layout_Mens. Favor de verificar" , HttpStatus.NOT_FOUND.toString());
        }

        if (consultasCobuRepository.countLayoutVent() > 0){
        	throw new GenericException("Error existen duplicados en el Layout_Vent. Favor de verificar" , HttpStatus.NOT_FOUND.toString());
        }*/
		
		
			List<LayoutBeDTO> consultaBe = consultasCobuRepository.cosultaLayoutBe();
			List<LayoutMensDTO> consultaMens = consultasCobuRepository.cosultaLayoutMens();
			List<LayoutVentDTO> consultaVent = consultasCobuRepository.cosultaLayoutVent();
			List<TarifasDTO> consultaTarifas = consultasCobuRepository.cosultaTarifas();
			List<CuentasVirtulesGposDTO> consultaCtasVirtGpos = consultasCobuRepository.cosultaCtasVirtualesGpos();
			List<TxnsImporteDTO> consultaTxnsImporte = consultasCobuRepository.cosultaTxnsImporte();
						
			String encabezadoBe[] = new String[] {"NUM_CLIENTE", "NOMBRE", "SUC", "CTA", "COM_BE", "MONTO_IVA", "MONTO_TOTAL", "AÑO", "MES", "PRODUCTO", "IVA", "MONEDA", "USO11"};
			String encabezadoMens[] = new String[] {"NUM_CLIENTE", "NOMBRE", "SUC", "CTA", "COM_MENS", "MONTO_IVA", "MONTO_TOTAL", "AÑO", "MES", "PRODUCTO", "IVA", "MONEDA"};
			String encabezadoVent[] = new String[] {"NUM_CLIENTE", "TARIFA_BE", "TAFIRA_VENT", "TARIFA_MENS"};
			String encabezadoTarifas[] = new String[] {"NUM_CLIENTE", "TARIFA_BE", "TARIFA_VENT", "TARIFA_MENS"};
			String encabezadoCtasVirtGpos[] = new String[] {"NUM_CLIENTE", "NUM_CUENTA", "NOMBRE", "CTAS_V", "TXNS_BE", "TXNS_VENT", "TARIFA_BE", "TARIFA_VENT", "TARIFA_MENS", "COM_BE", "COM_VENT", "COM_MENS", "USO11", "COM_TOTAL", "SUC", "CTA", "FRANQUICIA", "MONEDA", "IVA"};						
			String encabezadoTxnsImporte[] = new String[] {"NUM_CLIENTE", "NUM_CUENTA", "TIPO", "TXNS", "SumaDeIMP_TRANSACCION"};

			List<List<String>> filaBe = new ArrayList<>();
			consultaBe.forEach(ld -> {
		            List<String> celdaBe = new ArrayList<String>();
		            celdaBe.add(ld.getNumCliente().toString());
		            celdaBe.add(ld.getNombre());
		            celdaBe.add(String.valueOf(ld.getSuc()));
		            celdaBe.add(ld.getCta().toString());
		            celdaBe.add(ld.getComBe().toString());
		            celdaBe.add(ld.getMontoIva().toString());
		            celdaBe.add(String.valueOf(ld.getMontoTotal()));
		            celdaBe.add(String.valueOf(ld.getAnio()));
		            celdaBe.add(String.valueOf(ld.getMes()));
		            celdaBe.add(ld.getProducto());
		            celdaBe.add(ld.getIva().toString());
		            celdaBe.add(ld.getMoneda().toString());
		            celdaBe.add(ld.getUso11());		         
		            filaBe.add(celdaBe);          
			});
			
			List<List<String>> filaMens = new ArrayList<>();
			consultaMens.forEach(ld -> {
		            List<String> celdaMens = new ArrayList<String>();
		            celdaMens.add(ld.getNumCliente().toString());
		            celdaMens.add(ld.getNombre());
		            celdaMens.add(String.valueOf(ld.getSuc()));
		            celdaMens.add(ld.getCta().toString());
		            celdaMens.add(ld.getComMens().toString());
		            celdaMens.add(ld.getMontoIva().toString());
		            celdaMens.add(ld.getMontoTotal().toString());
		            celdaMens.add(String.valueOf(ld.getAnio()));
		            celdaMens.add(String.valueOf(ld.getMes()));
		            celdaMens.add(ld.getProducto());
		            celdaMens.add(ld.getIva().toString());
		            celdaMens.add(ld.getMoneda().toString());	         
		            filaMens.add(celdaMens);          
			});
			
			List<List<String>> filaVent = new ArrayList<>();
			consultaVent.forEach(ld -> {
		            List<String> celdaVent = new ArrayList<String>();
		            celdaVent.add(ld.getNumCliente().toString());
		            celdaVent.add(ld.getNombre());
		            celdaVent.add(String.valueOf(ld.getSuc()));
		            celdaVent.add(ld.getCta().toString());
		            celdaVent.add(ld.getComVent().toString());
		            celdaVent.add(ld.getMontoIva().toString());
		            celdaVent.add(String.valueOf(ld.getMontoTotal()));
		            celdaVent.add(String.valueOf(ld.getAnio()));
		            celdaVent.add(String.valueOf(ld.getMes()));
		            celdaVent.add(ld.getProducto());
		            celdaVent.add(ld.getIva().toString());
		            celdaVent.add(ld.getMoneda().toString());
		            celdaVent.add(ld.getUso11());		         
		            filaVent.add(celdaVent);          
			});
			
			List<List<String>> filaTarifas = new ArrayList<>();
			consultaTarifas.forEach(ld -> {
		            List<String> celdaTarifas = new ArrayList<String>();
		            celdaTarifas.add(ld.getNumCliente().toString());
		            celdaTarifas.add(ld.getTarifaTxBe().toString());
		            celdaTarifas.add(ld.getTarifaTxSuc().toString());
		            celdaTarifas.add(ld.getTarifaMensual().toString());         
		            filaTarifas.add(celdaTarifas);          
			});
						
			List<List<String>> filaCtasVirtGpos = new ArrayList<>();
			consultaCtasVirtGpos.forEach(ld -> {
		            List<String> celdaCtasVirtGpos = new ArrayList<String>();
		            celdaCtasVirtGpos.add(ld.getNumCliente().toString());
		            celdaCtasVirtGpos.add(ld.getNumCuenta().toString());
		            celdaCtasVirtGpos.add(ld.getNombre());
		            celdaCtasVirtGpos.add(ld.getCtasV().toString());
		            celdaCtasVirtGpos.add(ld.getTxnsBe().toString());
		            celdaCtasVirtGpos.add(ld.getTxnsVent().toString());
		            celdaCtasVirtGpos.add(ld.getTarifaBe().toString());
		            celdaCtasVirtGpos.add(ld.getTarifaVent().toString());
		            celdaCtasVirtGpos.add(ld.getTarifaMens().toString());
		            celdaCtasVirtGpos.add(ld.getComBe().toString());
		            celdaCtasVirtGpos.add(ld.getComVent().toString());
		            celdaCtasVirtGpos.add(ld.getComMens().toString());
		            celdaCtasVirtGpos.add(ld.getUso11());
		            celdaCtasVirtGpos.add(ld.getComTotal().toString());
		            celdaCtasVirtGpos.add(String.valueOf(ld.getSuc()));
		            celdaCtasVirtGpos.add(ld.getCuenta().toString());
		            celdaCtasVirtGpos.add(ld.getFranquicia().toString());
		            celdaCtasVirtGpos.add(String.valueOf(ld.getMoneda()));
		            celdaCtasVirtGpos.add(ld.getIva().toString());	          
		            filaCtasVirtGpos.add(celdaCtasVirtGpos);          
			});
			
			List<List<String>> filaTxnsImporte = new ArrayList<>();
			consultaTxnsImporte.forEach(ld -> {
		            List<String> celdaTxnsImporte = new ArrayList<String>();
		            celdaTxnsImporte.add(ld.getNumCliente().toString());
		            celdaTxnsImporte.add(ld.getNumCuenta().toString());
		            celdaTxnsImporte.add(ld.getTipo());    
		            celdaTxnsImporte.add(ld.getSumImpTrans().toString());  
		            filaTxnsImporte.add(celdaTxnsImporte);          
			});
			
			 Path testFile = Files.createTempFile("Reporte COBU", ".xlsx");
			 try(XSSFWorkbook workbook = new XSSFWorkbook()) {
		            XSSFSheet sheetBe = workbook.createSheet();
		            workbook.setSheetName(0, "Layout_be");
		            int colHeaderBe = 0;
		            Row rowheaderBe = sheetBe.createRow(colHeaderBe++);
		            int colCellBe = 0;
		            for (String field : encabezadoBe) {
		                Cell cell = rowheaderBe.createCell(colCellBe++);
		                if (field instanceof String) {
		                    cell.setCellValue((String) field);
		                }
		            }
		            
		            int rowNumBe = 1;
		            for (List<String> key : filaBe) {
		                Row row = sheetBe.createRow(rowNumBe++);
		                int colNum = 0;
		                for (String field : key) {
		                    Cell cell = row.createCell(colNum++);
		                    if (field instanceof String) {
		                        cell.setCellValue((String) field);
		                    }
		                }  
		            }
		            
		            XSSFSheet sheetMens = workbook.createSheet();
		            workbook.setSheetName(1, "Layout_Mens");
		            int colHeaderMens = 0;
		            Row rowheaderMens = sheetMens.createRow(colHeaderMens++);
		            int colCellMens = 0;
		            for (String field : encabezadoMens) {
		                Cell cell = rowheaderMens.createCell(colCellMens++);
		                if (field instanceof String) {
		                    cell.setCellValue((String) field);
		                }
		            }
		            
		            int rowNumMens = 1;
		            for (List<String> key : filaMens) {
		                Row row = sheetMens.createRow(rowNumMens++);
		                int colNumMens = 0;
		                for (String field : key) {
		                    Cell cell = row.createCell(colNumMens++);
		                    if (field instanceof String) {
		                        cell.setCellValue((String) field);
		                    }
		                }  
		            }
		            
		            XSSFSheet sheetVent = workbook.createSheet();
		            workbook.setSheetName(2, "Layout_Vent");
		            int colHeaderVent = 0;
		            Row rowheaderVent = sheetVent.createRow(colHeaderVent++);
		            int colCellVent = 0;
		            for (String field : encabezadoVent) {
		                Cell cell = rowheaderVent.createCell(colCellVent++);
		                if (field instanceof String) {
		                    cell.setCellValue((String) field);
		                }
		            }
		            
		            int rowNumVent = 1;
		            for (List<String> key : filaVent) {
		                Row row = sheetVent.createRow(rowNumVent++);
		                int colNumVent = 0;
		                for (String field : key) {
		                    Cell cell = row.createCell(colNumVent++);
		                    if (field instanceof String) {
		                        cell.setCellValue((String) field);
		                    }
		                }  
		            }
		            
		            XSSFSheet sheetTarifas = workbook.createSheet();
		            workbook.setSheetName(3, "Tarifas");
		            int colHeaderTarifas = 0;
		            Row rowheaderTarifas = sheetTarifas.createRow(colHeaderTarifas++);
		            int colCellTarifas = 0;
		            for (String field : encabezadoTarifas) {
		                Cell cell = rowheaderTarifas.createCell(colCellTarifas++);
		                if (field instanceof String) {
		                    cell.setCellValue((String) field);
		                }
		            }
		            
		            int rowNumTarifas = 1;
		            for (List<String> key : filaTarifas) {
		                Row row = sheetTarifas.createRow(rowNumTarifas++);
		                int colNumTarifas = 0;
		                for (String field : key) {
		                    Cell cell = row.createCell(colNumTarifas++);
		                    if (field instanceof String) {
		                        cell.setCellValue((String) field);
		                    }
		                }  
		            }
		            
		            XSSFSheet sheetCtasVirtGpos = workbook.createSheet();
		            workbook.setSheetName(4, "Detalle TXS X CTA");
		            int colHeaderCtasVirtGpos = 0;
		            Row rowheaderCtasVirtGpos = sheetCtasVirtGpos.createRow(colHeaderCtasVirtGpos++);
		            int colCellCtasVirtGpos = 0;
		            for (String field : encabezadoCtasVirtGpos) {
		                Cell cell = rowheaderCtasVirtGpos.createCell(colCellCtasVirtGpos++);
		                if (field instanceof String) {
		                    cell.setCellValue((String) field);
		                }
		            }
		            
		            int rowNumCtasVirtGpos = 1;
		            for (List<String> key : filaCtasVirtGpos) {
		                Row row = sheetCtasVirtGpos.createRow(rowNumCtasVirtGpos++);
		                int colNumCtasVirtGpos = 0;
		                for (String field : key) {
		                    Cell cell = row.createCell(colNumCtasVirtGpos++);
		                    if (field instanceof String) {
		                        cell.setCellValue((String) field);
		                    }
		                }  
		            }
		            
		            XSSFSheet sheetTxnsImporte = workbook.createSheet();
		            workbook.setSheetName(5, "Txns_Con_Importe");
		            int colHeaderTxnsImporte = 0;
		            Row rowheaderTxnsImporte = sheetTxnsImporte.createRow(colHeaderTxnsImporte++);
		            int colCellTxnsImporte = 0;
		            for (String field : encabezadoTxnsImporte) {
		                Cell cell = rowheaderTxnsImporte.createCell(colCellTxnsImporte++);
		                if (field instanceof String) {
		                    cell.setCellValue((String) field);
		                }
		            }
		            
		            int rowNumTxnsImporte = 1;
		            for (List<String> key : filaTxnsImporte) {
		                Row row = sheetTxnsImporte.createRow(rowNumTxnsImporte++);
		                int colNumTxnsImporte = 0;
		                for (String field : key) {
		                    Cell cell = row.createCell(colNumTxnsImporte++);
		                    if (field instanceof String) {
		                        cell.setCellValue((String) field);
		                    }
		                }  
		            }
		            ByteArrayOutputStream bos = new ByteArrayOutputStream();
		            workbook.write(bos);
		            bos.close();
		            Files.write(testFile, bos.toByteArray());
			
			 }
			 Path reporteCobu = FormatUtils.convertZip(testFile);
		     String ecoder = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(reporteCobu.toFile()));

		     log.info("File Encoder ReporteCobu.zip :: " + ecoder);
		     ReportesCobuDTO response = new ReportesCobuDTO();
		     response.setFile(ecoder);
		     return response;		
	}


	@Override
	public ReportesCobuDTO cifrasControl() throws GenericException, IOException, ParseException, SQLException, InvalidFormatException {
			List<CifrasControlDTO> consultaCifras = consultasCobuRepository.cosultaCifras();
			String encabezadoCifras[] = new String[] {"Consulta", "Cifra"};			
			if (consultaCifras.isEmpty()) {
	            throw new GenericException("No hay registros en Cifras Control",HttpStatus.NOT_FOUND.toString());
	        }
			
			List<List<String>> filas = new ArrayList<>();
			consultaCifras.forEach(ld -> {
		            List<String> celdas = new ArrayList<String>();
		            celdas.add(ld.getConsulta().toString());
		            celdas.add(String.valueOf(ld.getCifra()));
		            filas.add(celdas);          
			});
			 Path testFile = Files.createTempFile("CifrasCOBU", ".xlsx");
		        try(XSSFWorkbook workbook = new XSSFWorkbook()) {
		            XSSFSheet sheet = workbook.createSheet("Resumen");
		            int colHeader = 0;
		            Row rowheader = sheet.createRow(colHeader++);
		            int colCell = 0;
		            for (String field : encabezadoCifras) {
		                Cell cell = rowheader.createCell(colCell++);
		                if (field instanceof String) {
		                    cell.setCellValue((String) field);
		                }
		            }
		            int rowNum = 1;
		            for (List<String> key : filas) {
		                Row row = sheet.createRow(rowNum++);
		                int colNum = 0;
		                for (String field : key) {
		                    Cell cell = row.createCell(colNum++);
		                    if (field instanceof String) {
		                        cell.setCellValue((String) field);
		                    }
		                }
		            }
		            ByteArrayOutputStream bos = new ByteArrayOutputStream();
		            workbook.write(bos);
		            bos.close();
		            Files.write(testFile, bos.toByteArray());
		        }
		        Path cifrasZip = FormatUtils.convertZip(testFile);
		        String ecoder = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(cifrasZip.toFile()));

		        log.info("File Encoder CifrasControl.zip :: " + ecoder);
		        ReportesCobuDTO response = new ReportesCobuDTO();
		        response.setFile(ecoder);
		        return response;
	
	}


	


}
