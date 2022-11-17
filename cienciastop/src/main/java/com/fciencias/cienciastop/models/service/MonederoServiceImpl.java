package com.fciencias.cienciastop.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fciencias.cienciastop.models.dao.IMonederoDao;
import com.fciencias.cienciastop.models.entity.Monedero;

@Service
public class MonederoServiceImpl implements IMonederoService {

    @Autowired
    private IMonederoDao monederoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Monedero> findAll() {
        return (List<Monedero>)monederoDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Monedero findById(Long id) {
        return monederoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional()
    public Monedero save(Monedero monedero) {
        return monederoDao.save(monedero);
    }

    /* (non-Javadoc)
     * @see com.fciencias.cienciastop.models.service.IMonederoService#delete(java.lang.Long)
     */
    @Override
    @Transactional()
    public void delete(Long id) {
        monederoDao.deleteById(id);
    }

    @Override
    @Transactional
    public void deshabilitar(Long id) {
        monederoDao.deshabilitar(id);
    }

    @Override
    public Monedero obtenerPorDueno(Long ownerId, String periodo) {
        return monederoDao.obtenerPorDueno(ownerId, periodo);
    }

    
    
}
