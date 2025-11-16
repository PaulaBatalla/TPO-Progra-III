package com.example.TPO_Progra_III.config;

import com.example.TPO_Progra_III.model.dijkstra.DestinoDijkstra;
import com.example.TPO_Progra_III.model.prim.SucursalPrim; // <- NUEVO
import com.example.TPO_Progra_III.repository.DestinoRepository;
import com.example.TPO_Progra_III.repository.SucursalRepository; // <- NUEVO
import com.example.TPO_Progra_III.repository.UbicacionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UbicacionRepository ubicacionRepository;
    private final DestinoRepository destinoRepository;
    private final SucursalRepository sucursalRepository; // <- NUEVO

    // MODIFICAR EL CONSTRUCTOR
    public DataInitializer(UbicacionRepository ubicacionRepository,
                           DestinoRepository destinoRepository,
                           SucursalRepository sucursalRepository) { // <- NUEVO
        this.ubicacionRepository = ubicacionRepository;
        this.destinoRepository = destinoRepository;
        this.sucursalRepository = sucursalRepository; // <- NUEVO
    }

    @Override
    public void run(String... args) throws Exception {

        // --- (Código de Ubicacion y Destino se queda igual) ---
        // ...
        // ...

        // --- NUEVO: INICIALIZADOR DE SUCURSAL (Prim) ---
        sucursalRepository.deleteAll(); // Limpiamos datos anteriores

        // 1. Crear los Nodos (Sucursales/Puntos)
        SucursalPrim restaurante = new SucursalPrim("Restaurante");
        SucursalPrim norte = new SucursalPrim("Sucursal Zona Norte");
        SucursalPrim sur = new SucursalPrim("Sucursal Zona Sur");
        SucursalPrim proveedor = new SucursalPrim("Proveedor");
        SucursalPrim deposito = new SucursalPrim("Depósito Central");

        // 2. Crear Relaciones (Conexiones con peso)
        // NOTA: Como el grafo es NO DIRIGIDO, agregamos la conexión
        // en ambas direcciones para que la consulta funcione desde cualquier nodo.
        restaurante.agregarConexion(norte, 10);
        norte.agregarConexion(restaurante, 10);

        restaurante.agregarConexion(sur, 5);
        sur.agregarConexion(restaurante, 5);

        norte.agregarConexion(proveedor, 2);
        proveedor.agregarConexion(norte, 2);

        norte.agregarConexion(deposito, 8);
        deposito.agregarConexion(norte, 8);

        sur.agregarConexion(proveedor, 4);
        proveedor.agregarConexion(sur, 4);

        proveedor.agregarConexion(deposito, 3);
        deposito.agregarConexion(proveedor, 3);

        // 3. Guardar en Neo4j
        sucursalRepository.save(restaurante);
        sucursalRepository.save(norte);
        sucursalRepository.save(sur);
        sucursalRepository.save(proveedor);
        sucursalRepository.save(deposito);

        System.out.println("--- Grafo de Sucursales (Prim) inicializado en Neo4j ---");
    }
}