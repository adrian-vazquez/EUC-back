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

import com.citi.euces.pronosticos.entities.User;
import com.citi.euces.pronosticos.exceptions.UserNotFoundException;
import com.citi.euces.pronosticos.services.api.UserService;

/**
 * @author lbermejo
 *
 */
@RestController
@RequestMapping(path= UserController.USERS_RESOURCE,
	//consumes = MediaType.APPLICATION_JSON_VALUE, 
	produces = MediaType.APPLICATION_JSON_VALUE )
public class UserController {
	
	private static final Logger LOGGER = LogManager.getLogger(GreetingController.class);
	static final String USERS_RESOURCE = "/users";
	
	private UserService userService;
	
	/**
	 * @param userService
	 */
	public UserController(UserService userService) {
		this.userService = userService;
	}
	

	@GetMapping("")
    public List<User> allUsers(HttpServletRequest request) {
		LOGGER.info("Finding all users");
        String auth_header = request.getHeader("AUTH_HEADER");
        LOGGER.info("AUTH_HEADER: "+auth_header);
        return userService.findAllUsers();
	}
	
	@GetMapping("/{id}")
    public User UserById(@PathVariable final Integer id) {
		LOGGER.info("Finding user by id :"+id);
		return userService.findById(id)
		      .orElseThrow(() -> new UserNotFoundException("User with id ["+id+"] doesn't exist"));
	
	}
	
}
