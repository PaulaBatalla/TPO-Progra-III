package com.example.TPO_Progra_III.dto;

import com.example.TPO_Progra_III.model.Pedido;

import java.util.List;

public class OrdenamientoResultado {
    private List<Pedido> pedidosOrdenados;
    private double duracionMilisegundos;

    public OrdenamientoResultado(List<Pedido> pedidosOrdenados, double duracionMilisegundos) {
        this.pedidosOrdenados = pedidosOrdenados;
        this.duracionMilisegundos = duracionMilisegundos;
    }

    public List<Pedido> getPedidosOrdenados() {
        return pedidosOrdenados;
    }

    public void setPedidosOrdenados(List<Pedido> pedidosOrdenados) {
        this.pedidosOrdenados = pedidosOrdenados;
    }

    public double getDuracionMilisegundos() {
        return duracionMilisegundos;
    }

    public void setDuracionMilisegundos(double duracionMilisegundos) {
        this.duracionMilisegundos = duracionMilisegundos;
    }
}
