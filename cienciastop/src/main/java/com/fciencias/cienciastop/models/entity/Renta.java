package com.fciencias.cienciastop.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="rentas")
public class Renta implements Serializable{
	
	/* ID de la renta */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/* Usuario que realizo la renta. */
	@ManyToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	/* Producto que se rentó. */
	@ManyToOne
	@JoinColumn(name="producto_id")
	private Producto producto;
	
	/* Fecha en la que se rentó el producto */
	@Column
	private Date fecha_renta;
	
	/* Fecha en la que se entrega el producto rentado. */
	@Column
	private Date fecha_entrega;
	
	/* Status de la renta. */
	@Column
	private boolean status_entrega;


	/**
	 * Regresa el id de la renta
	 * @return el id de la renta.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Define el nuevo ID de la renta.
	 * @param id el nuevo ID de la renta.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Regresa el usuari que realizo la renta
	 * @return el usuario de la renta.
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Define el nuevo usuario de la renta.
	 * @param usuario el nuevo usuario de la renta.
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Regresa el procucto que se rento
	 * @return el producto de la renta.
	 */
	public Producto getProducto() {
		return producto;
	}

	/**
	 * Define el nuevo producto de la renta.
	 * @param producto el nuevo producto de la renta.
	 */
	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	/**
	 * Regresa la fecha en la se realizo la renta
	 * @return la fecha de la renta.
	 */
	public Date getFecha_renta() {
		return fecha_renta;
	}

	/**
	 * Define la nueva fecha de la renta.
	 * @param fecha_renta la fecha nueva de la renta.
	 */
	public void setFecha_renta(Date fecha_renta) {
		this.fecha_renta = fecha_renta;
	}

	/**
	 * Regresa la fecha en la se entregara el producto de la renta
	 * @return la fecha de entrega de la renta.
	 */
	public Date getFecha_entrega() {
		return fecha_entrega;
	}

	/**
	 * Define la nueva fecha de entrega la renta.
	 * @param fecha_entrega la fecha nueva de entrega de la renta.
	 */
	public void setFecha_entrega(Date fecha_entrega) {
		this.fecha_entrega = fecha_entrega;
	}
	
	/**
	 * Regresa el status de la renta
	 * @return status de la renta.
	 */
	public boolean isStatus_entrega() {
		return status_entrega;
	}

	/**
	 * Define el nuevo status de entrega de la renta.
	 * @param status_entrega el nuevo status de entrega de la renta.
	 */
	public void setStatus_entrega(boolean status_entrega) {
		this.status_entrega = status_entrega;
	}





	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}