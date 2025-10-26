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

    /**
     * Resuelve el problema de caminos más cortos utilizando el algoritmo de Dijkstra.
     * @param origen Índice del nodo de origen.
     * @return Lista de DijkstraResultado con el camino más corto a cada destino.
     */
    public List<DijkstraResultado> calcularCaminosMinimos(int origen) {
        // 1. Inicialización y construcción del grafo (debería venir de una capa de datos)
        GrafoDijkstra grafo = inicializarGrafo();

        // 2. Ejecución del algoritmo y obtención de los resultados internos
        GrafoDijkstra.DijkstraResultadoCompleto resultados = grafo.dijkstra(origen);
        int[] distancias = resultados.distancias;
        int[] anterior = resultados.anterior;

        // 3. Mapeo de resultados a la lista de DTOs
        List<DijkstraResultado> resultadosDTO = new ArrayList<>();

        for (int i = 0; i < NOMBRES_NODOS.length; i++) {
            if (i == origen) continue; // No incluir el origen

            if (distancias[i] != Integer.MAX_VALUE) {
                List<String> camino = reconstruirCamino(i, anterior);
                resultadosDTO.add(new DijkstraResultado(
                        NOMBRES_NODOS[i],
                        camino,
                        distancias[i]
                ));
            } else {
                // Opcional: manejar destinos inalcanzables si es necesario
                // Por simplicidad, solo agregamos los alcanzables.
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
        // Ejemplo simple basado en el ejercicio de clase
        // 5 nodos: 0=Restaurante, 1=Facundo, 2=Ramiro, 3=Paula, 4=Depósito
        GrafoDijkstra grafo = new GrafoDijkstra(NOMBRES_NODOS.length);

        // Ejemplo de conexiones con pesos (cuadras/tiempo)
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