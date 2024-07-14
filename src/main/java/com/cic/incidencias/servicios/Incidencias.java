package com.cic.incidencias.servicios;

import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cic.incidencias.datos.RepoDatUsuario;
import com.cic.incidencias.datos.SpIncidenciasImp;
import com.cic.incidencias.datos.vUsuarioDiasHoras;
import com.cic.incidencias.datosAjax.CorrimientoSinRest;
import com.cic.incidencias.datosAjax.CorrimientoUpdate;
import com.cic.incidencias.datosAjax.DatDiaEconomico;
import com.cic.incidencias.datosAjax.DatIncCorrimiento;
import com.cic.incidencias.datosAjax.DatIncidencia;
import com.cic.incidencias.datosAjax.DatReposicion;
import com.cic.incidencias.datosAjax.DatReposicionUpdate;
import com.cic.incidencias.datosAjax.EconomicoSinRest;
import com.cic.incidencias.datosAjax.ReposicionSinRest;
import com.cic.incidencias.datosAjax.ReposicionUpdate;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.cic.incidencias.errores.Error;
import com.cic.incidencias.reportes.DatIncCorrimiento2;
import com.cic.incidencias.reportes.DatIncReposicion;
import com.google.gson.Gson;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
@Service
public class Incidencias {
    @Autowired
    private RepoDatUsuario datos;
    @Autowired
    private SpIncidenciasImp sp;
    @Autowired
    private Cryptografia crypto;
    @Autowired
    private Reporte reporte;
    @Autowired
    private Fechas fecha;

    @Value("${ruta.exportacion.pdf}")
    private String carpetaPDF;
    @Value("${reporte.reposicion}")
    private String reportReposicion;
    @Value("${reporte.corrimiento}")
    private String reportCorrimiento;
    @Value("${reporte.diaeconomico}")
    private String reportEconomico;
    @Value("${param.nombre.MEMO}")
    private String parametroMemo;
    @Value("${param.nombre.INCIDENCIA}")
    private String parametroListaInc;

    @Transactional(readOnly = false)
    public ResponseEntity getRespuestaDiaEconomico(DatDiaEconomico inc)
    {
        Map<String, Object> listHorMemoInc = null;
        String rutaReporte = "", nombre_prof = "";
        Error error = new Error();
        Integer respInc = 1;

        try 
        {

            Map<String, Object> res = fecha.getExtraeFechasFromIniFin
            (
                inc.getFechaIni(),
                inc.getFechaFin()
            );

            for( int i = 1; i < res.size(); i++ )
            {
                Map<String, Object> res2 = (Map<String, Object>)res.get(i+"");

                Map<String, Object> resp = sp.SpValidaIncDiaEconomico
                (
                    inc.getUsuario(), 
                    inc.getFechaIni(), 
                    inc.getFechaFin(),
                    res2.get("ano")+"-"+res2.get("mes")+"-"+res2.get("dia")
                );

                Integer diaEcoBool = (Integer)resp.get("bool_salida");
                Integer idSalida = (Integer)resp.get("id_salida");
                nombre_prof = (String) resp.get("nombre_prof");

                if( diaEcoBool != 1 )
                {
                    respInc = 0;
                    error.setCodigo(diaEcoBool);
                    throw new Exception();
                }

                listHorMemoInc = datos.listaDiaEconomicoMemosPdf(inc.getUsuario(), idSalida);
                
                Map<String, Object> parametros = new HashMap<String, Object>();

                rutaReporte = crypto.crearSHA512(String.valueOf(idSalida)+reportEconomico+fecha.getFechaHoraUTC()+i);
                rutaReporte = carpetaPDF+"MEMO_DCIC_ECONOMICO_"+nombre_prof+"_";
                rutaReporte += res2.get("ano")+"-"+res2.get("mes")+"-"+res2.get("dia")+"_"+rutaReporte+".pdf";

                Map<String, Object> respMemo = sp.SpIngresaDatosMemo
                (
                    null, 
                    idSalida, 
                    fecha.getAnoCreacion(), 
                    Base64.getEncoder().encodeToString(rutaReporte.getBytes())
                );

                diaEcoBool = (Integer)respMemo.get("bool_salida");

                if( diaEcoBool != 1 )
                {
                    respInc = 0;
                    error.setCodigo(diaEcoBool);
                    throw new Exception();
                }

                parametros.put("DiaInc", res2.get("dia"));
                parametros.put("MesInc", res2.get("mes"));
                parametros.put("AnoInc", res2.get("ano"));

                if( reporte.generaReporte(parametros, reportEconomico, rutaReporte, inc.getUsuario()) != 1 )
                {
                    respInc = 0;
                    error.setCodigo(100);
                    throw new Exception();
                }
            }
            
        } catch (Exception e) {
            HashMap<String, Object> respuesta = new HashMap<String, Object>();
            respuesta.put("msm", error.getMsmCodigo());
            respuesta.put("inc", respInc);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.badRequest().body(respuesta);
        }

        return ResponseEntity.ok().build();
    }

