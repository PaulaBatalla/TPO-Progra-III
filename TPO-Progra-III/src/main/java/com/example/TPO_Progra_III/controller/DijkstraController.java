package com.example.TPO_Progra_III.controller;

import com.example.TPO_Progra_III.service.ServicioDijkstra;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dijkstra")
@CrossOrigin(origins = "*", allowCredentials = "false")
public class DijkstraController {

    private final ServicioDijkstra servicioDijkstra;

    public DijkstraController(ServicioDijkstra servicioDijkstra) {
        this.servicioDijkstra = servicioDijkstra;
    }

    /**
     * Endpoint JSON — devuelve los caminos mínimos en formato estructurado (para el frontend).
     */
    @GetMapping("/diccionario")
    public List<Map<String, Object>> mostrarDesdeDiccionario() {
        return servicioDijkstra.calcularCaminosDesdeDiccionario();
    }

    /**
     * Endpoint de texto — devuelve los caminos formateados como texto para leerlos en el navegador.
     * Ideal para mostrar o testear sin usar Postman.
     */
    @GetMapping(value = "/diccionario/texto", produces = "text/html; charset=UTF-8")
    public String mostrarComoTexto() {
        List<Map<String, Object>> caminos = servicioDijkstra.calcularCaminosDesdeDiccionario();

        StringBuilder sb = new StringBuilder();
        sb.append("<h2>🔹Delivery desde el Restaurante con Dijkstra:</h2>");

        for (Map<String, Object> camino : caminos) {
            sb.append("<b>Destino:</b> ").append(camino.get("destino")).append("<br>");
            sb.append("<b>Recorrido:</b> ").append(camino.get("recorrido")).append("<br>");
            sb.append("<b>Distancia:</b> ").append(camino.get("distancia")).append(" cuadras<br><br>");
        }

        return sb.toString();
    }
}
