package com.citi.euces.pronosticos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.citi.euces.pronosticos.entities.CatalogoPronosticosAltCheq;


@Repository
public interface PronosticosAltCheqRepository extends JpaRepository<CatalogoPronosticosAltCheq, Integer> {

	
}

