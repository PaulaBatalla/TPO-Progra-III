package com.example.TPO_Progra_III.repository;

import com.example.TPO_Progra_III.model.prim.SucursalPrim;
import org.springframework.data.neo4j.repository.Neo4jRepository;

// Repositorio para la entidad Sucursal, cuyo @Id es un String
public interface SucursalRepository extends Neo4jRepository<SucursalPrim, String> {
}