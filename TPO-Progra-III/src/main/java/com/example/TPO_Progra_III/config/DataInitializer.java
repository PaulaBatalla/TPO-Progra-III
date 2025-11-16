package com.example.TPO_Progra_III.config;

// Imports de todos los Modelos
import com.example.TPO_Progra_III.model.Ubicacion;
import com.example.TPO_Progra_III.model.dijkstra.DestinoDijkstra; // Asegúrate que el nombre sea el tuyo
import com.example.TPO_Progra_III.model.prim.SucursalPrim;
import com.example.TPO_Progra_III.model.ProveedorKruskal;

// Imports de todos los Repositorios
import com.example.TPO_Progra_III.repository.UbicacionRepository;
import com.example.TPO_Progra_III.repository.DestinoRepository;
import com.example.TPO_Progra_III.repository.SucursalRepository;
import com.example.TPO_Progra_III.repository.ProveedorRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UbicacionRepository ubicacionRepository;
    private final DestinoRepository destinoRepository;
    private final SucursalRepository sucursalRepository;
    private final ProveedorRepository proveedorRepository;

    public DataInitializer(UbicacionRepository ubicacionRepository,
                           DestinoRepository destinoRepository,
                           SucursalRepository sucursalRepository,
                           ProveedorRepository proveedorRepository) {
        this.ubicacionRepository = ubicacionRepository;
        this.destinoRepository = destinoRepository;
        this.sucursalRepository = sucursalRepository;
        this.proveedorRepository = proveedorRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // --- 1. INICIALIZADOR DE UBICACION (BFS/DFS) ---
        ubicacionRepository.deleteAll();

        Ubicacion cocina = new Ubicacion("Cocina");
        Ubicacion pasillo = new Ubicacion("Pasillo_Central");
        Ubicacion deposito = new Ubicacion("Depósito");
        Ubicacion freezer = new Ubicacion("Freezer");
        Ubicacion barra = new Ubicacion("Barra");
        Ubicacion salon = new Ubicacion("Salón");
        Ubicacion banos = new Ubicacion("Baños");

        // Creamos conexiones bidireccionales
        cocina.conectaCon(pasillo);
        pasillo.conectaCon(cocina);
        cocina.conectaCon(deposito);
        deposito.conectaCon(cocina);
        pasillo.conectaCon(barra);
        barra.conectaCon(pasillo);
        pasillo.conectaCon(salon);
        salon.conectaCon(pasillo);
        deposito.conectaCon(freezer);
        freezer.conectaCon(deposito);
        salon.conectaCon(banos);
        banos.conectaCon(salon);

        ubicacionRepository.save(cocina);
        ubicacionRepository.save(pasillo);
        ubicacionRepository.save(deposito);
        ubicacionRepository.save(freezer);
        ubicacionRepository.save(barra);
        ubicacionRepository.save(salon);
        ubicacionRepository.save(banos);
        System.out.println("--- Grafo del restaurante (BFS/DFS) inicializado en Neo4j ---");


        // --- 2. INICIALIZADOR DE DESTINO (Dijkstra) ---
        destinoRepository.deleteAll();

        DestinoDijkstra restaurante = new DestinoDijkstra("Restaurante");
        DestinoDijkstra casaFacu = new DestinoDijkstra("Casa de Facundo");
        DestinoDijkstra casaRamiro = new DestinoDijkstra("Casa de Ramiro");
        DestinoDijkstra casaPaula = new DestinoDijkstra("Casa de Paula");
        DestinoDijkstra depositoDijkstra = new DestinoDijkstra("Depósito"); // OJO: nombre distinto al de Kruskal

        // Relaciones DIRIGIDAS (un solo sentido)
        restaurante.agregarRuta(casaFacu, 1);
        restaurante.agregarRuta(casaRamiro, 4);
        casaFacu.agregarRuta(casaRamiro, 2);
        casaFacu.agregarRuta(casaPaula, 6);
        casaRamiro.agregarRuta(casaPaula, 3);
        casaPaula.agregarRuta(depositoDijkstra, 1);
        depositoDijkstra.agregarRuta(restaurante, 8);

        destinoRepository.save(restaurante);
        destinoRepository.save(casaFacu);
        destinoRepository.save(casaRamiro);
        destinoRepository.save(casaPaula);
        destinoRepository.save(depositoDijkstra);
        System.out.println("--- Grafo de Destinos (Dijkstra) inicializado en Neo4j ---");


        // --- 3. INICIALIZADOR DE SUCURSAL (Prim) ---
        sucursalRepository.deleteAll();

        SucursalPrim restaurantePrim = new SucursalPrim("Restaurante");
        SucursalPrim norte = new SucursalPrim("Sucursal Zona Norte");
        SucursalPrim sur = new SucursalPrim("Sucursal Zona Sur");
        SucursalPrim proveedorPrim = new SucursalPrim("Proveedor"); // OJO: nombre distinto
        SucursalPrim depositoPrim = new SucursalPrim("Depósito Central");

        // Relaciones NO DIRIGIDAS (bidireccionales)
        restaurantePrim.agregarConexion(norte, 10);
        norte.agregarConexion(restaurantePrim, 10);
        restaurantePrim.agregarConexion(sur, 5);
        sur.agregarConexion(restaurantePrim, 5);
        norte.agregarConexion(proveedorPrim, 2);
        proveedorPrim.agregarConexion(norte, 2);
        norte.agregarConexion(depositoPrim, 8);
        depositoPrim.agregarConexion(norte, 8);
        sur.agregarConexion(proveedorPrim, 4);
        proveedorPrim.agregarConexion(sur, 4);
        proveedorPrim.agregarConexion(depositoPrim, 3);
        depositoPrim.agregarConexion(proveedorPrim, 3);

        sucursalRepository.save(restaurantePrim);
        sucursalRepository.save(norte);
        sucursalRepository.save(sur);
        sucursalRepository.save(proveedorPrim);
        sucursalRepository.save(depositoPrim);
        System.out.println("--- Grafo de Sucursales (Prim) inicializado en Neo4j ---");


        // --- 4. INICIALIZADOR DE PROVEEDORES (Kruskal) ---
        proveedorRepository.deleteAll();

        ProveedorKruskal carnes = new ProveedorKruskal("Proveedor Carnes");
        ProveedorKruskal vegetales = new ProveedorKruskal("Proveedor Vegetales");
        ProveedorKruskal bebidas = new ProveedorKruskal("Proveedor Bebidas");
        ProveedorKruskal panificados = new ProveedorKruskal("Proveedor Panificados");
        ProveedorKruskal depositoKruskal = new ProveedorKruskal("Depósito Central");

        // Relaciones NO DIRIGIDAS (bidireccionales)
        carnes.agregarRuta(vegetales, 7);
        vegetales.agregarRuta(carnes, 7);
        carnes.agregarRuta(bebidas, 3);
        bebidas.agregarRuta(carnes, 3);
        carnes.agregarRuta(panificados, 8);
        panificados.agregarRuta(carnes, 8);
        vegetales.agregarRuta(panificados, 2);
        panificados.agregarRuta(vegetales, 2);
        bebidas.agregarRuta(depositoKruskal, 5);
        depositoKruskal.agregarRuta(bebidas, 5);
        panificados.agregarRuta(depositoKruskal, 6);
        depositoKruskal.agregarRuta(panificados, 6);
        vegetales.agregarRuta(depositoKruskal, 4);
        depositoKruskal.agregarRuta(vegetales, 4);

        proveedorRepository.save(carnes);
        proveedorRepository.save(vegetales);
        proveedorRepository.save(bebidas);
        proveedorRepository.save(panificados);
        proveedorRepository.save(depositoKruskal);
        System.out.println("--- Grafo de Proveedores (Kruskal) inicializado en Neo4j ---");
    }
}