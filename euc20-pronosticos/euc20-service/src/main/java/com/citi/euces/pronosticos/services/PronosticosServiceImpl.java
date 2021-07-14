package com.citi.euces.pronosticos.services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.hibernate.exception.SQLGrammarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.citi.euces.pronosticos.infra.dto.BancaDTO;
import com.citi.euces.pronosticos.infra.dto.CatServiciosPronosticosDTO;
import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.dto.RebajaFileOndemandDTO;
import com.citi.euces.pronosticos.infra.dto.RechazosFileDTO;
import com.citi.euces.pronosticos.infra.dto.RespuestasFileDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.infra.utils.FormatUtils;
import com.citi.euces.pronosticos.repositories.BancaJDBCRepository;
import com.citi.euces.pronosticos.repositories.CatServiciosPronosticoJDBCRepository;
import com.citi.euces.pronosticos.repositories.PronosticosAltCheqJDBCRepository;
import com.citi.euces.pronosticos.repositories.PronosticosTmpJDBCRepository;
import com.citi.euces.pronosticos.repositories.RebajaPronosticoJDBCRepository;
import com.citi.euces.pronosticos.repositories.RespPronosticosTmpJDBCRepository;
import com.citi.euces.pronosticos.services.api.PronosticosService;

import jdk.internal.org.jline.utils.Log;

@Service
@Transactional
public class PronosticosServiceImpl implements PronosticosService {
	
	static final Logger log = LoggerFactory.getLogger(PronosticosServiceImpl.class);

	@Autowired
	private CatServiciosPronosticoJDBCRepository catServiciosPronosticoJDBCRepository;
	@Autowired
	private PronosticosTmpJDBCRepository pronosticosTmpJDBCRepository;
	@Autowired
	private PronosticosAltCheqJDBCRepository pronosticosAltCheqJDBCRepository;
	@Autowired
	private BancaJDBCRepository bancaJDBCRepository;
	@Autowired
	private RespPronosticosTmpJDBCRepository respPronosticosTmpJDBCRepository;
	@Autowired
	RebajaPronosticoJDBCRepository rebajaPronosticoJDBCRepository;
	
	/**********************************************************************LIMPIAR PRONOSTICOS******************************************************************/
	@Override
	public MensajeDTO limpiarPronosticos() throws GenericException, SQLGrammarException 
	{	
		log.info("limpiarPronosticosIMPL:: ");
		MensajeDTO msg = new MensajeDTO(); 
		try {
			pronosticosTmpJDBCRepository.BorrarDLDatosPronosticosTmp();
			pronosticosAltCheqJDBCRepository.BorrarDLDatosPronosticosAltCheq();
		} catch (EntityNotFoundException e) {
			throw new GenericException(
					"Error al limpiar las tablas Pronostico:: " , HttpStatus.BAD_REQUEST.toString());
		}catch(SQLGrammarException ex) {
			throw new GenericException("Error e", HttpStatus.BAD_REQUEST.toString());
		}
		msg.setMensajeInfo("Aviso");
		msg.setMensajeConfirm("La tabla de pronósticos se encuentra sin registros.");
		return msg;
	}

	/**************************************************************CARGA RECHAZOS*******************************************************************************/
	@Override
	public MensajeDTO cargarRechazos(String file, Integer diasProteccion, boolean extraCont, boolean cobEspecial) throws GenericException, IOException, ParseException  
	{
		MensajeDTO msg = new MensajeDTO();
		String message = null;
		
		if(diasProteccion == null) 
		{
			msg.setMensajeInfo("Alerta");
			msg.setMensajeConfirm("Días de protección incorrecto, favor de establecer los días de protección");
			return msg;
		}
		
		if(extraCont == true && cobEspecial == true) 
		{
			msg.setMensajeInfo("Alerta");
			msg.setMensajeConfirm("Solo puedes seleccionar un tipo");
			return msg;
		}
		
		if(extraCont == false && cobEspecial == false) 
		{
			msg.setMensajeInfo("Alerta");
			msg.setMensajeConfirm("Seleccione un tipo");
			return msg;
		}
		
        Path testFile = Files.createTempFile("rechazosZip", ".zip");
        testFile.toFile().deleteOnExit();
        byte[] decoder = Base64.getDecoder().decode(file);
        Files.write(testFile, decoder);
        ZipFile zipFile = new ZipFile(testFile.toFile());
        Enumeration<?> enu = zipFile.entries();
        while (enu.hasMoreElements()) {
            ZipEntry zipEntry = (ZipEntry) enu.nextElement();
            String name = zipEntry.getName();
            if (name.endsWith("/") || name.startsWith("__MACOSX")) {
                continue;
            }
            InputStream is = zipFile.getInputStream(zipEntry);
            Path tempFile = Files.createTempFile("rechazosTXT", ".txt");
            tempFile.toFile().deleteOnExit();
            try (FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
                IOUtils.copy(is, fos);
                //Lectura TXT
                message = leerArchivoRechazos(tempFile, diasProteccion);
            }
        }
        zipFile.close();
    
        
        if(extraCont == true) {
        	try {
            	pronosticosTmpJDBCRepository.updateExtraCont();
            } catch (Exception e) {
                throw new GenericException( "Error al actualizar Extra contable:: " , HttpStatus.NOT_FOUND.toString());
            }	
        }
        if(cobEspecial == true) {
        	try {
            	pronosticosTmpJDBCRepository.updateCobrosEsp();
            } catch (Exception e) {
                throw new GenericException( "Error al actualizar Cobros Especiales:: " , HttpStatus.NOT_FOUND.toString());
            }	
        }
        
        msg.setMensajeInfo("Aviso");
        msg.setMensajeConfirm(message);
		return msg;
	}
	
