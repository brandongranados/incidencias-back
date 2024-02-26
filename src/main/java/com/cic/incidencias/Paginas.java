package com.cic.incidencias;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cic.incidencias.datos.DatosObli;
import com.cic.incidencias.datos.QuicenaActiva;
import com.cic.incidencias.datos.RepoDatUsuario;
import com.cic.incidencias.datos.SpIncidenciasImp;
import com.cic.incidencias.datos.Usuario;
import com.cic.incidencias.datosAjax.Contador;
import com.cic.incidencias.datosAjax.DatDiaEconomico;
import com.cic.incidencias.datosAjax.DatIncCorrimiento;
import com.cic.incidencias.datosAjax.DatIncidencia;
import com.cic.incidencias.datosAjax.DatosExcel;
import com.cic.incidencias.datosAjax.DatosPdfMemos;
import com.cic.incidencias.datosAjax.DatosPdfMemosEco;
import com.cic.incidencias.datosAjax.Login;
import com.cic.incidencias.servicios.DatosUsuario;
import com.cic.incidencias.servicios.EnviarCorreo;
import com.cic.incidencias.servicios.Excel;
import com.cic.incidencias.servicios.Incidencias;
import com.cic.incidencias.servicios.Memos;
import com.cic.incidencias.servicios.ServicioParametros;


@RestController
@CrossOrigin(originPatterns = "*")
public class Paginas {
    @Autowired
    private DatosUsuario datosUusarioSesion;
    @Autowired
    private Incidencias reponerHorario;
    @Autowired
    private EnviarCorreo correo;
    @Autowired
    private Excel crearExcel;
    @Autowired
    private RepoDatUsuario obtener;
    @Autowired
    private SpIncidenciasImp sp;
    @Autowired
    private ServicioParametros param;
    @Autowired
    private Memos memo;
   
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

    @PostMapping("/excelProf")
    public ResponseEntity cargarExcelProfesores(@RequestBody DatosExcel datos) 
    {
        return crearExcel.cargarProfesores(datos);
    }
    
    @PostMapping("/getObligatorios")
    public ResponseEntity revisarObligatorios(@RequestBody Usuario datos)
    {
        Integer  res = obtener.getAccesoAndContrasena(datos.getUsuario());
        Map<String, Object> resp = new HashMap<String, Object>();
        resp.put("cantidad", res);
        return ResponseEntity.ok().body(resp);
    }

    @PostMapping("/setObligatorios")
    @Transactional(readOnly = false)
    public ResponseEntity asignarObligatorios(@RequestBody DatosObli datos)
    {
        try {
            Integer bool = sp.SpAgregarCorreoContra
            (
                datos.getUsuario(), 
                datos.getCorreo(), 
                datos.getContra()
            );

            if( bool != 1 )
                throw new Exception();
                
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/getParametros")
    public ResponseEntity getParametros()
    {
        return param.getParametros();
    }

    @PostMapping("/setContador")
    public ResponseEntity setContador(@RequestBody Contador cont)
    {
        return param.setContadorMemos(cont);
    }

    @PostMapping("/setQuincena")
    public ResponseEntity setQuincena(@RequestBody QuicenaActiva quicena)
    {
        return param.setQuincena(quicena);
    }

    @PostMapping("/getMemosInc")
    public ResponseEntity getMemos(@RequestBody DatosPdfMemos datos)
    {
        return memo.getMemosInc(1, datos);
    }

    @PostMapping("/getMemosEco")
    public ResponseEntity getMemosEconomicos(@RequestBody DatosPdfMemosEco datos)
    {
        return memo.getMemosEco(1, datos);
    }
}
