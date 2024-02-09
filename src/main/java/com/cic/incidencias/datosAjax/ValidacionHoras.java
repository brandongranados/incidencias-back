package com.cic.incidencias.datosAjax;

import java.sql.Timestamp;
import java.util.Date;

public class ValidacionHoras {
    private Date fecha;
    private Timestamp horaIni;
    private Timestamp horaFin;
    private int observaciones;

    
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public Timestamp getHoraIni() {
        return horaIni;
    }
    public void setHoraIni(Timestamp horaIni) {
        this.horaIni = horaIni;
    }
    public Timestamp getHoraFin() {
        return horaFin;
    }
    public void setHoraFin(Timestamp horaFin) {
        this.horaFin = horaFin;
    }
    public int getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(int observaciones) {
        this.observaciones = observaciones;
    }
    
}
