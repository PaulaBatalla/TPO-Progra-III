package com.example.TPO_Progra_III.service;

import com.example.TPO_Progra_III.model.GrafoKruskal;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServicioKruskal {

    private final String[] NOMBRES_NODOS = {
            "Proveedor Carnes", "Proveedor Vegetales", "Proveedor Bebidas",
            "Proveedor Panificados", "Depósito Central"
    };

    /**
     * Calcula la red de abastecimiento mínima entre proveedores (árbol mínimo).
     */
    public Map<String, Object> calcularRedAbastecimiento() {
        GrafoKruskal grafo = construirGrafoDesdeDiccionario();

        List<GrafoKruskal.Arista> mst = grafo.kruskalMST();

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

    private GrafoKruskal construirGrafoDesdeDiccionario() {
        List<Map<String, Object>> conexiones = List.of(
                Map.of("origen", "Proveedor Carnes", "destino", "Proveedor Vegetales", "peso", 7),
                Map.of("origen", "Proveedor Carnes", "destino", "Proveedor Bebidas", "peso", 3),
                Map.of("origen", "Proveedor Carnes", "destino", "Proveedor Panificados", "peso", 8),
                Map.of("origen", "Proveedor Vegetales", "destino", "Proveedor Panificados", "peso", 2),
                Map.of("origen", "Proveedor Bebidas", "destino", "Depósito Central", "peso", 5),
                Map.of("origen", "Proveedor Panificados", "destino", "Depósito Central", "peso", 6),
                Map.of("origen", "Proveedor Vegetales", "destino", "Depósito Central", "peso", 4)
        );

        Map<String, Integer> mapaNodos = new HashMap<>();
        for (int i = 0; i < NOMBRES_NODOS.length; i++) {
            mapaNodos.put(NOMBRES_NODOS[i], i);
        }

        GrafoKruskal grafo = new GrafoKruskal(NOMBRES_NODOS.length);

        for (Map<String, Object> conexion : conexiones) {
            String origen = (String) conexion.get("origen");
            String destino = (String) conexion.get("destino");
            int peso = (int) conexion.get("peso");

            int origenIdx = mapaNodos.get(origen);
            int destinoIdx = mapaNodos.get(destino);

            grafo.agregarArista(origenIdx, destinoIdx, peso);

            System.out.println("Ruta agregada: " + origen + " ↔ " + destino + " | Distancia: " + peso + " km");
        }

        return grafo;
    }
}
