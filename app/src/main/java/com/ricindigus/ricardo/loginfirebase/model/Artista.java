package com.ricindigus.ricardo.loginfirebase.model;

/**
 * Created by RICARDO on 9/07/2017.
 */

public class Artista {
    private String id;
    private String nombre;
    private String genero;

    public Artista() {
    }

    public Artista(String id, String nombre, String genero) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
