package com.cic.incidencias.reportes;

public class DatIncReposicion {
    private String fechainc;
    private String horaent;
    private String horasal;
    private String obs;

    public DatIncReposicion(String fechainc, String horaent, String horasal, String obs)
    {
        this.setFechainc(fechainc);
        this.setHoraent(horaent);
        this.setHorasal(horasal);
        this.setObs(obs);
    }
    public String getFechainc() {
        return fechainc;
    }
    public void setFechainc(String fechainc) {
        this.fechainc = fechainc;
    }
    public String getHoraent() {
        return horaent;
    }
    public void setHoraent(String horaent) {
        this.horaent = horaent;
    }
    public String getHorasal() {
        return horasal;
    }
    public void setHorasal(String horasal) {
        this.horasal = horasal;
    }
    public String getObs() {
        return obs;
    }
    public void setObs(String obs) {
        this.obs = obs;
    }
    
}
