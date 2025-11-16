package com.example.TPO_Progra_III.dto;
import java.util.List;

public class CombinacionRequestDTO {

    private double precioObjetivo;
    private List<PlatoDTO> platosDisponibles;

    // Getters y Setters (necesarios para la deserialización JSON)
    public double getPrecioObjetivo() {
        return precioObjetivo;
    }

    public void setPrecioObjetivo(double precioObjetivo) {
        this.precioObjetivo = precioObjetivo;
    }

    public List<PlatoDTO> getPlatosDisponibles() {
        return platosDisponibles;
    }

    public void setPlatosDisponibles(List<PlatoDTO> platosDisponibles) {
        this.platosDisponibles = platosDisponibles;
    }
}