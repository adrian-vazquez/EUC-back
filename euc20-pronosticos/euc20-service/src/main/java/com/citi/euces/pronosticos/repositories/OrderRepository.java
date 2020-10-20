/**
 * 
 */
package com.citi.euces.pronosticos.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citi.euces.pronosticos.entities.Order;

/**
 * @author lbermejo
 *
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

	Optional<Order> findByCode(final String code);
	
	/*@Query("select a, cf from Autotasas a, CertificadosFisicos cf where a.numCliente = cf.numCliente")
	List<ObjectDefinido[]> findAutotasasWithCerFisi();*/
}
