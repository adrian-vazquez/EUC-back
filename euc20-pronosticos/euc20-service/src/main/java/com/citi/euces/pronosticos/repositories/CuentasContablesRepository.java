package com.citi.euces.pronosticos.repositories;

import com.citi.euces.pronosticos.entities.CuentasContables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentasContablesRepository  extends JpaRepository<CuentasContables, Long> {

}
