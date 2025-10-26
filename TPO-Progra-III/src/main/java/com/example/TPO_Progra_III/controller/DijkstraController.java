package com.example.TPO_Progra_III.controller;

import com.example.TPO_Progra_III.dto.DijkstraResultado;
import com.example.TPO_Progra_III.service.ServicioDijkstra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dijkstra")
public class DijkstraController {

    private final ServicioDijkstra servicioDijkstra;

    @Autowired
    public DijkstraController(ServicioDijkstra servicioDijkstra) {
        this.servicioDijkstra = servicioDijkstra;
    }

    /**
     * Endpoint para calcular los caminos más cortos desde un nodo de origen.
     * * Por ejemplo: GET /api/dijkstra/caminos?origen=0
     * Donde 0 es el índice del 'Restaurante'.
     */
    @GetMapping("/caminos")
    public List<DijkstraResultado> obtenerCaminosMinimos(@RequestParam int origen) {

        List<DijkstraResultado> resultados = servicioDijkstra.calcularCaminosMinimos(origen);

        return resultados;
    }

}