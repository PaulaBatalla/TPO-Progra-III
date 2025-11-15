package com.example.TPO_Progra_III.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node("Ubicacion")
public class Ubicacion {

    @Id
    private final String nombre;

    // Relación recursiva: Una Ubicacion se conecta con otras Ubicaciones
    @Relationship(type = "CONECTA_CON", direction = Relationship.Direction.OUTGOING)
    private Set<Ubicacion> conexiones = new HashSet<>();

    public Ubicacion(String nombre) {
        this.nombre = nombre;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public Set<Ubicacion> getConexiones() {
        return conexiones;
    }

    // Método para agregar conexiones (útil para poblar el grafo)
    public void conectaCon(Ubicacion otra) {
        conexiones.add(otra);
    }
}