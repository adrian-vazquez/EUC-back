package com.citi.euces.pronosticos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.citi.euces.pronosticos.entities.QueryCtosCobu;

@Repository
public interface QueryCtosCobuRepository extends JpaRepository<QueryCtosCobu, Long>{

	
}
