package com.cic.incidencias.datosAjax;

public class DatosPdfMemosEco {
    private String fechaIniCompensacion;
    private String fechaFinCompensacion;
    private String fechaRegistro;
    private String busqueda;
    private String paginacion;

    public String getFechaIniCompensacion() {
        return fechaIniCompensacion;
    }
    public void setFechaIniCompensacion(String fechaIniCompensacion) {
        this.fechaIniCompensacion = fechaIniCompensacion;
    }
    public String getFechaFinCompensacion() {
        return fechaFinCompensacion;
    }
    public void setFechaFinCompensacion(String fechaFinCompensacion) {
        this.fechaFinCompensacion = fechaFinCompensacion;
    }
    public String getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
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