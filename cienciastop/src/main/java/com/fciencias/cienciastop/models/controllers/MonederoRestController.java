package com.fciencias.cienciastop.models.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    //@GetMapping("/monederos/{id}")
    //public Monedero obtenerMonedero(@PathVariable Long id) {
    //    return monederoService.findById(id);
    //}

    @GetMapping("/monederos/{ownerId}/{periodo}")
    public ResponseEntity<?> obtenerMonedero(@PathVariable Long ownerId, @PathVariable String periodo) {
        Monedero monederoActual = this.monederoService.obtenerPorDueno(ownerId, periodo);
        Map<String, Object> response = new HashMap<>();

        if (monederoActual == null) {
            response.put("mensaje", String.format("ERROR: Monedero para el usuario: %s para el periodo %s no existe en la base de datos", ownerId, periodo));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("mensaje","ÉXITO: Monedero encontrado con éxito");
        response.put("monedero", monederoActual);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/monederos/{ownerId}")
    public ResponseEntity<?> crearMonedero(@PathVariable Long ownerId) {
        String pattern = "yyyy-MM";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("es", "MX"));
        String periodoActual = simpleDateFormat.format(new Date());
        Map<String, Object> response = new HashMap<>();

        Monedero monederoActual = this.monederoService.obtenerPorDueno(ownerId, periodoActual);
        if (monederoActual != null && monederoActual.getStatus().equals("activo")) {
            response.put("mensaje",String.format("ÉXITO: El usuario %s ya cuenta con un monedero activo para el periodo %s.", ownerId,periodoActual));
            response.put("monedero", monederoActual);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        if (monederoActual != null && monederoActual.getStatus().equals("inactivo")) {
            response.put("mensaje",String.format("ÉXITO: El usuario %s ya cuenta con un monedero activo para el periodo %s. Reactivando monedero.", ownerId, periodoActual));
            monederoActual.setPumaPuntos(0.0);
            monederoActual.setStatus("activo");
            response.put("monedero", monederoActual);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        Monedero nuevoMonedero = new Monedero();
        nuevoMonedero.setOwnerId(ownerId);
        nuevoMonedero.setStatus("activo");
        nuevoMonedero.setPumaPuntos(100.0);
        nuevoMonedero.setPeriodo(periodoActual);

        try {
            monederoService.save(nuevoMonedero);
        } catch (DataAccessException e) {
            response.put("mensaje","ERROR: Error al actualizar el balance en la base de datos");
            String aux = "" + e.getMessage() + ": ";
            response.put("error", aux.concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje",String.format("ÉXITO: Usuario cuenta con un nuevo monedero para el periodo %s además de 100.00 puma puntos de regalo.", periodoActual));
        response.put("monedero", nuevoMonedero);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/monederos/{id}/{pp}")
	@PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<?> sumarRestarPumaPuntos(@PathVariable Long id, @PathVariable Double pp) {

        String pattern = "yyyy-MM";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("es", "MX"));
        String periodoActual = simpleDateFormat.format(new Date());

        Monedero monederoActual = this.monederoService.obtenerPorDueno(id, periodoActual);
        Map<String, Object> response = new HashMap<>();
        Monedero monederoActualizado = null;

        if (monederoActual == null) {
            response.put("mensaje", String.format("ERROR: Monedero con id: %s no existe en la base de datos", id));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Double balanceActualizado = monederoActual.getPumaPuntos() + pp;

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
            String aux = "" + e.getMessage() + ": ";
            response.put("error", aux.concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","ÉXITO: Saldo actualizado.");
        response.put("monedero", monederoActualizado);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/monederos/{ownerId}")
    public ResponseEntity<?> desactivarMonedero(@PathVariable Long ownerId) {

        String pattern = "yyyy-MM";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("es", "MX"));
        String periodoActual = simpleDateFormat.format(new Date());
        Map<String, Object> response = new HashMap<>();

        Monedero monederoActual = this.monederoService.obtenerPorDueno(ownerId, periodoActual);
        if (monederoActual != null && monederoActual.getStatus().equals("activo")) {
            try {
                this.monederoService.deshabilitar(ownerId);
            } catch (DataAccessException e) {
                response.put("mensaje","ÉXITO: Error al intentar desactivar el monedero");
                String aux = "" + e.getMessage() + ": ";
                response.put("error", aux.concat(e.getMostSpecificCause().getMessage()));
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            response.put("mensaje", "Monedero desactivado correctamente");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        if (monederoActual != null && monederoActual.getStatus().equals("inactivo")) {
            response.put("mensaje", "ÉXITO: Monedero desactivado correctamente");

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        response.put("mensaje","ERROR: El usuario no cuenta con un monedero para el periodo actual");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
