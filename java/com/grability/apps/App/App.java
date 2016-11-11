package com.grability.apps.App;

/**
 * Created by ana on 09/11/2016.
 */

public class App {

    String imagen;
    String label;
    String descripcion;

    public App (String imagen, String label, String descripcion) {

        this.imagen = imagen;
        this.label = label;
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public String getLabel() {
        return label;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
