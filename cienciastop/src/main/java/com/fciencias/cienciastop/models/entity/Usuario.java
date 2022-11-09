package com.fciencias.cienciastop.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="usuarios")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/* Una cadena con un nombre de longitud  igual o menor a xxx */
	@NotNull
	@Column(name="nombre")
	private String nombre;
	/* Una cadena con unos apellidos de longitud  igual o menor a xxx */
	@NotNull
	@Column(name="apellidos")
	private String apellidos;
	/* Un '?entero? que tiene 9 di'gitos */
	@Id
	@NotNull
	@Column(name="noCT")
	private Integer noCT;
	/* Un '?Long? que tiene 10 d'igitos */
	@Column(name="telefono")
	private Long telefono;
	/* Una cadena con un correo electr'onico de longitud xxx o menor */
	@NotNull
	@Column(name="correo")
	private String correo;
	/* Una cadena con una carrera de longitud xxx o menor */
	@Column(name="carrera")
	private String carrera;
	/* Una cadena con un rol de longitud xxx o menor */
	@NotNull
	@Column(name="rol")
	private String rol;
	/* Una cadena con una contrasenya de longitud xxx o menor */
	@NotNull
	@Column(name="contrasenya")
	private String contrasenya;
	/* Un valor entre 0 y 1 que indica si el usuario est'a desactivado o no */
	@Column(name="status")
	@Min(value = 0, message = "status must be 0")
	@Max(value = 1, message = "status must be 1")
	//@JsonIgnore
	private Integer status;

	public Usuario() {}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public int getNoCT() {
		return noCT;
	}

	public void setNoCT(int noCT) {
		this.noCT = noCT;
	}

	public Long getTelefono() {
		return telefono;
	}

	public void setTelefono(Long telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getContrasenya() {
		return contrasenya;
	}

	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}