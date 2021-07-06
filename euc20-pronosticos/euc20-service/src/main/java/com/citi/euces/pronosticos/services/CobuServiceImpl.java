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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.exception.SQLGrammarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;

import com.citi.euces.pronosticos.infra.dto.CobuDTO;
import com.citi.euces.pronosticos.infra.dto.CtasVirtualesDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.infra.utils.FormatUtils;
import com.citi.euces.pronosticos.repositories.*;
import com.citi.euces.pronosticos.services.api.CobuService;



@Service
@Transactional
public class CobuServiceImpl implements CobuService{
	static final Logger log = LoggerFactory.getLogger(RebajasServiceImp.class);

	@Autowired
	private CifrasControlRepository cifrasControl;
	@Autowired
	private CtasVirtualesGposRepository ctasVirtualesGpos;	
	@Autowired
	private CtasVirtualesRepository ctasVirtuales;	
	@Autowired
	private CtosUnicosRepository ctosUnicos;	
	@Autowired
	private LayoutBeRepository layoutBe;	
	@Autowired
	private LayoutMensRepository layoutMens;	
	@Autowired
	private LayoutVentRepository layoutVent;	
	@Autowired
	private ProcesadoRepository procresado;	
	@Autowired
	private QueryCtosAgrupadoRepository queryCtosAgrupado;	
	@Autowired
	private QueryCtosCobuRepository queryCtosCobu;	
	@Autowired
	private QueryCtosDuplicadosRepository queryCtosDuplicados;	
	@Autowired
	private TarifasRepository tarifas;	
	@Autowired
	private TxnsImporteRepository txnsImporte;	
	@Autowired
	private TxnsXTipoRepository txnsXTipo;	
	@Autowired
	private TxsCtasVirtRepository txsCtasVirt;	
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


	/*@Override
	public CobuDTO cargaCtasCobu(String file) throws GenericException, IOException{
		log.info("aplicarRebajaloadFile ::  init");
        log.info("File :: " + file);
        Path testFile = Files.createTempFile("rebajasZip", ".zip");
        testFile.toFile().deleteOnExit();
        byte[] decoder = Base64.getDecoder().decode(file);
        Files.write(testFile, decoder);
        System.out.println(testFile.toFile().getAbsoluteFile());
        System.out.println("arhivo Zip guardado");

        ZipFile zipFile = new ZipFile(testFile.toFile());
        Enumeration<?> enu = zipFile.entries();
        Integer procesados = 0;
		try {
			
			
		}catch(EntityNotFoundException ex) {
			throw new GenericException("Error al importar registros", HttpStatus.BAD_REQUEST.toString());
		}
		int totalRegistros = 0;
		log.info("queryCtosAgrupado ::  init");
        CobuDTO response = new CobuDTO();
        response.setProcesoCompletado("El archivo se importó exitosamente" + totalRegistros);
        return response;
	}*/


	@Override
	public CobuDTO cargaCtasVirt(String file) throws GenericException, IOException, ParseException{
		try {
			log.info("Query_Ctas_COBU  ::  init");
	        Path testFile = Files.createTempFile("cargaCtasVirt", ".zip");
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
	                Path tempFile = Files.createTempFile("TXS_CTAS_VIRT", ".xlsx");
	                tempFile.toFile().deleteOnExit();
	                try (FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
	                    IOUtils.copy(is, fos);
	                    procesados = leerExcel(tempFile);
	                 
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
	
	public String leerExcel(Path tempFile) throws GenericException, FileNotFoundException, IOException, ParseException, OfficeXmlFileException{
		String responseMessage = "";
		List<CtasVirtualesDTO> contenido = new ArrayList<CtasVirtualesDTO>();
		
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(tempFile.toFile()));
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		
		int numFilas = sheet.getLastRowNum();
		for(int i = 1; i < numFilas; i++) {
			XSSFRow fila = sheet.getRow(i);		
			CtasVirtualesDTO data = new CtasVirtualesDTO();
			 
			 data.setNumCliente(fila.getCell(0).getNumericCellValue());
             data.setNumCuenta(fila.getCell(1).getNumericCellValue());
             data.setFecAlta(fila.getCell(2).getStringCellValue());
             data.setCuentasX(fila.getCell(3).getNumericCellValue());
             data.setNombre(fila.getCell(5).getStringCellValue());
             contenido.add(data);
           
		}
		workbook.close();
		Integer contentInint = contenido.size();	
		
		 try {
			insertsCobuRepository.insertCtasVirtuales(contenido);
	        } catch (Exception e) {
	            throw new GenericException(
	                    "Error al importar registros :: " , HttpStatus.NOT_FOUND.toString());
	        }
		
	  
	   responseMessage = "Carga de registros completa" + contenido.size() + " de un total de:" + " de un total de:"
               + (contentInint - 1) + " elementos.";
       return responseMessage;
   }


	/*@Override
	public CobuDTO cargaTxsCtas(String file) throws GenericException, IOException{
		try {
			
		}catch(EntityNotFoundException ex) {
			throw new GenericException("Error al importar registros", HttpStatus.BAD_REQUEST.toString());
		}
		int totalRegistros = 0;
		log.info("cargaTxsCtas ::  init");
		CobuDTO response = new CobuDTO();
        response.setProcesoCompletado("El archivo se importó exitosamente" + totalRegistros);
        return response;
	}


	@Override
	public CobuDTO cargaTarEspCobu(String file) throws GenericException, IOException{
		try {
			
		}catch(EntityNotFoundException ex) {
			throw new GenericException("Error al importar registros", HttpStatus.BAD_REQUEST.toString());
		}
		int totalRegistros = 0;
		log.info("cargaTarEspCobu ::  init");
		CobuDTO response = new CobuDTO();
        response.setProcesoCompletado("El archivo se importó exitosamente" + totalRegistros);
        return response;
	}


	@Override
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
