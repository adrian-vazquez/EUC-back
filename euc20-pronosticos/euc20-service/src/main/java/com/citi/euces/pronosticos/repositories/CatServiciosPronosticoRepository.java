package com.citi.euces.pronosticos.repositories;

import com.citi.euces.pronosticos.entities.CatServiciosPronostico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatServiciosPronosticoRepository extends JpaRepository<CatServiciosPronostico, Long> {

}