    @Transactional(readOnly = false)
    public ResponseEntity getRespuestaIncidenciaCorrimiento(DatIncCorrimiento inc)
    {
        Map<String, Object> parametros = new HashMap<String, Object>();
        Map<String, Object> listHorMemoInc = null;
        Collection<DatIncCorrimiento2> listaInclist = new ArrayList<DatIncCorrimiento2>();
        String rutaReporte = "", nombre_prof = "";
        Error error = new Error();
        Integer respInc = 1;

        try {
            Map<String, Object> resp = sp.SpValidaIncidenciaCorrimiento
            (
                inc.getUsuario(), 
                inc.getFechInc(), 
                inc.getHorIniInc(), 
                inc.getHorFinInc()
            );

            Integer corriBool = (Integer)resp.get("bool_salida");
            Integer idSlida = (Integer)resp.get("id_salida");
            nombre_prof = (String) resp.get("nombre_prof");

            if( corriBool != 1 )
            {
                respInc = 0;
                error.setCodigo(corriBool);
                throw new Exception();
            }

            rutaReporte = crypto.crearSHA512(String.valueOf(idSlida)+reportCorrimiento+fecha.getFechaHoraUTC());
            rutaReporte = carpetaPDF+"MEMO_DCIC_CORRIMIENTO_"+nombre_prof+"_"+rutaReporte+".pdf";

            Map<String, Object> respMemo = sp.SpIngresaDatosMemo
            (
                idSlida, 
                null, 
                fecha.getAnoCreacion(), 
                Base64.getEncoder().encodeToString(rutaReporte.getBytes())
            );

            corriBool = (Integer)respMemo.get("bool_salida");
            listHorMemoInc = datos.listaCorrimientoMemosPdf(inc.getUsuario(), idSlida);
            listaInclist.add(
                    new DatIncCorrimiento2
                    (
                        (String)listHorMemoInc.get("fecha_incidencia"),
                        (String)listHorMemoInc.get("hora_ini_incidencia"),
                        (String)listHorMemoInc.get("hora_fin_incidencia")
                    )
                );

            parametros.put(parametroListaInc, new JRBeanCollectionDataSource(listaInclist));
            parametros.put(parametroMemo, respMemo.get("serie_memos"));

            if( corriBool != 1 )
            {
                respInc = 0;
                error.setCodigo(corriBool);
                throw new Exception();
            }

            if( reporte.generaReporte(parametros, reportCorrimiento, rutaReporte, inc.getUsuario()) != 1 )
            {
                respInc = 0;
                error.setCodigo(100);
                throw new Exception();
            }
            
        } catch (Exception e) {
            HashMap<String, Object> respuesta = new HashMap<String, Object>();
            respuesta.put("msm", error.getMsmCodigo());
            respuesta.put("inc", respInc);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.badRequest().body(respuesta);
        }

        return ResponseEntity.ok().build();
    }

