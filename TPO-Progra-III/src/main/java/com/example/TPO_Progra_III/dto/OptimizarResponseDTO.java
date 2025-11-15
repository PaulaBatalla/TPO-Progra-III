package com.example.TPO_Progra_III.dto;

import java.util.List;

public record OptimizarResponseDTO(
        List<PedidoDTO> pedidosOptimos,
        double pesoTotal,
        double beneficioTotal
) {}