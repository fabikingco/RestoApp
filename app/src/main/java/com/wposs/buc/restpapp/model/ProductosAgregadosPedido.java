package com.wposs.buc.restpapp.model;

public class ProductosAgregadosPedido {

    private String id;
    private String name;
    private int valor;
    private int cantidad;

    public ProductosAgregadosPedido() {
    }

    public ProductosAgregadosPedido(String id, String name, int valor) {
        this.id = id;
        this.name = name;
        this.valor = valor;
    }

    public ProductosAgregadosPedido(String id, String name, int valor, int cantidad) {
        this.id = id;
        this.name = name;
        this.valor = valor;
        this.cantidad = cantidad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
