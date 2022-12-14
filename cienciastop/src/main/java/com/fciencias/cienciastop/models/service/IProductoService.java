package com.fciencias.cienciastop.models.service;

import java.util.List;
import com.fciencias.cienciastop.models.entity.Producto;

public interface IProductoService {
	
	/**
	 * Regresa una lista con todos los productos
	 * @param filtro valor booleano para saber si se aplica un filtro o no.
	 * <code>True</code> si queremos descartar los productos con stock_inicial < 1,
	 * <code>False</code> en otro caso.
	 * @return una lista con todos los productos
	 */
	public List<Producto> findAll(boolean filtro);
	
	/**
	 * Busca un producto por codigo en la base de datos.
	 * @param codigo el codigo que se buscara.
	 * @return el producto que tenga el mismo codigo ingresado.
	 */
	public Producto findByCodigo(String codigo);
	
	/**
	 * Busca productos por nombre en la base de datos.
	 * @param nombre el nombre que se buscara.
	 * @param filtro valor booleano para saber si se aplica un filtro o no.
	 * <code>True</code> si queremos descartar los productos con stock_inicial < 1,
	 * <code>False</code> en otro caso.
	 * @return una lista de productos que contienen la cadena ingresada en su nombre.
	 */
	public List<Producto> findByNombre(String nombre, boolean filtro);
	
	public Producto save(Producto producto);
	
	public void delete(String codigo);

	/**
	 * Regresa la lista del top 5 de los productos que requieren menor
	 * cantidad de puma puntos.
	 * @return la lista del top 5 de los productos que requieren menor
	 * cantidad de puma puntos.
	 */
	public List<Producto> topFiveBaratos();
}