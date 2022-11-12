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
	public List<Usuario> verUsuarios() {
		return usuarioService.verUsuarios();
	}
	
	@GetMapping("/usuarios/{noCT}")
	public Usuario buscarUsuario(@PathVariable int noCT) {
		return usuarioService.buscarUsuarioPorNoCT(noCT);
	}
	
	@PostMapping("/usuarios")
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario agregarUsuario(@RequestBody Usuario usuario) {
		return usuarioService.guardar(usuario);
	}
	
	@PutMapping("/usuarios/{noCT}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminatUsuario(@PathVariable int noCT) {
		usuarioService.borrar(noCT);
	}

	@PutMapping("/usuarios/{noCT}")
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario editar(@RequestBody Usuario usuario, @PathVariable int noCT) {
		Usuario currentUsuario = this.usuarioService.buscarUsuarioPorNoCT(noCT);
		currentUsuario.setNombre(usuario.getNombre());
		currentUsuario.setApellidos(usuario.getApellidos());
		currentUsuario.setRol(usuario.getRol());
		currentUsuario.setTelefono(usuario.getTelefono());
		return usuarioService.editar(currentUsuario);
		
	}
	

}
