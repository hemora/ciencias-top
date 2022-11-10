package com.fciencias.cienciastop.models.service;

import java.util.List;

import com.fciencias.cienciastop.models.entity.Usuario;

public interface IUsuarioService {
	public List<Usuario> verUsuarios();
	
	public Usuario buscarUsuarioPorNoCT(int noCT);
	
	public Usuario guardar(Usuario usuario);
	
	public int borrar(int noCT);
}
