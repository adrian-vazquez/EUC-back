/**
 * 
 */
package com.citi.euces.pronosticos.services.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.citi.euces.pronosticos.configuration.PersistanceContext;
import com.citi.euces.pronosticos.configuration.SpringContext;
import com.citi.euces.pronosticos.entities.User;
import com.citi.euces.pronosticos.services.api.UserService;


/**
 * @author luis
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringContext.class, PersistanceContext.class })
public class UserServiceTest{

	/**
	 * Referencia para el Servicio de prueba.
	 */
	@Autowired
	private UserService userService; 

	@Test
	public void testFindAll() {
		
		try {

			List<User> list = userService.findAllUsers();

			assertTrue("Prueba correcta.", !list.isEmpty());

		} catch (Exception e) {

			fail("Prueba incorrecta.");
		}
		
	}
	
}
