package com.cic.incidencias.servicios;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cic.incidencias.datos.RepoDatUsuario;
import com.cic.incidencias.reportes.DatProfesorReposicion;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class Reporte {
    @Autowired
    private RepoDatUsuario datos;
    @Autowired
    private Fechas fecha;

    @Value("${param.nombre.IPN}")
    private String parametroIPN;
    @Value("${param.nombre.ESCOM}")
    private String parametroESCOM;
    @Value("${param.variable.IPN}")
    private String variableIPN;
    @Value("${param.variable.ESCOM}")
    private String variableESCOM;
    @Value("${param.nombre.FECHA}")
    private String parametroFecha;
    @Value("${param.nombre.HORARIO}")
    private String parametroListaHorario;
    @Value("${reporte.diaeconomico}")
    private String reportEconomico;

    public int generaReporte(Map<String, Object> param, String jasper, String destino, String usuario) throws Exception
    {
        int salida = 1;

        if( !jasper.contains("Economico") )
            param = this.datosProfesor(param, usuario);
        else
            param = this.datosProfesorEconomico(param, usuario);

        try {
            JasperReport leerJasper = (JasperReport) JRLoader.loadObjectFromFile(jasper);
            JasperPrint crearReporte = JasperFillManager.fillReport(leerJasper, param, new JREmptyDataSource());
            JasperExportManager.exportReportToPdfFile(crearReporte, destino);
        } catch (Exception e) {
            e.printStackTrace();
            salida = 0;
        }

        return salida;
    }

    private Map<String, Object> datosProfesorEconomico(Map<String, Object> parametros, String usuario)throws Exception
    {
        Map<String, Object> datosMemo = datos.datosUsuarioMemo(usuario);
        Map<String, String> fechaHoy = fecha.getDiaMesAno("", true);

        parametros.put(parametroIPN, variableIPN);
        parametros.put(parametroESCOM, variableESCOM);
        parametros.put("Nombre", (String)datosMemo.get("nombre"));
        parametros.put("Tarjeta", (String)datosMemo.get("terjeta_cic"));
        parametros.put("Dia", fechaHoy.get("dia"));
        parametros.put("Mes", fechaHoy.get("mes"));
        parametros.put("Ano", fechaHoy.get("ano"));

        return parametros;
    }

    private Map<String, Object> datosProfesor(Map<String, Object> parametros, String usuario)
    {
        Collection<DatProfesorReposicion> lista = new ArrayList<DatProfesorReposicion>();
        Map<String, Object> datosMemo = datos.datosUsuarioMemo(usuario);
        List<Map<String, Object>> listHorMemo = datos.datosProfesorDiasHoras(usuario);
        String fechaHoy = fecha.getFechaCompletaCDMX();

        for( Map<String, Object> horaMemo : listHorMemo )
        {
            DatProfesorReposicion nuevo = new DatProfesorReposicion
            (
                (String)datosMemo.get("nombre"), 
                (String)datosMemo.get("terjeta_cic"), 
                "DOCENTE", 
                (String)horaMemo.get("nombre_dia")
            );

            lista.add(nuevo);
        }

        parametros.put(parametroIPN, variableIPN);
        parametros.put(parametroESCOM, variableESCOM);
        parametros.put(parametroFecha, fechaHoy);
        parametros.put(parametroListaHorario, new JRBeanCollectionDataSource(lista));

        return parametros;
    }
}
