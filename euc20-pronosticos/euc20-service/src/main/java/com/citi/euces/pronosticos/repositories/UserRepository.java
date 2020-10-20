/**
 * 
 */
package com.citi.euces.pronosticos.repositories;

/*
import java.util.Optional;
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citi.euces.pronosticos.entities.User;


/**
 * @author lbermejo
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	/*@Query("select a, cf from Autotasas a, CertificadosFisicos cf where a.numCliente = cf.numCliente")
	List<ObjectDefinido[]> findAutotasasWithCerFisi();*/
	
}
