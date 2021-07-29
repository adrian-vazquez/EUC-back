package com.citi.euces.pronosticos.services;

import com.citi.euces.pronosticos.infra.dto.CargarCuentasCtrlRepoDTO;
import com.citi.euces.pronosticos.infra.dto.LayoutProteccionDTO;
import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.infra.utils.FormatUtils;
import com.citi.euces.pronosticos.repositories.LayoutProteccionJDBCRepository;
import com.citi.euces.pronosticos.services.api.IRepoService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Service
@Transactional
public class RepoServicesImpl implements IRepoService{
	
	static final Logger log = LoggerFactory.getLogger(RebajasServiceImp.class);
	
	private LayoutProteccionJDBCRepository layoutProteccionJDBCRepository;
	
	public RepoServicesImpl(LayoutProteccionJDBCRepository layoutProteccionJDBCRepository) {
		this.layoutProteccionJDBCRepository=layoutProteccionJDBCRepository;
		
	}

	@Override
	public String getLayoutProteccion() throws  GenericException {
		List<LayoutProteccionDTO> datos= layoutProteccionJDBCRepository.getDataLayout();
		File archivoTxt=generaTxt(datos);
		try{
			if(archivoTxt!=null) {
				return fileToB64(FormatUtils.convertZip(archivoTxt.toPath()));
			}
		}catch(Exception e) {
			throw new GenericException("Error en generar el layout", HttpStatus.BAD_REQUEST.toString());
			
		}
		return null;
		
	}
	
	@Override
	public MensajeDTO cargarCuentas(String file, String fecha) throws GenericException, IOException {
		log.info("cargarCuentas ::  init");
		Path testFile = Files.createTempFile("cargarCuentasZip", ".zip");
		testFile.toFile().deleteOnExit();
		byte[] decoder = Base64.getDecoder().decode(file);
		Files.write(testFile, decoder);
		ZipFile zipFile = new ZipFile(testFile.toFile());
		Enumeration<?> enu = zipFile.entries();
		String procesados = "";
		while (enu.hasMoreElements()) {
			ZipEntry zipEntry = (ZipEntry) enu.nextElement();
			String name = zipEntry.getName();
			long size = zipEntry.getSize();
			long compressedSize = zipEntry.getCompressedSize();
			if (name.endsWith("/") || name.startsWith("__MACOSX")) {
				continue;
			}
			InputStream is = zipFile.getInputStream(zipEntry);
			Path tempFile = Files.createTempFile("catgarCuentasTXT", ".txt");
			tempFile.toFile().deleteOnExit();
			try (FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
				IOUtils.copy(is, fos);
				//Lectura TXT
				procesados = leerArchivo(tempFile, fecha);
			}
		}
		zipFile.close();
		MensajeDTO response = new MensajeDTO();
		response.setMensajeConfirm(procesados);
		return response;
	}
	
	//Escribe la lista en archivo txt
	private File generaTxt(List<?> lista) {
		File txt=null;
		try {
			txt = File.createTempFile("archivo", ".txt");
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		
		try(PrintWriter escribir=new PrintWriter(new FileWriter(txt,true))) {
			Iterator<?> iter=lista.listIterator();
			while(iter.hasNext()) {
				escribir.println(iter.next().toString());
				
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return txt;
		
	}
	
	//Convierte el archivo en b64
	private String fileToB64(Path archivo) {
		byte[] archivoBytes = null;
		try {
			archivoBytes = Files.readAllBytes(archivo);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return Base64.getMimeEncoder().encodeToString(archivoBytes).replaceAll("\r\n", "");

	}
	
	
	public String leerArchivo(Path tempFile, String fecha) throws IOException, GenericException {
		String responseMessage = "";
		String cadena;
		FileReader f = new FileReader(tempFile.toFile());
		BufferedReader b = new BufferedReader(f);
		String[] readFile = new String[0];
		List<CargarCuentasCtrlRepoDTO> content = new ArrayList<>();
		Long consecutuvo = 0L;
		while ((cadena = b.readLine()) != null) {
			readFile = cadena.split("\t");
			if(readFile.length == 7){
				consecutuvo++;
				CargarCuentasCtrlRepoDTO data = new CargarCuentasCtrlRepoDTO();
				data.setConsecutivo(1L);
				data.setNoCliente(Long.parseLong(readFile[0])); //cliente
				data.setBlanco("");
				data.setCuenta(crearCuenta(readFile[1]));//cuenta
				data.setImporte(Double.parseDouble(readFile[4])); //total
				data.setIva(new Double(16));
				data.setCausaRechazo("");
				data.setMes(FormatUtils.validFechaMes(Integer.parseInt(readFile[5])));//mes
				data.setAnio(Integer.parseInt(readFile[6])); //anio
				data.setServicio("PE REP EXT");
				data.setCsi("");
				data.setCom(0F);  //va vacio
				data.setComisionSinIva(Float.parseFloat(readFile[2])); //comision
				data.setIvaa(Float.parseFloat(readFile[3])); //iva
				data.setTotal(Float.parseFloat(readFile[4])); //total
				data.setCom(0F); //va vacio
				data.setLlave("PE".concat(FormatUtils.formatDateSinEspacios(new Date())).concat(consecutuvo.toString()));
				data.setEje(crearEje(readFile[1]));
				data.setCatalogada("Perfil Ejecutivo");
				data.setEtiqueta("Perfil Ejecutivo");
			}
			log.info("readFile :: " + readFile.length);
			for (String line : readFile){
				log.info("line :: " + line);
			}
		}
		b.close();
		return responseMessage;
	}
	
	public static String crearCuenta(String cuenta) {
		String cuentaFinal = "";
		if(cuenta.length() > 1){
			String dato = cuenta.substring(cuenta.length() - 9 , (cuenta.length() - 9) + 7);
			Integer length1 =  dato.length() - cuenta.length();
			String cuentaParte1 = cuenta.substring(0, Math.abs(length1));
			String cuentaParte2 = cuenta.substring(Math.abs(length1), (Math.abs(length1) + 1) + 6);
			cuentaFinal = cuentaParte1.concat("     ").concat(cuentaParte2);
		}
		log.info("cuentaFinal + :: " +  cuentaFinal);
		return cuentaFinal;
	}
	
	public static String crearEje(String eje) {
		String ejeFinal = "";
		if(eje.length() > 1){
			ejeFinal = eje.substring(0, 4).concat("     ").concat(eje.substring(3));
		}
		log.info("ejeFinal + :: " +  ejeFinal);
		return ejeFinal;
	}
	

}
