/**
 * 
 */
package com.citi.euces.pronosticos.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 */
@WebFilter("/rest/*")
public class CORSFilter implements Filter {
	
	private static final Logger LOGGER = LogManager.getLogger(CORSFilter.class);

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse)res;
		
		response.setHeader("access-control-allow-origin", "*");
		response.setHeader("access-control-allow-Credentials", "true");
		response.setHeader("access-control-allow-Methods", "POST, GET, PUT, DELETE, HEAD, OPTIONS");
		response.setHeader("access-control-allow-headers", "Content-type, Accept, X-Requested-With, remember-me");
		response.setHeader("access-control-allow-headers", "Access-Control-Request-Method, Access-Control-Request-Headers");
		response.setHeader("access-control-allow-headers", "*");
		response.setHeader("access-control-max-age", "3600");
		
		chain.doFilter(req,res);
		
	}
	
	@Override
	public void init(FilterConfig filterConfig) {
		LOGGER.info("*** CORS Filter applied");
	}

}
