package com.cic.incidencias.reportes;

public class DatIncDiaEconomico {
    private String fechaini;
    private String fechafin;

    public DatIncDiaEconomico(String fechaini, String fechafin)
    {
        this.setFechaini(fechaini);
        this.setFechafin(fechafin);
    }
    public String getFechaini() {
        return fechaini;
    }
    public void setFechaini(String fechaini) {
        this.fechaini = fechaini;
    }
    public String getFechafin() {
        return fechafin;
    }
    public void setFechafin(String fechafin) {
        this.fechafin = fechafin;
    }
}
