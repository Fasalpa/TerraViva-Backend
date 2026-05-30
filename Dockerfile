# =============================================
# ETAPA 1: Compilar el proyecto con Maven
# =============================================
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copia el pom.xml primero para aprovechar caché de dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia el código fuente y compila
COPY src ./src
RUN mvn clean package -DskipTests

# =============================================
# ETAPA 2: Ejecutar el .jar generado
# =============================================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copia el .jar desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Puerto que expone la app
EXPOSE 8081

# Comando para arrancar
ENTRYPOINT ["java", "-jar", "app.jar"]