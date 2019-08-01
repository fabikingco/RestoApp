package com.wposs.buc.restpapp.model;

public class Usuarios {

    private String user;
    private String pass;
    private String name;
    private String role;
    private String status;
    private String photo;  //Falta por implementar

    public Usuarios(String user, String pass, String name, String role, String status) {
        this.user = user;
        this.pass = pass;
        this.name = name;
        this.role = role;
        this.status = status;
    }

    public Usuarios(String user) {
        this.user = user;
    }

    public Usuarios() {
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}