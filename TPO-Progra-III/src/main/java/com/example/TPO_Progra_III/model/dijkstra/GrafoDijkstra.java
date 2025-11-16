package com.example.TPO_Progra_III.model.dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class GrafoDijkstra {
    private int numNodos;
    private List<List<AristaDijkstra>> adyacencias;

    public static class DijkstraResultadoCompleto {
        public final int[] distancias;
        public final int[] anterior;

        public DijkstraResultadoCompleto(int[] distancias, int[] anterior) {
            this.distancias = distancias;
            this.anterior = anterior;
        }
    }

    public GrafoDijkstra(int numNodos) {
        this.numNodos = numNodos;
        adyacencias = new ArrayList<>();
        for (int i = 0; i < numNodos; i++) {
            adyacencias.add(new ArrayList<>());
        }
    }

    public void agregarArista(int origen, int destino, int peso) {
        adyacencias.get(origen).add(new AristaDijkstra(destino, peso));
    }

    public DijkstraResultadoCompleto dijkstra(int origen) {
        int[] distancias = new int[numNodos];
        int[] anterior = new int[numNodos];

        Arrays.fill(distancias, Integer.MAX_VALUE);
        Arrays.fill(anterior, -1);
        distancias[origen] = 0;

        PriorityQueue<NodoDistanciaDijkstra> colaPrioridad = new PriorityQueue<>();
        colaPrioridad.add(new NodoDistanciaDijkstra(origen, 0));

        boolean[] visitado = new boolean[numNodos];

        while (!colaPrioridad.isEmpty()) {
            NodoDistanciaDijkstra actual = colaPrioridad.poll();
            int nodo = actual.nodo;

            if (visitado[nodo]) continue;
            visitado[nodo] = true;

            for (AristaDijkstra arista : adyacencias.get(nodo)) {
                int vecino = arista.destino;
                int pesoArista = arista.peso;
                int nuevaDistancia = distancias[nodo] + pesoArista;

                if (nuevaDistancia < distancias[vecino]) {
                    distancias[vecino] = nuevaDistancia;
                    anterior[vecino] = nodo;

                    colaPrioridad.add(new NodoDistanciaDijkstra(vecino, nuevaDistancia));
                }
            }
        }
        return new DijkstraResultadoCompleto(distancias, anterior);
    }

}