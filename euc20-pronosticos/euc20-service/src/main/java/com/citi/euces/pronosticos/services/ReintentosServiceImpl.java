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
import java.util.ArrayList;
import java.util.Base64;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.citi.euces.pronosticos.infra.dto.CuentasAlternasDTO;
import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.repositories.CuentasAlternasJDBCRepository;
import com.citi.euces.pronosticos.repositories.ReintentosJDBCRepository;
import com.citi.euces.pronosticos.services.api.ReintentosService;

@Service
@Transactional
public class ReintentosServiceImpl implements ReintentosService {
	static final Logger log = LoggerFactory.getLogger(ReintentosServiceImpl.class);

	@Autowired
	private ReintentosJDBCRepository reintentosJDBCRepository;

	@Autowired
	private CuentasAlternasJDBCRepository cuentasAlternasJDBCRepository;

	@Override
	public MensajeDTO limpiarTabla() throws GenericException {
		// TODO Auto-generated method stub
		log.info("limpiarReintentos:: ");
		try {
			reintentosJDBCRepository.truncateTableReintentos();
		} catch (Exception e) {
			throw new GenericException("Error al limpiar tabla Reintentos :: ", HttpStatus.NOT_FOUND.toString());
		}

		MensajeDTO response = new MensajeDTO();
		response.setMensajeConfirm("Tabla vacía.");
		response.setMensajeInfo("La tabla Reintentos se encuentra sin registros.");
		return response;
	}

	@Override
	public MensajeDTO cuentasAltloadFile(String file) throws GenericException, IOException, ParseException {
		MensajeDTO msg = new MensajeDTO();
		String message = null;

		log.info("cuentasAltloadFile ::  init");
		log.info("File :: " + file);
		Path testFile = Files.createTempFile(Paths.get("/Documents/EUC20/Prueba/Archivos/PruebasZIP"), "cuantasAltZip",
				".zip");
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
				Path tempFile = Files.createTempFile(Paths.get("/Documents/EUC20/Prueba/Archivos/PruebasTXT"),
						"cuentasAltTXT", ".txt");
				tempFile.toFile().deleteOnExit();
				try (FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
					IOUtils.copy(is, fos);
					message = leerArchivo(tempFile);
				}
			}
			zipFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		msg.setMensajeInfo("Aviso");
		msg.setMensajeConfirm(message);
		return msg;

	}

	public String leerArchivo(Path tempFile) throws IOException, GenericException, ParseException {
		String linea, n = null;
		String[] ArrayAux;
		int ini = 1;
		FileReader f = new FileReader(tempFile.toFile());
		BufferedReader b = new BufferedReader(f);
		List<CuentasAlternasDTO> listaRespuesta = new ArrayList<CuentasAlternasDTO>();

		while ((linea = b.readLine()) != null) {
			log.info(linea);
			if (ini != 1) {
				if (!linea.equals("") && !linea.equals("\t\t\t\t\t\t\t\t")) {
					ArrayAux = linea.split("\t");
					CuentasAlternasDTO data = new CuentasAlternasDTO();
					data.setNum_Cliente(Long.parseLong(validaNull(ArrayAux[0])));
					data.setNum_Producto(Long.parseLong(validaNull(ArrayAux[1])));
					data.setCve_Instrumento(Long.parseLong(validaNull(ArrayAux[2])));
					data.setNum_Contrato(Long.parseLong(validaNull(ArrayAux[3])));
					data.setCve_Estatus(Long.parseLong(validaNull(ArrayAux[4])));
					data.setSdo_Actual(Long.parseLong(validaNull(ArrayAux[5])));
					data.setPrefmda(Long.parseLong(validaNull(ArrayAux[6])));
					data.setCuentamda(Long.parseLong(validaNull(ArrayAux[7])));
					listaRespuesta.add(data);
				}
			}
            ini = 2;
		}
		b.close();
		log.info("CuentasAlternasDTO content init  ::  " + listaRespuesta.size());

		try {
			cuentasAlternasJDBCRepository.TruncateTbCuentasAlternas();
		} catch (Exception e) {
			throw new GenericException("Error al limpiar la tabla cuentas alternas:: ",
					HttpStatus.NOT_FOUND.toString());
		}

		try {
			cuentasAlternasJDBCRepository.batchInsert(listaRespuesta, 500);
		} catch (Exception e) {
			throw new GenericException("Error al guardar en Respuestas Pronosticos :: ",
					HttpStatus.NOT_FOUND.toString());
		}

		return "Se importaron existosamente: " + n + "respuestas.";
	}
	
	 private String validaNull(String cad){
         if (cad == null || cad.equals(""))
         {
             cad = "0";
         }
         return cad;
     }

}
