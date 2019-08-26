package com.wposs.buc.restpapp.model;

public class PedidosActivos {

    private String id;
    private String mesa;
    private String mesero;
    private String meseroNombre;
    private int subTotal;
    private int imp;
    private int total;

    public PedidosActivos() {
    }

    public PedidosActivos(String id, String mesa, String mesero, String meseroNombre, int subTotal, int imp, int total) {
        this.id = id;
        this.mesa = mesa;
        this.mesero = mesero;
        this.meseroNombre = meseroNombre;
        this.subTotal = subTotal;
        this.imp = imp;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public String getMesero() {
        return mesero;
    }

    public void setMesero(String mesero) {
        this.mesero = mesero;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public int getImp() {
        return imp;
    }

    public void setImp(int imp) {
        this.imp = imp;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getMeseroNombre() {
        return meseroNombre;
    }

    public void setMeseroNombre(String meseroNombre) {
        this.meseroNombre = meseroNombre;
    }
}
