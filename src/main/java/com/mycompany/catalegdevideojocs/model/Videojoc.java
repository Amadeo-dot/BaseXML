/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.catalegdevideojocs.model;

import java.util.List;

/**
 *
 * @author alumne
 */
public class Videojoc {
    String id;
    String estat;
    String titol;
    String desenvolupador;
    double preu;
    List<String> plataformes;
    String any_llancament;

    public Videojoc(String id, String estat, String titol, String desenvolupador, double preu, List<String> plataformes, String any_llancament) {
        this.id = id;
        this.estat = estat;
        this.titol = titol;
        this.desenvolupador = desenvolupador;
        this.preu = preu;
        this.plataformes = plataformes;
        this.any_llancament = any_llancament;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEstat() {
        return estat;
    }

    public void setEstat(String estat) {
        this.estat = estat;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getDesenvolupador() {
        return desenvolupador;
    }

    public void setDesenvolupador(String desenvolupador) {
        this.desenvolupador = desenvolupador;
    }

    public double getPreu() {
        return preu;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }

    public List<String> getPlataformes() {
        return plataformes;
    }

    public void setPlataformes(List<String> plataformes) {
        this.plataformes = plataformes;
    }

    public String getAny_llancament() {
        return any_llancament;
    }

    public void setAny_llancament(String any_llancament) {
        this.any_llancament = any_llancament;
    }
    
}
