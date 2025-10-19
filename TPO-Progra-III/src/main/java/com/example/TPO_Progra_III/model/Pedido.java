package com.example.TPO_Progra_III.model;

// Importa la nueva clase
import java.util.List;

public class Pedido {

    private String id;
    private double precio;
    private List<ItemDescripcion> descripcion;
    private String direccion;

    public Pedido() {
    }

    public Pedido(String id, double precio, List<ItemDescripcion> descripcion, String direccion) {
        this.id = id;
        this.precio = precio;
        this.descripcion = descripcion;
        this.direccion = direccion;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public List<ItemDescripcion> getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(List<ItemDescripcion> descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Pedido [id=" + id + ", precio=" + precio + ", descripcion (items)=" + (descripcion != null ? descripcion.size() : 0) + ", direccion=" + direccion + "]";
    }
}