	public String leerArchivoRechazos(Path tempFile, Integer diasProteccion) throws IOException, GenericException, ParseException
	{
		Date fecha = new Date();
		String fecha2 = new SimpleDateFormat("dd-mm-yyyy").format(fecha);
		int max = 1;
		String mes = null, anio = null;
		String linea;
		String[] valores;
		FileReader f = new FileReader(tempFile.toFile());
        BufferedReader b = new BufferedReader(f);
        List<RechazosFileDTO> fileRechazos = new ArrayList<RechazosFileDTO>();
        List<CatServiciosPronosticosDTO> serviciosPronostico = catServiciosPronosticoJDBCRepository.findAll();
        
        while ((linea= b.readLine()) != null) {
        	max++;
            valores = linea.split("\t");
            Long cuenta = Long.parseLong(valores[3].replace(" ", ""));
            String concepto;
            mes = valores[7];
            anio = valores[8];
            String valorDefault = "00";
            
            if(valores.length > 20) {
        		concepto = valores[21];
            }else {
            	concepto = "";
            }
            
            RechazosFileDTO data = new RechazosFileDTO(); 
            data.setId(Integer.parseInt(valores[0]));
            data.setCliente(Integer.parseInt(valores[1]));
            data.setBlanco(valores[2].equals("") ? "" : valores[2]);
            data.setCuenta(cuenta);
            data.setImporte(Double.parseDouble(validaComas(valores[4])));
            data.setIva(valores[5].equals("") ? 16: Integer.parseInt(valores[5]));
            data.setStatus(valores[6]);
            data.setMes(valores[7]);
            data.setAnio(Integer.parseInt(valores[8]));
            data.setServicio(valores[9]);
            data.setCsi(valores[10]);
            data.setCom(valores[11]);
            data.setComSinIva(Double.parseDouble(validaComas(valores[12])));
            data.setIvaa(valores[13].equals("") ? 16 : Double.parseDouble(validaComas(valores[13])));
            data.setTotal(Double.parseDouble(validaComas(valores[14])));
            data.setTipoComision(valores[15].equals("") ? 0 : Integer.parseInt(valores[15]));
            data.setLlave(fecha2.replace("-", "") + "-"+ valores[0]);
            data.setEje(valores[17]);
            data.setCatalogada(valores[9].equals("Pago Interbancario Personas Fisicas") || valores[9].equals("TASA CERO") ? "Cobro Especial" : "REINTENTOS");
            data.setSecuencial(fecha2);
            data.setFecha(fecha);
            data.setConcepto(concepto);
            data.setLeyenda(valores[9].equals("Pago Interbancario Personas Fisicas") || valores[9].equals("TASA CERO") ? "Cobro Especial" : "Extra Contable");
            data.setOpenItem(valores[23]);
            data.setDias(diasProteccion);
            data.setIdServicio(Long.parseLong(valorDefault));
    		data.setIdOndemand(Long.parseLong(valorDefault));
            for(CatServiciosPronosticosDTO s:serviciosPronostico) 
            {
            	if(s.getServicio() == valores[9]) 
            	{
            		data.setIdServicio((s.getIdServicio()));
            		data.setIdOndemand(s.getIdOndemand());
            	}
            }
            fileRechazos.add(data);
        }
        b.close();
        try {
        	pronosticosAltCheqJDBCRepository.BorrarDLDatosPronosticosAltCheq();
        	pronosticosTmpJDBCRepository.batchInsert(fileRechazos, 500);
            pronosticosAltCheqJDBCRepository.batchInsert(fileRechazos, 500);
        } catch (Exception e) {
            throw new GenericException( "Ocurrió un error durante el proceso de guardado :: " , HttpStatus.NOT_FOUND.toString());
        }
        
		return "Esta cargando información correspondientes al mes " + mes + " y Año " + anio;
	}
	
