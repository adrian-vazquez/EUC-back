/**
 * 
 */
package com.citi.euces.pronosticos.configuration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Componente de configuracion para las pruebas unitarias.
 * 
 * @author luis
 */
@Configuration
@ComponentScan(includeFilters = { @Filter(type = FilterType.ANNOTATION, value = Service.class) }, basePackages = {
		"com.citi.euces.pronosticos.services.*" })
public class SpringContext {

	@Bean("restTemplate")
	public RestTemplate restTemplate() {

		return new RestTemplate();
	}

	@Bean("servletRequest")
	public HttpServletRequest servletRequest() {

		return new MockHttpServletRequest();
	}

}
