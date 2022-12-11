package com.fciencias.cienciastop.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fciencias.cienciastop.models.dao.IProductoDao;
import com.fciencias.cienciastop.models.entity.Producto;

@Service
public class ProductoServiceImpl implements IProductoService {
	
	@Autowired
	private IProductoDao productoDao;
	
	/**
	 * Regresa una lista con todos los productos.
	 * @return una lista con todos los productos.
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Producto> findAll() {
		return (List<Producto>) productoDao.findAll();
	}
	
	/**
	 * Busca un producto por codigo en la base de datos.
	 * @param codigo el codigo que se buscara.
	 * @return el producto que tenga el mismo codigo ingresado.
	 */
	@Override
	@Transactional(readOnly=true)
	public Producto findByCodigo(String codigo) {
		return productoDao.findById(codigo).orElse(null);
	}
	
	/**
	 * Busca productos por nombre en la base de datos.
	 * @param nombre el nombre que se buscara.
	 * @return una lista de productos que contienen la cadena ingresada en su nombre.
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Producto> findByNombre(String nombre) {
		return productoDao.buscarPorNombre(nombre);
	}
	
	@Override
	@Transactional()
	public Producto save(Producto producto) {
		return productoDao.save(producto);
	}

	@Override
	@Transactional()
	public void delete(String codigo) {
		productoDao.deleteById(codigo);		
	}

	/**
	 * Regresa la lista del top 5 de los productos que requieren menor
	 * cantidad de puma puntos.
	 * @return la lista del top 5 de los productos que requieren menor
	 * cantidad de puma puntos.
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Producto> topFiveBaratos() {
		return productoDao.topFiveBaratos();
	}
}