package com.cic.incidencias.datosAjax;

public class DatIncCorrimiento {
    private String fechInc;
    private String horIniInc;
    private String horFinInc;
    private String usuario;


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
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
