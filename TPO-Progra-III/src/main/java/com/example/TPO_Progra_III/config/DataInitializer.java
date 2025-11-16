package com.example.TPO_Progra_III.config;

import com.example.TPO_Progra_III.model.dijkstra.DestinoDijkstra;
import com.example.TPO_Progra_III.repository.DestinoRepository;
import com.example.TPO_Progra_III.repository.UbicacionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UbicacionRepository ubicacionRepository;
    private final DestinoRepository destinoRepository; // <- NUEVO

    // MODIFICAR EL CONSTRUCTOR
    public DataInitializer(UbicacionRepository ubicacionRepository, DestinoRepository destinoRepository) {
        this.ubicacionRepository = ubicacionRepository;
        this.destinoRepository = destinoRepository; // <- NUEVO
    }

    @Override
    public void run(String... args) throws Exception {

        // --- INICIALIZADOR DE UBICACION (BFS/DFS) ---
        // (Tu código de Ubicacion... se queda como está)
        // ...

        // --- NUEVO: INICIALIZADOR DE DESTINO (Dijkstra) ---
        destinoRepository.deleteAll(); // Limpiamos datos anteriores

        // 1. Crear los Nodos (Destinos)
        DestinoDijkstra restaurante = new DestinoDijkstra("Restaurante");
        DestinoDijkstra casaFacu = new DestinoDijkstra("Casa de Facundo");
        DestinoDijkstra casaRamiro = new DestinoDijkstra("Casa de Ramiro");
        DestinoDijkstra casaPaula = new DestinoDijkstra("Casa de Paula");
        DestinoDijkstra deposito = new DestinoDijkstra("Depósito");

        // 2. Crear Relaciones (Rutas con peso)
        // Esto reemplaza tu lista de Mapas
        restaurante.agregarRuta(casaFacu, 1);
        restaurante.agregarRuta(casaRamiro, 4);

        casaFacu.agregarRuta(casaRamiro, 2);
        casaFacu.agregarRuta(casaPaula, 6);

        casaRamiro.agregarRuta(casaPaula, 3);

        casaPaula.agregarRuta(deposito, 1);

        deposito.agregarRuta(restaurante, 8);

        // 3. Guardar en Neo4j
        // (Guardamos los nodos, las relaciones se guardan en cascada)
        destinoRepository.save(restaurante);
        destinoRepository.save(casaFacu);
        destinoRepository.save(casaRamiro);
        destinoRepository.save(casaPaula);
        destinoRepository.save(deposito);

        System.out.println("--- Grafo de Destinos (Dijkstra) inicializado en Neo4j ---");
    }
}