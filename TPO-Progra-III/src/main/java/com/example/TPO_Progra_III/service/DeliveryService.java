package com.example.TPO_Progra_III.service;

import com.example.TPO_Progra_III.dto.OptimizarResponseDTO;
import com.example.TPO_Progra_III.dto.PedidoDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryService {

    private static class SolucionOptima {
        double maxBeneficio = 0.0;
        double pesoOptimo = 0.0;
        List<PedidoDTO> pedidos = new ArrayList<>();
    }

    /**
     * Método público que inicia el proceso de optimización.
     */
    public OptimizarResponseDTO encontrarOptimo(List<PedidoDTO> pedidos, double capacidadMaxima) {

        SolucionOptima solucion = new SolucionOptima();
        List<PedidoDTO> combinacionActual = new ArrayList<>();

        double[] beneficioRestante = new double[pedidos.size() + 1];
        for (int i = pedidos.size() - 1; i >= 0; i--) {
            beneficioRestante[i] = pedidos.get(i).beneficio() + beneficioRestante[i+1];
        }
        // -------------------------------------------

        branchAndBoundHelper(
                pedidos,
                capacidadMaxima,
                0,
                0.0,
                0.0,
                combinacionActual,
                solucion,
                beneficioRestante
        );

        return new OptimizarResponseDTO(
                solucion.pedidos,
                solucion.pesoOptimo,
                solucion.maxBeneficio
        );
    }

    /**
     * El núcleo del algoritmo de Ramificación y Poda (Branch & Bound).
     */
    private void branchAndBoundHelper(
            List<PedidoDTO> pedidos,
            double capacidadMaxima,
            int indice,
            double pesoActual,
            double beneficioActual,
            List<PedidoDTO> combinacionActual,
            SolucionOptima solucionOptima,
            double[] beneficioRestante) {

        // --- 1. ACTUALIZAR MEJOR SOLUCIÓN ---
        if (beneficioActual > solucionOptima.maxBeneficio) {
            solucionOptima.maxBeneficio = beneficioActual;
            solucionOptima.pesoOptimo = pesoActual;
            solucionOptima.pedidos = new ArrayList<>(combinacionActual);
        }

        // --- 2. CASO BASE (Rama Agotada) ---
        if (indice == pedidos.size()) {
            return;
        }

        // --- 3. PODA POR OPTIMALIDAD (Bound) ---
        double cotaSuperior = beneficioActual + beneficioRestante[indice];

        if (cotaSuperior <= solucionOptima.maxBeneficio) {
            return; // ¡PODA!
        }

        // --- 4. RAMIFICACIÓN (Branching) ---

        PedidoDTO pedidoActual = pedidos.get(indice);

        if (pesoActual + pedidoActual.peso() <= capacidadMaxima) {

            combinacionActual.add(pedidoActual);

            branchAndBoundHelper(
                    pedidos, capacidadMaxima,
                    indice + 1, // Siguiente índice
                    pesoActual + pedidoActual.peso(), // Actualizar peso
                    beneficioActual + pedidoActual.beneficio(), // Actualizar beneficio
                    combinacionActual, solucionOptima, beneficioRestante
            );

            combinacionActual.remove(combinacionActual.size() - 1);
        }

        branchAndBoundHelper(
                pedidos, capacidadMaxima,
                indice + 1, // Siguiente índice
                pesoActual, // El peso no cambia
                beneficioActual, // El beneficio no cambia
                combinacionActual, solucionOptima, beneficioRestante
        );
    }
}
