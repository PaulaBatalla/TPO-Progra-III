package com.example.TPO_Progra_III.model;

/**
 * Esta clase representa un ítem dentro de la descripción de un Pedido.
 * Corresponde al JSON: { "item": "...", "cantidad": ... }
 */
public class ItemDescripcion {

    private String item;
    private int cantidad;

    public ItemDescripcion() {
    }

    public ItemDescripcion(String item, int cantidad) {
        this.item = item;
        this.cantidad = cantidad;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}