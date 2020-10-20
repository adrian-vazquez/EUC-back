/**
 *
 */
package com.citi.euces.pronosticos.web;

import javax.annotation.PostConstruct;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author lbermejo
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan( basePackages = "com.citi.euces.pronosticos" )
@EnableAutoConfiguration
@PropertySource("classpath:application.properties")
public class EucApplicationConfig{

	private static final Logger LOGGER = LogManager.getLogger(EucApplicationConfig.class);
	private final ApplicationContext applicationContext;

	@PostConstruct
	public void init() {
		LOGGER.debug("Iniciando App... " + applicationContext.getApplicationName());
	}

	/**
	 * @param applicationContext
	 */
	public EucApplicationConfig(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
	     return new MethodValidationPostProcessor();
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
		final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("Messages");
		return messageSource;
	}

}
