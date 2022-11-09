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
	public Producto show(@PathVariable String codigo) {
		return productoService.findByCodigo(codigo);
	}
	
	@PostMapping("/productos")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto create(@RequestBody Producto producto) {
		return productoService.save(producto);
	}
	
	@PutMapping("/productos/{codigo}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto update(@RequestBody Producto producto, @PathVariable String codigo) {
		Producto currentProducto = this.productoService.findByCodigo(codigo);
		currentProducto.setNombre(producto.getCodigo());
		currentProducto.setCodigo(producto.getNombre());
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
}