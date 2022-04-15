package com.imf.famtree.beans;

import java.util.ArrayList;
import java.util.Arrays;

public class Arbol {

    private String idUser;
    private ArrayList<Miembro> bisabuelos;
    private ArrayList<Miembro> abuelos;
    private ArrayList<Miembro> padres;
    private ArrayList<Miembro> hijos;

    public Arbol() {
    }

    public Arbol(String idUser, ArrayList<Miembro> bisabuelos, ArrayList<Miembro> abuelos, ArrayList<Miembro> padres) {
        this.idUser = idUser;
        this.bisabuelos = bisabuelos;
        this.abuelos = abuelos;
        this.padres = padres;
    }

    @Override
    public String toString() {
        return "Arbol{" +
                "idUser='" + idUser + '\'' +
                ", bisabuelos=" + bisabuelos +
                ", abuelos=" + abuelos +
                ", padres=" + padres +
                '}';
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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

    public ArrayList<Miembro> getHijos() {
        return hijos;
    }

    public void setHijos(ArrayList<Miembro> hijos) {
        this.hijos = hijos;
    }
}
