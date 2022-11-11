package com.fciencias.cienciastop.models.service;

import java.util.List;

import com.fciencias.cienciastop.models.entity.Monedero;

public interface IMonederoService {
    
    public List<Monedero> findAll();

    public Monedero findById(Long id);

    public Monedero save(Monedero monedero);

    public void delete(Long id);

    public void deshabilitar(Long id);
}
