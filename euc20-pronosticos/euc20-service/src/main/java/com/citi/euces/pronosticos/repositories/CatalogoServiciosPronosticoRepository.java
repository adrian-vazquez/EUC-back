package com.citi.euces.pronosticos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.citi.euces.pronosticos.entities.CatalogoServiciosPronostico;

@Repository
public interface CatalogoServiciosPronosticoRepository extends JpaRepository<CatalogoServiciosPronostico, Integer> {

	@Query("SELECT idServicio, idOndemand, servicio FROM CatalogoServiciosPronostico")
	List<CatalogoServiciosPronostico> ObtenerServicios();
	
}
