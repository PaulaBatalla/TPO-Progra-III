package com.example.TPO_Progra_III.service;

import org.springframework.stereotype.Service;

import com.example.TPO_Progra_III.dto.PlatoDTO;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    public List<List<PlatoDTO>> encontrarCombinaciones(List<PlatoDTO> platosDisponibles, double precioObjetivo) {

        List<List<PlatoDTO>> solucionesEncontradas = new ArrayList<>();
        List<PlatoDTO> combinacionActual = new ArrayList<>();

        backtrackHelper(
                platosDisponibles,
                precioObjetivo,
                solucionesEncontradas,
                combinacionActual,
                0.0,
                0
        );

        return solucionesEncontradas;
    }

    private void backtrackHelper(
            List<PlatoDTO> platos,
            double objetivo,
            List<List<PlatoDTO>> soluciones,
            List<PlatoDTO> combinacionActual,
            double sumaActual,
            int indice) {

        if (sumaActual > objetivo) {
            return;
        }

        if (sumaActual == objetivo) {
            soluciones.add(new ArrayList<>(combinacionActual));
            return;
        }

        if (indice == platos.size()) {
            return;
        }

        PlatoDTO platoActual = platos.get(indice);

        // --- Decisión A: INCLUIR ---

        combinacionActual.add(platoActual);

        // --- LA CORRECCIÓN ESTÁ AQUÍ ---
        backtrackHelper(platos, objetivo, soluciones, combinacionActual, sumaActual + platoActual.precio(), indice + 1);

        combinacionActual.remove(combinacionActual.size() - 1);


        // --- Decisión B: NO INCLUIR ---
        backtrackHelper(platos, objetivo, soluciones, combinacionActual, sumaActual, indice + 1);
    }
}