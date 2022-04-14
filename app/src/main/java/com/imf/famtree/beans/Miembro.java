package com.imf.famtree.beans;

enum Tipo {
    BISABUELO,
    ABUELO,
    PADRE,
    YO,
    HIJO
}

public class Miembro {

    private Tipo tipo;
    private String nombre;
    private String fechaNacimiento;
    private String fechaDefuncion;
    private String foto;
    private String descripcion;

    public Miembro() {
    }

    public Miembro(Tipo tipo, String nombre, String fechaNacimiento, String fechaDefuncion, String foto, String descripcion) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaDefuncion = fechaDefuncion;
        this.foto = foto;
        this.descripcion = descripcion;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
