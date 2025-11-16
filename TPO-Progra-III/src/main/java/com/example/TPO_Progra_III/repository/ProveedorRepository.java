package com.example.TPO_Progra_III.repository;

import com.example.TPO_Progra_III.model.ProveedorKruskal; // <-- Nombre actualizado
import org.springframework.data.neo4j.repository.Neo4jRepository;

// Este repositorio maneja la entidad ProveedorKruskal
public interface ProveedorRepository extends Neo4jRepository<ProveedorKruskal, String> { // <-- Nombre actualizado
}