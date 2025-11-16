# Trabajo Práctico - Programación 3
## Universidad Argentina de la Empresa (UADE) - 2025
Este repositorio contiene el desarrollo del Trabajo Práctico (TPO) para la materia Programación 3.

El proyecto consiste en una API RESTful desarrollada con Spring Boot que se conecta a una base de datos de grafos Neo4j Aura. El objetivo principal es implementar y exponer diversos algoritmos y técnicas de diseño (Backtracking, Branch & Bound, BFS/DFS, etc.)  como endpoints de API.

### 🧑‍💻 Integrantes del Grupo
Facundo Solé

Paula Batalla

Ramiro De Marco

### 👨‍🏫 Profesores
Adrián Cáceres

Gustavo Arenas

### 🛠️ Stack Tecnológico
Lenguaje: Java 17+

Framework: Spring Boot 3

Base de Datos: Neo4j Aura (Cloud)

ORM / Data: Spring Data Neo4j

Gestión de Dependencias: Maven

##⚙️ Configuración y Ejecución
Sigue estos pasos para levantar el proyecto localmente.

1. Descomprimir el Proyecto
Descomprime el archivo .zip del TPO en una carpeta de tu elección.

Abre el proyecto (la carpeta descomprimida) con tu IDE (IntelliJ IDEA es recomendado).

Espera a que Maven descargue todas las dependencias.

2. Configurar la Base de Datos (Neo4j Aura)
El proyecto requiere una conexión a una base de datos Neo4j Aura (Cloud).

Ve al archivo src/main/resources/application.properties.

Modifica las siguientes tres líneas con tus propias credenciales de Neo4j :

Properties
```
spring.neo4j.uri=neo4j+s://TU_URI_AURA.databases.neo4j.io
spring.neo4j.authentication.username=neo4j
spring.neo4j.authentication.password=TU_PASSWORD_AURA
```

3. Ejecutar la Aplicación
Busca y ejecuta la clase principal: TpoPrograIiiApplication.java.

El servidor se iniciará en http://localhost:8080.
