package com.imf.famtree.beans;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Miembro implements Serializable {
    private String tipo;
    private String numeroMiembro;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String fechaNacimiento;
    private String fechaDefuncion;
    private String urlFoto;
    private String descripcion;

    public Miembro() {
    }

    public Miembro(String tipo, String nombre, String apellido1, String apellido2, String fechaNacimiento, String fechaDefuncion, String urlFoto) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaDefuncion = fechaDefuncion;
        this.urlFoto = urlFoto;
    }

    public Miembro(String tipo, String nombre, String apellido1, String apellido2, String fechaNacimiento, String fechaDefuncion, String urlFoto, String descripcion) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaDefuncion = fechaDefuncion;
        this.urlFoto = urlFoto;
        this.descripcion = descripcion;
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumeroMiembro() {
        return numeroMiembro;
    }

    public void setNumeroMiembro(String numeroMiembro) {
        this.numeroMiembro = numeroMiembro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getFechaDefuncion() {
        return fechaDefuncion;
    }

    public void setFechaDefuncion(String fechaDefuncion) {
        this.fechaDefuncion = fechaDefuncion;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @NonNull
    @Override
    public String toString() {
        return "Miembro{" +
                "tipo='" + tipo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido1='" + apellido1 + '\'' +
                ", Apellido2='" + apellido2 + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", fechaDefuncion='" + fechaDefuncion + '\'' +
                ", urlFoto='" + urlFoto + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
