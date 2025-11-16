package com.example.TPO_Progra_III.model.dijkstra;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node("Destino")
public class DestinoDijkstra {

    @Id
    private final String nombre;

    // Usamos 'direction = OUTGOING' para un grafo dirigido,
    // tal como tu código lo implementa.
    @Relationship(type = "RUTA_A", direction = Relationship.Direction.OUTGOING)
    private Set<RutaDijkstra> rutas = new HashSet<>();

    public DestinoDijkstra(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public Set<RutaDijkstra> getRutas() {
        return rutas;
    }

    // Método helper para agregar rutas
    public void agregarRuta(DestinoDijkstra destino, int peso) {
        this.rutas.add(new RutaDijkstra(destino, peso));
    }
}