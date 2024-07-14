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
        @Param("fecha_ini") String fechaIni,
        @Param("fecha_fin") String fechaFin,
        @Param("fecha") String fecha
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

    //PROCEDIMIENTOS PARA ADMINISTRADORES
    @Procedure(name = "SpIncidencias.spIncidenciaReposicionSinRestricciones")
    public Map<String, Object> spIncidenciaReposicionSinRestricciones
    (
        @Param("tarjeta_cic") String usuario,
        @Param("fecha") String fecha,
        @Param("hora_ini") String hora_ini,
        @Param("hora_fin") String hora_fin,
        @Param("observaciones") Integer observaciones
    );

    @Procedure(procedureName = "sp_valida_compensacion_reposicion_sin_restricciones")
    public Integer spValidaCompensacionReposicionSinRestricciones
    (
        @Param("tarjeta_cic") String tarjetaCic, 
        @Param("fecha") String fecha,
        @Param("hora_ini") String horaIni,
        @Param("hora_fin") String horaFin,
        @Param("id_prof_inc_repo") Integer idIncidencia,
        @Param("minutos_cubre") Integer minutosCubre
    );

    @Procedure(name = "SpIncidencias.spIncidenciaCorrimientoSinRestricciones")
    public Map<String, Object> spIncidenciaCorrimientoSinRestricciones
    (
        @Param("tarjeta") String tarjeta, 
        @Param("fecha") String fecha,
        @Param("hora_ini") String horaIni,
        @Param("hora_fin") String horaFin
    );

    @Procedure(name = "SpIncidencias.spIncidenciaDiaEconomicoSinRestricciones")
    public Map<String, Object> spIncidenciaDiaEconomicoSinRestricciones
    (
        @Param("tarjeta") String tarjeta, 
        @Param("fecha_ini") String fechaIni,
        @Param("fecha_fin") String fechaFin,
        @Param("fecha") String fecha
    );

    /* ACTUALIZACIONES DESDE ADMINISTRADORES */

    @Procedure(procedureName = "sp_actualiza_corrimiento_sin_restricciones")
    public Integer spActualizaCorrimientoSinRestricciones
    (
        @Param("fecha") String fecha, 
        @Param("hora_ini") String horaIni,
        @Param("hora_fin") String horaFin,
        @Param("id") Integer id
    );

    @Procedure(procedureName = "sp_actualiza_reposicion_sin_restricciones")
    public Integer spActualizaReposicionSinRestricciones
    (
        @Param("fecha_inc") String fechaInc, 
        @Param("hora_ini_inc") String horaIniInc,
        @Param("hora_fin_inc") String horaFinInc,
        @Param("observaciones") String observaciones,
        @Param("id") Integer id
    );

    @Procedure(procedureName = "sp_actualiza_valida_compensacion_reposicion")
    public Integer spActualizaValidaCompensacionReposicion
    (
        @Param("fecha") String fecha, 
        @Param("hora_ini") String horaIni,
        @Param("hora_fin") String horaFin,
        @Param("minutos_cubre") Integer minutosCubre,
        @Param("id") Integer id
    );

    @Procedure(procedureName = "sp_borra_compensaciones_reposicion")
    public Integer spBorraCompensacionesReposicion
    (
        @Param("id") Integer id
    );
}
