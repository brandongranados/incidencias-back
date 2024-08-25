package com.cic.incidencias.servicios;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.cic.incidencias.datos.DatosProfesor;
import com.cic.incidencias.datos.DiaHorasProfesor;
import com.cic.incidencias.datos.SpIncidenciasImp;
import com.cic.incidencias.datosAjax.DatosExcel;
import com.cic.incidencias.errores.Error;

@Service
@Transactional(readOnly = false)
public class Excel {
    @Value("${permitido.excel.xlsx}")
    private String xlsx;
    @Value("${permitido.excel.xls}")
    private String xls;
    @Value("${ruta.carpeta.excel}")
    private String carpetaExcel;

    @Autowired
    private Cryptografia crearCrypto;
    @Autowired
    private Fechas fecha;
    @Autowired
    private SpIncidenciasImp sp;
    
    public ResponseEntity cargarProfesores(DatosExcel datos)
    {
        Error err = new Error();
        Map<String, Object> resp = new HashMap<String, Object>();
        Map<String, Object> guardaExcel = null;

        if( !datos.getTipo().equals(xlsx) && !datos.getTipo().equals(xls)  )
        {
            err.setCodigo(15);
            resp.put("msm", err.getMsmCodigo());
            return ResponseEntity.badRequest().body(resp);
        }

        guardaExcel = this.guardarExcel(datos);

        if( !( (boolean)guardaExcel.get("bool") ) )
        {
            err.setCodigo(16);
            resp.put("msm", err.getMsmCodigo());
            return ResponseEntity.badRequest().body(resp);
        }

        datos = (DatosExcel) guardaExcel.get("resp");

        if( !this.leerExcel(datos) )
        {
            err.setCodigo(16);
            resp.put("msm", err.getMsmCodigo());
            return ResponseEntity.badRequest().body(resp);
        }

        return ResponseEntity.ok().build();
    }

    private Map<String, Object> guardarExcel(DatosExcel datos)
    {
        DataOutputStream sal = null;
        Map<String, Object> resp = new HashMap<String, Object>();
        String rem = crearCrypto.crearSHA512(datos.getArchivo()+datos.getNombre()+datos.getTipo()+fecha.getFechaHoraUTC());

        datos.setNombre(rem);
        resp.put("resp", datos);

        try {

            sal = new DataOutputStream(new FileOutputStream(carpetaExcel+rem+"."+datos.getTipo()));
            sal.write(Base64.getDecoder().decode(datos.getArchivo()));
            sal.close();

            resp.put("bool", true);
        } catch (Exception e) {     resp.put("bool", false);       }
        finally{
            try {
                sal.close();
            } catch (Exception e) {}
        }

        return resp;
    }

    private boolean leerExcel(DatosExcel datos)
    {
        Workbook libro = null;
        Sheet pestana = null;
        try {

            String temp = carpetaExcel+datos.getNombre()+"."+datos.getTipo();
            Integer bool = 1;

            libro =  WorkbookFactory.create(new FileInputStream(temp));
            pestana = libro.getSheetAt(0);

            for( int f=1; f<pestana.getPhysicalNumberOfRows(); f++ )
            {
                DatosProfesor p = new DatosProfesor();
                Row renglon = pestana.getRow(f);
                        
                p.setApePaterno(renglon.getCell(0).getStringCellValue().toUpperCase());
                p.setApeMaterno(renglon.getCell(1).getStringCellValue().toUpperCase());
                p.setNombre(renglon.getCell(2).getStringCellValue().toUpperCase());
                p.setTarjeta(renglon.getCell(3).getStringCellValue().toUpperCase().replaceAll(" ", ""));
                p.setTipo( renglon.getCell(9).getStringCellValue().toUpperCase().equals("BASE") ? 1 : 0 );

                bool = sp.SpAgregaDatosProfesor
                (
                    p.getTarjeta(), 
                    p.getNombre(), 
                    p.getApePaterno(), 
                    p.getApeMaterno(), 
                    p.getTipo()
                );

                if( bool != 1 )
                {
                    throw new Exception();
                }

                for( int i = 4; i<9; i++ )
                {
                    Cell celda = renglon.getCell(i);

                    this.tratarHorario(p.getTarjeta(), celda, i);
                }
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    private void tratarHorario(String tarjeta, Cell celda, int num) throws Exception
    {
        String valor = celda.getStringCellValue();
        String horarios[] = null;

        if( valor.length() == 0 )
            return;

        valor = valor.replaceAll(" ", "");
        horarios = valor.split(",");

        for( String horarioUnico : horarios )
        {
            String temp[] = horarioUnico.split("-");
            DiaHorasProfesor nuevo = new DiaHorasProfesor();
            Integer bool = 1;

            nuevo.setHoraIni(temp[0]);
            nuevo.setHoraFin(temp[1]);

            switch (num) 
            {
                case 4:
                    nuevo.setDia("lu");
                    break;
                case 5:
                    nuevo.setDia("ma");
                    break;
                case 6:
                    nuevo.setDia("mi");
                    break;
                case 7:
                    nuevo.setDia("ju");
                    break;
                case 8:
                    nuevo.setDia("vi");
                    break;
                default:
                    nuevo.setDia("lu");
                    break;
            }

            bool = sp.SpCargarDiasHorasProfesor
            (
                nuevo.getDia(), 
                nuevo.getHoraIni(), 
                nuevo.getHoraFin()
            );

            if( bool != 1 )
            {
                throw new Exception();
            }

            bool = sp.SpVincularProfHorarioLab
            (
                tarjeta, 
                nuevo.getDia(), 
                nuevo.getHoraIni(), 
                nuevo.getHoraFin()
            );

            if( bool != 1 )
            {
                throw new Exception();
            }

        }

        return;
    }
}
