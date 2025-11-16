package com.example.TPO_Progra_III.model.prim;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public class ConexionPrim {

    // ID interno requerido por Spring Data Neo4j
    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private final SucursalPrim sucursal;

    @Property("peso")
    private final int peso;

    public ConexionPrim(SucursalPrim sucursal, int peso) {
        this.sucursal = sucursal;
        this.peso = peso;
    }

    public SucursalPrim getSucursal() {
        return sucursal;
    }

    public int getPeso() {
        return peso;
    }
}