	public String validaComas(String valor) {
		valor = valor.replace(",", "");
		valor = valor.replace(".", "");
		return valor;
	}

	/************************************************************CARGA RESPUESTA PRONOSTICOS********************************************************************/
	@Override
	public MensajeDTO cargarRespuestas(String file) throws GenericException, IOException, ParseException 
	{
		MensajeDTO msg = new MensajeDTO();
		String message = null;
		
		log.info("cargaRespuestas ::  init");
        log.info("File :: " + file);
        Path testFile = Files.createTempFile(Paths.get("/Documents/EUC20/Prueba/Archivos/PruebasZIP"), "respuestasZip", ".zip");
        testFile.toFile().deleteOnExit();
        byte[] decoder = Base64.getDecoder().decode(file);
        Files.write(testFile, decoder);
        System.out.println(testFile.toFile().getAbsoluteFile());
        System.out.println("ZIP File Saved");
        
        ZipFile zipFile = new ZipFile(testFile.toFile());
        Enumeration<?> enu = zipFile.entries();
        
        try {
        	while (enu.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) enu.nextElement();

                String name = zipEntry.getName();
                long size = zipEntry.getSize();
                long compressedSize = zipEntry.getCompressedSize();

                System.out.printf("name: %-20s | size: %6d | compressed size: %6d\n", name, size, compressedSize);
                InputStream is = zipFile.getInputStream(zipEntry);
                Path tempFile = Files.createTempFile(Paths.get("/Documents/EUC20/Prueba/Archivos/PruebasTXT"), "respuestasTXT", ".txt");
                tempFile.toFile().deleteOnExit();
                try (FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
                    IOUtils.copy(is, fos);
                    message = leerArchivoRespuestas(tempFile);
                }
            }
            zipFile.close();
        } catch (IOException  e) {
            e.printStackTrace();
        }

