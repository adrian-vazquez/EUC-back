package com.citi.euces.pronosticos.repositories;

import com.citi.euces.pronosticos.entities.RebNumProtect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RebNumProtectRepository extends JpaRepository<RebNumProtect, Long> {

}
