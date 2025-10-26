package com.example.TPO_Progra_III.model.prim;

import java.util.*;

public class GrafoPrim {
    private int numNodos;
    private List<List<AristaPrim>> adyacencias;

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

    public void prim() {
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

        String[] nombres = {
                "Restaurante",
                "Sucursal Zona Norte",
                "Sucursal Zona Sur",
                "Proveedor",
                "Depósito"
        };

        System.out.println("\nConexión óptima para distribución de la materia prima:\n");
        int costoTotal = 0;

        for (int i = 1; i < numNodos; i++) {
            System.out.println(nombres[padre[i]] + " <-> " + nombres[i] + " | Costo: " + costo[i] + " km");
            costoTotal += costo[i];
        }

        System.out.println("\nCosto total mínimo de conexión: " + costoTotal + " km\n");
    }
}