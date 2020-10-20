/**
 * 
 */
package com.citi.euces.pronosticos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author lbermejo
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException{

	
	 private static final long serialVersionUID = 1L;
	 
	    public RecordNotFoundException(String exception) {
	        super(exception);
	    }
	    
	    
}
