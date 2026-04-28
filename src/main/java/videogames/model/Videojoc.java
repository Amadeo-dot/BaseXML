package videogames.model;

import java.util.ArrayList;
import java.util.List;

public class Videojoc {

    private String id;
    private String estat;
    private String titol;
    private String desenvolupador;
    private double preu;
    private List<String> plataformes;
    private int anyLlancament;

    public Videojoc() {
        plataformes = new ArrayList<>();
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

    public int getAnyLlancament() {
        return anyLlancament;
    }

    public void setAnyLlancament(int anyLlancament) {
        this.anyLlancament = anyLlancament;
    }

    @Override
    public String toString() {
        return "ID: " + id
                + " | Estat: " + estat
                + " | Titol: " + titol
                + " | Desenvolupador: " + desenvolupador
                + " | Preu: " + preu
                + " | Plataformes: " + plataformes
                + " | Any: " + anyLlancament;
    }
}
