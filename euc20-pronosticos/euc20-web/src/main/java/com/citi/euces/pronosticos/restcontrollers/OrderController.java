/**
 * 
 */
package com.citi.euces.pronosticos.restcontrollers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.citi.euces.pronosticos.entities.Order;
import com.citi.euces.pronosticos.exceptions.OrderNotFoundException;
import com.citi.euces.pronosticos.services.api.OrderService;

/**
 * @author lbermejo
 *
 */
@RestController  
@RequestMapping(path= OrderController.ORDERS_RESOURCE ,
	//consumes = MediaType.APPLICATION_JSON_VALUE, 
	produces = MediaType.APPLICATION_JSON_VALUE )
public class OrderController {
	
	private static final Logger LOGGER = LogManager.getLogger(GreetingController.class);
	static final String ORDERS_RESOURCE = "/orders";
	
	private final OrderService orderService;
	
	/**
	 * @param orderService
	 */
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping("")
    public List<Order> allOrders(HttpServletRequest request) {
		
		LOGGER.info("Finding all orders");
        String auth_header = request.getHeader("AUTH_HEADER");
        LOGGER.info("AUTH_HEADER: "+auth_header);
        
        return orderService.findAllOrders();
	}
	
	/**
	 * 
	 * @param code
	 * @return
	 */
	@GetMapping("/{code}")
    public Order orderByCode(@PathVariable final String code) {
		LOGGER.info("Finding product by code :"+code);
		
		//OrderTemplateResponse ... 
		
		return orderService.findByCode(code)
				.orElseThrow(() -> new OrderNotFoundException(
						"Product with code ["+code+"] doesn't exist"));
	}
	
	
}
