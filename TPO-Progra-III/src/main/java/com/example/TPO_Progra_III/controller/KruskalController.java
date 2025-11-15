package com.example.TPO_Progra_III.controller;

import com.example.TPO_Progra_III.service.ServicioKruskal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/kruskal")
@CrossOrigin(origins = "*", allowCredentials = "false")
public class KruskalController {

    private final ServicioKruskal servicioKruskal;

    public KruskalController(ServicioKruskal servicioKruskal) {
        this.servicioKruskal = servicioKruskal;
    }

    /**
     * Endpoint JSON — muestra la red mínima de abastecimiento.
     */
    @GetMapping("/red-abastecimiento")
    public Map<String, Object> obtenerRedAbastecimiento() {
        return servicioKruskal.calcularRedAbastecimiento();
    }

    /**
     * Endpoint texto — versión visual para navegador.
     */
    @GetMapping(value = "/red-abastecimiento/texto", produces = "text/html; charset=UTF-8")
    public String mostrarRedAbastecimientoTexto() {
        Map<String, Object> resultado = servicioKruskal.calcularRedAbastecimiento();

        var conexiones = (List<Map<String, Object>>) resultado.get("conexionesSeleccionadas");
        int total = (int) resultado.get("distanciaTotalKm");

        StringBuilder sb = new StringBuilder();
        sb.append("<h2>🔹Red de Abastecimiento con Kruskal:</h2>");
        sb.append("<p>Conexiones mínimas entre proveedores y el depósito</p><br>");

        for (Map<String, Object> conexion : conexiones) {
            sb.append("🏭 <b>").append(conexion.get("origen"))
                    .append("</b> ↔ <b>").append(conexion.get("destino"))
                    .append("</b> | Distancia: ").append(conexion.get("distanciaKm"))
                    .append(" km<br>");
        }

        sb.append("<br><b>Distancia total mínima:</b> ").append(total).append(" km");
        return sb.toString();
    }
}

