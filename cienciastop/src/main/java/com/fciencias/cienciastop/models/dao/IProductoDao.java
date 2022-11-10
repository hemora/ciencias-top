package com.fciencias.cienciastop.models.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fciencias.cienciastop.models.entity.Producto;

public interface IProductoDao extends CrudRepository<Producto, String> {
    
	@Query(value= "SELECT * FROM productos WHERE codigo = :codigo", nativeQuery = true)
	Producto encontrarPorCodigo(@Param("codigo") String codigo);
	
	@Modifying
	@Transactional
	@Query(value= "DELETE FROM productos WHERE codigo = :codigo", nativeQuery = true)
	void borrarPorCodigo(@Param("codigo") String codigo);
}
