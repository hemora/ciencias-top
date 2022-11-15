package com.fciencias.cienciastop.models.controllers;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

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
import org.springframework.web.bind.annotation.RequestParam;
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
	
	/**
	 * Regresa el producto que tenga como Codigo el parametro recibido.
	 * Si existe un error en la base de datos o no existen coincidencias
	 * se manda un mensaje sobre el tipo de error. 
	 * @param codigo el codigo del producto que se buscara.
	 * @return el producto que tenga como Codigo el parametro recibido.
	 * Si existe un error en la base de datos o no existen coincidencias
	 * se manda un mensaje sobre el tipo de error.
	 */
	@GetMapping("/productos/{codigo}")
	public ResponseEntity<?> show(@PathVariable String codigo) {
		Producto producto = null;
		HttpStatus status;
		Map<String, Object> response = new HashMap<>();
		String mensaje;
		try {
			producto = productoService.findByCodigo(codigo);
		} catch (DataAccessException e) {
			// Error en la base de datos
			mensaje = "Error al realizar la consulta en la base de datos";
			response.put("mensaje", mensaje);
			mensaje = "";
			mensaje += e.getMessage() + ": ";
			mensaje += e.getMostSpecificCause().getMessage();
			response.put("error", mensaje);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			return new ResponseEntity<Map<String, Object>>(response, status);
		}
		// Codigo correcto
		if (producto != null) {
			status = HttpStatus.OK;
			return new ResponseEntity<Producto>(producto, status);
		}
		// Error con el ID
		mensaje = String.format("El producto con Codigo: %s no existe", codigo);
		response.put("mensaje", mensaje);
		status = HttpStatus.NOT_FOUND;
		return new ResponseEntity<Map<String, Object>>(response, status);
	}

	/**
	 * Regresa una lista de productos que tenga como subcadena la entrada recibida
	 * en el nombre, o tengan un Codigo igual a la entrada recibida.
	 * Si existe un error en la base de datos o no existen coincidencias
	 * se manda un mensaje sobre el tipo de error.
	 * @param entrada la entrada a la que se le buscaran coincidencias por nombre o
	 * por codigo.
	 * @return una lista de productos que tenga como subcadena la entrada recibida
	 * en el nombre, o tengan un Codigo igual a la entrada recibida.
	 * Si existe un error en la base de datos o no existen coincidencias
	 * se manda un mensaje sobre el tipo de error.
	 */
	@GetMapping("/busqueda")
	public ResponseEntity<?> busqueda(@RequestParam String entrada) {
		Producto porCodigo = null;
		List<Producto> porNombre;
		HttpStatus status;
		Map<String, Object> response = new HashMap<>();
		String mensaje;
		try {
			porCodigo = productoService.findByCodigo(entrada);
			porNombre = productoService.findByNombre(entrada);
		} catch (DataAccessException e) {
			// Error en la base de datos
			mensaje = "Error al realizar la consulta en la base de datos";
			response.put("mensaje", mensaje);
			mensaje = "";
			mensaje += e.getMessage() + ": ";
			mensaje += e.getMostSpecificCause().getMessage();
			response.put("error", mensaje);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			return new ResponseEntity<Map<String, Object>>(response, status);
		}
		if (porNombre == null) {
			porNombre = new ArrayList<Producto>();
		}
		if (porCodigo != null && !porNombre.contains(porCodigo)) {
			porNombre.add(porCodigo);
		}
		// Entrada correcto
		if (!porNombre.isEmpty()) {
			status = HttpStatus.OK;
			return new ResponseEntity<List<Producto>>(porNombre, status);
		}
		// Error con la entrada
		mensaje = String.format("No existen coincidencias con: %s", entrada);
		response.put("mensaje", mensaje);
		status = HttpStatus.NOT_FOUND;
		return new ResponseEntity<Map<String, Object>>(response, status);
	}
	
	/**
	 * Crea un nuevo producto en la base de datos tras validar sus datos.
	 * @param producto, el producto a ser agregado
	 * @param noCT identificacion del usuario que solicito la operacion.
	 */
	@PostMapping("/productos")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@RequestBody Producto producto, long noCT) {
		producto.setCurrentStock(producto.getStockInicial());
		producto.setnoCT(noCT);
		Producto productoN = null;
		Map<String, Object> response = new HashMap<>();
		try {
			productoN = productoService.save(producto);
		}catch(DataAccessException e) {
			//Error del servidor
			response.put("mensaje", "Error al agregar a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El producto ha sido creado con exito");
		response.put("producto", productoN);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
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
	
	/**
	 * elimina un producto existente en la base de datos validando permisos.
	 * @param codio, del producto a ser eliminado
	 * @param noCT identificacion del usuario que solicito la operacion.
	 */
	@DeleteMapping("/productos/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> delete(@PathVariable String codigo, long noCT) {
		Map<String, Object> response = new HashMap<>();
		Producto aeliminar = this.productoService.findByCodigo(codigo);
		long original = aeliminar.getnoCT();
		if(noCT == original) {
			//Eliminacion exitosa del producto.
			productoService.delete(codigo);
			response.put("mensaje", "El producto ha sido eliminado con exito");
			response.put("producto", aeliminar);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}
		//Usuario sin permisos sobre el producto.
		response.put("mensaje", "No se tiene los permisos necesarios para eliminar este producto.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.UNAUTHORIZED);
		
	}
}