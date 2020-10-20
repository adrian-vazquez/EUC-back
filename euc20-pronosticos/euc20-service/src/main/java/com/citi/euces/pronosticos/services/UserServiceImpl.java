/**
 * 
 */
package com.citi.euces.pronosticos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.citi.euces.pronosticos.entities.User;
import com.citi.euces.pronosticos.repositories.UserRepository;
import com.citi.euces.pronosticos.services.api.UserService;


/**
 * @author lbermejo
 *
 */
//@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService{

	
	private final UserRepository userRepository;
	//private final UserJDBCRepository jdbcRepository;

	/**
	 * @param userRepository
	 * @param jdbcRepository
	 */
	public UserServiceImpl(UserRepository userRepository
			/*,UserJDBCRepository jdbcRepository*/) {
		//this.jdbcRepository = jdbcRepository;

		this.userRepository = userRepository;
	}
	
	//@Transactional
	//Insert / Update / Delete 

	@Override
	public List<User> findAllUsers() {
		
		final List<User> users = userRepository.findAll();
		return users;
		
		/*
        final Map<String, Integer> inventoryLevels = getInventoryLevelsWithFeignClient();
        final List<User> availableUsers = users.stream()
                .filter(p -> inventoryLevels.get(p.getCode()) != null && inventoryLevels.get(p.getCode()) > 0)
                .collect(Collectors.toList());
		return availableUsers;
		 */
		
		//return Collections.EMPTY_LIST;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<User> findById(final Integer iduser) {
		return userRepository.findById(iduser);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<User> getAllUserJdbc(){
		return userRepository.findAll();
	}
	
}
