package com.fciencias.cienciastop.models.controllers;

import java.util.Calendar;
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
import org.springframework.web.bind.annotation.RestController;

import com.fciencias.cienciastop.models.entity.Producto;
import com.fciencias.cienciastop.models.entity.Renta;
import com.fciencias.cienciastop.models.entity.Usuario;
import com.fciencias.cienciastop.models.service.IProductoService;
import com.fciencias.cienciastop.models.service.IRentaService;
import com.fciencias.cienciastop.models.service.IUsuarioService;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class RentaRestController {
	@Autowired
	private IRentaService rentaService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IProductoService productoService;
	
	/*@GetMapping("/rentas")
	public List<Renta> index() {
		return rentaService.findAll();
	}*/

	@GetMapping("aqui no estoy seguro cual seria la direccion")
	public ResponseEntity<?> rentasDeUsr(@RequestBody Usuario usuario) {
		List<Renta> historialDeUsr = null;
		Map<String,Object> response = new HashMap<String, Object>();
		try {
			historialDeUsr  = this.rentaService.rentasVencidasUsr(usuario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la conexión con la base de datos.");
			String cadenaError = "";
			cadenaError += e.getMessage() + ": ";
			cadenaError += e.getMostSpecificCause().getMessage();
			response.put("error", cadenaError);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (historialDeUsr  == null || historialDeUsr .isEmpty()) {
			response.put("mensaje", "No se encontraron rentas pasadas del usuario");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		response.put("rentasVencidas",historialDeUsr);
		List<Renta> rentasDeUsr = null;
		try {
			rentasDeUsr  = this.rentaService.rentasActualesUsr(usuario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la conexión con la base de datos.");
			String cadenaError = "";
			cadenaError += e.getMessage() + ": ";
			cadenaError += e.getMostSpecificCause().getMessage();
			response.put("error", cadenaError);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (rentasDeUsr  == null || rentasDeUsr .isEmpty()) {
			response.put("mensaje", "No se encontraron rentas actuales del usuario.");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		response.put("rentasActuales", rentasDeUsr);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/rentas")
	public ResponseEntity<?> verRentas() {
		List<Renta> rentasPorDevolver = null;
		Map<String,Object> response = new HashMap<String, Object>();
		try {
			rentasPorDevolver  = this.rentaService.verRentas();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la conexión con la base de datos.");
			String cadenaError = "";
			cadenaError += e.getMessage() + ": ";
			cadenaError += e.getMostSpecificCause().getMessage();
			response.put("error", cadenaError);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (rentasPorDevolver  == null || rentasPorDevolver .isEmpty()) {
			response.put("mensaje", "No se encontraron rentas sin devolver");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Renta>>(rentasPorDevolver ,HttpStatus.OK); 
	}
	
	/**
	 * Regresa la renta que tenga como ID el parametro recibido.
	 * Si existe un error en la base de datos o no existen coincidencias
	 * se manda un mensaje sobre el tipo de error. 
	 * @param id el id de la renta que se buscara.
	 * @return la renta que tenga como ID el parametro recibido.
	 */
	@GetMapping("/rentas/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Renta renta = null;
		Map<String, Object> response = new HashMap<>();
		String mensaje;
		try {
			renta = rentaService.findByID(id);
		} catch (DataAccessException e) {
			// Error en la base de datos
			mensaje = "Error al realizar la consulta en la base de datos";
			response.put("mensaje", mensaje);
			mensaje = "";
			mensaje += e.getMessage() + ": ";
			mensaje += e.getMostSpecificCause().getMessage();
			response.put("error", mensaje);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// Error con el ID ingresado
		if (renta == null) {
			mensaje = String.format("La renta con el ID: %s no existe", id);
			response.put("mensaje", mensaje);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Renta>(renta, HttpStatus.OK);
	}
	
	/**
	 * Crea una nueva renta con los datos del producto y usuario dados
	 * @param codigo, codigo del producto que se desea rentar
	 * @param noCT identificacion del usuario que solicito la renta.
	 * @return la renta nueva creada
	 */
	@PostMapping("/rentas/{codigo}/{noCT}")
	public ResponseEntity<?> rentarProducto(@PathVariable String codigo,@PathVariable Long noCT) {
		Map<String, Object> response = new HashMap<>();
		String mensaje;
		Producto producto = new Producto();
		producto = this.productoService.findByCodigo(codigo);
		Usuario usuario = new Usuario();
		usuario = this.usuarioService.buscarUsuarioPorNoCT(noCT);
		Renta rentaNueva = new Renta();
		try {
			rentaNueva= rentaService.save(rentaNueva);
		}catch(DataAccessException e){
			mensaje = "Error al realizar el insert en la base de datos";
			response.put("mensaje", mensaje);
			mensaje = "";
			mensaje += e.getMessage() + ": ";
			mensaje += e.getMostSpecificCause().getMessage();
			response.put("error", mensaje);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		Calendar fecha = Calendar.getInstance();
		rentaNueva.setFecha_renta(fecha.getTime());
		fecha.add(Calendar.DATE, producto.getPeriodoRenta());
		rentaNueva.setFecha_entrega(fecha.getTime());
		rentaNueva.setProducto(producto);
		rentaNueva.setUsuario(usuario);
		rentaNueva.setStatus_entrega(false);
		int stock = producto.getCurrentStock();
		if(stock<=0) {
			response.put("mensaje", "No hay productos disponibles por el momento");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		producto.setCurrentStock(stock-1);
		productoService.save(producto);
		response.put("mensaje", "La renta se ha realizado exitosamente");
		response.put("renta", rentaNueva);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	/**
	 * Actualiza el status de entrega de false a true
	 * @param id, de la renta a ser actualizada
	 * @return la renta con el status actualizado
	 */
	@PutMapping("/rentas/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		String mensaje;
		Renta rentaActualizada = null;
		rentaActualizada = this.rentaService.findByID(id);
		if (rentaActualizada == null) {
			mensaje = String.format("La renta con el ID: %s no existe", id);
			response.put("mensaje", mensaje);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			rentaActualizada.setStatus_entrega(true);
			rentaActualizada= rentaService.save(rentaActualizada);
		}catch(DataAccessException e){
			mensaje = "Error al actualizar la renta en la base de datos";
			response.put("mensaje", mensaje);
			mensaje = "";
			mensaje += e.getMessage() + ": ";
			mensaje += e.getMostSpecificCause().getMessage();
			response.put("error", mensaje);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La renta se ha actualizado exitosamente");
		response.put("renta", rentaActualizada);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	/**
	 * elimina una renta existente en la base de datos.
	 * @param id, de la renta a ser eliminada
	 */
	@DeleteMapping("/rentas/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		String mensaje;
		try {
			rentaService.delete(id);
		}catch(DataAccessException e){
			mensaje = "Error al eliminar la rent5a en la base de datos";
			response.put("mensaje", mensaje);
			mensaje = "";
			mensaje += e.getMessage() + ": ";
			mensaje += e.getMostSpecificCause().getMessage();
			response.put("error", mensaje);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La renta se ha eliminado exitosamente");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		
	}
	
}