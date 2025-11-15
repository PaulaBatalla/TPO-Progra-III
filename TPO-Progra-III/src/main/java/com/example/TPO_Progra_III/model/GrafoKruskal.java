package com.example.TPO_Progra_III.model;

import java.util.*;

public class GrafoKruskal {

    public static class Arista implements Comparable<Arista> {
        public int origen;
        public int destino;
        public int peso;

        public Arista(int origen, int destino, int peso) {
            this.origen = origen;
            this.destino = destino;
            this.peso = peso;
        }

        @Override
        public int compareTo(Arista otra) {
            return Integer.compare(this.peso, otra.peso);
        }
    }

    private final int numNodos;
    private final List<Arista> aristas;

    public GrafoKruskal(int numNodos) {
        this.numNodos = numNodos;
        this.aristas = new ArrayList<>();
    }

    public void agregarArista(int origen, int destino, int peso) {
        aristas.add(new Arista(origen, destino, peso));
    }

    public List<Arista> kruskalMST() {
        List<Arista> resultado = new ArrayList<>();
        Collections.sort(aristas); // ordena por peso

        int[] padre = new int[numNodos];
        for (int i = 0; i < numNodos; i++) padre[i] = i;

        int count = 0;
        for (Arista arista : aristas) {
            int raizA = encontrar(padre, arista.origen);
            int raizB = encontrar(padre, arista.destino);

            if (raizA != raizB) {
                resultado.add(arista);
                unir(padre, raizA, raizB);
                count++;
                if (count == numNodos - 1) break;
            }
        }

        return resultado;
    }

    private int encontrar(int[] padre, int nodo) {
        if (padre[nodo] != nodo)
            padre[nodo] = encontrar(padre, padre[nodo]);
        return padre[nodo];
    }

    private void unir(int[] padre, int a, int b) {
        padre[a] = b;
    }
}