        msg.setMensajeInfo("Aviso");
        msg.setMensajeConfirm(message);
        return msg;
        
	}

	public String leerArchivoRespuestas(Path tempFile) throws IOException, GenericException, ParseException 
	{
		String linea, n = null;
		FileReader f = new FileReader(tempFile.toFile());
        BufferedReader b = new BufferedReader(f);
        List<RespuestasFileDTO> listaRespuesta = new ArrayList<RespuestasFileDTO>();
        
        while ((linea= b.readLine()) != null) {
        	log.info(linea);
        	
        	RespuestasFileDTO data = new RespuestasFileDTO(); 
        	data.setNoCliente(Integer.parseInt(linea.substring(39, 51)));
        	data.setCtaCliente(obtenerCuenta(linea.substring(75, 95)));
        	data.setContrato(linea.substring(421, 433));
        	data.setImpOperacion1(Double.parseDouble(linea.substring(13, 28)));
        	data.setImpOperacion2(Double.parseDouble(linea.substring(476, 491)));
        	data.setCodOperacion(linea.substring(9, 11));
        	data.setDescRechazo(linea.substring(381, 421));
        	data.setLeyendaEmisor(linea.substring(237, 247));
        	data.setFecOperacion(FormatUtils.stringToDate(linea.substring(28, 36)));
        	data.setNumProteccion(linea.substring(288, 300));
        	data.setSecuencial(Integer.parseInt(linea.substring(2, 9)));
        	data.setFecReal(FormatUtils.stringToDate(linea.substring(9, 11).equals("05") ? linea.substring(420, 440) : ""));
        	data.setFranquicia(obtenerBanca(Integer.parseInt(linea.substring(39, 51))));
        	data.setFecVencimiento(FormatUtils.stringToDate(linea.substring(62, 70)));
        	data.setSecArc(Integer.parseInt(linea.substring(319, 321)));
        	data.setSecInt(Integer.parseInt(linea.substring(2, 9)));
        	data.setNomFranquicia("");
        	listaRespuesta.add(data);
        }
        b.close();
        log.info("RechazosFileDTO content init  ::  " + listaRespuesta.size());
        
        try {
        	respPronosticosTmpJDBCRepository.TCBorrarRespPronosticos();
        } catch (Exception e) {
            throw new GenericException( "Error al limpiar la tabla respuestas pronosticos:: " , HttpStatus.NOT_FOUND.toString());
        }
        
        try {
        	respPronosticosTmpJDBCRepository.batchInsert(listaRespuesta, 500);
        } catch (Exception e) {
            throw new GenericException( "Error al guardar en Respuestas Pronosticos :: " , HttpStatus.NOT_FOUND.toString());
        }
        
        try {
        	n = respPronosticosTmpJDBCRepository.updatePronosticosRespuesta();
        } catch (Exception e) {
            throw new GenericException( "Error al actualizar las respuestas pronosticos :: " , HttpStatus.NOT_FOUND.toString());
        }
        
        try {
        	respPronosticosTmpJDBCRepository.updateCobrosResp();
        } catch (Exception e) {
            throw new GenericException( "Error al actualizar cobros respuestas :: " , HttpStatus.NOT_FOUND.toString());
        }
        
		return "Se importaron existosamente: " + n + "respuestas.";
	}
	
	public String obtenerCuenta(String cuenta) {
		String cta = cuenta.substring(9, 11);
		String cta1 = cta.substring(0, 4);
		String cta2 = cta.substring(4, 7);
		return cta1 + "-" + cta2;
	}
	
	public String obtenerBanca(Integer numCliente) 
	{
		int banca = 0;
		List<BancaDTO> listaBanca = bancaJDBCRepository.selectAll();
		for(BancaDTO val: listaBanca) {
			if(numCliente == val.getNoCliente()) {
				banca = val.getBanca();
			}
		}
		return banca > 1 ? Integer.toString(banca) : "1";
	}
	/*************************************************************CARGA ARCHIVO REBAJA***************************************************************************/

	@Override
	public MensajeDTO cargaArchivoRebaja(String file) throws GenericException, IOException, ParseException {
		MensajeDTO msg = new MensajeDTO();
		String message = null;
		
		log.info("cargaArhivoRebaja ::  init");
        log.info("File :: " + file);
        Path testFile = Files.createTempFile(Paths.get("/Documents/EUC20/Prueba/Archivos/PruebasZIP"), "rebajaZip", ".zip");
        testFile.toFile().deleteOnExit();
        byte[] decoder = Base64.getDecoder().decode(file);
        Files.write(testFile, decoder);
        System.out.println(testFile.toFile().getAbsoluteFile());
        System.out.println("ZIP File Saved");
        
        ZipFile zipFile = new ZipFile(testFile.toFile());
        Enumeration<?> enu = zipFile.entries();
        
        try {
        	while (enu.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) enu.nextElement();

                String name = zipEntry.getName();
                long size = zipEntry.getSize();
                long compressedSize = zipEntry.getCompressedSize();

                System.out.printf("name: %-20s | size: %6d | compressed size: %6d\n", name, size, compressedSize);
                InputStream is = zipFile.getInputStream(zipEntry);
                Path tempFile = Files.createTempFile(Paths.get("/Documents/EUC20/Prueba/Archivos/PruebasTXT"), "rebajaTXT", ".txt");
                tempFile.toFile().deleteOnExit();
                try (FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
                    IOUtils.copy(is, fos);
                    message = leerArchivoRespuestas(tempFile);
                }
            }
            zipFile.close();
        } catch (IOException  e) {
            e.printStackTrace();
        }

        msg.setMensajeInfo("Aviso");
        msg.setMensajeConfirm(message);
        return msg;
	}

	public String leerArchivoRebaja(Path tempFile) throws IOException, GenericException, ParseException {
		String linea;
		FileReader f = new FileReader(tempFile.toFile());
        BufferedReader b = new BufferedReader(f);
        List<RebajaFileOndemandDTO> listaRebaja = new ArrayList<RebajaFileOndemandDTO>();
        Double suma = null;
        
        while ((linea= b.readLine()) != null) {
        	log.info(linea);
        	RebajaFileOndemandDTO data = new RebajaFileOndemandDTO();
        	if (linea.contains("120983/") || linea.contains("120984/")) {
        		if(linea.substring(132, 3).equals("CGO")) {
        			data.setNumProteccion(Long.parseLong(linea.substring(14, 26)));
        			data.setImporte(Double.parseDouble(linea.substring(95, 108)));
        			listaRebaja.add(data);
        		}
        	}
		}
        b.close();
        log.info("RebajasFileDTO content init  ::  " + listaRebaja.size());
        listaRebaja = listaRebaja.stream().distinct().collect(Collectors.toList());
        Map<Long, List<RebajaFileOndemandDTO>> listaRebajaGroup = listaRebaja.stream().collect(Collectors.groupingBy(RebajaFileOndemandDTO -> RebajaFileOndemandDTO.getNumProteccion()));
        
        for(Map.Entry<Long, List<RebajaFileOndemandDTO>> valores: listaRebajaGroup.entrySet()) {
        	for(RebajaFileOndemandDTO value: valores.getValue()) {
        		suma += value.getImporte();
        	}
        }
        
        try {
        	rebajaPronosticoJDBCRepository.truncateTable();
        	
        } catch (Exception e) {
            throw new GenericException( "Error al limpiar rebaja pronósticos:: " , HttpStatus.NOT_FOUND.toString());
        }
        
        
        
        return "";
	}
	
}
