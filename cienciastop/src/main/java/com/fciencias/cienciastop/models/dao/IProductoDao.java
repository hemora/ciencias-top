package com.fciencias.cienciastop.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.fciencias.cienciastop.models.entity.Producto;

public interface IProductoDao extends CrudRepository<Producto, String> {
    
	/**
	 * Busca productos por nombre en la base de datos.
	 * @param nombre el nombre que se buscara.
	 * @return una lista de productos que contienen la cadena ingresada en su nombre.
	 */
	@Query(
			value= "SELECT * FROM productos WHERE LOWER(nombre) LIKE %?1% OR nombre LIKE %?1% ", 
			nativeQuery = true)
	public List<Producto> buscarPorNombre(String nombre);

	/**
	 * Regresa la lista del top 5 de los productos que requieren menor
	 * cantidad de puma puntos.
	 * @return la lista del top 5 de los productos que requieren menor
	 * cantidad de puma puntos.
	 */
	@Query(
			value= "SELECT * FROM productos ORDER BY precio asc LIMIT 5", 
			nativeQuery = true)
	public List<Producto> topFiveBaratos();
}
