package com.example.TPO_Progra_III.model.dijkstra;

import org.springframework.data.neo4j.core.schema.*;

@RelationshipProperties
public class RutaDijkstra {

    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private final DestinoDijkstra destino;

    @Property("peso")
    private final int peso;

    public RutaDijkstra(DestinoDijkstra destino, int peso) {
        this.destino = destino;
        this.peso = peso;
    }

    public DestinoDijkstra getDestino() {
        return destino;
    }

    public int getPeso() {
        return peso;
    }
}