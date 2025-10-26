package com.example.TPO_Progra_III.dto;

import java.util.List;

public class DijkstraResultado {
    private String destino;
    private List<String> camino;
    private int distanciaTotal;

    public DijkstraResultado(String destino, List<String> camino, int distanciaTotal) {
        this.destino = destino;
        this.camino = camino;
        this.distanciaTotal = distanciaTotal;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public List<String> getCamino() {
        return camino;
    }

    public void setCamino(List<String> camino) {
        this.camino = camino;
    }

    public int getDistanciaTotal() {
        return distanciaTotal;
    }

    public void setDistanciaTotal(int distanciaTotal) {
        this.distanciaTotal = distanciaTotal;
    }
}
