package com.example.TPO_Progra_III.dto;

import java.util.List;

/**
 * DTO para transportar el resultado del cálculo del Árbol de Recubrimiento Mínimo (MST) por Prim.
 */
public class PrimResultado {
    private List<ConexionPrimDTO> conexiones;
    private int costoTotal;

    public PrimResultado(List<ConexionPrimDTO> conexiones, int costoTotal) {
        this.conexiones = conexiones;
        this.costoTotal = costoTotal;
    }

    // Getters y Setters
    public List<ConexionPrimDTO> getConexiones() {
        return conexiones;
    }

    public void setConexiones(List<ConexionPrimDTO> conexiones) {
        this.conexiones = conexiones;
    }

    public int getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(int costoTotal) {
        this.costoTotal = costoTotal;
    }
}