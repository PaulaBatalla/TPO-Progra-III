package com.example.TPO_Progra_III.service;

import com.example.TPO_Progra_III.model.dijkstra.DestinoDijkstra;
import com.example.TPO_Progra_III.model.dijkstra.GrafoDijkstra;
import com.example.TPO_Progra_III.model.dijkstra.RutaDijkstra;
import com.example.TPO_Progra_III.repository.DestinoRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServicioDijkstra {

    private final DestinoRepository destinoRepository;

    public ServicioDijkstra(DestinoRepository destinoRepository) {
        this.destinoRepository = destinoRepository;
    }


    public List<Map<String, Object>> calcularCaminosDesdeDiccionario() {

        List<DestinoDijkstra> nodos = destinoRepository.findAll();

        int n = nodos.size();
        if (n == 0) {
            return Collections.emptyList(); // No hay datos
        }

        GrafoDijkstra grafo = new GrafoDijkstra(n);

        // --- MAPEAR NODOS A ÍNDICES ---
        Map<String, Integer> mapaNodos = new HashMap<>();
        String[] NOMBRES_NODOS = new String[n];

        int i = 0;
        for (DestinoDijkstra nodo : nodos) {
            mapaNodos.put(nodo.getNombre(), i);
            NOMBRES_NODOS[i] = nodo.getNombre();
            i++;
        }

        // --- AGREGAR ARISTAS AL GRAFO ---
        for (DestinoDijkstra origen : nodos) {
            int origenIdx = mapaNodos.get(origen.getNombre());

            for (RutaDijkstra ruta : origen.getRutas()) {
                DestinoDijkstra destino = ruta.getDestino();
                int peso = ruta.getPeso();

                if (mapaNodos.containsKey(destino.getNombre())) {
                    int destinoIdx = mapaNodos.get(destino.getNombre());

                    grafo.agregarArista(origenIdx, destinoIdx, peso); // [cite: 7]

                    System.out.println("Origen: " + origen.getNombre() + " → Destino: " + destino.getNombre() + " | Cuadras: " + peso);
                }
            }
        }

        // --- EJECUTAR DIJKSTRA ---

        int inicioIdx = mapaNodos.get("Restaurante");

        GrafoDijkstra.DijkstraResultadoCompleto resultados = grafo.dijkstra(inicioIdx); //

        int[] distancias = resultados.distancias;
        int[] anterior = resultados.anterior;

        List<Map<String, Object>> caminos = new ArrayList<>();

        for (int j = 0; j < n; j++) {
            if (j == inicioIdx) continue; // Saltear el nodo de origen

            List<String> recorrido = reconstruirCamino(j, anterior, NOMBRES_NODOS); // [cite: 6]

            Map<String, Object> info = new HashMap<>();
            info.put("Origen", NOMBRES_NODOS[inicioIdx]);
            info.put("destino", NOMBRES_NODOS[j]);
            info.put("recorrido", recorrido);
            info.put("distancia", distancias[j]);
            caminos.add(info);

            System.out.println("Ruta: " + recorrido + " | Distancia: " + distancias[j]);
        }

        return caminos;
    }

    private List<String> reconstruirCamino(int destino, int[] anterior, String[] nombres) {
        LinkedList<String> camino = new LinkedList<>();
        int actual = destino;

        // Manejar el caso de un nodo inalcanzable
        if (anterior[actual] == -1 && actual != 0) { // Asumiendo 0 como inicio
            camino.addFirst(nombres[actual]);
            camino.addFirst("Inalcanzable desde " + nombres[0]);
            return camino;
        }

        while (actual != -1) {
            camino.addFirst(nombres[actual]);
            actual = anterior[actual];
        }
        return camino;
    }
}