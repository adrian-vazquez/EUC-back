/**
 * 
 */
package com.citi.euces.pronosticos.restcontrollers;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.citi.euces.pronosticos.models.CustomerDetailRequest;
import com.citi.euces.pronosticos.models.CustomerDetailResponse;

/**
 * @author lbermejo
 *
 */
@RestController
@RequestMapping(path = CustomerControllers.CUSTOMER_RESOURCE,
	consumes = MediaType.APPLICATION_JSON_VALUE, 
	produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerControllers {
	
	private static final Logger LOGGER = LogManager.getLogger(GreetingController.class);
	static final String CUSTOMER_RESOURCE = "/customer";
	
	@ResponseStatus(code = HttpStatus.OK)
	@PostMapping("")
	public CustomerDetailResponse getCustomerDetail(
			@Valid @RequestBody CustomerDetailRequest tdcCustomer) {
		
		LOGGER.debug(tdcCustomer);
		
		return new CustomerDetailResponse(); 
	}
	
}

