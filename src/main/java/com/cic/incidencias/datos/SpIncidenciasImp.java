package com.cic.incidencias.datos;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpIncidenciasImp extends JpaRepository<SpIncidencias, Long>{
    //PROCEDIMIENTOS DEDICADOS A REPOSICION DE HORARIO
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

    //PROCEDIMIENTOS DEDICADOS A CORRIMINTO DE HORARIO
    @Procedure( name = "SpIncidencias.spValidaIncidenciaCorrimiento" )
    public Map<String, Object> SpValidaIncidenciaCorrimiento
    (
        @Param("usuario") String usuario,
        @Param("fecha") String fecha,
        @Param("hora_ini") String horaIni,
        @Param("hora_fin") String horaFin
    );

    //PROCEDIMIENTOS DEDICADOS A DIAS ESCONOMICOS
    @Procedure( name = "SpIncidencias.spValidaIncidenciaDiaEconomico" )
    public Map<String, Object> SpValidaIncDiaEconomico
    (
        @Param("usuario") String usuario,
        @Param("fecha_ini") String fecha,
        @Param("fecha_fin") String horaIni
    );

    //PROCEDIMIENTOS DEDICADOS CREACION DE MEMOS
    @Procedure( name = "SpIncidencias.spIngresaDatosMemo" )
    public Map<String, Object> SpIngresaDatosMemo
    (
        @Param("id_prof_incidencia") Integer idProfIncidencia,
        @Param("id_compensacion_dia_economico") Integer idCompensacionDiaEconomico,
        @Param("ano_creacion_memo") String anoCreacionMemo,
        @Param("ruta_doc") String rutaDoc
    );

    //PROCEDIMIENTOS DEDICADOS A CARGA DE PROFESORES
    @Procedure( procedureName = "sp_agrega_datos_profesor" )
    public Integer SpAgregaDatosProfesor
    (
        @Param("tarjeta") String tarjeta,
        @Param("nombre") String nombre,
        @Param("ape_paterno") String apePaterno,
        @Param("ape_materno") String apeMaterno,
        @Param("tipo") Integer tipo
    );

    @Procedure( procedureName = "sp_cargar_dias_horas_profesor" )
    public Integer SpCargarDiasHorasProfesor
    (
        @Param("dia") String dia,
        @Param("hora_ini") String horaIni,
        @Param("hora_fin") String horaFin
    );

    @Procedure( procedureName = "sp_vincular_prof_horario_lab" )
    public Integer SpVincularProfHorarioLab
    (
        @Param("tarjeta") String tarjeta,
        @Param("dia") String dia,
        @Param("hora_ini") String horaIni,
        @Param("hora_fin") String horaFin
    );

    //PROCEDIMIENTOS DEDICADOS A RESTABPECER DATOS OBLIGATORIOS
    @Procedure( procedureName = "sp_agregar_correo_contra" )
    public Integer SpAgregarCorreoContra
    (
        @Param("usuario") String usuario,
        @Param("correo") String correo,
        @Param("contra") String contra
    );
}
