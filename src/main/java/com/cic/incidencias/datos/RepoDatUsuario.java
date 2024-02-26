package com.cic.incidencias.datos;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoDatUsuario extends CrudRepository<vUsuarioDiasHoras, Long>{

    @Query
    (
        value = "SELECT * FROM v_usuario_dias_horas WHERE "+
                "CAST(nom_usuario AS VARCHAR) = :nom_usuario "+
                "ORDER BY numero_dia, hora_ini", 
        nativeQuery = true
    )
    public List<Map<String, Object>> datosUusario
    (
        @Param("nom_usuario") String usuario
    );
    
    @Query
    (
        value = "SELECT nombre, terjeta_cic, personal "+
        "FROM v_datos_prof_memos WHERE nom_usuario = :nom_usuario", 
        nativeQuery = true
    )
    public Map<String, Object> datosUsuarioMemo
    (
        @Param("nom_usuario") String usuario
    );

    @Query
    (
        value = "SELECT CONCAT( STRING_AGG(nombre_dia, ', '), ' ', "+
                "RIGHT('0' + CAST(DATEPART(HOUR, hora_ini) AS VARCHAR(2)), 2) + ':' + "+
                "RIGHT('0' + CAST(DATEPART(MINUTE, hora_ini) AS VARCHAR(2)), 2), '-', "+
                "RIGHT('0' + CAST(DATEPART(HOUR, hora_fin) AS VARCHAR(2)), 2) + ':' + "+
                "RIGHT('0' + CAST(DATEPART(MINUTE, hora_fin) AS VARCHAR(2)), 2) ) "+
                "AS nombre_dia FROM v_dias_horas_memos WHERE nom_usuario = :nom_usuario "+
                "GROUP BY hora_ini, hora_fin, nom_usuario ORDER BY MIN(numero_dia)",
        nativeQuery = true
    )
    public List<Map<String, Object>> datosProfesorDiasHoras
    (
        @Param("nom_usuario") String usuario
    );

    @Query
    (
        value = "SELECT fecha_incidencia, hora_ini_incidencia, hora_fin_incidencia, "+
                "observaciones, fecha_compensacion, hora_ini_compensacion, "+
                "hora_fin_compensacion, cant_tiempo_cubre FROM v_lista_reposicion_memos_pdf "+
                "WHERE nom_usuario = :nom_usuario AND id_prof_incidencia = :id_prof_incidencia",
        nativeQuery = true
    )
    public List<Map<String, Object>> listaReposicionMemosPdf
    (
        @Param("nom_usuario") String usuario, 
        @Param("id_prof_incidencia") Integer idProfIncidencia
    );

    @Query
    (
        value = "SELECT TOP 1 fecha_incidencia, hora_ini_incidencia, "+
                "hora_fin_incidencia FROM v_lista_corrimiento_memos_pdf "+
                "WHERE nom_usuario = :nom_usuario AND id_prof_incidencia = :id_prof_incidencia ",
        nativeQuery = true
    )
    public Map<String, Object> listaCorrimientoMemosPdf
    (
        @Param("nom_usuario") String usuario, 
        @Param("id_prof_incidencia") Integer idProfIncidencia
    );

    @Query
    (
        value = "SELECT TOP 1 fecha_ini_compensacion, fecha_fin_compensacion "+
                "FROM v_lista_dia_economico_memos_pdf "+
                "WHERE nom_usuario = :nom_usuario AND "+
                "id_compensacion_dia_economico = :id_compensacion_dia_economico ",
        nativeQuery = true
    )
    public Map<String, Object> listaDiaEconomicoMemosPdf
    (
        @Param("nom_usuario") String usuario, 
        @Param("id_compensacion_dia_economico") Integer idCompensacionDiaEconomico
    );

    @Query
    (
        value = " SELECT COUNT(*) AS total FROM v_usuario_profesor_restablece "+
                    "WHERE nom_usuario = :nom_usuario AND "+
                    "( LEN(correo_electronico) = 0 OR contra_restablece = 1 )",
        nativeQuery = true
    )
    public Integer getAccesoAndContrasena(@Param("nom_usuario") String usuario);

    @Query
    (
        value = "SELECT * FROM ( "+
                    "SELECT *, ROW_NUMBER() "+
                    "OVER (ORDER BY numero_serie DESC) as row_num "+
                    "FROM v_lista_datos_incidencias_memos "+ 
                    "WHERE (:fecha_incidencia IS NULL OR fecha_incidencia = :fecha_incidencia) "+
                    "AND (:fecha_registro IS NULL OR fecha_registro = :fecha_registro) "+
                    "AND (:busqueda IS NULL OR (nom_usuario LIKE '%' + :busqueda + '%' "+ 
                        "OR nombre LIKE '%' + :busqueda + '%' "+ 
                        "OR serie_memos LIKE '%' + :busqueda + '%' "+  
                        "OR tipo LIKE '%' + :busqueda + '%')) "+
                ") AS sub "+
                "WHERE (row_num > ( (:paginacion - 1) * 100) + 1 ) "+
                "AND (row_num <= (:paginacion * 100))",
        nativeQuery = true
    )
    public List<Map<String, Object>> getListaDatosIncidenciasMemos
    (
        @Param("fecha_incidencia") String fechaIni,
        @Param("fecha_registro") String fechaFin,
        @Param("busqueda") String busqueda,
        @Param("paginacion") Integer paginacion
    );
    @Query
    (
        value = "SELECT * FROM ( "+
                    "SELECT *, ROW_NUMBER() "+
                    "OVER (ORDER BY numero_serie DESC) as row_num "+
                    "FROM v_lista_datos_dia_economico_memos "+ 
                    "WHERE (:fecha_ini_compensacion IS NULL OR fecha_ini_compensacion = :fecha_ini_compensacion) "+
                    "AND (:fecha_fin_compensacion IS NULL OR fecha_fin_compensacion = :fecha_fin_compensacion) "+
                    "AND (:fecha_registro IS NULL OR fecha_registro = :fecha_registro) "+
                    "AND (:busqueda IS NULL OR (nom_usuario LIKE '%' + :busqueda + '%' "+ 
                        "OR nombre LIKE '%' + :busqueda + '%' "+ 
                        "OR correo_electronico LIKE '%' + :busqueda + '%' "+ 
                        "OR serie_memos LIKE '%' + :busqueda + '%' "+  
                        "OR tipo LIKE '%' + :busqueda + '%')) "+
                ") AS sub "+
                "WHERE (row_num > ( (:paginacion - 1) * 100) + 1 ) "+
                "AND (row_num <= (:paginacion * 100))",
        nativeQuery = true
    )
    public List<Map<String, Object>> getListaDatosEconomicoMemos
    (
        @Param("fecha_ini_compensacion") String fechaIniCompensacion,
        @Param("fecha_fin_compensacion") String fechaFinCompensacion,
        @Param("fecha_registro") String fechaRegistro,
        @Param("busqueda") String busqueda,
        @Param("paginacion") Integer paginacion
    );

    @Query
    (
        value = "SELECT TOP 1 incremento FROM incremento_id_memos",
        nativeQuery = true
    )
    public Map<String, Object> getContadorMemos();

    @Query
    (
        value = "SELECT TOP 1 fecha_inicio, fecha_fin FROM quincena_activa",
        nativeQuery = true
    )
    public Map<String, Object> getQuicenaActiva();

    @Query
    (
        value = "UPDATE incremento_id_memos "+
                "SET incremento = :contador "+
                "WHERE id_incremento_id_memos > 0",
        nativeQuery = true
    )
    @Modifying
    public void setContadorMemos
    (
        @Param("contador") int contador
    );

    @Query
    (
        value = "UPDATE quincena_activa "+
                "SET fecha_inicio = :fecha_inicio, "+
                "fecha_fin = :fecha_fin "+
                "WHERE id_quincena > 0",
        nativeQuery = true
    )
    @Modifying
    public void setQuicena
    (
        @Param("fecha_inicio") String fecha_inicio,
        @Param("fecha_fin") String fecha_fin
    );
} 
