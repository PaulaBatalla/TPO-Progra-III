package com.example.TPO_Progra_III.dto;

import java.util.List;

public class OptimizarRequestDTO {

    private double capacidadMaxima;
    private List<PedidoDTO> pedidos;

    // Getters y Setters
    public double getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(double capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public List<PedidoDTO> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<PedidoDTO> pedidos) {
        this.pedidos = pedidos;
    }
}
