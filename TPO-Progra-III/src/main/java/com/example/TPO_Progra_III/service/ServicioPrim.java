package com.example.TPO_Progra_III.service;

import com.example.TPO_Progra_III.dto.ConexionPrimDTO;
import com.example.TPO_Progra_III.dto.PrimResultado;
import com.example.TPO_Progra_III.model.prim.GrafoPrim;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioPrim {

    private final String[] NOMBRES_NODOS = {
            "Restaurante", "Sucursal Zona Norte", "Sucursal Zona Sur", "Proveedor", "Depósito"
    };

    public PrimResultado calcularMST() {
        GrafoPrim grafo = inicializarGrafo();

        GrafoPrim.PrimResultadoCompleto resultados = grafo.prim();
        int[] costo = resultados.costo;
        int[] padre = resultados.padre;

        List<ConexionPrimDTO> conexionesDTO = new ArrayList<>();
        int costoTotal = 0;

        for (int i = 1; i < NOMBRES_NODOS.length; i++) {
            if (padre[i] != -1) {
                conexionesDTO.add(new ConexionPrimDTO(
                        NOMBRES_NODOS[padre[i]],
                        NOMBRES_NODOS[i],
                        costo[i]
                ));
                costoTotal += costo[i];
            }
        }
        return new PrimResultado(conexionesDTO, costoTotal);
    }

    private GrafoPrim inicializarGrafo() {
        // 5 nodos: 0=Restaurante, 1=Norte, 2=Sur, 3=Proveedor, 4=Depósito
        GrafoPrim grafo = new GrafoPrim(NOMBRES_NODOS.length);

        grafo.agregarArista(0, 1, 10);
        grafo.agregarArista(0, 2, 5);
        grafo.agregarArista(1, 3, 2);
        grafo.agregarArista(1, 4, 8);
        grafo.agregarArista(2, 3, 4);
        grafo.agregarArista(3, 4, 3);

        return grafo;
    }
}