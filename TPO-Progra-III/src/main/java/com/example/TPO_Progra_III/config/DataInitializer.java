package com.example.TPO_Progra_III.config;

import com.example.TPO_Progra_III.model.ProveedorKruskal;
import com.example.TPO_Progra_III.repository.DestinoRepository;
import com.example.TPO_Progra_III.repository.ProveedorRepository;
import com.example.TPO_Progra_III.repository.SucursalRepository; // <- NUEVO
import com.example.TPO_Progra_III.repository.UbicacionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UbicacionRepository ubicacionRepository;
    private final DestinoRepository destinoRepository;
    private final SucursalRepository sucursalRepository;
    private final ProveedorRepository proveedorRepository; // <- NUEVO

    // MODIFICAR EL CONSTRUCTOR
    public DataInitializer(UbicacionRepository ubicacionRepository,
                           DestinoRepository destinoRepository,
                           SucursalRepository sucursalRepository,
                           ProveedorRepository proveedorRepository) { // <- NUEVO
        this.ubicacionRepository = ubicacionRepository;
        this.destinoRepository = destinoRepository;
        this.sucursalRepository = sucursalRepository;
        this.proveedorRepository = proveedorRepository; // <- NUEVO
    }

    @Override
    public void run(String... args) throws Exception {

        // --- (Código de Ubicacion, Destino y Sucursal se queda igual) ---
        // ...
        // ...

        // --- NUEVO: INICIALIZADOR DE PROVEEDORES (Kruskal) ---
        proveedorRepository.deleteAll();

        // 1. Crear Nodos
        ProveedorKruskal carnes = new ProveedorKruskal("Proveedor Carnes");
        ProveedorKruskal vegetales = new ProveedorKruskal("Proveedor Vegetales");
        ProveedorKruskal bebidas = new ProveedorKruskal("Proveedor Bebidas");
        ProveedorKruskal panificados = new ProveedorKruskal("Proveedor Panificados");
        ProveedorKruskal deposito = new ProveedorKruskal("Depósito Central");

        // 2. Crear Relaciones (bidireccionales)
        carnes.agregarRuta(vegetales, 7);
        vegetales.agregarRuta(carnes, 7);

        carnes.agregarRuta(bebidas, 3);
        bebidas.agregarRuta(carnes, 3);

        carnes.agregarRuta(panificados, 8);
        panificados.agregarRuta(carnes, 8);

        vegetales.agregarRuta(panificados, 2);
        panificados.agregarRuta(vegetales, 2);

        bebidas.agregarRuta(deposito, 5);
        deposito.agregarRuta(bebidas, 5);

        panificados.agregarRuta(deposito, 6);
        deposito.agregarRuta(panificados, 6);

        vegetales.agregarRuta(deposito, 4);
        deposito.agregarRuta(vegetales, 4);

        // 3. Guardar en Neo4j
        proveedorRepository.save(carnes);
        proveedorRepository.save(vegetales);
        proveedorRepository.save(bebidas);
        proveedorRepository.save(panificados);
        proveedorRepository.save(deposito);

        System.out.println("--- Grafo de Proveedores (Kruskal) inicializado en Neo4j ---");
    }
}