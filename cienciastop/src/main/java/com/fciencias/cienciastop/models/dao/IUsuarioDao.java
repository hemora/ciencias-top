package com.fciencias.cienciastop.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fciencias.cienciastop.models.entity.Usuario;

@Repository
public interface IUsuarioDao extends CrudRepository<Usuario, Long> {
	
	@Query(value= "SELECT * FROM usuarios WHERE status = :status", nativeQuery = true)
	List<Usuario> encontrarPorStatus(@Param("status") Integer status);

	@Query(value= "SELECT * FROM usuarios WHERE LOWER(nombre) LIKE %:nombre% OR nombre LIKE %:nombre% ", nativeQuery = true)
	List<Usuario> encontrarPorNombre(@Param("nombre") String nombre );
	
	@Query(value= "SELECT * FROM usuarios WHERE noCT = :noCT AND status = 1", nativeQuery = true)
	Usuario encontrarPorNoCT(@Param("noCT") Long noCT);

	/*@Query(value= "SELECT * FROM usuarios WHERE noCT = :noCT AND status = :status", nativeQuery = true)
	Usuario encontrarPorNoCTyStatus(@Param("noCT") Long noCT, @Param("status") Integer status);*/
	
	@Query(value= "SELECT * FROM usuarios WHERE correo = :correo", nativeQuery = true)
	Usuario encontrarPorCorreo(@Param("correo") String correo);
	
	@Modifying
	@Transactional
	@Query(value= "INSERT INTO usuarios "
		+"(nombre,apellidos,noCT,telefono,correo,carrera,rol,contrasenya,status)"
		+" VALUES (:nombre,:apellidos,:noCT,:telefono,:correo,:carrera,:rol,"
		+":contrasenya,1)", nativeQuery = true)
	Integer crear(
		@Param("nombre") String nombre,
		@Param("apellidos") String apellidos,
		@Param("noCT") Long noCT,
		@Param("telefono") Long telefono,
		@Param("correo") String correo,
		@Param("carrera") String carrera,
		@Param("rol") String rol,
		@Param("contrasenya") String contrasenya);

	@Modifying
	@Transactional
	@Query(value ="UPDATE usuarios SET status = 1 WHERE noCT = :noCT", nativeQuery = true)
	Integer activar(@Param("noCT") Long noCT);
	
	@Modifying
	@Transactional
	@Query(value ="UPDATE usuarios SET status = 0 WHERE noCT = :noCT", nativeQuery = true)
	Integer desactivar(@Param("noCT") Long noCT);

	/**
	 * Agrupar usuarios por carrera.
	 * @return una lista de usuarios agrupados por su carrera.
	 */
	@Query(
			value= "SELECT COUNT(noCT), carrera FROM usuarios WHERE status=1 GROUP BY carrera ", 
			nativeQuery = true)
	public List<Object[]> agruparPorCarrera();

	/**
	 * Regresa la lista de usuarios agrupada por cuentas activas e inactivas.
	 * @return la lista de usuarios agrupada por cuentas activas e inactivas.
	 */
	@Query(
			value= "SELECT COUNT(noCT), status FROM usuarios GROUP BY status ", 
			nativeQuery = true)
	public List<Object[]> agruparPorStatus();
}
