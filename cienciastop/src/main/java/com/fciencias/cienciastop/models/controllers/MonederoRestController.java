package com.fciencias.cienciastop.models.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    
    @PostMapping("/monederos")
    @ResponseStatus(HttpStatus.CREATED)
    public Monedero crearMonedero(@RequestBody Monedero monedero) {
        return monederoService.save(monedero);
    }

    @PutMapping("/monederos/{id}")
    public ResponseEntity<?> sumarRestarPumaPuntos(@RequestBody Monedero monederoUpdated, @PathVariable Long id) {
        Monedero monederoActual = this.monederoService.findById(id);
        Map<String, Object> response = new HashMap<>();
        Monedero monederoActualizado = null;

        if (monederoActual == null) {
            response.put("mensaje", String.format("ERROR: Monedero con id: %s no existe en la base de datos", id));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Double balanceActualizado = monederoActual.getPumaPuntos() + monederoUpdated.getPumaPuntos();

        if (balanceActualizado > 500.0) {
            response.put("mensaje", String.format("ERROR: El balance final es mayor a 500 por %.2f unidades", (balanceActualizado - 500.0)));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (balanceActualizado < 0.0) {
            response.put("mensaje", String.format("ERROR: El balance final es menor a 0 por %.2f unidades", (0.0 - balanceActualizado)));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            monederoActual.setPumaPuntos(balanceActualizado);
            monederoActualizado = monederoService.save(monederoActual);
        } catch (DataAccessException e) {
            response.put("mensaje","ERROR: Error al actualizar el balance en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","ÉXITO: Saldo actualizado.");
        response.put("error", monederoActualizado);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/monederos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.monederoService.deshabilitar(id);
    }

}
