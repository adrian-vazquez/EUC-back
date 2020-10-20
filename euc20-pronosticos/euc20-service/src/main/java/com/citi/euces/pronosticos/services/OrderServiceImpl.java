/**
 * 
 */
package com.citi.euces.pronosticos.services;

import java.util.List;
import java.util.Optional;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.citi.euces.pronosticos.entities.Order;
import com.citi.euces.pronosticos.repositories.OrderRepository;
import com.citi.euces.pronosticos.services.api.OrderService;


/**
 * @author lbermejo
 *
 */
//@RequiredArgsConstructor
@Service
@Transactional
public class OrderServiceImpl implements OrderService {	

	
	private OrderRepository orderRepository;
	
	/**
	 * @param orderRepository
	 */
	public OrderServiceImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	//@Transactional
	//Insert / Update / Delete 
	
	/**
	 * 
	 */
	public List<Order> findAllOrders() {
		return orderRepository.findAll();
	}
	
	/**
	 * 
	 */
	public Optional<Order> findByCode(final String code) {
		return orderRepository.findByCode(code);
	}

}
