package com.fciencias.cienciastop.models.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fciencias.cienciastop.models.entity.Monedero;

public interface IMonederoDao extends CrudRepository<Monedero, Long> {

    @Modifying
	@Transactional
	@Query(value ="UPDATE monederos SET status = '2' WHERE id = :id", nativeQuery = true)
	Integer deshabilitar(@Param("id") Long id);

	@Transactional
	@Query(value ="SELECT * FROM monederos WHERE owner_id = :ownerId AND periodo = :periodo", nativeQuery = true)
	// TODO: Evitar que se puedan agregar más de un monedero por periodo
	Monedero obtenerPorDueno(@Param("ownerId") Long ownerId, @Param("periodo") String periodo);
}
