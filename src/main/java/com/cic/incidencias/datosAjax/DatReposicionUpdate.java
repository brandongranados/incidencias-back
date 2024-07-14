package com.cic.incidencias.datosAjax;

public class DatReposicionUpdate {
    private String fecha;
    private String horaInicio;
    private String horaFin;
    private int horaCubre;

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getHoraInicio() {
        return horaInicio;
    }
    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }
    public String getHoraFin() {
        return horaFin;
    }
    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }
    public int getHoraCubre() {
        return horaCubre;
    }
    public void setHoraCubre(int horaCubre) {
        this.horaCubre = horaCubre;
    }
}
