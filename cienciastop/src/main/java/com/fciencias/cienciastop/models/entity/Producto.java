package com.fciencias.cienciastop.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Clase para representar un producto de Ciencias Top.
 */
@Entity
@Table(name="productos")
public class Producto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/* Codigo de longitud 12 del producto. */
	@Id
	@Column(name = "codigo", unique=true)
	@JsonProperty("codigo")
	private String codigo;
	
	/* Nombre del producto. */
	@Column(name="nombre")
	@JsonProperty("nombre")
	@NotNull(message="El nombre es requerido")
	private String nombre;
	
	/* Stock inicial del producto. */
	@Column(name="stock_inicial")
	@JsonProperty("stock_inicial")
	@NotNull(message="El stock inicial es requerido")
	//@Min(value=0, message="El stock inicial debe ser más grande que 1")
	private int stock_inicial;	
	
	/* Stock actual del prodcuto. */
	@Column(name="current_stock")
	@JsonProperty("current_stock")
	@NotNull(message="El stock actual es requerido")
	@Min(value=0, message="El stock inicial debe ser más grande que 1")
	private int current_stock;
	
	/* Precio del prodcuto. */
	@Column(name="precio")
	@NotNull(message="El precio es requerido")
	@Min(value=0, message="El precio debe de ser positivo")
	@Max(value=500, message="El precio no puede ser más de 500")
	private double precio;
	
	/* Descripcion del prodcuto. */
	@Column(name="descripcion")
	private String descripcion;	
	
	/* Ruta de la imagen del prodcuto. */
	@Column(name="imagen")
	@NotNull(message="La imagen es requerida")
	private String imagen;	
	
	/* Tipo del producto. */
	@Column(name="tipo")
	@NotNull(message="El tipo es requerido")
	private String tipo;	
	
	/* Categoria del producto. */
	@Column(name="categoria")
	@NotNull(message="La categoría es requerido")
	private String categoria;	
	
	/* Periodo de renta del producto. */
	@Column(name="periodo_renta")
	@JsonProperty("periodo_renta")
	@Min(value=3, message="El días de renta deben de ser mayor a 3")
	@Max(value=7, message="El días de renta deben de ser menores a 8")
	private int periodo_renta;

	// Numero de Cuenta del usuario que agrego el Producto 
	@Column(name="noCT")
	@NotNull(message="La categoría es requerido")
	private long noCT;
	
	/**
	 * Regresa el nombre del producto.
	 * @return el nombre del producto.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Define el nuevo nombre del producto.
	 * @param nombre el nuevo nombre del producto.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Regresa el codigo del producto.
	 * @return el codigo del producto.
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Define el nuevo codigo del producto.
	 * @param codigo el nuevo codigo del producto.
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Regresa el stock inicial del producto.
	 * @return el stock inicial del producto.
	 */
	public int getStockInicial() {
		return stock_inicial;
	}

	/**
	 * Define el nuevo stock inicial del producto.
	 * @param stockInicial el nuevo stock inicial del producto.
	 */
	public void setStockInicial(int stockInicial) {
		this.stock_inicial = stockInicial;
	}

	/**
	 * Regresa el stock actual del producto.
	 * @return el stock actual del producto.
	 */
	public int getCurrentStock() {
		return current_stock;
	}

	/**
	 * Define el nuevo stock actual del producto.
	 * @param currentStock el nuevo stock actual del producto.
	 */
	public void setCurrentStock(int currentStock) {
		this.current_stock = currentStock;
	}

	/**
	 * Regresa el precio del producto.
	 * @return el precio del producto.
	 */
	public double getPrecio() {
		return precio;
	}

	/**
	 * Define el nuevo precio del producto.
	 * @param precio el nuevo precio del producto.
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	/**
	 * Regresa la descripcion del producto.
	 * @return la descripcion del producto.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Define la nueva descripcion del producto.
	 * @param descripcion la nueva descripcion del producto.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Regresa la ruta de la imagen del producto.
	 * @return la ruta de la imagen del producto.
	 */
	public String getImagen() {
		return imagen;
	}

	/**
	 * Define la nueva ruta de la imagen del producto.
	 * @param imagen la nueva ruta de la imagen del producto.
	 */
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	/**
	 * Regresa el tipo del producto.
	 * @return el tipo del producto.
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Define el nuevo tipo del producto.
	 * @param tipo el nuevo tipo del producto.
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Regresa la categoria del producto.
	 * @return la categoria del producto.
	 */
	public String getCategoria() {
		return categoria;
	}

	/**
	 * Define la nueva categoria del producto.
	 * @param categoria la nueva categoria del producto.
	 */
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	/**
	 * Regresa el perido de renta del producto.
	 * @return el perido de renta del producto.
	 */
	public int getPeriodoRenta() {
		return periodo_renta;
	}

	/**
	 * Define el nuevo periodo de renta del producto.
	 * @param periodoRenta el nuevo periodo de renta del producto.
	 */
	public void setPeriodoRenta(int periodoRenta) {
		this.periodo_renta = periodoRenta;
	}
	
	/**
	 * Regresa la identificacion del usuario que agrego el producto.
	 * @return la identificacion del usuario que agrego el producto.
	 */
	public long getnoCT() {
		return noCT;
	}

	/**
	 * Define la nueva identificacion del usuario encargado del producto.
	 * @param periodoRenta la nueva identificacion del usuario encargado del producto.
	 */
	public void setnoCT(long noCT) {
		this.noCT = noCT;
	}
}