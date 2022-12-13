package com.fciencias.cienciastop.models.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fciencias.cienciastop.models.entity.Monedero;
import com.fciencias.cienciastop.models.entity.Producto;
import com.fciencias.cienciastop.models.entity.Renta;
import com.fciencias.cienciastop.models.entity.Usuario;
import com.fciencias.cienciastop.models.service.IMonederoService;
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
	
	@Autowired
	private IMonederoService monederoService;
	
	/*@GetMapping("/rentas")
	public List<Renta> index() {
		return rentaService.findAll();
	}*/	
	
	@GetMapping("/rentas")
	@PreAuthorize("hasRole('Administrador')")
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
		Monedero monedero = new Monedero();
		String pattern = "yyyy-MM";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("es", "MX"));
        String periodoActual = simpleDateFormat.format(new Date());
		monedero = this.monederoService.obtenerPorDueno(noCT,periodoActual);
		
		
		Calendar fecha = Calendar.getInstance();
		rentaNueva.setFecha_renta(fecha.getTime());
		fecha.add(Calendar.DATE, producto.getPeriodoRenta());
		rentaNueva.setFecha_entrega(fecha.getTime());
		rentaNueva.setProducto(producto);
		rentaNueva.setUsuario(usuario);
		rentaNueva.setStatus_entrega(false);
		int stock = producto.getCurrentStock();
		double pumapuntos = monedero.getPumaPuntos();
		double precio = producto.getPrecio();
		if(pumapuntos < precio) {
			response.put("mensaje", "Pumampuntos insuficientes");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		List<Renta> rentasHoy;
		rentasHoy = rentaService.findAll();
		rentasHoy = auxHistorial(rentasHoy, noCT);
		rentasHoy = auxRenta(rentasHoy);
		if(rentasHoy.size() == 3) {
			response.put("mensaje", "Ya has excedido el limite de rentas por hoy");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
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
		producto.setCurrentStock(stock-1);
		productoService.save(producto);
		monedero.setPumaPuntos(pumapuntos-precio);
		monederoService.save(monedero);
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
			mensaje = "Error al eliminar la renta en la base de datos";
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

	/**
	 * Regresa la lista de los 5 usuarios con mayor cantidad de rentas en la semana.
	 * Si existe un error en la base de datos se manda un mensaje sobre el error.
	 * @return la lista de los 5 usuarios con mayor cantidad de rentas en la semana.
	 * Si existe un error en la base de datos se manda un mensaje de error.
	 */
	@GetMapping("/rentas/con-mas-rentas")
	@PreAuthorize("hasRole('Administrador')")
	public ResponseEntity<?> topFiveConMasRentas() {
		List<Object[]> conMasRentas;
		HttpStatus status;
		Map<String, Object> response = new HashMap<>();
		String mensaje;
		try {
			conMasRentas = rentaService.topFiveConMasRentas();
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
		if (conMasRentas == null) {
			conMasRentas = new ArrayList<Object[]>();
		}
		status = HttpStatus.OK;
		return new ResponseEntity<List<Object[]>>(conMasRentas, status);
	}

	/**
	 * Regresa la lista de los 5 productos mas rentados del mes.
	 * Si existe un error en la base de datos se manda un mensaje sobre el error.
	 * @return la lista de los 5 productos mas rentados del mes.
	 * Si existe un error en la base de datos se manda un mensaje de error.
	 */
	@GetMapping("/rentas/prod-mas-rentados")
	@PreAuthorize("hasRole('Administrador')")
	public ResponseEntity<?> topFiveMasRentados() {
		List<Object[]> masRentados;
		HttpStatus status;
		Map<String, Object> response = new HashMap<>();
		String mensaje;
		try {
			masRentados = rentaService.topFiveMasRentados();
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
		if (masRentados == null) {
			masRentados = new ArrayList<Object[]>();
		}
		status = HttpStatus.OK;
		return new ResponseEntity<List<Object[]>>(masRentados, status);
	}

	/**
	 * Regresa la lista de los 10 usuarios con mas retardos.
	 * Si existe un error en la base de datos se manda un mensaje sobre el error.
	 * @return la lista de los 10 usuarios con mas retardos.
	 * Si existe un error en la base de datos se manda un mensaje de error.
	 */
	@GetMapping("/rentas/usr-mas-retardos")
	@PreAuthorize("hasRole('Administrador')")
	public ResponseEntity<?> topTenConMasRetardos() {
		List<Object[]> conMasRetardos;
		HttpStatus status;
		Map<String, Object> response = new HashMap<>();
		String mensaje;
		try {
			conMasRetardos = rentaService.topTenConMasRetardos();
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
		if (conMasRetardos == null) {
			conMasRetardos = new ArrayList<Object[]>();
		}
		status = HttpStatus.OK;
		return new ResponseEntity<List<Object[]>>(conMasRetardos, status);
	}

	/**
	 * Regresa una lista de rentas realizadas por el usuario.
	 * Si existe un error en la base de datos o no existen coincidencias
	 * se manda un mensaje sobre el tipo de error.
	 * @param entrada la entrada a la que se le buscaran coincidencias por id
	 * del usuario.
	 * @return una lista de rentas realizadas por el usuario.
	 * Si existe un error en la base de datos o no existen coincidencias
	 * se manda un mensaje sobre el tipo de error.
	 */
	@GetMapping("/rentas/historial")
	@PreAuthorize("hasRole('Administrador') || hasRole('Alumno') || hasRole('Proveedor')")
	public ResponseEntity<?> historial(@RequestParam String entrada) {
		List<Renta> historial;
		HttpStatus status;
		Map<String, Object> response = new HashMap<>();
		String mensaje;
		try {
			Long auxEntrada = Long.parseLong(entrada);
			historial = rentaService.findAll();
			historial = auxHistorial(historial, auxEntrada);
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
		// Entrada correcto
		if (!historial.isEmpty()) {
			status = HttpStatus.OK;
			return new ResponseEntity<List<Renta>>(historial, status);
		}
		// Error con la entrada
		//mensaje = String.format("No existen coincidencias con: %s", entrada);
		mensaje = "Historial vacio";		
		response.put("mensaje", mensaje);
		status = HttpStatus.NOT_FOUND;
		return new ResponseEntity<Map<String, Object>>(response, status);
	}

	// Auxiliar para historial
	private List<Renta> auxHistorial(List<Renta> historial, Long entrada) {
		if (historial == null) {
			historial = new ArrayList<Renta>();
		}
		if (historial.isEmpty()) {
			return historial;
		}
		List<Renta> aux = new ArrayList<Renta>();
		for (Renta renta : historial) {
			Long noCT = renta.getUsuario().getNoCT();
			int com = Long.compare(entrada, noCT);
			if (com == 0) {
				aux.add(renta);
			}
		}
		return aux;
	}
	// Auxiliar para historial
		private List<Renta> auxRenta(List<Renta> rentasHoy) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Calendar fecha = Calendar.getInstance();
	        Date fechaActual = fecha.getTime();
			if (rentasHoy == null) {
				rentasHoy = new ArrayList<Renta>();
			}
			if (rentasHoy.isEmpty()) {
				return rentasHoy;
			}
			List<Renta> aux = new ArrayList<Renta>();
			for (Renta renta : rentasHoy) {
				//System.out.println(renta.getUsuario().getNoCT());
				//System.out.println(entrada);
				Date fecharenta = renta.getFecha_renta();
				if (sdf.format(fechaActual).equals(sdf.format(fecharenta))) {
					aux.add(renta);
				}
			}
			return aux;
		}
}