package com.example.TPO_Progra_III.repository;

import com.example.TPO_Progra_III.model.Ubicacion;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import java.util.Optional;

// Spring Data Neo4j creará la implementación de esto automáticamente.
public interface UbicacionRepository extends Neo4jRepository<Ubicacion, String> {

    // Busca una ubicación por su nombre (que es el @Id)
    Optional<Ubicacion> findByNombre(String nombre);

    // Una consulta personalizada para traer una ubicación con todas sus conexiones
    // El 'depth' 1 significa que trae la ubicación y sus vecinos directos.
    @Query("MATCH (u:Ubicacion {nombre: $nombre})-[r:CONECTA_CON*0..1]->(c) RETURN u, collect(r), collect(c)")
    Optional<Ubicacion> findUbicacionConConexiones(String nombre);
}
