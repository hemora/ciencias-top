package com.fciencias.cienciastop.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="usuarios")
public class Usuario implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column
	private String nombre;
	@Column
	private String correo;
	
	
	@OneToMany(
			mappedBy = "usuario",
			cascade = CascadeType.ALL,
			orphanRemoval = true
		)
		private List<Renta> productos = new ArrayList<>();
	
	
	
	
	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public String getNombre() {
		return nombre;
	}




	public void setNombre(String nombre) {
		this.nombre = nombre;
	}




	public String getCorreo() {
		return correo;
	}




	public void setCorreo(String correo) {
		this.correo = correo;
	}




	public List<Renta> getProductos() {
		return productos;
	}




	public void setProductos(List<Renta> productos) {
		this.productos = productos;
	}




	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}