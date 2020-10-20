/**
 * 
 */
package com.citi.euces.pronosticos.infra.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class StringUtils {

	private static final Logger LOGGER = LogManager.getLogger(StringUtils.class);
	
	static public boolean isNullOrEmpty(String str) {
		if( str == null )
			return true;
		return str.trim().length() == 0;
	}
	
	static public boolean isOnlyDigits(String str) {
		if( str == null )
			return false;
		return inChars(str, "0123456789");
	}
	
	static public boolean isOnlyDigitsWith(String str, String with) {
		if( str == null )
			return false;
		return inChars(str, "0123456789" + with);
	}
	
	
	static public boolean isPaddedLength(String str, int min, int max) {
		if( str == null )
			return false;
		return str.trim().length() >= min && str.trim().length() <= max; 
	}
	
	static public boolean hasNoSpaces(String str) {
		if( str == null ) 
			return false;
		
		return str.indexOf(" ") == -1;
	}

	static public boolean isOnlyChars(String str) {
		if( str == null )
			return false;
		return inChars(str, "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ");
	}
	
	static public boolean isOnlyCharsWith(String str, String with) {
		if( str == null )
			return false;
		return inChars(str, "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ" + with);
	}
	
	static public boolean isOnlyCharWithSpaces(String str) {
		if( str == null )
			return false;
		return inChars(str, "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ ");
	}
	
	static public boolean isEnum( String str, String... words ) {
		if( isNullOrEmpty(str) )
			return false;
		
		for( String word : words ) {
			if( word.equalsIgnoreCase(str) )
				return true;
		}
		
		return false;
	}
	
	static public String fromEnum( String str, String strDefault, String... words ) {
		if( isNullOrEmpty(str) )
			return strDefault;
		
		for( int idx = 0; idx < words.length; idx += 2 ) {
			if( str.equalsIgnoreCase(words[idx])) {
				return words[idx+1];
			}
		}
		
		return strDefault;
	}
	
	static public boolean inChars( String str, String in) {
		int length = str.length();
		
		for( int idx = 0; idx < length; idx ++ ) {
			if( in.indexOf(str.substring(idx, idx+1)) == -1 ) {
				LOGGER.error("Error inChars:" + str + " [" + str.substring(idx, idx+1) + "] not in [" + in + "]"  );
				return false;
			}
		}
		
		return true;
	}
	
	static public String formatDate( String str, String from, String to ) {
		try {
			SimpleDateFormat sdfFrom = new SimpleDateFormat(from); 
			SimpleDateFormat sdfTo = new SimpleDateFormat(to);
			
			Date currDate = sdfFrom.parse(str);
			
			return sdfTo.format(currDate);
		} catch( Exception ex ) {
			return null;
		}
	}

	static public String formatDate( Date date, String to ) {
		try {
			SimpleDateFormat sdfTo = new SimpleDateFormat(to);
			
			return sdfTo.format(date);
		} catch( Exception ex ) {
			return null;
		}
	}
	
	static public String readFromStream(InputStream is ) {
		try {
			final int bufferSize = 1024;
			final char[] buffer = new char[bufferSize];
			final StringBuilder out = new StringBuilder();
			Reader in = new InputStreamReader(is, "UTF-8");
			for (; ; ) {
			    int rsz = in.read(buffer, 0, buffer.length);
			    if (rsz < 0)
			        break;
			    out.append(buffer, 0, rsz);
			}
			return out.toString();		
		} catch( Exception ex ) {
			return null;
		}
	}
	
	static public String getXMLNodeValue( String node, String xml) {
		int index = -1;
		
		if( ( index = xml.indexOf("<" + node + ">") ) == -1)
			return null;
		
		int indexEnd = xml.indexOf("</" + node + ">");
		
		return xml.substring(index + node.length() + 2, indexEnd);
	}
	
}
