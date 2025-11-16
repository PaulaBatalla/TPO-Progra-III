package com.example.TPO_Progra_III.model.prim;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node("Sucursal")
public class SucursalPrim {

    @Id
    private final String nombre;

    @Relationship(type = "CONEXION_CON", direction = org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING)
    private Set<ConexionPrim> conexiones = new HashSet<>();

    public SucursalPrim(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public Set<ConexionPrim> getConexiones() {
        return conexiones;
    }

    // Método helper para agregar conexiones
    public void agregarConexion(SucursalPrim destino, int peso) {
        this.conexiones.add(new ConexionPrim(destino, peso));
    }
}