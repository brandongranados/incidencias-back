package com.cic.incidencias.datos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;

@Entity

//PARA USUARIOS ADMINISTRADORES

@NamedStoredProcedureQuery
(
    name = "SpIncidencias.spIncidenciaReposicionSinRestricciones", 
    procedureName = "sp_incidencia_reposicion_sin_restricciones",
    parameters = {
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "tarjeta_cic", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "fecha", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "hora_ini", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "hora_fin", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "observaciones", 
            type = Integer.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.OUT, 
            name = "inci_capturada", 
            type = Integer.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.OUT, 
            name = "bool", 
            type = Integer.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.OUT, 
            name = "nombre_prof", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.OUT, 
            name = "usuario", 
            type = String.class
            )
    }
)
@NamedStoredProcedureQuery
(
    name = "SpIncidencias.spIncidenciaCorrimientoSinRestricciones", 
    procedureName = "sp_incidencia_corrimiento_sin_restricciones",
    parameters = {
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "tarjeta", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "fecha", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "hora_ini", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "hora_fin", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.OUT, 
            name = "bool_salida", 
            type = Integer.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.OUT, 
            name = "id_salida", 
            type = Integer.class
            ),
        
        @StoredProcedureParameter(
            mode = ParameterMode.OUT, 
            name = "nombre_prof", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.OUT, 
            name = "usuario", 
            type = String.class
            )
    }
)
@NamedStoredProcedureQuery
(
    name = "SpIncidencias.spIncidenciaDiaEconomicoSinRestricciones", 
    procedureName = "sp_incidencia_dia_economico_sin_restricciones",
    parameters = {
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "tarjeta", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "fecha_ini", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "fecha_fin", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "fecha", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.OUT, 
            name = "bool_salida", 
            type = Integer.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.OUT, 
            name = "id_salida", 
            type = Integer.class
            ),
        
        @StoredProcedureParameter(
            mode = ParameterMode.OUT, 
            name = "nombre_prof", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.OUT, 
            name = "usuario", 
            type = String.class
            )
    }
)
//jajajjajajajjajaj
@NamedStoredProcedureQuery
(
    name = "SpIncidencias.spValidaIncidenciaReposicion", 
    procedureName = "sp_valida_incidencia_reposicion", 
    parameters = {
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "usuario", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "fecha", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "hora_ini", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "hora_fin", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "observaciones", 
            type = Integer.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.OUT, 
            name = "bool_salida", 
            type = Integer.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.OUT, 
            name = "inci_capturada", 
            type = Integer.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.OUT, 
            name = "nombre_prof", 
            type = String.class
            )
    }
)
@NamedStoredProcedureQuery
(
    name = "SpIncidencias.spValidaCompensacionReposicion", 
    procedureName = "sp_valida_compensacion_reposicion", 
    parameters = {
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "usuario", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "fecha", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "hora_ini", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "hora_fin", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "id_prof_inc_repo", 
            type = Integer.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.OUT, 
            name = "bool_salida", 
            type = Integer.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.OUT, 
            name = "minutos_cubre", 
            type = Integer.class
            )
    }
)
@NamedStoredProcedureQuery
(
    name = "SpIncidencias.spValidaIncidenciaCorrimiento", 
    procedureName = "sp_valida_incidencia_corrimiento", 
    parameters = {
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "usuario", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "fecha", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "hora_ini", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "hora_fin", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.OUT, 
            name = "bool_salida", 
            type = Integer.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.OUT, 
            name = "id_salida", 
            type = Integer.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.OUT, 
            name = "nombre_prof", 
            type = String.class
            )
    }
)
@NamedStoredProcedureQuery
(
    name = "SpIncidencias.spValidaIncidenciaDiaEconomico", 
    procedureName = "sp_valida_incidencia_dia_economico", 
    parameters = {
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "usuario", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "fecha_ini", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "fecha_fin", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "fecha", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.OUT, 
            name = "bool_salida", 
            type = Integer.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.OUT, 
            name = "id_salida", 
            type = Integer.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.OUT, 
            name = "nombre_prof", 
            type = String.class
            )
    }
)
@NamedStoredProcedureQuery
(
    name = "SpIncidencias.spIngresaDatosMemo", 
    procedureName = "sp_ingresa_datos_memo", 
    parameters = {
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "id_prof_incidencia", 
            type = Integer.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "id_compensacion_dia_economico", 
            type = Integer.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "ano_creacion_memo", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.IN, 
            name = "ruta_doc", 
            type = String.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.OUT, 
            name = "bool_salida", 
            type = Integer.class
            ),
        @StoredProcedureParameter(
            mode = ParameterMode.OUT, 
            name = "serie_memos", 
            type = String.class
            )
    }
)
public class SpIncidencias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
