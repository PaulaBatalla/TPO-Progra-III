package com.example.TPO_Progra_III.dto;

import java.util.List;

public class PrimResultado {
    private List<ConexionPrimDTO> conexiones;
    private int costoTotal;

    public PrimResultado(List<ConexionPrimDTO> conexiones, int costoTotal) {
        this.conexiones = conexiones;
        this.costoTotal = costoTotal;
    }

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