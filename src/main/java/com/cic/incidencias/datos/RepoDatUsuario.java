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
        value = "SELECT TOP 1 * FROM v_usuario_dias_horas WHERE "+
                "CAST(terjeta_cic AS VARCHAR) = :terjeta_cic ", 
        nativeQuery = true
    )
    public Map<String, Object> datosUsusarioTarjeta
    (
        @Param("terjeta_cic") String tarjeta
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
        value = "SELECT id_prof_incidencia, nombre,\n" + //
            "            fecha_incidencia,\n" + //
            "            hora_ini_incidencia,\n" + //
            "            hora_fin_incidencia,\n" + //
            "            fecha_registro,\n" + //
            "            serie_memos,\n" + //
            "            correo_electronico,\n" + //
            "            nom_usuario,\n" + //
            "            tarjeta,\n" + //
            "            autorizada_admin,\n" + //
            "            tipo,\n" + //
            "            ruta_doc," + //
            "            numero_serie,\n" + //
            "            tipo_num,\n" + //
            "            observaciones" + //
            "        FROM v_lista_datos_incidencias_memos\n" + //
            "    WHERE ( :fecha_ini IS NULL OR :fecha_fin IS NULL OR \n" + //
            "                 ( fecha_incidencia BETWEEN :fecha_ini AND :fecha_fin ) ) AND \n" + //
            "            (\n" + //
            "                :busqueda IS NULL OR\n" + //
            "                (\n" + //
            "                    nombre LIKE ('%' + :busqueda + '%') OR\n" + //
            "                    correo_electronico LIKE ('%' + :busqueda + '%') OR\n" + //
            "                    serie_memos LIKE ('%' + :busqueda + '%') OR\n" + //
            "                    tipo LIKE ('%' + :busqueda + '%')\n" + //
            "                )\n" + //
            "            )\n" + //
            "    ORDER BY numero_serie DESC\n" + //
            "    OFFSET ( ( :paginacion -1) *10 ) \n" + //
            "    ROWS FETCH NEXT ( :paginacion *10) ROWS ONLY",
        nativeQuery = true
    )
    public List<Map<String, Object>> getListaDatosIncidenciasMemos
    (
        @Param("fecha_ini") String fechaIni,
        @Param("fecha_fin") String fechaFin,
        @Param("busqueda") String busqueda,
        @Param("paginacion") Integer paginacion
    );

    @Query
    (
        value = "SELECT COUNT(*) AS cant\n" + //
        "        FROM v_lista_datos_incidencias_memos\n" + //
        "    WHERE ( :fecha_ini IS NULL OR :fecha_fin IS NULL OR \n" + //
        "                 ( fecha_incidencia BETWEEN :fecha_ini AND :fecha_fin ) ) AND \n" + //
        "            (\n" + //
        "                :busqueda IS NULL OR\n" + //
        "                (\n" + //
        "                    nombre LIKE ('%' + :busqueda + '%') OR\n" + //
        "                    correo_electronico LIKE ('%' + :busqueda + '%') OR\n" + //
        "                    serie_memos LIKE ('%' + :busqueda + '%') OR\n" + //
        "                    tipo LIKE ('%' + :busqueda + '%')\n" + //
        "                )\n" + //
        "            )",
        nativeQuery = true
    )
    public Map<String, Object> getCantListaDatosIncidenciasMemos
    (
        @Param("fecha_ini") String fechaIni,
        @Param("fecha_fin") String fechaFin,
        @Param("busqueda") String busqueda
    );

    @Query(
        value = "SELECT * FROM v_lista_datos_incidencias_memos "+
                " WHERE id_prof_incidencia = :id ",
        nativeQuery = true
    )
    public Map<String, Object> getBuscaIncidenciasId
    (
        @Param("id") Integer id
    );
    

    @Query(
        value = "SELECT fecha_compensacion, hora_ini_compensacion, hora_fin_compensacion, ( cant_tiempo_cubre / 60 ) AS horas_cubre "+
                " FROM v_profesor_usuario_reposicion "+
                " WHERE id_prof_incidencia = :id_prof_incidencia",
        nativeQuery = true
    )
    public List<Map<String, Object>> getMemosRepComComplemento
    (
        @Param("id_prof_incidencia") String idProfIncidencia
    );
    @Query
    (
        value = "SELECT *\n" + //
                "    FROM v_lista_datos_dia_economico_memos \n" + //
                "    WHERE ( :fecha_ini IS NULL OR :fecha_fin IS NULL OR \n" + //
                "    ( fecha_pertenece BETWEEN :fecha_ini AND :fecha_fin )) AND\n" + //
                "    (:busqueda IS NULL OR ( nombre LIKE '%' + :busqueda + '%'  \n" + //
                "        OR correo_electronico LIKE '%' + :busqueda + '%'  \n" + //
                "        OR serie_memos LIKE '%' + :busqueda + '%'   \n" + //
                "        OR tipo LIKE '%' + :busqueda + '%')) \n" + //
                "    ORDER BY numero_serie DESC\n" + //
                "    OFFSET ( ( :paginacion -1) *10 ) \n" + //
                "    ROWS FETCH NEXT ( :paginacion * 10 ) ROWS ONLY",
        nativeQuery = true
    )
    public List<Map<String, Object>> getListaDatosEconomicoMemos
    (
        @Param("fecha_ini") String fechaIni,
        @Param("fecha_fin") String fechaFin,
        @Param("busqueda") String busqueda,
        @Param("paginacion") Integer paginacion
    );

    @Query
    (
        value = "SELECT COUNT(*) AS cant \n" + //
                "    FROM v_lista_datos_dia_economico_memos \n" + //
                "    WHERE ( :fecha_ini IS NULL OR :fecha_fin IS NULL OR \n" + //
                "    ( fecha_pertenece BETWEEN :fecha_ini AND :fecha_fin )) AND\n" + //
                "    (:busqueda IS NULL OR ( nombre LIKE '%' + :busqueda + '%'  \n" + //
                "        OR correo_electronico LIKE '%' + :busqueda + '%'  \n" + //
                "        OR serie_memos LIKE '%' + :busqueda + '%'   \n" + //
                "        OR tipo LIKE '%' + :busqueda + '%'))",
        nativeQuery = true
    )
    public Map<String, Object> getCantListaDatosEconomicoMemos
    (
        @Param("fecha_ini") String fechaIni,
        @Param("fecha_fin") String fechaFin,
        @Param("busqueda") String busqueda
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
