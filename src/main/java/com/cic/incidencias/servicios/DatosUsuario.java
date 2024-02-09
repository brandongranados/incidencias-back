package com.cic.incidencias.servicios;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cic.incidencias.datos.RepoDatUsuario;

@Service
public class DatosUsuario {
    @Autowired
    private RepoDatUsuario datos;

    public ResponseEntity datosPorUusario(String usuario)
    {
        List<Map<String, Object>> datosUsuario = datos.datosUusario(usuario);
        HashMap<String, Object> resp = new HashMap<String, Object>();
        HashMap<String, Object> repDat[] = null;

        if( datosUsuario.size() == 0 )
        {
            resp.put("msm", "contactar a sistemas no hay datos del profesor registrado");
            return ResponseEntity.badRequest().body(resp);
        }

        repDat = new HashMap[datosUsuario.size()];
        for( int i=0; i<datosUsuario.size(); i++ )
        {
            repDat[i] = new HashMap<String, Object>();
            repDat[i].put("nombreProf", datosUsuario.get(i).get("nombre"));
            repDat[i].put("nombreDia", datosUsuario.get(i).get("nombre_dia"));
            repDat[i].put("tarjetaCic", datosUsuario.get(i).get("terjeta_cic"));
            repDat[i].put("basificado", datosUsuario.get(i).get("basificado"));
            repDat[i].put("horaEntrada", datosUsuario.get(i).get("hora_ini"));
            repDat[i].put("horaSalida", datosUsuario.get(i).get("hora_fin"));
            repDat[i].put("correo", datosUsuario.get(i).get("correo_electronico"));
        }

        resp.put("msm", "ok");
        resp.put("respuesta", repDat);
        
        return ResponseEntity.ok(resp);
    } 
}
