package com.fciencias.cienciastop.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fciencias.cienciastop.models.dao.IRentaDao;
import com.fciencias.cienciastop.models.entity.Renta;

@Service
public class RentaServiceImpl implements IRentaService {

	@Autowired
	private IRentaDao rentaDao;
	
	@Override
	public List<Renta> findAll() {
		return (List<Renta>) rentaDao.findAll();
		
	}

	@Override
	public Renta findByID(Long id) {
		return rentaDao.findById(id).orElse(null);
	}

	@Override
	public Renta save(Renta renta) {
		return rentaDao.save(renta);
	}

	@Override
	public void delete(Long id) {
		rentaDao.deleteById(id);

	}

}