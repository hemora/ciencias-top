package com.fciencias.cienciastop.models.controllers;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
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
import com.fciencias.cienciastop.models.entity.Usuario;
import com.fciencias.cienciastop.models.service.IProductoService;
import com.fciencias.cienciastop.models.service.IUsuarioService;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ProductoRestController {
	
	@Autowired
	private IProductoService productoService;
	@Autowired
	private IUsuarioService usuarioService;
	
	/**
	 * Regresa una lista con todos los productos.
	 * @return una lista con todos los productos.
	 */
	@GetMapping("/productos")
	@PreAuthorize("hasRole('Administrador') || hasRole('Alumno') || hasRole('Proveedor')")
	public List<Producto> index() {
		return productoService.findAll(false);
	}

	/**
	 * Regresa una lista con todos los productos donde stock_inicial > 0
	 * @return una lista con todos los productos donde stock_inicial > 0
	 */
	@GetMapping("/productos-filtro")
	@PreAuthorize("hasRole('Administrador') || hasRole('Alumno') || hasRole('Proveedor')")
	public List<Producto> indexFiltro() {
		return productoService.findAll(true);
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
	@PreAuthorize("hasRole('Administrador') || hasRole('Alumno') || hasRole('Proveedor')")
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
	@PreAuthorize("hasRole('Administrador') || hasRole('Alumno') || hasRole('Proveedor')")
	public ResponseEntity<?> busqueda(@RequestParam String entrada, @RequestParam boolean filtro) {
		Producto porCodigo = null;
		List<Producto> porNombre;
		HttpStatus status;
		Map<String, Object> response = new HashMap<>();
		String mensaje;
		try {
			porCodigo = productoService.findByCodigo(entrada);
			porNombre = productoService.findByNombre(entrada, filtro);
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
			if (filtro == true && porCodigo.getStockInicial() > 0) {
				porNombre.add(porCodigo);
			}
			if (filtro == false) {
				porNombre.add(porCodigo);
			}
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
	@PostMapping("/productos/{noCT}")
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasRole('Administrador') || hasRole('Proveedor')")
	public ResponseEntity<?> create(@RequestBody Producto producto, @PathVariable long noCT) {
		producto.setCurrentStock(producto.getStockInicial());
		producto.setnoCT(noCT);
		Producto productoN = null;
		Producto existente = productoService.findByCodigo(producto.getCodigo());
		Map<String, Object> response = new HashMap<>();
		try {
			if(existente != null) {
				throw new DuplicateKeyException("Intento de insercion que peligra la integridad.");
			}
		}catch(DuplicateKeyException e) {
			response.put("mensaje", "Error, el código ya esta siendo usado.");
			String aux = "" + e.getMessage() + ": ";
			response.put("error", aux.concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			productoN = productoService.save(producto);
		}catch(DataAccessException e) {
			//Error del servidor
			response.put("mensaje", "Error al agregar a la base de datos");
			String aux = "" + e.getMessage() + ": ";
			response.put("error", aux.concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El producto ha sido creadooo con exito");
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
	 * Metodo que se encarga de editar un producto, ignorando el codigo y el noCT
	 * del producto proporcionado en el JSON, ya que la edición será sobre
	 * el producto relacionado con el codigo proporcionado en el path y el noCT
	 * ya que el noCT sera obtenido desde el login, se tiene que trear desde el Front.
	 * 
	 * @param Producto producto - representado en un Json
	 * @param codigo identificador del producto que queremos editar
	 *///317804511
	@PutMapping("/productos/{codigo}/editar/{noCT}/{rol}") // el noCT del que se encuentra logeado /{noCT} 
	// @PreAuthorize("hasRole('Administrador') || hasRole('Alumno')")
	@PreAuthorize("hasRole('Administrador') || hasRole('Proveedor')")
	public ResponseEntity<?> editarProducto (@Valid @RequestBody Producto producto,  BindingResult bindingResult, @PathVariable String codigo, @PathVariable long noCT,  @PathVariable String rol) {//
		// Verificamos que no tengamos errores en el JSON de acuerdo a nuestra Identidad
		if(bindingResult.hasErrors()) {
			Map<String,Object> response = new HashMap<>();
			response.put("mensaje", "Error en los datos incluidos en el Json");		
			response.put("error", bindingResult.getAllErrors().get(0).getDefaultMessage());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
		}
		Producto currentProd = null;
		Producto newProd = null;
		Map<String,Object> response = new HashMap<>();
		try {
			currentProd = productoService.findByCodigo(codigo);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta del producto relacionado con este código en la base de datos.");
			String mensaje = "";
			mensaje += e.getMessage() + ": ";
			response.put("error", mensaje + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// Verificamos que exista un producto con el codigo proporcionado
		if (currentProd == null) {
			response.put("mensaje", "No se puede editar este producto");
			response.put("error", "porque no existe un producto con el código:".concat(codigo.concat(" en nuestra base de datos."))	);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		long original = currentProd.getnoCT();
		String prov = "Proveedor";
		if(rol.equals(prov)) {
			if(noCT != original) {
				//Usuario sin permisos sobre el producto.
				response.put("mensaje", "No se tiene los permisos necesarios para eliminar este producto.");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.UNAUTHORIZED);
			}
		}
		try {
			// actualizacion de los atributos
			currentProd.setNombre(producto.getNombre());
			// No tomamos en cuenta el codigo que venga en el JSON ya que no se permite editar el codigo del producto
			currentProd.setCodigo(codigo);
			currentProd.setStockInicial(producto.getStockInicial());
			// No permitimos que el currentStock sea mayor que el Inicial 
			if(producto.getCurrentStock() > producto.getStockInicial()) {
				currentProd.setCurrentStock(producto.getStockInicial());
			} else {
				currentProd.setCurrentStock(producto.getCurrentStock());
			}
			currentProd.setPrecio(producto.getPrecio());
			currentProd.setDescripcion(producto.getDescripcion());
			currentProd.setImagen(producto.getImagen());
			currentProd.setTipo(producto.getTipo());
			currentProd.setCategoria(producto.getCategoria());
			currentProd.setPeriodoRenta(producto.getPeriodoRenta());
			currentProd.setnoCT(noCT);
			newProd=productoService.save(currentProd);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta del producto relacionado con este código en la base de datos.");
			String mensaje = "";
			mensaje += e.getMessage() + ": ";
			response.put("error", mensaje.concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// Si llegamos hasta acá es porque la edición fue valida
		response.put("mensaje", "El producto ha sido actualizado con éxito");
		response.put("producto", newProd);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/productos/{codigo}/prod")
	public ResponseEntity<?> getProducto(@PathVariable String codigo) {
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
			response.put("mensaje", "El usuario se ha editado correctamente.");
			response.put("producto",producto);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
		}
		// Error con el ID
		mensaje = String.format("El producto con Codigo: %s no existe", codigo);
		response.put("mensaje", mensaje);
		status = HttpStatus.NOT_FOUND;
		return new ResponseEntity<Map<String, Object>>(response, status);
	}
	
	/**
	 * elimina un producto existente en la base de datos validando permisos.
	 * @param codio, del producto a ser eliminado
	 * @param noCT identificacion del usuario que solicito la operacion.
	 */
	@DeleteMapping("/productos/{codigo}/{noCT}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasRole('Administrador') || hasRole('Proveedor')")
	public ResponseEntity<?> delete(@PathVariable String codigo, @PathVariable long noCT) {
		Map<String, Object> response = new HashMap<>();
		Producto aeliminar = this.productoService.findByCodigo(codigo);
		long original = aeliminar.getnoCT();
		Usuario user = this.usuarioService.buscarUsuarioPorNoCT(noCT);
		System.out.println(user.getRol());
		if((user.getRol().equals("Administrador")) || (noCT == original)) {
			// Eliminacion exitosa del producto.
			aeliminar.setNombre("☒" + aeliminar.getNombre());
			// aeliminar.setCurrentStock(0);
			aeliminar.setStockInicial(0);
			this.productoService.save(aeliminar);
			response.put("mensaje", "El producto ha sido eliminado con exito");
			response.put("producto", aeliminar);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}
		//Usuario sin permisos sobre el producto.
		response.put("mensaje", "No se tiene los permisos necesarios para eliminar este producto.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.UNAUTHORIZED);	
	}

	/**
	 * Regresa la lista del top 5 de los productos que requieren menor
	 * cantidad de puma puntos.
	 * Si existe un error en la base de datos se manda un mensaje sobre el tipo de error.
	 * @return la lista del top 5 de los productos que requieren menor
	 * cantidad de puma puntos.
	 * Si existe un error en la base de datos se manda un mensaje sobre el tipo de error.
	 */
	@GetMapping("/productos/top-5-baratos")
	@PreAuthorize("hasRole('Administrador')")
	public ResponseEntity<?> topFiveBaratos() {
		List<Producto> topFive;
		HttpStatus status;
		Map<String, Object> response = new HashMap<>();
		String mensaje;
		try {
			topFive = productoService.topFiveBaratos();
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
		if (topFive == null || topFive.isEmpty()) {
			topFive = new ArrayList<Producto>();
		}
		status = HttpStatus.OK;
		return new ResponseEntity<List<Producto>>(topFive, status);
	}
	
}