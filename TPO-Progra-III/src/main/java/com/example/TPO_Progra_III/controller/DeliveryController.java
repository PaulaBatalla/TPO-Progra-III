package com.example.TPO_Progra_III.controller;

import com.example.TPO_Progra_III.dto.OptimizarRequestDTO;
import com.example.TPO_Progra_III.dto.OptimizarResponseDTO;
import com.example.TPO_Progra_III.service.DeliveryService;
import com.example.TPO_Progra_III.service.GreedyService;
import com.example.TPO_Progra_III.service.DynamicProgrammingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private GreedyService greedyService;

    @Autowired
    private DynamicProgrammingService dpService;

    /**
     * Branch & Bound
     */
    @PostMapping("/optimizar")
    public ResponseEntity<OptimizarResponseDTO> optimizarEntrega(
            @RequestBody OptimizarRequestDTO request) {

        OptimizarResponseDTO solucionOptima = deliveryService.encontrarOptimo(
                request.getPedidos(),
                request.getCapacidadMaxima()
        );

        return ResponseEntity.ok(solucionOptima);
    }

    /**
     * Algoritmo Greedy
     * Selecciona pedidos según el mejor ratio beneficio/peso.
     */
    @PostMapping("/optimizar-greedy")
    public ResponseEntity<OptimizarResponseDTO> optimizarGreedy(
            @RequestBody OptimizarRequestDTO request) {

        OptimizarResponseDTO solucionGreedy = greedyService.resolverGreedy(
                request.getPedidos(),
                request.getCapacidadMaxima()
        );

        return ResponseEntity.ok(solucionGreedy);
    }

    /**
     *  Programación Dinámica
     * Encuentra la solución óptima mediante tabla DP.
     */
    @PostMapping("/optimizar-dp")
    public ResponseEntity<OptimizarResponseDTO> optimizarDP(
            @RequestBody OptimizarRequestDTO request) {

        int capacidadEntera = (int) request.getCapacidadMaxima(); // DP requiere enteros

        OptimizarResponseDTO solucionDP = dpService.resolverDP(
                request.getPedidos(),
                capacidadEntera
        );

        return ResponseEntity.ok(solucionDP);
    }
}
