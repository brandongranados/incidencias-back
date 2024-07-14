package com.cic.incidencias.datosAjax;

public class ReposicionUpdate {
    private String fechInc;
    private String horIniInc;
    private String horFinInc;
    private String obs;
    private DatReposicionUpdate reposicion[];
    private int idIncidencia;

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
    public String getObs() {
        return obs;
    }
    public void setObs(String obs) {
        this.obs = obs;
    }
    public DatReposicionUpdate[] getReposicion() {
        return reposicion;
    }
    public void setReposicion(DatReposicionUpdate[] reposicion) {
        this.reposicion = reposicion;
    }
    public int getIdIncidencia() {
        return idIncidencia;
    }
    public void setIdIncidencia(int idIncidencia) {
        this.idIncidencia = idIncidencia;
    }
}
