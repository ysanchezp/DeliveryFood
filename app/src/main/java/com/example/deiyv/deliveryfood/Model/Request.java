package com.example.deiyv.deliveryfood.Model;

import java.util.List;

public class Request {

    private String telefono;
    private String name;
    private String direccion;
    private String total;
    private String status;
    private List<Pedido> comidas; // lista de pedido de comidas

    public Request() {
    }

    public Request(String telefono, String name, String direccion, String total, List<Pedido> comidas) {
        this.telefono = telefono;
        this.name = name;
        this.direccion = direccion;
        this.total = total;
        this.status = "0";
        this.comidas = comidas;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getName() {
        return name;
    }

    public void setName(String nombre) {
        this.name = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String  total) {
        this.total = total;
    }

    public List<Pedido> getComidas() {
        return comidas;
    }

    public void setComidas(List<Pedido> comidas) {
        this.comidas = comidas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
