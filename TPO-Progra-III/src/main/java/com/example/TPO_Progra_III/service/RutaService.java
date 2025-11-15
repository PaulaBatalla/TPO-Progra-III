package com.example.TPO_Progra_III.service;

// import com.uade.tpo.demo.repository.UbicacionRepository; // Importarías tu Repo Neo4j
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RutaService {

    // @Autowired
    // private UbicacionRepository ubicacionRepository; // Inyectarías tu Repo Neo4j

    /**
     * Simula la carga del grafo desde la BBDD (Neo4j)
     * y la convierte en una lista de adyacencia.
     */
    private Map<String, List<String>> getGrafo() {
        // En un proyecto real:
        // 1. List<Ubicacion> ubicaciones = ubicacionRepository.findAll();
        // 2. Iterar sobre 'ubicaciones' y sus 'conexiones' para armar el Map.

        // Simulación para el TPO:
        Map<String, List<String>> adj = new HashMap<>();
        adj.put("Cocina", List.of("Pasillo_Central", "Depósito"));
        adj.put("Pasillo_Central", List.of("Cocina", "Barra", "Salón"));
        adj.put("Depósito", List.of("Cocina", "Freezer"));
        adj.put("Freezer", List.of("Depósito")); // Nodo sin salida (aparte de volver)
        adj.put("Barra", List.of("Pasillo_Central"));
        adj.put("Salón", List.of("Pasillo_Central", "Baños")); // Agregamos Baños
        adj.put("Baños", List.of("Salón"));

        return adj;
    }

    /**
     * 🧠 ALGORITMO BFS (Búsqueda en Anchura)
     * Encuentra el camino más corto (en número de saltos).
     * Usa una Cola (Queue) para explorar nivel por nivel.
     */
    public List<String> encontrarCaminoBfs(String inicio, String fin) {
        Map<String, List<String>> grafo = getGrafo();

        // --- 1. Inicialización ---
        // La cola almacena *caminos* (Listas de Strings)
        Queue<List<String>> cola = new LinkedList<>();
        Set<String> visitados = new HashSet<>();

        // Agregar el punto de partida como el primer camino
        List<String> caminoInicial = new ArrayList<>();
        caminoInicial.add(inicio);
        cola.add(caminoInicial);
        visitados.add(inicio);

        // --- 2. Bucle de Exploración ---
        while (!cola.isEmpty()) {
            List<String> caminoActual = cola.poll(); // Saca el primer camino de la cola
            String ultimoNodo = caminoActual.get(caminoActual.size() - 1);

            // --- 3. Condición de Éxito ---
            if (ultimoNodo.equals(fin)) {
                return caminoActual; // ¡Encontrado! Es el camino más corto.
            }

            // --- 4. Explorar Vecinos ---
            List<String> vecinos = grafo.getOrDefault(ultimoNodo, Collections.emptyList());
            for (String vecino : vecinos) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino); // Marcar como visitado

                    // Crear el nuevo camino y agregarlo a la cola
                    List<String> nuevoCamino = new ArrayList<>(caminoActual);
                    nuevoCamino.add(vecino);
                    cola.add(nuevoCamino);
                }
            }
        }

        return null; // No se encontró camino
    }

    /**
     * 🧠 ALGORITMO DFS (Búsqueda en Profundidad)
     * Encuentra *un* camino (no necesariamente el más corto).
     * Usa una Pila (Stack) para ir "hasta el fondo" de una rama.
     */
    public List<String> encontrarCaminoDfs(String inicio, String fin) {
        Map<String, List<String>> grafo = getGrafo();

        // --- 1. Inicialización ---
        // La pila (Stack) también almacena *caminos*
        Stack<List<String>> pila = new Stack<>();
        Set<String> visitados = new HashSet<>();

        // Agregar el punto de partida como el primer camino
        List<String> caminoInicial = new ArrayList<>();
        caminoInicial.add(inicio);
        pila.push(caminoInicial);

        // --- 2. Bucle de Exploración ---
        while (!pila.isEmpty()) {
            List<String> caminoActual = pila.pop(); // Saca el último camino de la pila
            String ultimoNodo = caminoActual.get(caminoActual.size() - 1);

            // Si ya lo visitamos en otra rama más profunda, saltar
            if (visitados.contains(ultimoNodo)) {
                continue;
            }
            visitados.add(ultimoNodo); // Marcar como visitado al procesar

            // --- 3. Condición de Éxito ---
            if (ultimoNodo.equals(fin)) {
                return caminoActual; // ¡Encontrado!
            }

            // --- 4. Explorar Vecinos ---
            List<String> vecinos = grafo.getOrDefault(ultimoNodo, Collections.emptyList());
            // Iteramos en reversa para que el Stack explore en orden "natural" (opcional)
            Collections.reverse(vecinos);
            for (String vecino : vecinos) {
                if (!visitados.contains(vecino)) {
                    // Crear el nuevo camino y agregarlo a la pila
                    List<String> nuevoCamino = new ArrayList<>(caminoActual);
                    nuevoCamino.add(vecino);
                    pila.push(nuevoCamino);
                }
            }
        }

        return null; // No se encontró camino
    }
}
