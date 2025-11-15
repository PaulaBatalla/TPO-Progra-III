package com.example.TPO_Progra_III.service;

// import com.uade.tpo.demo.repository.UbicacionRepository; // Importarías tu Repo Neo4j
import com.example.TPO_Progra_III.model.Ubicacion;
import com.example.TPO_Progra_III.repository.UbicacionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RutaService {

    // ¡Ahora inyectamos el repositorio real!
    private final UbicacionRepository ubicacionRepository;

    public RutaService(UbicacionRepository ubicacionRepository) {
        this.ubicacionRepository = ubicacionRepository;
    }

    /**
     * Esta función AHORA SÍ CONSTRUYE EL GRAFO DESDE NEO4J
     */
    private Map<String, List<String>> getGrafo() {
        Map<String, List<String>> adj = new HashMap<>();

        // Traemos todas las ubicaciones de la BBDD
        List<Ubicacion> ubicaciones = ubicacionRepository.findAll();

        for (Ubicacion u : ubicaciones) {
            List<String> vecinos = new ArrayList<>();
            // Por cada conexión de la ubicación, agregamos su nombre a la lista
            if (u.getConexiones() != null) {
                for (Ubicacion conexion : u.getConexiones()) {
                    vecinos.add(conexion.getNombre());
                }
            }
            adj.put(u.getNombre(), vecinos);
        }
        return adj;
    }

    /**
     * 🧠 ALGORITMO BFS (Búsqueda en Anchura)
     * (Este método no cambia, porque ahora getGrafo() le da los datos reales)
     */
    public List<String> encontrarCaminoBfs(String inicio, String fin) {
        Map<String, List<String>> grafo = getGrafo();

        Queue<List<String>> cola = new LinkedList<>();
        Set<String> visitados = new HashSet<>();

        List<String> caminoInicial = new ArrayList<>();
        caminoInicial.add(inicio);
        cola.add(caminoInicial);
        visitados.add(inicio);

        while (!cola.isEmpty()) {
            List<String> caminoActual = cola.poll();
            String ultimoNodo = caminoActual.get(caminoActual.size() - 1);

            if (ultimoNodo.equals(fin)) {
                return caminoActual;
            }

            List<String> vecinos = grafo.getOrDefault(ultimoNodo, Collections.emptyList());
            for (String vecino : vecinos) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    List<String> nuevoCamino = new ArrayList<>(caminoActual);
                    nuevoCamino.add(vecino);
                    cola.add(nuevoCamino);
                }
            }
        }
        return null;
    }

    /**
     * 🧠 ALGORITMO DFS (Búsqueda en Profundidad)
     * (Este método tampoco cambia)
     */
    public List<String> encontrarCaminoDfs(String inicio, String fin) {
        Map<String, List<String>> grafo = getGrafo();

        Stack<List<String>> pila = new Stack<>();
        Set<String> visitados = new HashSet<>();

        List<String> caminoInicial = new ArrayList<>();
        caminoInicial.add(inicio);
        pila.push(caminoInicial);

        while (!pila.isEmpty()) {
            List<String> caminoActual = pila.pop();
            String ultimoNodo = caminoActual.get(caminoActual.size() - 1);

            if (visitados.contains(ultimoNodo)) {
                continue;
            }
            visitados.add(ultimoNodo);

            if (ultimoNodo.equals(fin)) {
                return caminoActual;
            }

            List<String> vecinos = grafo.getOrDefault(ultimoNodo, Collections.emptyList());
            Collections.reverse(vecinos);
            for (String vecino : vecinos) {
                if (!visitados.contains(vecino)) {
                    List<String> nuevoCamino = new ArrayList<>(caminoActual);
                    nuevoCamino.add(vecino);
                    pila.push(nuevoCamino);
                }
            }
        }
        return null;
    }
}
