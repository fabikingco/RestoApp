package com.wposs.buc.restpapp.BD.Model;

import com.wposs.buc.restpapp.R;

public class Mesas {

    private int id;
    private String name;
    private String status;
    private int imagen;

    public Mesas(int id, String name, String imagen, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.imagen = Integer.parseInt(imagen);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = Integer.parseInt(imagen);
    }
}
