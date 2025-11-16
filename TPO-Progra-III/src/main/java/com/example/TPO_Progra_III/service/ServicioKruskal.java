package com.example.TPO_Progra_III.service;

import com.example.TPO_Progra_III.model.GrafoKruskal;
import com.example.TPO_Progra_III.model.ProveedorKruskal;
import com.example.TPO_Progra_III.model.RutaAbastecimientoKruskal;
import com.example.TPO_Progra_III.repository.ProveedorRepository; // <- NUEVO
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServicioKruskal {

    // --- ¡NUEVO! Inyectamos el repositorio ---
    private final ProveedorRepository proveedorRepository;

    // Clase interna helper para devolver el grafo y el mapa de nombres
    private record GrafoConstruido(GrafoKruskal grafo, String[] nombres) {}

    public ServicioKruskal(ProveedorRepository proveedorRepository) { // <- NUEVO
        this.proveedorRepository = proveedorRepository;
    }

    /**
     * Calcula la red de abastecimiento mínima entre proveedores (árbol mínimo).
     */
    public Map<String, Object> calcularRedAbastecimiento() {
        // 1. Construir el grafo desde Neo4j
        GrafoConstruido datos = construirGrafoDesdeNeo4j(); // <- MÉTODO ACTUALIZADO
        GrafoKruskal grafo = datos.grafo;
        String[] NOMBRES_NODOS = datos.nombres;

        if (NOMBRES_NODOS.length == 0) {
            return Collections.emptyMap();
        }

        // 2. Ejecutar Kruskal
        List<GrafoKruskal.Arista> mst = grafo.kruskalMST();

        // 3. Formatear la respuesta (tu código original)
        List<Map<String, Object>> conexiones = new ArrayList<>();
        int total = 0;

        for (GrafoKruskal.Arista arista : mst) {
            Map<String, Object> info = new LinkedHashMap<>();
            info.put("origen", NOMBRES_NODOS[arista.origen]);
            info.put("destino", NOMBRES_NODOS[arista.destino]);
            info.put("distanciaKm", arista.peso);
            conexiones.add(info);
            total += arista.peso;
        }

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("algoritmo", "Kruskal - Red de Abastecimiento");
        respuesta.put("conexionesSeleccionadas", conexiones);
        respuesta.put("distanciaTotalKm", total);

        return respuesta;
    }

    /**
     * Método privado que consulta Neo4j y prepara el grafo.
     */
    private GrafoConstruido construirGrafoDesdeNeo4j() {
        // 1. LEER NODOS DESDE NEO4J
        List<ProveedorKruskal> nodos = proveedorRepository.findAll(); // <- USAMOS REPO
        int n = nodos.size();

        GrafoKruskal grafo = new GrafoKruskal(n);

        // 2. MAPEAR NODOS A ÍNDICES
        Map<String, Integer> mapaNodos = new HashMap<>();
        String[] NOMBRES_NODOS = new String[n];

        int i = 0;
        for (ProveedorKruskal nodo : nodos) {
            mapaNodos.put(nodo.getNombre(), i);
            NOMBRES_NODOS[i] = nodo.getNombre();
            i++;
        }

        // 3. AGREGAR ARISTAS AL GRAFO
        Set<String> aristasAgregadas = new HashSet<>();

        for (ProveedorKruskal origen : nodos) {
            int origenIdx = mapaNodos.get(origen.getNombre());

            for (RutaAbastecimientoKruskal ruta : origen.getRutas()) {
                ProveedorKruskal destino = ruta.getProveedor();
                int peso = ruta.getPeso();

                if (mapaNodos.containsKey(destino.getNombre())) {
                    int destinoIdx = mapaNodos.get(destino.getNombre());

                    String claveArista = Math.min(origenIdx, destinoIdx) + "-" + Math.max(origenIdx, destinoIdx);

                    if (!aristasAgregadas.contains(claveArista)) {
                        grafo.agregarArista(origenIdx, destinoIdx, peso);
                        aristasAgregadas.add(claveArista);

                        System.out.println("Ruta agregada: " + origen.getNombre() + " ↔ " + destino.getNombre() + " | Distancia: " + peso + " km");
                    }
                }
            }
        }
        return new GrafoConstruido(grafo, NOMBRES_NODOS);
    }
}