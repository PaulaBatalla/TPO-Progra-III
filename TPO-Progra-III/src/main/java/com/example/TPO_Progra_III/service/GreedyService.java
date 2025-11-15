package com.example.TPO_Progra_III.service;

import com.example.TPO_Progra_III.dto.PedidoDTO;
import com.example.TPO_Progra_III.dto.OptimizarResponseDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class GreedyService {

    public OptimizarResponseDTO resolverGreedy(List<PedidoDTO> pedidos, double capacidadMaxima) {

        // Ordenar por mayor beneficio/peso (ratio)
        List<PedidoDTO> ordenados = pedidos.stream()
                .sorted(Comparator.comparingDouble(
                        p -> - (p.beneficio() / p.peso())
                ))
                .toList();

        double pesoActual = 0;
        double beneficioTotal = 0;
        List<PedidoDTO> seleccionados = new ArrayList<>();

        for (PedidoDTO p : ordenados) {
            if (pesoActual + p.peso() <= capacidadMaxima) {
                pesoActual += p.peso();
                beneficioTotal += p.beneficio();
                seleccionados.add(p);
            }
        }

        return new OptimizarResponseDTO(seleccionados, pesoActual, beneficioTotal);
    }
}
