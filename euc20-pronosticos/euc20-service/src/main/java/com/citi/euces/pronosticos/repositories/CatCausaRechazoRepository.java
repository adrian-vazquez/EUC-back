package com.citi.euces.pronosticos.repositories;

import com.citi.euces.pronosticos.entities.CatCausaRechazo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatCausaRechazoRepository extends JpaRepository<CatCausaRechazo, Long> {

}
