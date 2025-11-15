package com.example.TPO_Progra_III.service;

import com.example.TPO_Progra_III.dto.PedidoDTO;
import com.example.TPO_Progra_III.dto.OptimizarResponseDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DynamicProgrammingService {

    public OptimizarResponseDTO resolverDP(List<PedidoDTO> pedidos, int capacidadMaxima) {

        int n = pedidos.size();
        int W = capacidadMaxima;

        double[][] dp = new double[n + 1][W + 1];

        for (int i = 1; i <= n; i++) {
            PedidoDTO p = pedidos.get(i - 1);
            int peso = (int) p.peso();
            double beneficio = p.beneficio();

            for (int w = 0; w <= W; w++) {

                if (peso > w) {
                    dp[i][w] = dp[i - 1][w];
                } else {
                    dp[i][w] = Math.max(
                            dp[i - 1][w],
                            dp[i - 1][w - peso] + beneficio
                    );
                }
            }
        }

        // Reconstrucción de solución
        List<PedidoDTO> seleccionados = new ArrayList<>();
        int w = W;

        for (int i = n; i > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                PedidoDTO p = pedidos.get(i - 1);
                seleccionados.add(p);
                w -= (int) p.peso();
            }
        }

        double pesoTotal = seleccionados.stream().mapToDouble(PedidoDTO::peso).sum();
        double beneficioTotal = dp[n][W];

        return new OptimizarResponseDTO(seleccionados, pesoTotal, beneficioTotal);
    }
}
