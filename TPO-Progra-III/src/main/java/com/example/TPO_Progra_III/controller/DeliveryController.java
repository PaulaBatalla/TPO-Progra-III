package com.example.TPO_Progra_III.controller;

import com.example.TPO_Progra_III.dto.OptimizarRequestDTO;
import com.example.TPO_Progra_III.dto.OptimizarResponseDTO;
import com.example.TPO_Progra_III.service.DeliveryService;
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

    /**
     * Endpoint para optimizar la carga de un repartidor.
     * Recibe una capacidad máxima y una lista de pedidos.
     * Devuelve la combinación de pedidos que maximiza el beneficio
     * sin exceder la capacidad.
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
}