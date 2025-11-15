/*
package com.example.TPO_Progra_III.controller;

import com.example.TPO_Progra_III.dto.PrimResultado;
import com.example.TPO_Progra_III.service.ServicioPrim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prim")
public class PrimController {

    private final ServicioPrim servicioPrim;

    @Autowired
    public PrimController(ServicioPrim servicioPrim) {
        this.servicioPrim = servicioPrim;
    }

    /**
     * Endpoint para calcular el Árbol de Recubrimiento Mínimo (MST) usando Prim.
     * URL: GET /api/prim/mst

    @GetMapping("/mst")
    public PrimResultado obtenerMST() {

        return servicioPrim.calcularMST();
    }
}
*/

package com.example.TPO_Progra_III.controller;

import com.example.TPO_Progra_III.dto.ConexionPrimDTO;
import com.example.TPO_Progra_III.dto.PrimResultado;
import com.example.TPO_Progra_III.service.ServicioPrim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prim")
@CrossOrigin(origins = "*", allowCredentials = "false")
public class PrimController {

    private final ServicioPrim servicioPrim;

    @Autowired
    public PrimController(ServicioPrim servicioPrim) {
        this.servicioPrim = servicioPrim;
    }

    /**
     * Endpoint JSON: devuelve el MST como lista de conexiones + costo total.
     * Ejemplo: GET /api/prim/mst
     */
    @GetMapping("/mst")
    public PrimResultado obtenerMST() {
        return servicioPrim.calcularMST();
    }

    /**
     * Endpoint texto: muestra las conexiones del MST formateadas con HTML.
     * Ejemplo: GET /api/prim/mst/texto
     */
    @GetMapping(value = "/mst/texto", produces = "text/html; charset=UTF-8")
    public String mostrarMSTComoTexto() {
        PrimResultado resultado = servicioPrim.calcularMST();
        List<ConexionPrimDTO> conexiones = resultado.getConexiones();
        int costoTotal = resultado.getCostoTotal();

        StringBuilder sb = new StringBuilder();
        sb.append("<h2>🔹Conexiones óptimas entre restaurante-sucursales-proveedor con Prim:</h2>");

        for (ConexionPrimDTO conexion : conexiones) {
            sb.append("🏠 <b>").append(conexion.getOrigen())
                    .append("</b> ↔ <b>").append(conexion.getDestino())
                    .append("</b> | Distancia: ").append(conexion.getPeso())
                    .append(" km<br>");
        }

        sb.append("<br><b>Distancia total mínima:</b> ").append(costoTotal);

        return sb.toString();
    }
}
