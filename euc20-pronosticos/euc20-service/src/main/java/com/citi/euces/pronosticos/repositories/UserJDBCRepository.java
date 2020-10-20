/**
 * 
 */
package com.citi.euces.pronosticos.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.citi.euces.pronosticos.entities.User;

/**
 * @author lbermejo
 *
 */
@Repository
public class UserJDBCRepository {
	/*
	private final JdbcTemplate jdbcTemplate;
	
	/**-
	 * @param jdbcTemplate
	 -* /
	public UserJDBCRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Transactional(readOnly=true)
	public List<User> findAll() {
		return jdbcTemplate.query("select * from users", new UserRowMapper());		
	}

	@Transactional(readOnly=true)
	public User findUserById(int id) {
		return jdbcTemplate.queryForObject("select * from users where id=?", new Object[]{id}, new UserRowMapper());
	}
	*/
	
}

//Manual mapping user class
class UserRowMapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		//user.setId(rs.getInt("id"));
		//user.setName(rs.getString("name"));
		//user.setEmail(rs.getString("email"));
		
		return user;
	}
	
}
