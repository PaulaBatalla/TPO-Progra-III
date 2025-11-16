package com.example.TPO_Progra_III.repository;

import com.example.TPO_Progra_III.model.dijkstra.DestinoDijkstra;
import org.springframework.data.neo4j.repository.Neo4jRepository;

// El repositorio para la entidad Destino, cuyo @Id es un String
public interface DestinoRepository extends Neo4jRepository<DestinoDijkstra, String> {
}