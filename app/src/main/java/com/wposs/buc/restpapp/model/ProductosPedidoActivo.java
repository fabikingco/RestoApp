package com.wposs.buc.restpapp.model;

public class ProductosPedidoActivo {

    private String id;
    private String name;
    private int cantidad;
    private int valorTotal;

    public ProductosPedidoActivo() {
    }

    public ProductosPedidoActivo(String id, String name, int cantidad, int valorTotal) {
        this.id = id;
        this.name = name;
        this.cantidad = cantidad;
        this.valorTotal = valorTotal;
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

    public int getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(int valorTotal) {
        this.valorTotal = valorTotal;
    }
}
