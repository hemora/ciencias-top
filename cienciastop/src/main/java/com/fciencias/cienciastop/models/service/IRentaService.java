package com.fciencias.cienciastop.models.service;

import java.util.List;

import com.fciencias.cienciastop.models.entity.Renta;

public interface IRentaService {
	
	public List<Renta> findAll();
	
	public List<Renta> verRentas();
	
	public Renta findByID(Long id);
	
	public Renta save(Renta producto);
	
	public void delete(Long id);

}