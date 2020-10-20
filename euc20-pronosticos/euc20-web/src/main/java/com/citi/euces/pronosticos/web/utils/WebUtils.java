/**
 * 
 */
package com.citi.euces.pronosticos.web.utils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.citi.euces.pronosticos.infra.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author lbermejo
 *
 */
public class WebUtils {
	
	private static final Logger LOGGER = LogManager.getLogger(WebUtils.class);
	
	static public String loadResourceFromWEBINF(ServletContext context, String fileName) {

		try {
			
			LOGGER.info(fileName);
			final int bufferSize = 1024;
			final char[] buffer = new char[bufferSize];
			final StringBuilder out = new StringBuilder();

			LOGGER.debug( fileName);
			if( context != null ) {
				InputStream inputStream = context.getResourceAsStream(fileName);
				 
				if (inputStream != null) {
					Reader in = new InputStreamReader(inputStream, "UTF-8");
					for (; ; ) {
					    int rsz = in.read(buffer, 0, buffer.length);
					    if (rsz < 0)
					        break;
					    out.append(buffer, 0, rsz);
					}
					return out.toString();					
				} else {
					throw new FileNotFoundException("NOT Found file '" + fileName + "' not found in the classpath");
				}
			} else {
				byte[] encoded = Files.readAllBytes(Paths.get(fileName));
				return new String(encoded);
			}


		} catch( Exception ex ) {
			LOGGER.error(ex);
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	static public Map<String,Object> jsonToMap( String json ) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, Map.class);
		
	}
	
	static public String mapToJson( Map<String,Object> map ) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

        // convert map to JSON string
        return mapper.writeValueAsString(map);

	}

	static public String simpleTemplate(String template, Map<String,Object> data ) {
		if( StringUtils.isNullOrEmpty(template) )
			return null;
		
		String result = template;
		for(Map.Entry<String, Object> entry : data.entrySet()) {
			String key = "{{" + entry.getKey() + "}}";
			String value = entry.getValue() == null ? "" : entry.getValue().toString();
			
			result = result.replace(key, value);
			
		}
		return result;
	}
	
}
