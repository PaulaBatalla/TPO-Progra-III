package com.example.TPO_Progra_III.model.dijkstra;

public class NodoDistanciaDijkstra implements Comparable<NodoDistanciaDijkstra> {
    int nodo;
    int distancia;

    public NodoDistanciaDijkstra(int nodo, int distancia) {
        this.nodo = nodo;
        this.distancia = distancia;
    }

    @Override
    public int compareTo(NodoDistanciaDijkstra otro) {
        return Integer.compare(this.distancia, otro.distancia);
    }
}