    @Transactional(readOnly = false)
    public ResponseEntity getRespuestaIncidenciaReposicion(DatIncidencia incidencias)
    {
        Map<String, Object> parametros = new HashMap<String, Object>();
        List<Map<String, Object>> listHorMemoInc = null;
        Collection<DatIncReposicion> listaInclist = new ArrayList<DatIncReposicion>();
        Integer repCom[] = new Integer[incidencias.getReposicion().length];
        Error error = new Error();
        String rutaReporte = "", nombre_prof = "";
        Integer respInc = 1;
        int cont = 0;

        try {
            DatReposicion reponer[] = incidencias.getReposicion();

            Map<String, Object> spIncRep = sp.spValidacionIncidenciaReposicion
            (
                incidencias.getUsuario(),
                incidencias.getFechInc(),
                incidencias.getHorIniInc(),
                incidencias.getHorFinInc(),
                Integer.parseInt(incidencias.getObs())
            );

            Integer bool = (Integer) spIncRep.get("bool_salida");
            Integer idInc = (Integer) spIncRep.get("inci_capturada");
            nombre_prof = (String) spIncRep.get("nombre_prof");

            if( bool != 1 )
            {
                respInc = 0;
                error.setCodigo(bool);
                throw new Exception();
            }

            for (DatReposicion rep : reponer) 
            {
                Map<String, Object>  respCompRep = sp.SpValidaCompensacionReposicion
                (
                    incidencias.getUsuario(),
                    rep.getFecha(),
                    rep.getHoraInicio(), 
                    rep.getHoraFin(),
                    idInc
                );

                Integer reponerBool = (Integer)respCompRep.get("bool_salida");

                if( reponerBool != 1 )
                {
                    repCom[cont] = 0;
                    error.setCodigo(reponerBool);
                    throw new Exception();
                }

                repCom[cont] = 1;
                cont ++;

            }

            Integer incidenciaBool = sp.SpValidaHorasCubiertasReposicion
            (
                incidencias.getUsuario(), 
                idInc, 
                Integer.parseInt(incidencias.getObs())
            );

            if( incidenciaBool != 1 )
            {
                respInc = 0;
                error.setCodigo(incidenciaBool);
                throw new Exception();
            }

            rutaReporte = crypto.crearSHA512(String.valueOf(idInc)+reportReposicion+fecha.getFechaHoraUTC());
            rutaReporte = carpetaPDF+"MEMO_DCIC_REPOSICION_"+nombre_prof+"_"+rutaReporte+".pdf";

            Map<String, Object> respMemo = sp.SpIngresaDatosMemo
            (
                idInc, 
                null, 
                fecha.getAnoCreacion(), 
                Base64.getEncoder().encodeToString(rutaReporte.getBytes())
            );

            bool = (Integer)respMemo.get("bool_salida");
            listHorMemoInc = datos.listaReposicionMemosPdf(incidencias.getUsuario(), idInc);

            Map<String, Object> incMemo = listHorMemoInc.get(0);
            listaInclist.add(
                    new DatIncReposicion
                    (
                        (String)incMemo.get("fecha_incidencia"),
                        ( incMemo.get("hora_ini_incidencia") == null ? "--:--" : (String)incMemo.get("hora_ini_incidencia") ),
                        ( incMemo.get("hora_fin_incidencia") == null ? "--:--" : (String)incMemo.get("hora_fin_incidencia") ),
                        String.valueOf((Integer)incMemo.get("observaciones"))
                    )
                );
            for( Map<String, Object> iterador : listHorMemoInc )
            {
                listaInclist.add(
                    new DatIncReposicion
                    (
                        (String)incMemo.get("fecha_compensacion"),
                        (String)incMemo.get("hora_ini_compensacion"),
                        (String)incMemo.get("hora_fin_compensacion"),
                        "+"+String.valueOf((Integer)incMemo.get("cant_tiempo_cubre"))
                    )
                );
            }
            parametros.put(parametroListaInc, new JRBeanCollectionDataSource(listaInclist));
            parametros.put(parametroMemo, respMemo.get("serie_memos"));

            if( bool != 1 )
            {
                respInc = 0;
                error.setCodigo(incidenciaBool);
                throw new Exception();
            }

            if( reporte.generaReporte(parametros, reportReposicion, rutaReporte, incidencias.getUsuario()) != 1 )
            {
                respInc = 0;
                error.setCodigo(100);
                throw new Exception();
            }
                
        } catch (Exception e) {
            HashMap<String, Object> respuesta = new HashMap<String, Object>();
            respuesta.put("msm", error.getMsmCodigo());
            respuesta.put("inc", respInc);
            respuesta.put("comp", repCom); 
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok().build();
    }


    //PARA USUARIOS ADMINISTRADORES
    @Transactional(readOnly = false)
    public ResponseEntity getRespuestaIncidenciaReposicionSinRestriccion(ReposicionSinRest incidencias)
    {
        Map<String, Object> parametros = new HashMap<String, Object>();
        List<Map<String, Object>> listHorMemoInc = null;
        Collection<DatIncReposicion> listaInclist = new ArrayList<DatIncReposicion>();
        Integer repCom[] = new Integer[incidencias.getReposicion().length];
        Error error = new Error();
        String rutaReporte = "", nombre_prof = "", usuario = "";
        Integer respInc = 1;
        int cont = 0;

        try {
            DatReposicionUpdate reponer[] = incidencias.getReposicion();

            Map<String, Object> spIncRep = sp.spIncidenciaReposicionSinRestricciones
            (
                incidencias.getTarjetaCic(),
                incidencias.getFechInc(),
                incidencias.getHorIniInc(),
                incidencias.getHorFinInc(),
                Integer.parseInt(incidencias.getObs())
            );

            Integer bool = (Integer) spIncRep.get("bool");
            Integer idInc = (Integer) spIncRep.get("inci_capturada");
            nombre_prof = (String) spIncRep.get("nombre_prof");
            usuario =  (String) spIncRep.get("usuario");

            if( bool != 1 )
            {
                respInc = 0;
                error.setCodigo(bool);
                throw new Exception();
            }

            for (DatReposicionUpdate rep : reponer) 
            {
                Integer reponerBool = sp.spValidaCompensacionReposicionSinRestricciones
                (
                    incidencias.getTarjetaCic(),
                    rep.getFecha(),
                    rep.getHoraInicio(), 
                    rep.getHoraFin(),
                    idInc,
                    rep.getHoraCubre()
                );

                if( reponerBool != 1 )
                {
                    repCom[cont] = 0;
                    error.setCodigo(reponerBool);
                    throw new Exception();
                }

                repCom[cont] = 1;
                cont ++;

            }

            rutaReporte = crypto.crearSHA512(String.valueOf(idInc)+reportReposicion+fecha.getFechaHoraUTC());
            rutaReporte = carpetaPDF+"MEMO_DCIC_REPOSICION_"+nombre_prof+"_"+rutaReporte+".pdf";

            Map<String, Object> respMemo = sp.SpIngresaDatosMemo
            (
                idInc, 
                null, 
                fecha.getAnoCreacion(), 
                Base64.getEncoder().encodeToString(rutaReporte.getBytes())
            );

            bool = (Integer)respMemo.get("bool_salida");
            listHorMemoInc = datos.listaReposicionMemosPdf(usuario, idInc);

            Map<String, Object> incMemo = listHorMemoInc.get(0);
            listaInclist.add(
                    new DatIncReposicion
                    (
                        (String)incMemo.get("fecha_incidencia"),
                        ( incMemo.get("hora_ini_incidencia") == null ? "--:--" : (String)incMemo.get("hora_ini_incidencia") ),
                        ( incMemo.get("hora_fin_incidencia") == null ? "--:--" : (String)incMemo.get("hora_fin_incidencia") ),
                        String.valueOf((Integer)incMemo.get("observaciones"))
                    )
                );
            for( Map<String, Object> iterador : listHorMemoInc )
            {
                listaInclist.add(
                    new DatIncReposicion
                    (
                        (String)incMemo.get("fecha_compensacion"),
                        (String)incMemo.get("hora_ini_compensacion"),
                        (String)incMemo.get("hora_fin_compensacion"),
                        "+"+String.valueOf((Integer)incMemo.get("cant_tiempo_cubre"))
                    )
                );
            }
            parametros.put(parametroListaInc, new JRBeanCollectionDataSource(listaInclist));
            parametros.put(parametroMemo, respMemo.get("serie_memos"));

            if( bool != 1 )
            {
                respInc = 0;
                error.setCodigo(bool);
                throw new Exception();
            }

            if( reporte.generaReporte( parametros, reportReposicion, rutaReporte, usuario ) != 1 )
            {
                respInc = 0;
                error.setCodigo(100);
                throw new Exception();
            }
                
        } catch (Exception e) {
            HashMap<String, Object> respuesta = new HashMap<String, Object>();
            respuesta.put("msm", error.getMsmCodigo());
            respuesta.put("inc", respInc);
            respuesta.put("comp", repCom); 
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok().build();
    }

    @Transactional(readOnly = false)
    public ResponseEntity getRespuestaIncidenciaReposicionSinRestriccionUpdate(ReposicionUpdate incidencias)
    {
        Map<String, Object> parametros = new HashMap<String, Object>();
        Collection<DatIncReposicion> listaInclist = new ArrayList<DatIncReposicion>();
        Error error = new Error();
        String rutaReporte = "";
        Integer respInc = 1;

        try {
            DatReposicionUpdate reponer[] = incidencias.getReposicion();
            String temp = new Gson().toJson(datos.getBuscaIncidenciasId(incidencias.getIdIncidencia()));
            HashMap<String, Object> memos = new Gson().fromJson(temp, HashMap.class);
            byte rut[] = Base64.getDecoder().decode((String)memos.get("ruta_doc"));

            Integer bool = sp.spActualizaReposicionSinRestricciones
            (
                incidencias.getFechInc(),
                incidencias.getHorIniInc(),
                incidencias.getHorFinInc(),
                incidencias.getObs(),
                incidencias.getIdIncidencia()
            );

            rutaReporte = new String(rut, StandardCharsets.UTF_8);

            if( bool != 1 )
            {
                respInc = 0;
                error.setCodigo(bool);
                throw new Exception();
            }

            bool = sp.spBorraCompensacionesReposicion(incidencias.getIdIncidencia());

            if( bool != 1 )
            {
                respInc = 0;
                error.setCodigo(bool);
                throw new Exception();
            }

            if( bool != 1 )
            {
                error.setCodigo(bool);
                throw new Exception();
            }

            listaInclist.add(
                new DatIncReposicion
                (
                    incidencias.getFechInc(),
                    ( incidencias.getHorIniInc() == null ? "--:--" : incidencias.getHorIniInc() ),
                    ( incidencias.getHorFinInc() == null ? "--:--" : incidencias.getHorFinInc() ),
                    incidencias.getObs()
                    
                )
            );

            for (DatReposicionUpdate rep : reponer) 
            {
                bool = sp.spActualizaValidaCompensacionReposicion
                (
                    rep.getFecha(),
                    rep.getHoraInicio(), 
                    rep.getHoraFin(),
                    rep.getHoraCubre(),
                    incidencias.getIdIncidencia()
                );

                if( bool != 1 )
                {
                    error.setCodigo(bool);
                    throw new Exception();
                }

                listaInclist.add(
                    new DatIncReposicion
                    (
                        rep.getFecha(),
                        rep.getHoraInicio(),
                        rep.getHoraFin(),
                        "+"+rep.getHoraCubre()
                    )
                );

            }

            parametros.put(parametroListaInc, new JRBeanCollectionDataSource(listaInclist));
            parametros.put(parametroMemo, (String)memos.get("serie_memos"));

            if( bool != 1 )
            {
                respInc = 0;
                error.setCodigo(bool);
                throw new Exception();
            }

            if( reporte.generaReporte( parametros, reportReposicion, rutaReporte, (String)memos.get("nom_usuario") ) != 1 )
            {
                respInc = 0;
                error.setCodigo(100);
                throw new Exception();
            }
                
        } catch (Exception e) {
            HashMap<String, Object> respuesta = new HashMap<String, Object>();
            respuesta.put("msm", error.getMsmCodigo());
            respuesta.put("inc", respInc);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok().build();
    }

    @Transactional(readOnly = false)
    public ResponseEntity getRespuestaIncidenciaCorrimientoAbierta(CorrimientoSinRest inc)
    {
        Map<String, Object> parametros = new HashMap<String, Object>();
        Map<String, Object> listHorMemoInc = null;
        Collection<DatIncCorrimiento2> listaInclist = new ArrayList<DatIncCorrimiento2>();
        String rutaReporte = "", nombre_prof = "", usuario = "";
        Error error = new Error();
        Integer respInc = 1;

        try {
            Map<String, Object> resp = sp.spIncidenciaCorrimientoSinRestricciones
            (
                inc.getTarjetaCic(), 
                inc.getFechInc(), 
                inc.getHorIniInc(), 
                inc.getHorFinInc()
            );

            Integer corriBool = (Integer)resp.get("bool_salida");
            Integer idSlida = (Integer)resp.get("id_salida");
            nombre_prof = (String) resp.get("nombre_prof");
            usuario = (String) resp.get("usuario");

            if( corriBool != 1 )
            {
                respInc = 0;
                error.setCodigo(corriBool);
                throw new Exception();
            }

            rutaReporte = crypto.crearSHA512(String.valueOf(idSlida)+reportCorrimiento+fecha.getFechaHoraUTC());
            rutaReporte = carpetaPDF+"MEMO_DCIC_CORRIMIENTO_"+nombre_prof+"_"+rutaReporte+".pdf";

            Map<String, Object> respMemo = sp.SpIngresaDatosMemo
            (
                idSlida, 
                null, 
                fecha.getAnoCreacion(), 
                Base64.getEncoder().encodeToString(rutaReporte.getBytes())
            );

            corriBool = (Integer)respMemo.get("bool_salida");
            listHorMemoInc = datos.listaCorrimientoMemosPdf(usuario, idSlida);
            listaInclist.add(
                    new DatIncCorrimiento2
                    (
                        (String)listHorMemoInc.get("fecha_incidencia"),
                        (String)listHorMemoInc.get("hora_ini_incidencia"),
                        (String)listHorMemoInc.get("hora_fin_incidencia")
                    )
                );

            parametros.put(parametroListaInc, new JRBeanCollectionDataSource(listaInclist));
            parametros.put(parametroMemo, respMemo.get("serie_memos"));

            if( corriBool != 1 )
            {
                respInc = 0;
                error.setCodigo(corriBool);
                throw new Exception();
            }

            if( reporte.generaReporte(parametros, reportCorrimiento, rutaReporte, usuario) != 1 )
            {
                respInc = 0;
                error.setCodigo(100);
                throw new Exception();
            }
            
        } catch (Exception e) {
            HashMap<String, Object> respuesta = new HashMap<String, Object>();
            respuesta.put("msm", error.getMsmCodigo());
            respuesta.put("inc", respInc);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.badRequest().body(respuesta);
        }

        return ResponseEntity.ok().build();
    }

    @Transactional(readOnly = false)
    public ResponseEntity getRespuestaIncidenciaCorrimientoAbiertaActuliza(CorrimientoUpdate inc)
    {
        Collection<DatIncCorrimiento2> listaInclist = new ArrayList<DatIncCorrimiento2>();
        Map<String, Object> parametros = new HashMap<String, Object>();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        Error error = new Error();
        String ruta = "";

        try {
            Integer bool = sp.spActualizaCorrimientoSinRestricciones
            (
                inc.getFechInc(), 
                inc.getHorIniInc(), 
                inc.getHorFinInc(), 
                inc.getIdIncidencia()
            );

            Map<String, Object> memos = datos.getBuscaIncidenciasId(inc.getIdIncidencia());

            byte rut[] = Base64.getDecoder().decode((String)memos.get("ruta_doc"));

            if( bool != 1 )
            {
                error.setCodigo(bool);
                throw new Exception();
            }

            ruta = new String( rut, StandardCharsets.UTF_8 );

            Date fech = (Date)memos.get("fecha_incidencia");
            Time ini = (Time)memos.get("hora_ini_incidencia");
            Time fin = (Time)memos.get("hora_fin_incidencia");

            listaInclist.add(
                new DatIncCorrimiento2
                (
                    formatoFecha.format(fech),
                    formatoHora.format(ini),
                    formatoHora.format(fin)
                )
            );

            parametros.put(parametroListaInc, new JRBeanCollectionDataSource(listaInclist));
            parametros.put(parametroMemo, (String)memos.get("serie_memos"));

            if( reporte.generaReporte(parametros, reportCorrimiento, ruta, (String)memos.get("nom_usuario")) != 1 )
            {
                error.setCodigo(100);
                throw new Exception();
            }
            
        } catch (Exception e) {
            HashMap<String, Object> respuesta = new HashMap<String, Object>();
            respuesta.put("msm", error.getMsmCodigo());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.badRequest().body(respuesta);
        }

        return ResponseEntity.ok().build();
    }

    @Transactional(readOnly = false)
    public ResponseEntity getRespuestaDiaEconomicoAbierta(EconomicoSinRest inc)
    {
        Map<String, Object> listHorMemoInc = null;
        String rutaReporte = "", nombre_prof = "", usuario = "";
        Error error = new Error();
        Integer respInc = 1;

        try 
        {
            Map<String, Object> res = fecha.getExtraeFechasFromIniFin2
            (
                inc.getFechaIni(),
                inc.getFechaFin()
            );

            for( int i = 0; i < res.size(); i++ )
            {
                Map<String, Object> res2 = (Map<String, Object>)res.get((i+1)+"");

                Map<String, Object> resp = sp.spIncidenciaDiaEconomicoSinRestricciones
                (
                    inc.getTarjetaCic(), 
                    inc.getFechaIni(), 
                    inc.getFechaFin(),
                    (String)res2.get("ano")+"-"+(String)res2.get("mes")+"-"+(String)res2.get("dia")
                );

                Integer diaEcoBool = (Integer)resp.get("bool_salida");
                Integer idSalida = (Integer)resp.get("id_salida");
                nombre_prof = (String) resp.get("nombre_prof");
                usuario = (String) resp.get("usuario");

                if( diaEcoBool != 1 )
                {
                    respInc = 0;
                    error.setCodigo(diaEcoBool);
                    throw new Exception();
                }

                Map<String, Object> parametros = new HashMap<String, Object>();
                String hash = crypto.crearSHA512(String.valueOf(idSalida)+reportEconomico+fecha.getFechaHoraUTC()+i);
                
                rutaReporte = carpetaPDF+"MEMO_DCIC_ECONOMICO_"+nombre_prof+"_";
                rutaReporte += res2.get("ano")+"-"+res2.get("mes")+"-"+res2.get("dia")+"_"+hash+".pdf";
                
                Map<String, Object> respMemo = sp.SpIngresaDatosMemo
                (
                    null, 
                    idSalida, 
                    fecha.getAnoCreacion(), 
                    Base64.getEncoder().encodeToString(rutaReporte.getBytes())
                );

                listHorMemoInc = datos.listaDiaEconomicoMemosPdf(usuario, idSalida);
                diaEcoBool = (Integer)respMemo.get("bool_salida");

                if( diaEcoBool != 1 )
                {
                    respInc = 0;
                    error.setCodigo(diaEcoBool);
                    throw new Exception();
                }

                parametros.put("DiaInc", res2.get("dia"));
                parametros.put("MesInc", res2.get("mes"));
                parametros.put("AnoInc", res2.get("ano"));

                if( reporte.generaReporte(parametros, reportEconomico, rutaReporte, usuario) != 1 )
                {
                    respInc = 0;
                    error.setCodigo(100);
                    throw new Exception();
                }
            }
            
        } catch (Exception e) {
            HashMap<String, Object> respuesta = new HashMap<String, Object>();
            respuesta.put("msm", error.getMsmCodigo());
            respuesta.put("inc", respInc);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.badRequest().body(respuesta);
        }

        return ResponseEntity.ok().build();
    }

    private vUsuarioDiasHoras[] getDatos(String usuario)
    {
        List<Map<String, Object>> horarioDatos = datos.datosUusario(usuario);
        vUsuarioDiasHoras horario[] = new vUsuarioDiasHoras[horarioDatos.size()];

        for( int i=0; i<horarioDatos.size(); i++ )
        {
            Map<String, Object> prof = horarioDatos.get(i);
            horario[i] = new vUsuarioDiasHoras();

            horario[i].setBasificado((Integer)prof.get("basificado"));
            horario[i].setNombreProf((String)prof.get("nombre"));
            horario[i].setNombreDia((String)prof.get("nombre_dia"));
            horario[i].setTarjetaCic((String)prof.get("terjeta_cic"));
            horario[i].setHoraEnt((Timestamp)prof.get("hora_ini"));
            horario[i].setHoraSal((Timestamp)prof.get("hora_fin"));
        }

         return horario;
    }
}
