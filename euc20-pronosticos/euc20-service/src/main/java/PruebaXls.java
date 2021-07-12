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

import org.apache.commons.io.IOUtils;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;

import com.citi.euces.pronosticos.infra.dto.CobuDTO;
import com.citi.euces.pronosticos.infra.dto.TxsCtasVirtDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;

public class PruebaXls {
	public static void main(String[]args) throws GenericException, IOException, ParseException {
		String archivo = "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";
		cargaTxsCtas(archivo);
	}
	public static CobuDTO cargaTxsCtas(String file) throws GenericException, IOException, ParseException{
		try {
			//System.out.print("Obtiene_TXS_CTAS :: init");
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
	       response.setProcesoResultado("Proceso completado");
	        return response;
		}catch(EntityNotFoundException ex) {
			throw new GenericException("Error al importar registros", HttpStatus.BAD_REQUEST.toString());
		}
	}

	public static String leerExcelTxsCtas(Path tempFile) throws GenericException, FileNotFoundException, IOException, ParseException, OfficeXmlFileException{
		String responseMessage = "";
		List<TxsCtasVirtDTO> contenido3 = new ArrayList<TxsCtasVirtDTO>();
		
		XSSFWorkbook workbook3 = new XSSFWorkbook(new FileInputStream(tempFile.toFile()));
		XSSFSheet sheet3 = workbook3.getSheetAt(0);
		
		
		int numFilas3 = sheet3.getLastRowNum();
		for(int i = 0; i <= numFilas3; i++) {
			XSSFRow fila3 = sheet3.getRow(i);		
			TxsCtasVirtDTO data3 = new TxsCtasVirtDTO();
			 
			
			//data3.setCteAlias(String.valueOf(fila3.getCell(2).getCellType()));///////////////////////////////////////////////////////
			DataFormatter formatter = new DataFormatter();
			System.out.println(fila3.getCell(1));
			String val = formatter.formatCellValue(fila3.getCell(2));
			
			System.out.println(val);

			
            contenido3.add(data3);
           
		}
		workbook3.close();
		Integer contentInint = contenido3.size();	
		
		 try {
			//insertsCobuRepository.insertTxsCtas(contenido3, 500);
	        } catch (Exception e) {
	            throw new GenericException(
	                    "Error al importar registros :: " , HttpStatus.NOT_FOUND.toString());
	        }
		
	  
	   responseMessage = "Carga de registros completa. Se han cargado un total de: " + contentInint + " elemento";
       return responseMessage;
   }
}
