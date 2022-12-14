package com.fciencias.cienciastop.models.service;

import java.util.List;

import com.fciencias.cienciastop.models.entity.Renta;
import com.fciencias.cienciastop.models.entity.Usuario;

public interface IRentaService {
	
	public List<Renta> findAll();
	
	public List<Renta> verRentas();
	
	public Renta findByID(Long id);
	
	public Renta save(Renta producto);
	
	public void delete(Long id);

	/**
	 * Regresa la lista de los 5 usuarios con mayor cantidad de rentas en la semana.
	 * @return la lista de los 5 usuarios con mayor cantidad de rentas en la semana.
	 */
	public List<Object[]> topFiveConMasRentas();

	/**
	 * Regresa la lista de los 5 productos mas rentados del mes.
	 * @return la lista de los 5 productos mas rentados del mes.
	 */
	public List<Object[]> topFiveMasRentados();

	/**
	 * Regresa la lista de los 10 usuarios con mas retardos.
	 * @return la lista de los 10 usuarios con mas retardos.
	 */
	public List<Object[]> topTenConMasRetardos();

	/**
	 * Busca rentas por usuario_id en la base de datos.
	 * @param usuario_id el usuario_id que se buscara.
	 * @return una lista de rentas que contienen el usuario_id.
	 */
	public List<Renta> historial(Long usuario_id);
	public List<Renta> rentasVencidasUsr(Usuario usuario);

	public List<Renta> rentasActualesUsr(Usuario usuario);

}