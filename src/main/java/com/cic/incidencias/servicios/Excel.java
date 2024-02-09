package com.cic.incidencias.servicios;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class Excel {
    
    public ResponseEntity cargarProfesores()
    {

        return ResponseEntity.ok().build();
    }

    private void leerExcel()
    {
        Workbook libro =  WorkbookFactory.create(null);
    }
}
