package com.example.TPO_Progra_III.service;

import com.example.TPO_Progra_III.dto.ConexionPrimDTO;
import com.example.TPO_Progra_III.dto.PrimResultado;
import com.example.TPO_Progra_III.model.prim.ConexionPrim;
import com.example.TPO_Progra_III.model.prim.GrafoPrim;
import com.example.TPO_Progra_III.model.prim.SucursalPrim;
import com.example.TPO_Progra_III.repository.SucursalRepository; // <- NUEVO
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServicioPrim {

    private final SucursalRepository sucursalRepository;

    public ServicioPrim(SucursalRepository sucursalRepository) {
        this.sucursalRepository = sucursalRepository;
    }

    private record GrafoConstruido(GrafoPrim grafo, String[] nombres) {}

    public PrimResultado calcularMST() {
        GrafoConstruido datos = construirGrafoDesdeNeo4j();
        GrafoPrim grafo = datos.grafo;
        String[] NOMBRES_NODOS = datos.nombres;

        if (NOMBRES_NODOS.length == 0) {
            return new PrimResultado(Collections.emptyList(), 0);
        }

        // Ejecutar Prim
        GrafoPrim.PrimResultadoCompleto resultados = grafo.prim();
        int[] costo = resultados.costo;
        int[] padre = resultados.padre;

        // Formatear la respuesta (igual que antes)
        List<ConexionPrimDTO> conexionesDTO = new ArrayList<>();
        int costoTotal = 0;

        for (int i = 1; i < NOMBRES_NODOS.length; i++) {
            if (padre[i] != -1) {
                conexionesDTO.add(new ConexionPrimDTO(
                        NOMBRES_NODOS[padre[i]],
                        NOMBRES_NODOS[i],
                        costo[i]
                ));
                costoTotal += costo[i];
            }
        }

        return new PrimResultado(conexionesDTO, costoTotal);
    }

    /**
     * Método privado que consulta Neo4j y prepara el grafo.
     */
    private GrafoConstruido construirGrafoDesdeNeo4j() {
        // 1. LEER NODOS DESDE NEO4J
        List<SucursalPrim> nodos = sucursalRepository.findAll();
        int n = nodos.size();

        GrafoPrim grafo = new GrafoPrim(n);

        // 2. MAPEAR NODOS A ÍNDICES
        Map<String, Integer> mapaNodos = new HashMap<>();
        String[] NOMBRES_NODOS = new String[n];

        int i = 0;
        for (SucursalPrim nodo : nodos) {
            mapaNodos.put(nodo.getNombre(), i);
            NOMBRES_NODOS[i] = nodo.getNombre();
            i++;
        }

        // 3. AGREGAR ARISTAS AL GRAFO
        // Usamos un Set para evitar agregar la arista A->B y B->A duplicada,
        Set<String> aristasAgregadas = new HashSet<>();

        for (SucursalPrim origen : nodos) {
            int origenIdx = mapaNodos.get(origen.getNombre());

            for (ConexionPrim conexion : origen.getConexiones()) {
                SucursalPrim destino = conexion.getSucursal();
                int peso = conexion.getPeso();

                if (mapaNodos.containsKey(destino.getNombre())) {
                    int destinoIdx = mapaNodos.get(destino.getNombre());

                    // Clave para grafo NO DIRIGIDO:
                    // Creamos una clave única para la arista (ej. "0-1" o "1-0")
                    String claveArista = Math.min(origenIdx, destinoIdx) + "-" + Math.max(origenIdx, destinoIdx);

                    if (!aristasAgregadas.contains(claveArista)) {
                        grafo.agregarArista(origenIdx, destinoIdx, peso);
                        aristasAgregadas.add(claveArista);

                        System.out.println("Conexión agregada → " + origen.getNombre() + " ↔ " + destino.getNombre() + " | Costo: " + peso);
                    }
                }
            }
        }

        return new GrafoConstruido(grafo, NOMBRES_NODOS);
    }
}