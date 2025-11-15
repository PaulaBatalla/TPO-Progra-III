package com.example.TPO_Progra_III.service;

import com.example.TPO_Progra_III.dto.OptimizarResponseDTO;
import com.example.TPO_Progra_III.dto.PedidoDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryService {

    // Clase interna para guardar el estado de la mejor solución encontrada.
    // Usamos una clase "Holder" para poder modificarla dentro de la recursión.
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

        // --- PRE-CÁLCULO PARA LA COTA SUPERIOR ---
        // Para podar eficientemente, necesitamos un "estimado optimista" (cota).
        // Un estimado simple es la suma del beneficio restante.
        // beneficioRestante[i] = beneficio de todos los pedidos desde i hasta el final.
        double[] beneficioRestante = new double[pedidos.size() + 1];
        for (int i = pedidos.size() - 1; i >= 0; i--) {
            beneficioRestante[i] = pedidos.get(i).beneficio() + beneficioRestante[i+1];
        }
        // -------------------------------------------

        branchAndBoundHelper(
                pedidos,
                capacidadMaxima,
                0, // Inicia en el índice 0
                0.0, // Peso actual 0
                0.0, // Beneficio actual 0
                combinacionActual,
                solucion,
                beneficioRestante
        );

        // Al final, 'solucion' tiene la mejor combinación. La formateamos y devolvemos.
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
        // Cada nodo es una solución parcial válida.
        // Si la actual es mejor que la global, la guardamos.
        if (beneficioActual > solucionOptima.maxBeneficio) {
            solucionOptima.maxBeneficio = beneficioActual;
            solucionOptima.pesoOptimo = pesoActual;
            solucionOptima.pedidos = new ArrayList<>(combinacionActual);
        }

        // --- 2. CASO BASE (Rama Agotada) ---
        // Si ya no hay más pedidos por considerar, retrocedemos.
        if (indice == pedidos.size()) {
            return;
        }

        // --- 3. PODA POR OPTIMALIDAD (Bound) ---
        // Esta es la clave de Branch & Bound.
        // Cota Superior (Optimista): beneficioActual + todo el beneficio restante.
        double cotaSuperior = beneficioActual + beneficioRestante[indice];

        // Si nuestro mejor escenario posible (cotaSuperior) no supera
        // la mejor solución que YA tenemos (solucionOptima.maxBeneficio),
        // entonces no tiene sentido seguir explorando esta rama.
        if (cotaSuperior <= solucionOptima.maxBeneficio) {
            return; // ¡PODA!
        }

        // --- 4. RAMIFICACIÓN (Branching) ---

        PedidoDTO pedidoActual = pedidos.get(indice);

        // --- RAMA 1: INCLUIR el pedido actual ---

        // PODA POR FACTIBILIDAD: Solo exploramos si el pedido cabe.
        if (pesoActual + pedidoActual.peso() <= capacidadMaxima) {

            // 1. Actuar
            combinacionActual.add(pedidoActual);

            // 2. Recur
            branchAndBoundHelper(
                    pedidos, capacidadMaxima,
                    indice + 1, // Siguiente índice
                    pesoActual + pedidoActual.peso(), // Actualizar peso
                    beneficioActual + pedidoActual.beneficio(), // Actualizar beneficio
                    combinacionActual, solucionOptima, beneficioRestante
            );

            // 3. Retroceder (Backtrack)
            combinacionActual.remove(combinacionActual.size() - 1);
        }

        // --- RAMA 2: NO INCLUIR el pedido actual ---

        // Exploramos la rama donde decidimos "no llevar" este pedido.
        branchAndBoundHelper(
                pedidos, capacidadMaxima,
                indice + 1, // Siguiente índice
                pesoActual, // El peso no cambia
                beneficioActual, // El beneficio no cambia
                combinacionActual, solucionOptima, beneficioRestante
        );
    }
}
