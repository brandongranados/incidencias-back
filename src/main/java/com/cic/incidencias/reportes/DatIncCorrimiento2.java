package com.cic.incidencias.reportes;

public class DatIncCorrimiento2 {
    private String fechainc;
    private String horaent;
    private String horasal;

    public DatIncCorrimiento2(String fechainc, String horaent, String horasal)
    {
        this.setFechainc(fechainc);
        this.setHoraent(horaent);
        this.setHorasal(horasal);
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
}
