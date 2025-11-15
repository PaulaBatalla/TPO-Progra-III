package com.example.TPO_Progra_III.controller;

import com.example.TPO_Progra_III.service.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rutas")
public class RutaController {

    @Autowired
    private RutaService rutaService;

    /**
     * Endpoint para encontrar un camino entre dos ubicaciones del restaurante.
     * @param desde El nodo de inicio (ej. "Cocina")
     * @param hasta El nodo de fin (ej. "Freezer")
     * @param modo El algoritmo a usar ("bfs" o "dfs"). Default es "bfs".
     * @return Una lista de strings representando el camino.
     */
    @GetMapping("/camino")
    public ResponseEntity<List<String>> encontrarCamino(
            @RequestParam String desde,
            @RequestParam String hasta,
            @RequestParam(defaultValue = "bfs") String modo) {

        List<String> camino;

        if ("dfs".equalsIgnoreCase(modo)) {
            // Llama al servicio de DFS
            camino = rutaService.encontrarCaminoDfs(desde, hasta);
        } else {
            // Llama al servicio de BFS (por defecto)
            camino = rutaService.encontrarCaminoBfs(desde, hasta);
        }

        if (camino == null || camino.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404 si no hay camino
        }

        return ResponseEntity.ok(camino); // 200 con el camino
    }
}