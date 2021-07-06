package com.citi.euces.pronosticos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.citi.euces.pronosticos.entities.CtasVirtualesGpos;

@Repository
public interface CtasVirtualesGposRepository extends JpaRepository<CtasVirtualesGpos, Long>{
	
	
}
