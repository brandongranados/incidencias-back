package com.cic.incidencias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cic.incidencias.datosAjax.DatDiaEconomico;
import com.cic.incidencias.datosAjax.DatIncCorrimiento;
import com.cic.incidencias.datosAjax.DatIncidencia;
import com.cic.incidencias.datosAjax.Login;
import com.cic.incidencias.servicios.DatosUsuario;
import com.cic.incidencias.servicios.EnviarCorreo;
import com.cic.incidencias.servicios.Incidencias;


@RestController
@CrossOrigin(originPatterns = "*")
public class Paginas {
    @Autowired
    private DatosUsuario datosUusarioSesion;
    @Autowired
    private Incidencias reponerHorario;
    @Autowired
    private EnviarCorreo correo;
   
    @PostMapping("/sesionUsuario")
    public ResponseEntity datosUsuario(@RequestBody Login usuarioLogeado)
    {
        String parametro = usuarioLogeado.getUsuario().length() > 0 ? usuarioLogeado.getUsuario() : "";
        return datosUusarioSesion.datosPorUusario(parametro);
    }

    @PostMapping("/reposicion")
    public ResponseEntity reponerHoras(@RequestBody DatIncidencia incidencia)
    {
        ResponseEntity respuesta = reponerHorario.getRespuestaIncidenciaReposicion(incidencia);

        if( respuesta.getStatusCode() == HttpStatusCode.valueOf(200) )
            correo.enviarCorreo
            (
                incidencia.getUsuario(), 
                "la incidencia de reposicion de horario", 
                3
            );

        return respuesta;
    }

    @PostMapping("/corrimiento")
    public ResponseEntity recorrerHoras(@RequestBody DatIncCorrimiento incidencia)
    {
        ResponseEntity respuesta = reponerHorario.getRespuestaIncidenciaCorrimiento(incidencia);

        if( respuesta.getStatusCode() == HttpStatusCode.valueOf(200) )
            correo.enviarCorreo
            (
                incidencia.getUsuario(), 
                "la incidencia de corrimiento de horario", 
                3
            );
        return respuesta;
    }

    @PostMapping("/diaEconomico")
    public ResponseEntity diaEconomico(@RequestBody DatDiaEconomico incidencia)
    {
        ResponseEntity respuesta = reponerHorario.getRespuestaDiaEconomico(incidencia);

        if( respuesta.getStatusCode() == HttpStatusCode.valueOf(200) )
            correo.enviarCorreo
            (
                incidencia.getUsuario(), 
                "la incidencia de dia economico", 
                3
            );

        return respuesta;
    }

}
