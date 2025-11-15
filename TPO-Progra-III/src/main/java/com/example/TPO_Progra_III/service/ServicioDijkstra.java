package com.example.TPO_Progra_III.service;

import com.example.TPO_Progra_III.model.dijkstra.GrafoDijkstra;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServicioDijkstra {

    /**
     * Calcula los caminos mínimos a partir de un diccionario en memoria.
     * Las conexiones se cargan desde una lista de Map.
     */
    public List<Map<String, Object>> calcularCaminosDesdeDiccionario() {
        List<Map<String, Object>> conexiones = List.of(
                Map.of("origen", "Restaurante", "destino", "Casa de Facundo", "peso", 1),
                Map.of("origen", "Restaurante", "destino", "Casa de Ramiro", "peso", 4),
                Map.of("origen", "Casa de Facundo", "destino", "Casa de Ramiro", "peso", 2),
                Map.of("origen", "Casa de Facundo", "destino", "Casa de Paula", "peso", 6),
                Map.of("origen", "Casa de Ramiro", "destino", "Casa de Paula", "peso", 3),
                Map.of("origen", "Casa de Paula", "destino", "Depósito", "peso", 1),
                Map.of("origen", "Depósito", "destino", "Restaurante", "peso", 8)
        );

        String[] NOMBRES_NODOS = {
                "Restaurante", "Casa de Facundo", "Casa de Ramiro", "Casa de Paula", "Depósito"
        };

        Map<String, Integer> mapaNodos = new HashMap<>();
        for (int i = 0; i < NOMBRES_NODOS.length; i++) {
            mapaNodos.put(NOMBRES_NODOS[i], i);
        }

        GrafoDijkstra grafo = new GrafoDijkstra(NOMBRES_NODOS.length);

        for (Map<String, Object> conexion : conexiones) {
            String origen = (String) conexion.get("origen");
            String destino = (String) conexion.get("destino");
            int peso = (int) conexion.get("peso");

            int origenIdx = mapaNodos.get(origen);
            int destinoIdx = mapaNodos.get(destino);

            grafo.agregarArista(origenIdx, destinoIdx, peso);

            System.out.println("Origen: " + origen + " → Destino: " + destino + " | Cuadras: " + peso);
        }

        GrafoDijkstra.DijkstraResultadoCompleto resultados = grafo.dijkstra(0);

        int[] distancias = resultados.distancias;
        int[] anterior = resultados.anterior;

        List<Map<String, Object>> caminos = new ArrayList<>();

        for (int i = 1; i < NOMBRES_NODOS.length; i++) {
            List<String> recorrido = reconstruirCamino(i, anterior, NOMBRES_NODOS);

            Map<String, Object> info = new HashMap<>();
            info.put("Origen", NOMBRES_NODOS[0]);
            info.put("destino", NOMBRES_NODOS[i]);
            info.put("recorrido", recorrido);
            info.put("distancia", distancias[i]);
            caminos.add(info);

            System.out.println("Ruta: " + recorrido + " | Distancia: " + distancias[i]);
        }

        return caminos;
    }

    private List<String> reconstruirCamino(int destino, int[] anterior, String[] nombres) {
        LinkedList<String> camino = new LinkedList<>();
        int actual = destino;

        while (actual != -1) {
            camino.addFirst(nombres[actual]);
            actual = anterior[actual];
        }
        return camino;
    }
}
