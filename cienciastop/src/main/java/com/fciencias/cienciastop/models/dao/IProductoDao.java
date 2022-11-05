package com.fciencias.cienciastop.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.fciencias.cienciastop.models.entity.Producto;

public interface IProductoDao extends CrudRepository<Producto, Long> {
    
}
