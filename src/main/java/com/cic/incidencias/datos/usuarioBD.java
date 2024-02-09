package com.cic.incidencias.datos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "v_usuario_bd")
public class usuarioBD {
    @Id
    @Column(name = "id_usuario")
    private Long idUsuario;
    @Column(name = "nombre_rol")
    private String nombreRol;
    @Column(name = "nom_usuario")
    private String nomUsuario;
    private String contrasena;
    @Column(name = "terjeta_cic")
    private String tarjetaCic;
    @Column(name = "nombre_dia")
    private String nombreDia;
    @Column(name = "hora_ini")
    private String horaEntrada;
    @Column(name = "hora_fin")
    private String horaSalida;
    public Long getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getNombreRol() {
        return nombreRol;
    }
    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
    public String getNomUsuario() {
        return nomUsuario;
    }
    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }
    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    public String getTarjetaCic() {
        return tarjetaCic;
    }
    public void setTarjetaCic(String tarjetaCic) {
        this.tarjetaCic = tarjetaCic;
    }
    public String getNombreDia() {
        return nombreDia;
    }
    public void setNombreDia(String nombreDia) {
        this.nombreDia = nombreDia;
    }
    public String getHoraEntrada() {
        return horaEntrada;
    }
    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }
    public String getHoraSalida() {
        return horaSalida;
    }
    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    
}
