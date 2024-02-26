package com.cic.incidencias.servicios;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cic.incidencias.datos.RepoDatUsuario;
import com.cic.incidencias.datosAjax.DatosPdfMemos;
import com.cic.incidencias.datosAjax.DatosPdfMemosEco;
import com.cic.incidencias.datos.RepoDatUsuario;

@Service
public class Memos {
    @Autowired
    private RepoDatUsuario datos;
    
    public ResponseEntity getMemosInc(int tipo, DatosPdfMemos datos)
    {
        try {
            List<Map<String, Object>> inc = this.getMemosBase64Incidencias(datos);

            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(inc);
        } catch (Exception e) {
                return ResponseEntity.badRequest().build();
        }

    }

    public ResponseEntity getMemosEco(int tipo, DatosPdfMemosEco datos)
    {
        try {
            List<Map<String, Object>> inc = this.getMemosBase64Economicos(datos);

            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(inc);
        } catch (Exception e) {
                return ResponseEntity.badRequest().build();
        }
    }

    private List<Map<String, Object>> getMemosBase64Incidencias(DatosPdfMemos memos) throws Exception
    {
        List<Map<String, Object>> salida = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> res = datos.getListaDatosIncidenciasMemos
        (
            memos.getFechaIncidencia(), 
            memos.getFechaResgistro(), 
            memos.getBusqueda(), 
            Integer.parseInt(memos.getPaginacion())
        );

        for( Map<String, Object> doc : res )
        {
            Map<String,Object> docSal = new HashMap<>();
            String arch = this.memoBase64((String)doc.get("ruta_doc"));

            docSal.put("nom_usuario", doc.get("nom_usuario"));
            docSal.put("nombre", doc.get("nombre"));
            docSal.put("fecha_incidencia", doc.get("fecha_incidencia"));
            docSal.put("hora_ini_incidencia", doc.get("hora_ini_incidencia"));
            docSal.put("hora_fin_incidencia", doc.get("hora_fin_incidencia"));
            docSal.put("observaciones", doc.get("observaciones"));
            docSal.put("fecha_registro", doc.get("fecha_registro"));
            docSal.put("numero_serie", doc.get("numero_serie"));
            docSal.put("serie_memos", doc.get("serie_memos"));
            docSal.put("ruta_doc", arch);
            docSal.put("tipo", doc.get("tipo"));
            docSal.put("correo_electronico", doc.get("correo_electronico"));
            docSal.put("tipo_num", doc.get("tipo_num"));

            salida.add(docSal);
        }

        return salida;
    }

    private List<Map<String, Object>> getMemosBase64Economicos(DatosPdfMemosEco memos) throws Exception
    {
        List<Map<String, Object>> salida = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> res = datos.getListaDatosEconomicoMemos
        (
            memos.getFechaIniCompensacion(), 
            memos.getFechaFinCompensacion(), 
            memos.getFechaRegistro(),
            memos.getBusqueda(),
            Integer.parseInt(memos.getPaginacion())
        );

        for( Map<String, Object> doc : res )
        {
            Map<String,Object> docSal = new HashMap<>();
            String arch = this.memoBase64((String)doc.get("ruta_doc"));

            docSal.put("nom_usuario", doc.get("nom_usuario"));
            docSal.put("nombre", doc.get("nombre"));
            docSal.put("correo_electronico", doc.get("correo_electronico"));
            docSal.put("fecha_ini_compensacion", doc.get("fecha_ini_compensacion"));
            docSal.put("fecha_fin_compensacion", doc.get("fecha_fin_compensacion"));
            docSal.put("numero_serie", doc.get("numero_serie"));
            docSal.put("serie_memos", doc.get("serie_memos"));
            docSal.put("ruta_doc", arch);
            docSal.put("fecha_registro", doc.get("fecha_registro"));
            docSal.put("tipo", doc.get("tipo"));
            docSal.put("tipo_num", doc.get("tipo_num"));

            salida.add(docSal);
        }

        return salida;
    }

    private String memoBase64(String ruta)throws Exception
    {
        String crudoBase64 = "";
        DataInputStream ent = null;

        ruta = new String(Base64.getDecoder().decode(ruta), StandardCharsets.UTF_8);

        try {
            ent = new DataInputStream(new FileInputStream(ruta));

            byte crudo[] = new byte[ent.available()];
            ent.read(crudo);
            crudoBase64 = Base64.getEncoder().encodeToString(crudo);

            ent.close();
        } catch (Exception e) {
            crudoBase64 = "";
        }
        finally{
            try {
                ent.close();
            } catch (Exception e) {}
        }

        return crudoBase64;
    }
}
