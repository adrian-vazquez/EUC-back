/**
 *
 */
package com.citi.euces.pronosticos.web;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PostConstruct;
import java.time.Duration;

/**
 * @author lbermejo
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan( basePackages = "com.citi.*" )
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
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.setConnectTimeout(Duration.ofMillis(3000)).setReadTimeout(Duration.ofMillis(3000)).build();

	}

	/*@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize((long)(1024 * 1024) * 5);//5 MB
		return multipartResolver;
	}*/

}
