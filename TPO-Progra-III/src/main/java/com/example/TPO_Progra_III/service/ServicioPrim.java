package com.example.TPO_Progra_III.service;

import com.example.TPO_Progra_III.dto.ConexionPrimDTO;
import com.example.TPO_Progra_III.dto.PrimResultado;
import com.example.TPO_Progra_III.model.prim.GrafoPrim;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServicioPrim {

    private final String[] NOMBRES_NODOS = {
            "Restaurante", "Sucursal Zona Norte", "Sucursal Zona Sur", "Proveedor", "Depósito"
    };

    /**
     * Calcula el Árbol de Recubrimiento Mínimo (MST) usando Prim,
     * con los datos cargados desde un diccionario en memoria.
     */
    public PrimResultado calcularMST() {
        GrafoPrim grafo = construirGrafoDesdeDiccionario();

        GrafoPrim.PrimResultadoCompleto resultados = grafo.prim();
        int[] costo = resultados.costo;
        int[] padre = resultados.padre;

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

    private GrafoPrim construirGrafoDesdeDiccionario() {
        List<Map<String, Object>> conexiones = List.of(
                Map.of("origen", "Restaurante", "destino", "Sucursal Zona Norte", "peso", 10),
                Map.of("origen", "Restaurante", "destino", "Sucursal Zona Sur", "peso", 5),
                Map.of("origen", "Sucursal Zona Norte", "destino", "Proveedor", "peso", 2),
                Map.of("origen", "Sucursal Zona Norte", "destino", "Depósito", "peso", 8),
                Map.of("origen", "Sucursal Zona Sur", "destino", "Proveedor", "peso", 4),
                Map.of("origen", "Proveedor", "destino", "Depósito", "peso", 3)
        );

        Map<String, Integer> mapaNodos = new HashMap<>();
        for (int i = 0; i < NOMBRES_NODOS.length; i++) {
            mapaNodos.put(NOMBRES_NODOS[i], i);
        }

        GrafoPrim grafo = new GrafoPrim(NOMBRES_NODOS.length);

        for (Map<String, Object> conexion : conexiones) {
            String origen = (String) conexion.get("origen");
            String destino = (String) conexion.get("destino");
            int peso = (int) conexion.get("peso");

            int origenIdx = mapaNodos.get(origen);
            int destinoIdx = mapaNodos.get(destino);

            grafo.agregarArista(origenIdx, destinoIdx, peso);

            System.out.println("Conexión agregada → " + origen + " ↔ " + destino + " | Costo: " + peso);
        }

        return grafo;
    }
}
