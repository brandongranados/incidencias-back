package com.cic.incidencias.datosAjax;

public class ProfesorFront {
    private Long numeroEmpleado;
    private String nombre;
    private String tarjetaCic;
    public Long getNumeroEmpleado() {
        return numeroEmpleado;
    }
    public void setNumeroEmpleado(Long numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getTarjetaCic() {
        return tarjetaCic;
    }
    public void setTarjetaCic(String tarjetaCic) {
        this.tarjetaCic = tarjetaCic;
    }
}
