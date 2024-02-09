package com.cic.incidencias.datos;

import java.util.Optional;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoRolImp extends CrudRepository<usuarioBD, Long> {

    @Query(value = "SELECT * FROM v_usuario_bd WHERE CONVERT(varchar(max), nom_usuario) = :nom_usuario", nativeQuery = true)
    public Optional<usuarioBD> usuarioFindNombre(@Param("nom_usuario") String nombre);

    @Query(value = "SELECT * FROM v_usuario_bd WHERE CAST(nom_usuario AS varchar) = :nomUsuario ORDER BY CAST(nombre_dia AS VARCHAR), hora_ini", nativeQuery = true)
    List<Map<String, Object>> findByNomUsuario(@Param("nomUsuario") String nomUsuario);

}
