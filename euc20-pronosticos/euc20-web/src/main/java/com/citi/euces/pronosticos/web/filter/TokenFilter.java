/**
 * 
 */
package com.citi.euces.pronosticos.web.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 *
 */
//@WebFilter("/rest/*")
public class TokenFilter implements Filter {
	
	private static final Logger LOGGER = LogManager.getLogger(TokenFilter.class);

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		
		String paths[] = request.getRequestURI().split("/");
		
		
		String relativePath = "/";
		for( int idx = 3; idx < paths.length; idx ++) {
			relativePath += paths[idx] + "/";
		}
		
		String token = request.getHeader("Authorization");
		if( token == null ) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		Map<String,Object> tokenMap = null;
		
		try {
			HttpHeaders headers = new HttpHeaders();
			// set `content-type` header
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", token);

			// request body parameters
			// build the request
			HttpEntity<Map<String, Object>> entity = new HttpEntity<>( headers);
			LOGGER.info(request.getRequestURI());
			RestTemplate restTemplate = new RestTemplateBuilder().build();
			String hostIp = request.getLocalName();
			String hostPort = request.getLocalPort() + "";
			String hostProtocol = request.getScheme();
			String thisHost = hostProtocol + "://" + hostIp + ":" + hostPort;
			String authURL = "/euc-seguridad/rest/auth/revalidate";
			LOGGER.info("Call auth in " + thisHost + authURL);
			ResponseEntity<Map> responseTokenMap = restTemplate.exchange(
					thisHost  + authURL , HttpMethod.GET, entity, Map.class);
			tokenMap = (Map<String,Object>)responseTokenMap.getBody();
			
			LOGGER.info("TokenMap:" + tokenMap);
		} catch( HttpClientErrorException httpError ) {
			LOGGER.error("*** 1) " + request.getContextPath() );
			LOGGER.error("*** 2) " + relativePath);
			LOGGER.error(httpError);
			response.setStatus(httpError.getStatusCode().value());
			return;
		} catch( Exception ex ) {
			LOGGER.error("*** 1) " + request.getContextPath() );
			LOGGER.error("*** 2) " + relativePath);
			LOGGER.error(ex);
			return;
		}
		response.setHeader("Authorization",token);
		chain.doFilter(req,res);
		
		
	}
	
	@Override
	public void init(FilterConfig filterConfig) {
		
		LOGGER.info("*** TOKEN Filter applied");
	}

}
