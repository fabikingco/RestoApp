package com.wposs.buc.restpapp.model;

public class ProductosPedidoActivo {

    private String id;
    private String name;
    private int cantidad;
    private int valor;
    private int valorTotal;
    private String photoUrl;

    public ProductosPedidoActivo() {
    }

    public ProductosPedidoActivo(String id, String name, int cantidad, int valor, int valorTotal, String photoUrl) {
        this.id = id;
        this.name = name;
        this.cantidad = cantidad;
        this.valor = valor;
        this.valorTotal = valorTotal;
        this.photoUrl = photoUrl;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(int valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
