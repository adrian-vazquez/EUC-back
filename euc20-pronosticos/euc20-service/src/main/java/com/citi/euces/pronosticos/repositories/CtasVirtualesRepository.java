package com.citi.euces.pronosticos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citi.euces.pronosticos.entities.CtasVirtuales;

@Repository
public interface CtasVirtualesRepository extends JpaRepository<CtasVirtuales, Long>{


}
