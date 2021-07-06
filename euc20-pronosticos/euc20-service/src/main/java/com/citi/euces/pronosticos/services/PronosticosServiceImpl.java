package com.citi.euces.pronosticos.services;

import java.io.BufferedReader;
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
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.citi.euces.pronosticos.entities.CatalogoServiciosPronostico;
import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.dto.RechazosFileDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.repositories.CatalogoServiciosPronosticoRepository;
import com.citi.euces.pronosticos.repositories.PronosticosAltCheqJDBCRepository;
//import com.citi.euces.pronosticos.repositories.PronosticosAltCheqRepository;
import com.citi.euces.pronosticos.repositories.PronosticosTmpJDBCRepository;
import com.citi.euces.pronosticos.repositories.PronosticosTmpRepository;
import com.citi.euces.pronosticos.services.api.PronosticosService;

@Service
@Transactional
public class PronosticosServiceImpl implements PronosticosService {
	
	static final Logger log = LoggerFactory.getLogger(PronosticosServiceImpl.class);

	//@Autowired
	//private PronosticosAltCheqRepository pronosticosAltCheqRepository;
	//@Autowired
	//private PronosticosTmpRepository pronosticosTmpRepository; 
	@Autowired
	private CatalogoServiciosPronosticoRepository catalogoServiciosPronosticoRepository;
	@Autowired
	private PronosticosTmpJDBCRepository pronosticosTmpJDBCRepository;
	@Autowired
	private PronosticosAltCheqJDBCRepository pronosticosAltCheqJDBCRepository; 

	@Override
	public MensajeDTO limpiarPronosticos() throws GenericException 
	{	
		log.info("limpiarPronosticosIMPL:: ");
		MensajeDTO msg = new MensajeDTO(); 
		
		try {
			pronosticosTmpJDBCRepository.BorrarDLDatosPronosticosTmp();
		} catch (Exception e) {
			throw new GenericException(
					"Error al limpiar la tabla Pronostico :: " , HttpStatus.NOT_FOUND.toString());
		}
		
		try {
			pronosticosAltCheqJDBCRepository.BorrarTCDatosPronosticosAltCheq();
		} catch (Exception e) {
			throw new GenericException(
					"Error al limpiar la tabla Pronosticos Alt Cheq :: " , HttpStatus.NOT_FOUND.toString());
		}
		
		msg.setMensajeInfo("Aviso");
		msg.setMensajeConfirm("La tabla de pronósticos se encuentra sin registros.");
		return msg;
	}

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
		
		log.info("cargaRechazos ::  init");
        log.info("File :: " + file);
        Path testFile = Files.createTempFile(Paths.get("/Documents/EUC20/Prueba/Archivos/PruebasZIP"), "rechazosZip", ".zip");
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
                Path tempFile = Files.createTempFile(Paths.get("/Documents/EUC20/Prueba/Archivos/PruebasTXT"), "rechazosTXT", ".txt");
                tempFile.toFile().deleteOnExit();
                try (FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
                    IOUtils.copy(is, fos);
                    message = leerArchivo(tempFile, diasProteccion);
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
	
	public String leerArchivo(Path tempFile, Integer diasProteccion) throws IOException, GenericException, ParseException
	{
		Date fecha = new Date();
		String fecha2 = new SimpleDateFormat("dd-mm-yyyy").format(fecha);
		int max = 0;
		String mes = null, anio = null;
		String linea;
		String[] valores;
		FileReader f = new FileReader(tempFile.toFile());
        BufferedReader b = new BufferedReader(f);
        List<RechazosFileDTO> fileRechazos = new ArrayList<RechazosFileDTO>();
        List<CatalogoServiciosPronostico> serviciosPronostico = catalogoServiciosPronosticoRepository.ObtenerServicios();
        
        while ((linea= b.readLine()) != null) {
        	max++;
            log.info(linea);
            valores = linea.split("\n");
            RechazosFileDTO data = new RechazosFileDTO(); 
            data.setId(Integer.parseInt(valores[0]));
            data.setCliente(Integer.parseInt(valores[1]));
            data.setBlanco(valores[2]);
            data.setCuenta(Integer.parseInt(valores[3]));
            data.setImporte(Double.parseDouble(valores[4]));
            data.setIva(Integer.parseInt(valores[5]));
            data.setStatus(valores[6]);
            data.setMes(valores[7]);
            data.setAnio(Integer.parseInt(valores[8]));
            data.setServicio(valores[9]);
            data.setCsi(valores[10]);
            data.setCom(Double.parseDouble(valores[11]));
            data.setComSinIva(Double.parseDouble(valores[12]));
            data.setIvaa(Double.parseDouble(valores[13]));
            data.setTotal(Double.parseDouble(valores[14]));
            data.setTipoComision(Integer.parseInt(valores[15]));
            data.setLlave(fecha2.replace("-", "") + max);
            data.setEje(valores[16]);
            data.setCatalogada(valores[17]);
            data.setSecuencial(fecha2);
            data.setFecha(fecha);
            data.setConcepto(valores[18]);
            data.setLeyenda(valores[19]);
            data.setDias(Integer.parseInt(valores[20]));
            for(CatalogoServiciosPronostico s:serviciosPronostico) 
            {
            	if(s.getServicio() == valores[9]) 
            	{
            		data.setIdServicio(s.getIdServicio());
            		data.setIdOndemand(s.getIdOndemand());
            	}
            }
            mes = valores[7];
            anio = valores[8];
            data.setEvaluacionVirtual(Integer.parseInt(valores[21]));
            data.setOpenItem(valores[22]);
            fileRechazos.add(data);
        }
        b.close();
        
        pronosticosAltCheqJDBCRepository.BorrarTCDatosPronosticosAltCheq();
        log.info("RechazosFileDTO content init  ::  " + fileRechazos.size());
        
        try {
        	pronosticosTmpJDBCRepository.batchInsert(fileRechazos, 500);
        } catch (Exception e) {
            throw new GenericException( "Error al guardar en PronosticosTmp :: " , HttpStatus.NOT_FOUND.toString());
        }
        
        try {
        	pronosticosAltCheqJDBCRepository.batchInsert(fileRechazos, 500);
        } catch (Exception e) {
            throw new GenericException( "Error al guardar en Pronosticos Alt Cheq:: " , HttpStatus.NOT_FOUND.toString());
        }
        
		return "Esta cargando información correspondientes al mes" + mes + " y Año" + anio;
		
	}
	
}
