package com.grability.apps.Categoria;

/**
 * Created by ana on 08/11/2016.
 */

public class Categoria {

    private String id;
    private String label;

    public Categoria(String label, String id) {
        this.id = id;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
}
