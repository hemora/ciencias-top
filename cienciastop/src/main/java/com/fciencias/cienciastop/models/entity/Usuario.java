package com.fciencias.cienciastop.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="usuarios")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	/* Una cadena con un nombre de longitud  igual o menor a xxx */
	@NotNull(message = "El nombre no puede ser nulo")
	@NotBlank(message = "El nombre no puede ser caracteres vacios")
	@Size(min = 3, max = 200, message 
      = "El nombre debe tener entre 3 y 200 caracteres")
	@Column(name="nombre")
	private String nombre;
	/* Una cadena con unos apellidos de longitud  igual o menor a xxx */
	@NotNull(message = "Los apellidos no pueden ser nulos")
	@NotBlank(message = "Los apellidos no pueden ser caracteres vacios")
	@Size(min = 3, max = 200, message 
      = "Los apellidos deben tener entre 3 y 200 caracteres")
	@Column(name="apellidos")
	private String apellidos;
	/* Un Long que tiene 9 digitos */
	@Id
	@NotNull(message = "El numero de cuenta no puede ser nulo")
	@Digits(integer=10,fraction=0,
			message="Se requiere un numero de cuenta valido")
	@Column(name="noCT")
	private Long noCT;
	/* Un Long que tiene 10 digitos */
    @NotNull(message = "El numero de telefono no puede ser nulo")
	@Digits(integer=10,fraction=0,
			message="Se requiere un telefono valido")
	@Column(name="telefono")
	private Long telefono;
	/* Una cadena con un correo electronico de longitud xxx o menor */
	@NotNull(message = "el correo no puede ser nulo")
	@Email
	//@Size(min = 5, max = 200, message 
    // = "El correo debe tener entre 10 y 200 caracteres")
	@Column(name="correo")
	private String correo;
	/* Una cadena con una carrera de longitud xxx o menor */
    @NotNull(message = "La carrera no puede ser nula")
	@NotBlank(message = "La carrera no puede ser caracteres vacios")
	@Size(min = 5, max = 200, message 
      = "La carrera debe tener entre 10 y 200 caracteres")
	@Column(name="carrera")
	private String carrera;
	/* Una cadena con un rol de longitud xxx o menor */
	@NotNull(message = "El rol no puede ser nulo")
	@NotBlank(message = "El rol no puede ser caracteres vacios")
	@Size(min = 1, max = 13, message 
      = "El rol debe tener entre 1 y 10 caracteres")
	@Column(name="rol")
	private String rol;
	/* Una cadena con una contrasenya de longitud xxx o menor */
	@NotNull(message = "La contrasenya no puede ser nula")
	@NotBlank(message = "La contrasenya no puede ser caracteres vacios")
	@Size(min = 8, max = 200, message 
      = "La contrasenya debe tener entre 10 y 200 caracteres")
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

	public Long getNoCT() {
		return noCT;
	}

	public void setNoCT(Long noCT) {
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