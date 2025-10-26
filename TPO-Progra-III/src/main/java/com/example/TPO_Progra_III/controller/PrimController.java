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
     */
    @GetMapping("/mst")
    public PrimResultado obtenerMST() {

        return servicioPrim.calcularMST();
    }
}