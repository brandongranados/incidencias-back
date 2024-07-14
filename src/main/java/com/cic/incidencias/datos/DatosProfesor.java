package com.cic.incidencias.datos;

public class DatosProfesor {
    private String nombre;
    private String apePaterno;
    private String apeMaterno;
    private String tarjeta;
    private int tipo;
    
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApePaterno() {
        return apePaterno;
    }
    public void setApePaterno(String apePaterno) {
        this.apePaterno = apePaterno;
    }
    public String getApeMaterno() {
        return apeMaterno;
    }
    public void setApeMaterno(String apeMaterno) {
        this.apeMaterno = apeMaterno;
    }
    public String getTarjeta() {
        return tarjeta;
    }
    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }
    public int getTipo() {
        return tipo;
    }
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
