package com.cic.incidencias.datos;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "v_usuario_dias_horas")
public class vUsuarioDiasHoras {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;
    @Column(name = "nom_usuario")
    private String nomUsuario;
    @Column(name = "nombre")
    private String nombreProf;
    @Column(name = "terjeta_cic")
    private String tarjetaCic;
    @Column(name = "basificado")
    private Integer basificado;
    @Column(name = "nombre_dia")
    private String nombreDia;
    @Column(name = "hora_ini")
    private Timestamp horaEnt;
    @Column(name = "hora_fin")
    private Timestamp horaSal;
    @Column(name =  "numero_dia")
    private Integer numeroDia;
    @Column(name = "correo_electronico")
    private String correoElectronico;

    public Long getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getNomUsuario() {
        return nomUsuario;
    }
    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }
    public String getNombreProf() {
        return nombreProf;
    }
    public void setNombreProf(String nombreProf) {
        this.nombreProf = nombreProf;
    }
    public String getTarjetaCic() {
        return tarjetaCic;
    }
    public void setTarjetaCic(String tarjetaCic) {
        this.tarjetaCic = tarjetaCic;
    }
    public Integer getBasificado() {
        return basificado;
    }
    public void setBasificado(Integer basificado) {
        this.basificado = basificado;
    }
    public String getNombreDia() {
        return nombreDia;
    }
    public void setNombreDia(String nombreDia) {
        this.nombreDia = nombreDia;
    }
    public Timestamp getHoraEnt() {
        return horaEnt;
    }
    public void setHoraEnt(Timestamp horaEnt) {
        this.horaEnt = horaEnt;
    }
    public Timestamp getHoraSal() {
        return horaSal;
    }
    public void setHoraSal(Timestamp horaSal) {
        this.horaSal = horaSal;
    }
    public Integer getNumeroDia() {
        return numeroDia;
    }
    public void setNumeroDia(Integer numeroDia) {
        this.numeroDia = numeroDia;
    }
    public String getCorreoElectronico() {
        return correoElectronico;
    }
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
}