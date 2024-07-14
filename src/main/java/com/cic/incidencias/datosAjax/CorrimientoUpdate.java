package com.cic.incidencias.datosAjax;

public class CorrimientoUpdate {
    private String fechInc;
    private String horIniInc;
    private String horFinInc;
    private int idIncidencia;
    private int tipo;

    public String getFechInc() {
        return fechInc;
    }
    public void setFechInc(String fechInc) {
        this.fechInc = fechInc;
    }
    public String getHorIniInc() {
        return horIniInc;
    }
    public void setHorIniInc(String horIniInc) {
        this.horIniInc = horIniInc;
    }
    public String getHorFinInc() {
        return horFinInc;
    }
    public void setHorFinInc(String horFinInc) {
        this.horFinInc = horFinInc;
    }
    public int getIdIncidencia() {
        return idIncidencia;
    }
    public void setIdIncidencia(int idIncidencia) {
        this.idIncidencia = idIncidencia;
    }
    public int getTipo() {
        return tipo;
    }
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
