package com.example.examenfinalronald;

public class ModelLibro {

    String imagen;
    String titulo;
    String resumen;
    String latitud;
    String longitud;
    boolean favorito;
    String libroFavorito;
    String id;



    public ModelLibro(String imagen, String titulo, String resumen, String latitud, String longitud, boolean favorito, String libroFavorito) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.resumen = resumen;
        this.latitud = latitud;
        this.longitud = longitud;
        this.favorito = favorito;
        this.libroFavorito = libroFavorito;
    }

    public ModelLibro() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getResumen() {
        return resumen;
    }

    public String getLatitud() {
        return latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public String getLibroFavorito() {
        return libroFavorito;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public void setLibroFavorito(String libroFavorito) {
        this.libroFavorito = libroFavorito;
    }
}
