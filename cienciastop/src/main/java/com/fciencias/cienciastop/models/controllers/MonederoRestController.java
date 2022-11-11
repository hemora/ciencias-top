package com.fciencias.cienciastop.models.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fciencias.cienciastop.models.entity.Monedero;
import com.fciencias.cienciastop.models.service.IMonederoService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class MonederoRestController {
    
    @Autowired
    private IMonederoService monederoService;
    
    @GetMapping("/monederos")
    public List<Monedero> index() {
        return monederoService.findAll();
    }

    @GetMapping("/monederos/{id}")
    public Monedero obtenerMonedero(@PathVariable Long id) {
        return monederoService.findById(id);
    }
    
    @PostMapping("/monederos/")
    @ResponseStatus(HttpStatus.CREATED)
    public Monedero crearMonedero(@RequestBody Monedero monedero) {
        return monederoService.save(monedero);
    }

    @PutMapping("/monederos/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Monedero sumarRestarPumaPuntos(@RequestBody Monedero monedero, @PathVariable Long id) {
        Monedero monederoActual = this.monederoService.findById(id);
        //TODO: Hace cosas para ver si se desea modificar en una cantidad válida
        monederoActual.setPumaPuntos(monederoActual.getPumaPuntos() + monedero.getPumaPuntos());
        return this.monederoService.save(monederoActual);
    }

    @DeleteMapping("/monederos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.monederoService.deshabilitar(id);
    }

}
