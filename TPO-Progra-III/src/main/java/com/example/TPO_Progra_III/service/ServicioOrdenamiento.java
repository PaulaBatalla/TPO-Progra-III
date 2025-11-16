package com.example.TPO_Progra_III.service;

import com.example.TPO_Progra_III.dto.OrdenamientoResultado;
import com.example.TPO_Progra_III.model.Pedido;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioOrdenamiento {
    // --- QUICKSORT ---

    public OrdenamientoResultado ordenarPedidosPorPrecioQuickSort(List<Pedido> pedidos, String order) {
        if (pedidos == null || pedidos.size() < 2) {
            return new OrdenamientoResultado(pedidos, 0.0);
        }

        long startTime = System.nanoTime();
        quickSort(pedidos, 0, pedidos.size() - 1, order);
        long endTime = System.nanoTime();

        double duracionMilisegundos = (double) (endTime - startTime) / 1_000_000.0;
        return new OrdenamientoResultado(pedidos, duracionMilisegundos);
    }

    private void quickSort(List<Pedido> pedidos, int begin, int end, String order) {
        if (begin < end) {
            int partitionIndex = partition(pedidos, begin, end, order);

            quickSort(pedidos, begin, partitionIndex - 1, order);
            quickSort(pedidos, partitionIndex + 1, end, order);
        }
    }

    private int partition(List<Pedido> pedidos, int begin, int end, String order) {
        double pivote = pedidos.get(end).getPrecio();
        int i = (begin - 1);
        for (int j = begin; j < end; j++) {

            boolean shouldSwap;
            if (order.equalsIgnoreCase("desc")) {
                shouldSwap = (pedidos.get(j).getPrecio() >= pivote); // Descendente
            } else {
                shouldSwap = (pedidos.get(j).getPrecio() <= pivote); // Ascendente (default)
            }

            if (shouldSwap) {
                i++;
                swap(pedidos, i, j);
            }
        }
        swap(pedidos, i + 1, end);
        return i + 1;
    }

    // --- MERGESORT ---

    public OrdenamientoResultado ordenarPedidosPorPrecioMergeSort(List<Pedido> pedidos, String order) {
        if (pedidos == null || pedidos.size() < 2) {
            return new OrdenamientoResultado(pedidos, 0.0);
        }

        long startTime = System.nanoTime();
        mergeSort(pedidos, 0, pedidos.size() - 1, order);
        long endTime = System.nanoTime();

        double duracionMilisegundos = (double) (endTime - startTime) / 1_000_000.0;
        return new OrdenamientoResultado(pedidos, duracionMilisegundos);
    }

    private void mergeSort(List<Pedido> pedidos, int left, int right, String order) {
        if (left < right) {
            int middle = left + (right - left) / 2;

            mergeSort(pedidos, left, middle, order);
            mergeSort(pedidos, middle + 1, right, order);

            merge(pedidos, left, middle, right, order);
        }
    }

    private void merge(List<Pedido> pedidos, int left, int middle, int right, String order) {
        int n1 = middle - left + 1;
        int n2 = right - middle;

        List<Pedido> leftArray = new ArrayList<>(n1);
        List<Pedido> rightArray = new ArrayList<>(n2);

        for (int i = 0; i < n1; ++i)
            leftArray.add(pedidos.get(left + i));
        for (int j = 0; j < n2; ++j)
            rightArray.add(pedidos.get(middle + 1 + j));

        int i = 0, j = 0;
        int k = left;

        while (i < n1 && j < n2) {

            boolean takeFromLeft;
            if (order.equalsIgnoreCase("desc")) {
                // Descendente: tomamos el mayor
                takeFromLeft = (leftArray.get(i).getPrecio() >= rightArray.get(j).getPrecio());
            } else {
                // Ascendente: tomamos el menor
                takeFromLeft = (leftArray.get(i).getPrecio() <= rightArray.get(j).getPrecio());
            }

            if (takeFromLeft) {
                pedidos.set(k, leftArray.get(i));
                i++;
            } else {
                pedidos.set(k, rightArray.get(j));
                j++;
            }
            k++;
        }
        while (i < n1) {
            pedidos.set(k, leftArray.get(i));
            i++;
            k++;
        }
        while (j < n2) {
            pedidos.set(k, rightArray.get(j));
            j++;
            k++;
        }
    }

    private void swap(List<Pedido> list, int i, int j) {
        Pedido temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
