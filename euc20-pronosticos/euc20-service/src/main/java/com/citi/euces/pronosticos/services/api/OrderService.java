/**
 * 
 */
package com.citi.euces.pronosticos.services.api;

import java.util.List;
import java.util.Optional;

import com.citi.euces.pronosticos.entities.Order;

/**
 * @author lbermejo
 *
 */
public interface OrderService {
	
	/**
	 * 
	 * @return the orderList 
	 */
	List<Order> findAllOrders();
	
	
	/**
	 * @param orderRepository the order code to find
	 * 
	 * @return the optional order 
	 */
	Optional<Order> findByCode(String code);
}
