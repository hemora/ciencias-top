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

import com.fciencias.cienciastop.models.entity.Producto;
import com.fciencias.cienciastop.models.service.IProductoService;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ProductoRestController {
	
	@Autowired
	private IProductoService productoService;
	
	@GetMapping("/productos")
	public List<Producto> index() {
		return productoService.findAll();
	}
	
	@GetMapping("/productos/{codigo}")
	public ResponseEntity<?> show(@PathVariable String codigo) {
		Producto producto = null;
		Map<String,Object> response = new HashMap<>();
		
		try {
			producto = productoService.findByCodigo(codigo);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al consultar la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(producto == null) {
			response.put("mensaje", "El producto ID:".concat(codigo.concat("no existe en la base de datos.")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Producto>(producto,HttpStatus.OK);
		
	}
	
	@PostMapping("/productos")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@RequestBody Producto producto) {
		producto.setCurrentStock(producto.getStockInicial());
		producto.setnoCT(123456789);
		Producto productoN = null;
		Map<String, Object> response = new HashMap<>();
		if(validaCodigo(producto.getCodigo())) {
			try {
				productoN = productoService.save(producto);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al agregar a la base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El producto ha sido creado con exito");
			response.put("producto", productoN);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		}
		response.put("mensaje", "El codigo ingresado esta asociado a un producto ya existente.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/productos/{codigo}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto update(@RequestBody Producto producto, @PathVariable String codigo) {
		Producto currentProducto = this.productoService.findByCodigo(codigo);
		currentProducto.setNombre(producto.getNombre());
		currentProducto.setCodigo(producto.getCodigo());
		currentProducto.setStockInicial(producto.getStockInicial());
		currentProducto.setCurrentStock(producto.getCurrentStock());
		currentProducto.setPrecio(producto.getPrecio());
		currentProducto.setDescripcion(producto.getDescripcion());
		currentProducto.setImagen(producto.getImagen());
		currentProducto.setTipo(producto.getTipo());
		currentProducto.setCategoria(producto.getCategoria());
		currentProducto.setPeriodoRenta(producto.getPeriodoRenta());
		this.productoService.save(currentProducto);
		return currentProducto;
	}
	
	@DeleteMapping("/productos/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String codigo) {
		productoService.delete(codigo);
	}
	
	/**
	 * Valida si el codigo del nuevo producto pertenece a uno ya existente.
	 * @param codigo codigo del nuevo producto.
	 */
	public boolean validaCodigo(@PathVariable String codigo){
		Producto existente = productoService.findByCodigo(codigo);
		if(existente ==  null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Valida si el stock del producto nuevo es valido.
	 * @param is stock inicial.
	 * @param cs stock actual.
	 */
	public boolean validaStock(int is, int cs) {
		if((is < 0 || cs < 0) || cs > is) {
			return false;
		}
		return true;
	}
	
	/**
	 * Valida si el precio del producto esta entre los valores de 0 y 500.
	 * @param precio precio del nuevo producto
	 */
	public boolean validaPrecio(double precio) {
		if(precio < 0 || precio > 500) {
			return false;
		}
		return true;
	}
	
	/**
	 * Valida si el periodo de renta es valido.
	 * @param preiodorenta numero de dias de renta del nuevo producto.
	 */
	public boolean validaDias(int periodorenta) {
		if(periodorenta < 3 || periodorenta > 7) {
			return false;
		}
		return true;
	}
}