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
import com.fciencias.cienciastop.models.entity.Usuario;
import com.fciencias.cienciastop.models.service.IUsuarioService;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api")//ciencias-top?
/**
 * Clase controlador de usuario (aka. Administrador)
 * 
 */
public class UsuarioRestController {

	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping("/usuarios")
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
	
	@GetMapping("/usuarios/{noCT}")
	public Usuario buscarUsuario(@PathVariable Long noCT) {
		return usuarioService.buscarUsuarioPorNoCT(noCT);
	}
	
	@PostMapping("/usuarios")
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario agregarUsuario(@RequestBody Usuario usuario) {
		return usuarioService.guardar(usuario);
	}
	
	@DeleteMapping("/usuarios/{noCT}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminarUsuario(@PathVariable Long noCT) {
		usuarioService.borrar(noCT);
	}

	@PutMapping("/usuarios/{noCT}")
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
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El usuario se ha editado correctamente.");
		response.put("usuario",usuarioEditado);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	

}
