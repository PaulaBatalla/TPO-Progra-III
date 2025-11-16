package com.example.TPO_Progra_III.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node("Proveedor") // La etiqueta en Neo4j seguirá siendo "Proveedor"
public class ProveedorKruskal {

    @Id
    private final String nombre;

    @Relationship(type = "RUTA_CON", direction = Relationship.Direction.OUTGOING)
    private Set<RutaAbastecimientoKruskal> rutas = new HashSet<>();

    public ProveedorKruskal(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public Set<RutaAbastecimientoKruskal> getRutas() {
        return rutas;
    }

    public void agregarRuta(ProveedorKruskal destino, int peso) {
        this.rutas.add(new RutaAbastecimientoKruskal(destino, peso));
    }
}