package com.citi.euces.pronosticos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.citi.euces.pronosticos.entities.CatalogoPronosticosTmp;

@Repository
public interface PronosticosTmpRepository extends JpaRepository<CatalogoPronosticosTmp, Integer>{
		
}
