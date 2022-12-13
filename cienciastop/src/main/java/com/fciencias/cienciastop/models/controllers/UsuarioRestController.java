package com.fciencias.cienciastop.models.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fciencias.cienciastop.models.entity.Renta;
import com.fciencias.cienciastop.models.entity.Usuario;
import com.fciencias.cienciastop.models.service.IRentaService;
import com.fciencias.cienciastop.models.service.IUsuarioService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api") // ciencias-top?
/**
 * Clase controlador de usuario (aka. Administrador)
 * 
 */
public class UsuarioRestController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUsuarioService usuarioService;
	@Autowired
	private IRentaService rentaService;

	//private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@GetMapping("/usuarios")
	@PreAuthorize("hasRole('Administrador')")
	public ResponseEntity<?> verUsuarios() {
		List<Usuario> usuariosActivos = null;
		Map<String,Object> response = new HashMap<String, Object>();
		try {
			usuariosActivos = this.usuarioService.verUsuarios();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la conexión con la base de datos.");
			String cadenaError = "";
			cadenaError += e.getMessage() + ": ";
			cadenaError += e.getMostSpecificCause().getMessage();
			response.put("error", cadenaError);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (usuariosActivos == null || usuariosActivos.isEmpty()) {
			response.put("mensaje", "No se encontraron usuarios activos en el sistema");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Usuario>>(usuariosActivos,HttpStatus.OK); 
	}
	

	/**Buscar usuarios por nombre */

	@RequestMapping("/usuarios/nombre/{nombre}")
	@PreAuthorize("hasRole('Administrador')")
	public ResponseEntity<?> buscarUsuarioNombre(@PathVariable(value="nombre") String nombre) {
		System.out.println("Buscando usuarios...");
		List<Usuario> usuarios;
		String mensajeError="";
		Map<String, Object> response = new HashMap<>();
		try{
			usuarios = usuarioService.buscarUsuarioPorNombre(nombre);
		}catch(DataAccessException e){
			mensajeError = "Falla en la consulta a la base de datos";
			response.put("mensaje", mensajeError);
			mensajeError = "";
			mensajeError += e.getMessage() + ": ";
			mensajeError += e.getMostSpecificCause().getMessage();
			response.put("Error: ", mensajeError);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (usuarios == null) {
			usuarios = new ArrayList<Usuario>();
		}

		if (!usuarios.isEmpty()) {
			return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
		}
		mensajeError = "Usuario con nombre" + nombre + " no ha sido encontrado";
		response.put("mensaje", mensajeError);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
	}


	/**Buscar usuarios por correo */
	@GetMapping("/usuarios/correo/{correo}")
	@PreAuthorize("hasRole('Administrador')")
	public ResponseEntity<?> buscarUsuario(@PathVariable(value="correo") String correo){
        System.out.println("Buscando usuario con correo " + correo);
		Usuario usuario= null;
		String mensajeError="";
		Map<String, Object> response = new HashMap<>();
		try{
			usuario = usuarioService.buscarUsuarioPorCorreo(correo);
		}catch(DataAccessException e){
			mensajeError = "Falla en la consulta a la base de datos";
			response.put("mensaje", mensajeError);
			mensajeError = "";
			mensajeError += e.getMessage() + ": ";
			mensajeError += e.getMostSpecificCause().getMessage();
			response.put("Error: ", mensajeError);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        if (usuario == null) {
            System.out.println("Usuario con correo " + correo + " no ha sido encontrado");
            return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }

	/**Buscar usuarios por numero de cuenta */
	@RequestMapping(value = "/usuarios/{noCT}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('Administrador')")
	public ResponseEntity<?> buscarUsuario(@PathVariable("noCT") Long noCT) {
        System.out.println("Buscando usuario con numero de cuenta " + noCT);
		Usuario usuario= null;
		String mensajeError="";
		Map<String, Object> response = new HashMap<>();
		try{
			usuario = usuarioService.buscarUsuarioPorNoCT(noCT);
		}catch(DataAccessException e){
			mensajeError = "Falla en la consulta a la base de datos";
			response.put("mensaje", mensajeError);
			mensajeError = "";
			mensajeError += e.getMessage() + ": ";
			mensajeError += e.getMostSpecificCause().getMessage();
			response.put("Error: ", mensajeError);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        if (usuario == null) {
            System.out.println("Usuario con numero de cuenta " + noCT + " no ha sido encontrado");
            return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }
	
	@PostMapping("/usuarios")
	@PreAuthorize("hasRole('Administrador')")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> agregarUsuario(@RequestBody Usuario usuario) {
		Usuario usuarioNuevo = null;
		Map<String,Object> response = new HashMap<>();
		try {
			Usuario usuarioAux = usuario;
			usuarioAux.setContrasenya(passwordEncoder.encode(usuario.getContrasenya()));
			usuarioNuevo = usuarioService.guardar(usuarioAux);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos.");
			String aux = "" + e.getMessage() + ": ";
			response.put("error",aux.concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			// TODO: handle exception
		}
		/*if(usuarioNuevo == null){
			response.put("mensaje", "El usuario se ha reactivado con éxito. :D")
			response.put("reactivacion", ); //??
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
		}*/
		response.put("mensaje", "El usuario se ha creado con éxito. :D");
		response.put("usuario", usuarioNuevo);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}

	@PutMapping("/usuarios/{noCT}")
	@PreAuthorize("hasRole('Administrador')")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> editarUsuario(@RequestBody Usuario usuario, @PathVariable Long noCT) {
		Usuario currentUsuario = usuarioService.buscarUsuarioPorNoCT(noCT);
		Usuario usuarioEditado = null;
		Map<String,Object> response = new HashMap<>();
		if (currentUsuario == null) {
			response.put("mensaje", "Error: no se puede editar el usuario con es noCT:".concat(noCT.toString().concat(" no existe en la base de datos.")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			currentUsuario.setNombre(usuario.getNombre());
			currentUsuario.setApellidos(usuario.getApellidos());
			currentUsuario.setRol(usuario.getRol());
			currentUsuario.setTelefono(usuario.getTelefono());
			usuarioEditado = usuarioService.guardar(currentUsuario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el usuario en la base de datos.");
			String aux = "" + e.getMessage() + ": ";
			response.put("error",aux.concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El usuario se ha editado correctamente.");
		response.put("usuario",usuarioEditado);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}

    @DeleteMapping("/usuarios/{noCT}")
	@PreAuthorize("hasRole('Administrador')")
	//@PreAuthorize("hasRole('Administrador') || hasRole('Alumno')")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long noCT) {
        Map<String, Object> response = new HashMap<String, Object>();
        if(usuarioService.borrar(noCT) == 0) {
            response.put("mensaje", "El usuario con noCT: "
            .concat(String.valueOf(noCT)).concat(" no existe"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);    
        }
        response.put("mensaje", "El usuario ha sido eliminado con exito");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        //return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
    }

	/**
	 * Agrupar usuarios por carrera.
	 * Si existe un error en la base de datos se manda un mensaje sobre el error.
	 * @return una lista de usuarios agrupados por su carrera.
	 * Si existe un error en la base de datos se manda un mensaje de error.
	 */
	@GetMapping("/usuarios/agrupado-carrera")
	@PreAuthorize("hasRole('Administrador')")
	public ResponseEntity<?> agruparPorCarrera() {
		List<Object[]> agrupamiento;
		HttpStatus status;
		Map<String, Object> response = new HashMap<>();
		String mensaje;
		try {
			agrupamiento = usuarioService.agruparPorCarrera();
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
		if (agrupamiento == null) {
			agrupamiento = new ArrayList<Object[]>();
		}
		status = HttpStatus.OK;
		return new ResponseEntity<List<Object[]>>(agrupamiento, status);
	}

	/**
	 * Regresa la lista de usuarios agrupada por cuentas activas e inactivas.
	 * Si existe un error en la base de datos se manda un mensaje sobre el error.
	 * @return la lista de usuarios agrupada por cuentas activas e inactivas.
	 * Si existe un error en la base de datos se manda un mensaje de error.
	 */
	@GetMapping("/usuarios/agrupado-status")
	@PreAuthorize("hasRole('Administrador')")
	public ResponseEntity<?> agruparPorStatus() {
		List<Object[]> agrupamiento;
		HttpStatus status;
		Map<String, Object> response = new HashMap<>();
		String mensaje;
		try {
			agrupamiento = usuarioService.agruparPorStatus();
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
		if (agrupamiento == null) {
			agrupamiento = new ArrayList<Object[]>();
		}
		
		// Cambia 0 por Inactivos y 1 por Activos
		for (int i = 0; i < agrupamiento.size(); i++) {
			Object objetos[] = agrupamiento.get(i);
			int cast = Integer.valueOf(objetos[1].toString());
			if (cast == 0) {
				objetos[1] = "Inactivos";
			}
			if (cast == 1) {
				objetos[1] = "Activos";
			}
			agrupamiento.set(i, objetos);
		}

		status = HttpStatus.OK;
		return new ResponseEntity<List<Object[]>>(agrupamiento, status);
	}


	@PostMapping("/usuarios/restablecer-contrasenia/{noCTLong}/{contraseniaString}")
    public ResponseEntity<?> restablecerContrasenia(@PathVariable Long noCTLong, @PathVariable String contraseniaString ) {
        Usuario usuario;
		String mensajeError="";
		Map<String, Object> response = new HashMap<>();
		try{
			usuario = usuarioService.buscarUsuarioPorNoCT(noCTLong);
		}catch(DataAccessException e){
			mensajeError = "Falla en la consulta a la base de datos";
			response.put("mensaje", mensajeError);
			mensajeError = "";
			mensajeError += e.getMessage() + ": ";
			mensajeError += e.getMostSpecificCause().getMessage();
			response.put("Error: ", mensajeError);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
        if (usuario == null) {
            response.put("mensaje", "Error: no se puede restablecer contrasenia del usuario con noCT:".concat(noCTLong.toString().concat(" no existe en la base de datos.")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }
        String newPassword = passwordEncoder.encode(contraseniaString);
        usuario.setContrasenya(newPassword);
        usuarioService.guardar(usuario);
		response.put("Contrasenia actualizada:", "exitosamente!!!!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

	
	@GetMapping("usuarios/ver-perfil/{noCT}")
	@PreAuthorize("hasRole('Administrador')")
	public ResponseEntity<?> getPerfilAdmin(@PathVariable Long noCT) {
		Usuario usuario = new Usuario();
		List<Renta> historialDeUsr = new ArrayList<Renta>();
		List<Renta> rentasDeUsr = new ArrayList<Renta>();		
		Map<String,Object> response = new HashMap<String, Object>();		

		try {		
			usuario = this.usuarioService.buscarUsuarioPorNoCT(noCT);
			if (usuario == null) {
				response.put("mensaje", "Error: no se puede acceder al usuario con noCT:".concat(noCT.toString().concat(" no existe en la base de datos.")));
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
			}
			response.put("dataUsuario",usuario);	
			historialDeUsr  = this.rentaService.rentasVencidasUsr(usuario);
			rentasDeUsr  = this.rentaService.rentasActualesUsr(usuario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la conexión con la base de datos.");
			String cadenaError = "";
			cadenaError += e.getMessage() + ": ";
			cadenaError += e.getMostSpecificCause().getMessage();
			response.put("error", cadenaError);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}						
		
		if (historialDeUsr != null) {
			response.put("rentasVencidas",historialDeUsr);
		}
		if (rentasDeUsr != null) {
			response.put("rentasActuales", rentasDeUsr);
		}
		if (historialDeUsr.isEmpty() && rentasDeUsr.isEmpty()) {
			response.put("mensaje", "No se encontro informacion de rentas del usuario.");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.PARTIAL_CONTENT);
		}		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}

	@GetMapping("ver-perfil/{noCT}")
	@PreAuthorize("@securityService.hasUser(#noCT)")	
	public ResponseEntity<?> getPerfilUsr(@PathVariable Long noCT) {
		Usuario usuario = new Usuario();
		List<Renta> historialDeUsr = new ArrayList<Renta>();
		List<Renta> rentasDeUsr = new ArrayList<Renta>();		
		Map<String,Object> response = new HashMap<String, Object>();		

		try {		
			usuario = this.usuarioService.buscarUsuarioPorNoCT(noCT);
			if (usuario == null) {
				response.put("mensaje", "Error: no se puede acceder al usuario con noCT:".concat(noCT.toString().concat(" no existe en la base de datos.")));
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
			}
			response.put("dataUsuario",usuario);	
			historialDeUsr  = this.rentaService.rentasVencidasUsr(usuario);
			rentasDeUsr  = this.rentaService.rentasActualesUsr(usuario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la conexión con la base de datos.");
			String cadenaError = "";
			cadenaError += e.getMessage() + ": ";
			cadenaError += e.getMostSpecificCause().getMessage();
			response.put("error", cadenaError);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}						
		
		if (historialDeUsr != null) {
			response.put("rentasVencidas",historialDeUsr);
		}
		if (rentasDeUsr != null) {
			response.put("rentasActuales", rentasDeUsr);
		}
		if (historialDeUsr.isEmpty() && rentasDeUsr.isEmpty()) {
			response.put("mensaje", "No se encontro informacion de rentas del usuario.");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.PARTIAL_CONTENT);
		}		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
}
