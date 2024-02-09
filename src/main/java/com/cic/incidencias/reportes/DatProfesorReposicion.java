package com.cic.incidencias.reportes;

public class DatProfesorReposicion {
    private String Nombre;
    private String tarjeta;
    private String personal;
    private String horario;

    public DatProfesorReposicion(String nombre, String tarjeta, String personal, String horario)
    {
        this.setNombre(nombre);
        this.setTarjeta(tarjeta);
        this.setPersonal(personal);
        this.setHorario(horario);
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }
    public String getTarjeta() {
        return tarjeta;
    }
    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }
    public String getPersonal() {
        return personal;
    }
    public void setPersonal(String personal) {
        this.personal = personal;
    }
    public String getHorario() {
        return horario;
    }
    public void setHorario(String horario) {
        this.horario = horario;
    }
}
