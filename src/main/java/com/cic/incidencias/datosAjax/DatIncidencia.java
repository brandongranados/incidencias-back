package com.cic.incidencias.datosAjax;

public class DatIncidencia {
    private String fechInc;
    private String horIniInc;
    private String horFinInc;
    private String obs;
    private String usuario;
    private DatReposicion reposicion[];


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
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public DatReposicion[] getReposicion() {
        return reposicion;
    }
    public void setReposicion(DatReposicion[] reposicion) {
        this.reposicion = reposicion;
    }
}
