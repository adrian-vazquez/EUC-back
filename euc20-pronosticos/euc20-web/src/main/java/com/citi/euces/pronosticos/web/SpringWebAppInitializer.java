/**
 * 
 */
package com.citi.euces.pronosticos.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.citi.euces.pronosticos.PersistenceContext;

/**
 * @author lbermejo
 *
 */
@Configuration
@PropertySource("classpath:application.properties")
public class SpringWebAppInitializer 
	extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Value("${app.servlet.mapping}")
	private String servletMapping;
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { 
			PersistenceContext.class ,
			EucApplicationConfig.class 
			};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/rest/*" };
	}
	
}