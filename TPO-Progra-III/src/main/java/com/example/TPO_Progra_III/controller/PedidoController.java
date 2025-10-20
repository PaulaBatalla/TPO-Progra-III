package com.example.TPO_Progra_III.controller;

import com.example.TPO_Progra_III.dto.OrdenamientoResultado;
import com.example.TPO_Progra_III.model.Pedido;
import com.example.TPO_Progra_III.service.ServicioOrdenamiento;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final ServicioOrdenamiento servicioOrdenamiento;

    public PedidoController(ServicioOrdenamiento servicioOrdenamiento) {
        this.servicioOrdenamiento = servicioOrdenamiento;
    }

    /**
     * Endpoint para ordenar una lista de pedidos usando Quicksort.
     * Recibe un JSON (List<Pedido>) y devuelve un JSON (OrdenamientoResultado).
     */
    @PostMapping("/ordenar/quicksort")
    public OrdenamientoResultado ordenarConQuickSort(
            @RequestBody List<Pedido> pedidos,
            @RequestParam(defaultValue = "asc") String order) {

        return servicioOrdenamiento.ordenarPedidosPorPrecioQuickSort(pedidos, order);
    }

    /**
     * Endpoint para ordenar una lista de pedidos usando Mergesort.
     * Recibe un JSON (List<Pedido>) y devuelve un JSON (OrdenamientoResultado).
     */
    @PostMapping("/ordenar/mergesort")
    public OrdenamientoResultado ordenarConMergeSort(
            @RequestBody List<Pedido> pedidos,
            @RequestParam(defaultValue = "asc") String order) {

        return servicioOrdenamiento.ordenarPedidosPorPrecioMergeSort(pedidos, order);
    }
}
