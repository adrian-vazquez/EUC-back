/**
 * 
 */
package com.citi.euces.pronosticos.services.api;

import java.util.List;
import java.util.Optional;

import com.citi.euces.pronosticos.entities.User;

/**
 * @author lbermejo
 *
 */
public interface UserService {
	
	/**
	 * 
	 * @return the userList
	 */
	List<User> findAllUsers();
	
	/**
	 * 
	 * @param iduser
	 * @return
	 */
	Optional<User> findById(Integer iduser) ;
	
	/**
	 * 
	 * @return the userList
	 */
	List<User> getAllUserJdbc();
}
