package com.example.TPO_Progra_III.controller;

import com.example.TPO_Progra_III.dto.CombinacionRequestDTO;
import com.example.TPO_Progra_III.dto.PlatoDTO;
import com.example.TPO_Progra_III.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * Endpoint que recibe un precio objetivo y una lista de platos,
     * y devuelve todas las combinaciones de esos platos que suman
     * exactamente ese precio.
     */
    @PostMapping("/combinaciones")
    public ResponseEntity<List<List<PlatoDTO>>> encontrarCombinaciones(
            @RequestBody CombinacionRequestDTO request) {

        // 1. Recibe el DTO del frontend
        List<PlatoDTO> platos = request.getPlatosDisponibles();
        double objetivo = request.getPrecioObjetivo();

        // 2. Llama al servicio para que haga el trabajo
        List<List<PlatoDTO>> soluciones = menuService.encontrarCombinaciones(platos, objetivo);

        // 3. Devuelve la respuesta
        if (soluciones.isEmpty()) {
            // Es buena práctica devolver 204 No Content si no hay resultados
            return ResponseEntity.noContent().build();
        }
        // Devuelve 200 OK con la lista de soluciones en el body
        return ResponseEntity.ok(soluciones);
    }
}
