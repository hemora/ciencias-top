package com.fciencias.cienciastop.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fciencias.cienciastop.models.entity.Renta;
import com.fciencias.cienciastop.models.entity.Usuario;

public interface IRentaDao extends CrudRepository<Renta, Long>{
	@Query(value= "SELECT * FROM rentas WHERE status_entrega = :status", nativeQuery = true)
	List<Renta> encontrarPorStatus(@Param("status") boolean status);

	@Query(value = "SELECT * FROM rentas WHERE usuario_id = :usuario AND status_entrega = :status AND fecha_entrega >= CURRENT_DATE", nativeQuery = true)
	List<Renta> encontrarRentasUsuario(@Param ("status") boolean status, @Param ("usuario") Usuario usuario);
	
	@Query(value= "SELECT * FROM rentas WHERE usuario_id = :usuario AND fecha_entrega < CURRENT_DATE", nativeQuery = true)
	List<Renta> encontrarRentasVencidas(@Param ("usuario") Usuario usuario);
}
