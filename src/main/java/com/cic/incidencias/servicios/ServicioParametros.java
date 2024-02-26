package com.cic.incidencias.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cic.incidencias.datos.QuicenaActiva;
import com.cic.incidencias.datos.RepoDatUsuario;
import com.cic.incidencias.datosAjax.Contador;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class ServicioParametros {
    @Autowired
    private RepoDatUsuario datos;

    public ResponseEntity getParametros()
    {
        Map<String, Object> resp = new HashMap<String, Object>();
        Map<String, Object> res = datos.getQuicenaActiva();

        resp.put("incremento", datos.getContadorMemos().get("incremento"));
        resp.put("fecha_inicio", res.get("fecha_inicio"));
        resp.put("fecha_fin", res.get("fecha_fin"));

        return ResponseEntity.ok(resp);
    }

    @Transactional( readOnly = false )
    public ResponseEntity setContadorMemos(Contador cont)
    {
        try {
            datos.setContadorMemos(cont.getContador());
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @Transactional( readOnly = false )
    public ResponseEntity setQuincena(QuicenaActiva quicena)
    {
        try {
            datos.setQuicena
            (
                quicena.getInicio(), 
                quicena.getFin()
            );
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
