package com.cic.incidencias.datosAjax;

public class DatosPdfMemos {
    private String fechaIni;
    private String fechaFin;
    private String busqueda;
    private String paginacion;


    public String getFechaIni() {
        return fechaIni;
    }
    public void setFechaIni(String fechaIni) {
        this.fechaIni = fechaIni;
    }
    public String getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }
    public String getBusqueda() {
        return busqueda;
    }
    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }
    public String getPaginacion() {
        return paginacion;
    }
    public void setPaginacion(String paginacion) {
        this.paginacion = paginacion;
    }
}
