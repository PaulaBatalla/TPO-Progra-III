package com.example.TPO_Progra_III.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public class RutaAbastecimientoKruskal {

    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private final ProveedorKruskal proveedor;

    @Property("peso")
    private final int peso;

    public RutaAbastecimientoKruskal(ProveedorKruskal proveedor, int peso) {
        this.proveedor = proveedor;
        this.peso = peso;
    }

    public ProveedorKruskal getProveedor() { // <-- Nombre actualizado
        return proveedor;
    }

    public int getPeso() {
        return peso;
    }
}