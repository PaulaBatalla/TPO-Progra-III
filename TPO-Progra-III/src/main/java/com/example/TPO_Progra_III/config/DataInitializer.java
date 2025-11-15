package com.example.TPO_Progra_III.config;

import com.example.TPO_Progra_III.model.Ubicacion;
import com.example.TPO_Progra_III.repository.UbicacionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UbicacionRepository ubicacionRepository;

    public DataInitializer(UbicacionRepository ubicacionRepository) {
        this.ubicacionRepository = ubicacionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Limpiamos la base de datos para evitar duplicados al reiniciar
        ubicacionRepository.deleteAll();

        // 1. Crear Nodos
        Ubicacion cocina = new Ubicacion("Cocina");
        Ubicacion pasillo = new Ubicacion("Pasillo_Central");
        Ubicacion deposito = new Ubicacion("Depósito");
        Ubicacion freezer = new Ubicacion("Freezer");
        Ubicacion barra = new Ubicacion("Barra");
        Ubicacion salon = new Ubicacion("Salón");
        Ubicacion banos = new Ubicacion("Baños");

        // 2. Crear Relaciones (bidireccionales para el recorrido)
        cocina.conectaCon(pasillo);
        cocina.conectaCon(deposito);

        pasillo.conectaCon(cocina);
        pasillo.conectaCon(barra);
        pasillo.conectaCon(salon);

        deposito.conectaCon(cocina);
        deposito.conectaCon(freezer);

        freezer.conectaCon(deposito);

        barra.conectaCon(pasillo);

        salon.conectaCon(pasillo);
        salon.conectaCon(banos);

        banos.conectaCon(salon);

        // 3. Guardar todo en Neo4j
        // (Guardamos el nodo principal y Neo4j guardará las relaciones)
        ubicacionRepository.save(cocina);
        ubicacionRepository.save(pasillo);
        ubicacionRepository.save(deposito);
        ubicacionRepository.save(freezer);
        ubicacionRepository.save(barra);
        ubicacionRepository.save(salon);
        ubicacionRepository.save(banos);

        System.out.println("--- Grafo del restaurante inicializado en Neo4j ---");
    }
}
