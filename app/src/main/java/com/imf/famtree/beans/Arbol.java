package com.imf.famtree.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class Arbol implements Serializable {

    private String tipoArbol;
    private String nombreArbol;
    private ArrayList<Miembro> bisabuelos;
    private ArrayList<Miembro> abuelos;
    private ArrayList<Miembro> padres;
    private Miembro tu;

    public Arbol() {
    }

    public Arbol(String tipoArbol, String nombreArbol, ArrayList<Miembro> bisabuelos, ArrayList<Miembro> abuelos, ArrayList<Miembro> padres) {
        this.tipoArbol = tipoArbol;
        this.nombreArbol = nombreArbol;
        this.bisabuelos = bisabuelos;
        this.abuelos = abuelos;
        this.padres = padres;
    }

    public Arbol(String nombreArbol, ArrayList<Miembro> bisabuelos, ArrayList<Miembro> abuelos, ArrayList<Miembro> padres) {
        this.nombreArbol = nombreArbol;
        this.bisabuelos = bisabuelos;
        this.abuelos = abuelos;
        this.padres = padres;
    }

    public Arbol(String nombreArbol, ArrayList<Miembro> bisabuelos, ArrayList<Miembro> abuelos, ArrayList<Miembro> padres, Miembro tu) {
        this.nombreArbol = nombreArbol;
        this.bisabuelos = bisabuelos;
        this.abuelos = abuelos;
        this.padres = padres;
        this.tu = tu;
    }

    @Override
    public String toString() {
        return "Arbol{" +
                "nombreArbol='" + nombreArbol + '\'' +
                ", bisabuelos=" + bisabuelos +
                ", abuelos=" + abuelos +
                ", padres=" + padres +
                ", tu=" + tu +
                '}';
    }

    public String getTipoArbol() {
        return tipoArbol;
    }

    public void setTipoArbol(String tipoArbol) {
        this.tipoArbol = tipoArbol;
    }

    public String getNombreArbol() {
        return nombreArbol;
    }

    public void setNombreArbol(String nombreArbol) {
        this.nombreArbol = nombreArbol;
    }

    public ArrayList<Miembro> getBisabuelos() {
        return bisabuelos;
    }

    public void setBisabuelos(ArrayList<Miembro> bisabuelos) {
        this.bisabuelos = bisabuelos;
    }

    public ArrayList<Miembro> getAbuelos() {
        return abuelos;
    }

    public void setAbuelos(ArrayList<Miembro> abuelos) {
        this.abuelos = abuelos;
    }

    public ArrayList<Miembro> getPadres() {
        return padres;
    }

    public void setPadres(ArrayList<Miembro> padres) {
        this.padres = padres;
    }

    public Miembro getTu() {
        return tu;
    }

    public void setTu(Miembro tu) {
        this.tu = tu;
    }
}
