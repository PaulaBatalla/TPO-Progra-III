package com.example.TPO_Progra_III.service;

import com.example.TPO_Progra_III.dto.DijkstraResultado;
import com.example.TPO_Progra_III.model.dijkstra.GrafoDijkstra;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServicioDijkstra {

    private final String[] NOMBRES_NODOS = {
            "Restaurante", "Casa de Facundo", "Casa de Ramiro", "Casa de Paula", "Depósito"
    };

    public List<DijkstraResultado> calcularCaminosMinimos(int origen) {
        GrafoDijkstra grafo = inicializarGrafo();

        GrafoDijkstra.DijkstraResultadoCompleto resultados = grafo.dijkstra(origen);
        int[] distancias = resultados.distancias;
        int[] anterior = resultados.anterior;

        List<DijkstraResultado> resultadosDTO = new ArrayList<>();

        for (int i = 0; i < NOMBRES_NODOS.length; i++) {
            if (i == origen) continue;

            if (distancias[i] != Integer.MAX_VALUE) {
                List<String> camino = reconstruirCamino(i, anterior);
                resultadosDTO.add(new DijkstraResultado(
                        NOMBRES_NODOS[i],
                        camino,
                        distancias[i]
                ));
            } else {
            }
        }

        return resultadosDTO;
    }

    private List<String> reconstruirCamino(int destino, int[] anterior) {
        LinkedList<String> camino = new LinkedList<>();
        int actual = destino;

        while (actual != -1) {
            camino.addFirst(NOMBRES_NODOS[actual]);
            actual = anterior[actual];
        }
        return camino;
    }

    private GrafoDijkstra inicializarGrafo() {
        // 5 nodos: 0=Restaurante, 1=Facundo, 2=Ramiro, 3=Paula, 4=Depósito
        GrafoDijkstra grafo = new GrafoDijkstra(NOMBRES_NODOS.length);

        grafo.agregarArista(0, 1, 1);  // Restaurante (0) -> Facundo (1), peso 1
        grafo.agregarArista(0, 2, 4);  // Restaurante (0) -> Ramiro (2), peso 4
        grafo.agregarArista(1, 2, 2);  // Facundo (1) -> Ramiro (2), peso 2
        grafo.agregarArista(1, 3, 6);  // Facundo (1) -> Paula (3), peso 6
        grafo.agregarArista(2, 3, 3);  // Ramiro (2) -> Paula (3), peso 3
        grafo.agregarArista(3, 4, 1);  // Paula (3) -> Depósito (4), peso 1
        grafo.agregarArista(4, 0, 8);  // Depósito (4) -> Restaurante (0), peso 8

        return grafo;
    }
}