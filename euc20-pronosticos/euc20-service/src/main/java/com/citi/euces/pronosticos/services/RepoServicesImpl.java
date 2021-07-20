package com.citi.euces.pronosticos.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.citi.euces.pronosticos.infra.dto.LayoutProteccionDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.infra.utils.FormatUtils;
import com.citi.euces.pronosticos.repositories.LayoutProteccionJDBCRepository;
import com.citi.euces.pronosticos.services.api.IRepoService;

@Service
@Transactional
public class RepoServicesImpl implements IRepoService{
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

	

}
