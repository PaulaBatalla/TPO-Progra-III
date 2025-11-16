package com.example.TPO_Progra_III.repository;

import com.example.TPO_Progra_III.model.Ubicacion;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import java.util.Optional;

public interface UbicacionRepository extends Neo4jRepository<Ubicacion, String> {

    Optional<Ubicacion> findByNombre(String nombre);

    @Query("MATCH (u:Ubicacion {nombre: $nombre})-[r:CONECTA_CON*0..1]->(c) RETURN u, collect(r), collect(c)")
    Optional<Ubicacion> findUbicacionConConexiones(String nombre);
}
