package com.cic.incidencias.datos;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpIncidenciasImp extends JpaRepository<SpIncidencias, Long>{
    
    @Procedure(name = "SpIncidencias.spValidaIncidenciaReposicion")
    public Map<String, Object> spValidacionIncidenciaReposicion
    (
        @Param("usuario") String usuario,
        @Param("fecha") String fecha,
        @Param("hora_ini") String hora_ini,
        @Param("hora_fin") String hora_fin,
        @Param("observaciones") Integer observaciones
    );
    
    @Procedure(name = "SpIncidencias.spValidaCompensacionReposicion")
    public Map<String, Object> SpValidaCompensacionReposicion
    (
        @Param("usuario") String usuario, 
        @Param("fecha") String fecha,
        @Param("hora_ini") String horaIni,
        @Param("hora_fin") String horaFin,
        @Param("id_prof_inc_repo") Integer idIncidencia 
    );

    @Procedure( procedureName = "sp_valida_horas_cubiertas_reposicion" )                                                
    public Integer SpValidaHorasCubiertasReposicion
    (
        @Param("usuario") String usuario,
        @Param("inci_capturada") Integer idIncidencia,
        @Param("observaciones") Integer obs
    );

    @Procedure( name = "SpIncidencias.spValidaIncidenciaCorrimiento" )
    public Map<String, Object> SpValidaIncidenciaCorrimiento
    (
        @Param("usuario") String usuario,
        @Param("fecha") String fecha,
        @Param("hora_ini") String horaIni,
        @Param("hora_fin") String horaFin
    );

    @Procedure( name = "SpIncidencias.spValidaIncidenciaDiaEconomico" )
    public Map<String, Object> SpValidaIncDiaEconomico
    (
        @Param("usuario") String usuario,
        @Param("fecha_ini") String fecha,
        @Param("fecha_fin") String horaIni
    );

    @Procedure( name = "SpIncidencias.spIngresaDatosMemo" )
    public Map<String, Object> SpIngresaDatosMemo
    (
        @Param("id_prof_incidencia") Integer idProfIncidencia,
        @Param("id_compensacion_dia_economico") Integer idCompensacionDiaEconomico,
        @Param("ano_creacion_memo") String anoCreacionMemo,
        @Param("ruta_doc") String rutaDoc
    );
}
