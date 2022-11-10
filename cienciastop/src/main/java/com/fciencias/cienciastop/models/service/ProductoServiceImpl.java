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

	@Override
	@Transactional(readOnly=true)
	public List<Producto> findAll() {
		return (List<Producto>) productoDao.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Producto findByCodigo(String codigo) {
		return productoDao.findById(codigo).orElse(null);
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
}
