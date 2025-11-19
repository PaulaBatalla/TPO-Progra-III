# (Nombre de archivo: Dockerfile)

# --- 1. Etapa de Construcción (Builder) ---
# Usamos una imagen que ya tiene JDK 17 y Maven
FROM maven:3.9-eclipse-temurin-17 as builder
WORKDIR /app

# Copiamos solo el pom.xml para descargar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Ahora copiamos el resto del código fuente
COPY src src

# Compilamos y empaquetamos el .jar (usando 'mvn' directo)
RUN mvn package -DskipTests

# --- 2. Etapa de Ejecución (Runner) ---
# Usamos la imagen oficial de Eclipse Temurin slim, que es estable.
FROM eclipse-temurin:17-jre-focal
WORKDIR /app

# Copiamos solo el .jar construido en la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]