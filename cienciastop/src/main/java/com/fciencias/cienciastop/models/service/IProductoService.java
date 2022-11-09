package com.fciencias.cienciastop.models.service;

import java.util.List;
import com.fciencias.cienciastop.models.entity.Producto;

public interface IProductoService {
	public List<Producto> findAll();
	
	public Producto findByCodigo(String codigo);
	
	public Producto save(Producto producto);
	
	public void delete(String codigo);
}
