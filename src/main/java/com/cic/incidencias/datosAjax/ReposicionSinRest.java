package com.cic.incidencias.datosAjax;

public class ReposicionSinRest {
    private String fechInc;
    private String horIniInc;
    private String horFinInc;
    private String obs;
    private String tarjetaCic;
    private DatReposicionUpdate reposicion[];

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
    public String getTarjetaCic() {
        return tarjetaCic;
    }
    public void setTarjetaCic(String tarjetaCic) {
        this.tarjetaCic = tarjetaCic;
    }
    public DatReposicionUpdate[] getReposicion() {
        return reposicion;
    }
    public void setReposicion(DatReposicionUpdate[] reposicion) {
        this.reposicion = reposicion;
    }
}
