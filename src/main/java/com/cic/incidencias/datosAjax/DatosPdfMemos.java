package com.cic.incidencias.datosAjax;

public class DatosPdfMemos {
    private String fechaIncidencia;
    private String fechaResgistro;
    private String busqueda;
    private String paginacion;

    public String getFechaIncidencia() {
        return fechaIncidencia;
    }
    public void setFechaIncidencia(String fechaIncidencia) {
        this.fechaIncidencia = fechaIncidencia;
    }
    public String getFechaResgistro() {
        return fechaResgistro;
    }
    public void setFechaResgistro(String fechaResgistro) {
        this.fechaResgistro = fechaResgistro;
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
