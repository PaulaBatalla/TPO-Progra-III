package com.example.TPO_Progra_III.model.prim;

import java.util.*;

public class GrafoPrim {
    private int numNodos;
    private List<List<AristaPrim>> adyacencias;

    public static class PrimResultadoCompleto {
        public final int[] costo;
        public final int[] padre;

        public PrimResultadoCompleto(int[] costo, int[] padre) {
            this.costo = costo;
            this.padre = padre;
        }
    }

    public GrafoPrim(int numNodos) {
        this.numNodos = numNodos;
        this.adyacencias = new ArrayList<>();
        for (int i = 0; i < numNodos; i++) {
            adyacencias.add(new ArrayList<>());
        }
    }

    public void agregarArista(int origen, int destino, int peso) {
        adyacencias.get(origen).add(new AristaPrim(destino, peso));
        adyacencias.get(destino).add(new AristaPrim(origen, peso));
    }

    public PrimResultadoCompleto prim() {
        boolean[] visitado = new boolean[numNodos];
        int[] costo = new int[numNodos];
        int[] padre = new int[numNodos];
        Arrays.fill(costo, Integer.MAX_VALUE);
        Arrays.fill(padre, -1);

        costo[0] = 0;

        PriorityQueue<int[]> colaPrioridad = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        colaPrioridad.add(new int[]{0, 0});

        while (!colaPrioridad.isEmpty()) {
            int[] actual = colaPrioridad.poll();
            int nodo = actual[0];
            if (visitado[nodo]) continue;
            visitado[nodo] = true;

            for (AristaPrim arista: adyacencias.get(nodo)) {
                int vecino = arista.destino;
                int peso = arista.peso;

                if (!visitado[vecino] && peso < costo[vecino]) {
                    costo[vecino] = peso;
                    padre[vecino] = nodo;
                    colaPrioridad.add(new int[]{vecino, peso});
                }
            }
        }

        return new PrimResultadoCompleto(costo, padre);
    }
}