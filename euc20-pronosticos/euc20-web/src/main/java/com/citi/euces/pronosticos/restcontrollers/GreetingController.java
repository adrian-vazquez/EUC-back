/**
 * 
 */
package com.citi.euces.pronosticos.restcontrollers;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.citi.euces.pronosticos.models.GreetingResponse;

/**
 * @author lbermejo
 *
 */
@RestController
@RequestMapping(GreetingController.GREETING_RESOURCE)
public class GreetingController {
	
	private static final Logger LOGGER = LogManager.getLogger(GreetingController.class);
	
	static final String GREETING_RESOURCE = "/demo";
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("")
	public String home() {
		LOGGER.debug( "request to " + GREETING_RESOURCE );
		return "Hello World!";
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping(path = "/greeting", produces = "application/json")
	public GreetingResponse greeting(
			@RequestParam(value = "name", defaultValue = "World") String name) {
		
		LOGGER.debug( "request to greeting with name:" + name);
		
		return new GreetingResponse(
				counter.incrementAndGet(), String.format(template, name));
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping(path = "/extest", produces = "application/json")
	public GreetingResponse exceptionHandlerTest(
			@RequestParam(value = "name", defaultValue = "World") String name) throws Exception {
		
		LOGGER.debug( "request to extest. test exceptionHanlder");
		
		if (true) {
			
			Exception e = new Exception("Este es mi propio error.");
	        throw e;
	        
		}
		
		return new GreetingResponse(
				counter.incrementAndGet(), String.format(template, name));
	}
	
}
