# 🌿 TerraViva — Backend

[![Java](https://img.shields.io/badge/Java-17-orange)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.6-brightgreen)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/license-MIT-blue)](#-licencia)

Backend de **TerraViva**, una aplicación web para la gestión de habitaciones, clientes y reservas.

El backend proporciona una **API REST** encargada de procesar la lógica de negocio, gestionar la persistencia de datos, controlar la autenticación y comunicarse con el frontend de TerraViva.

---

## 🚀 Tecnologías utilizadas

- **Java 17** — Lenguaje principal del proyecto.
- **Spring Boot 4.0.6** — Framework utilizado para construir la aplicación backend.
- **Spring Web** — Creación de la API REST y manejo de solicitudes HTTP.
- **Spring Data JPA** — Persistencia y acceso a datos.
- **Hibernate** — ORM utilizado por JPA.
- **PostgreSQL** — Sistema gestor de base de datos.
- **Supabase** — Servicio utilizado para alojar y administrar la base de datos PostgreSQL.
- **JWT (JSON Web Token)** — Autenticación y autorización basada en tokens (Spring Security).
- **Swagger / springdoc-openapi** — Documentación interactiva de la API.
- **Maven** — Gestión de dependencias y construcción del proyecto.
- **Tomcat** — Servidor web embebido proporcionado por Spring Boot.

---

## 🏗️ Arquitectura

TerraViva utiliza una arquitectura por capas para separar las responsabilidades de cada componente:

```text
Frontend → Controller → Service → Repository → PostgreSQL (Supabase)
```

- **Controller**: recibe las solicitudes HTTP y expone los endpoints.
- **Service**: contiene la lógica de negocio.
- **Repository**: gestiona el acceso a datos vía Spring Data JPA.
- **Database**: PostgreSQL alojado en Supabase.

Las respuestas del backend se envían al frontend en formato JSON.

---

## 📁 Estructura del proyecto

```text
src/
└── main/
    ├── java/
    │   └── com/
    │       └── terraviva/
    │           ├── auth/          # Autenticación y credenciales
    │           ├── config/        # Configuración general (CORS, beans, etc.)
    │           ├── controller/    # Endpoints REST
    │           ├── dto/           # Objetos de transferencia de datos
    │           ├── exception/     # Manejo centralizado de errores
    │           ├── model/         # Entidades JPA (Cliente, Habitacion, Reserva)
    │           ├── projection/    # Proyecciones para consultas optimizadas
    │           ├── repository/    # Interfaces de acceso a datos
    │           ├── security/      # Configuración de JWT / Spring Security
    │           ├── service/       # Lógica de negocio
    │           └── TerravivaApplication.java
    │
    └── resources/
        └── application.properties
```

### Modelo de datos

Actualmente el modelo incluye: **Cliente**, **Habitacion**, **Reserva**.

```text
Cliente 1 ──────── N Reserva N ──────── 1 Habitación
```

Una reserva pertenece a un cliente y a una habitación. Hibernate/JPA se encarga del mapeo entre estas entidades y las tablas de PostgreSQL.

---

## 🔎 Gestión de habitaciones

El backend diferencia entre:

- Consulta de habitaciones según su estado.
- Consulta específica de habitaciones disponibles.
- Consulta de disponibilidad dentro de un rango de fechas.

La lógica de disponibilidad se maneja desde la capa de negocio, permitiendo que el frontend solicite las habitaciones que pueden ser reservadas para determinadas fechas.

---

## 🔐 Autenticación y seguridad

TerraViva utiliza **JWT** para gestionar la autenticación, mediante Spring Security.

```text
Frontend → (credenciales) → Backend → (autentica) → JWT → Frontend
Frontend → Authorization: Bearer <token> → Endpoints protegidos
```

El token permite identificar y autorizar las solicitudes realizadas posteriormente por el usuario.

---

## 📡 Endpoints principales

> ⚠️ Completa esta tabla con las rutas reales de tu proyecto. Estructura sugerida según tus controllers actuales:

| Método | Endpoint | Descripción | Auth requerida |
|--------|----------|-------------|-----------------|
| POST   | `/api/auth/login` | Inicia sesión y devuelve un JWT | No |
| GET    | `/api/habitaciones` | Lista todas las habitaciones | Sí |
| GET    | `/api/habitaciones/disponibles` | Lista habitaciones disponibles | Sí |
| GET    | `/api/habitaciones/disponibilidad?desde=&hasta=` | Consulta disponibilidad por rango de fechas | Sí |
| POST   | `/api/reservas` | Crea una nueva reserva | Sí |
| GET    | `/api/reservas` | Lista reservas | Sí |
| GET    | `/api/clientes` | Lista clientes | Sí |

### Documentación interactiva

El proyecto tiene **Swagger (springdoc-openapi)** configurado. Con el backend corriendo, la documentación completa está disponible en:

```text
http://localhost:8081/swagger-ui.html
```

---

## ⚙️ Configuración

La configuración de conexión se encuentra en `src/main/resources/application.properties`. Las credenciales de la base de datos **no deben almacenarse directamente en el repositorio** — se gestionan mediante variables de entorno.

```properties
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DATABASE_USERNAME}
spring.datasource.password=${DATABASE_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver

jwt.secret=${JWT_SECRET}
jwt.expiration=${JWT_EXPIRATION:3600000}
```

### Archivo `.env` de ejemplo

Crea un archivo `.env` en la raíz del proyecto (y agrégalo a `.gitignore`) con:

```env
DATABASE_URL=jdbc:postgresql://<host>:5432/<database>
DATABASE_USERNAME=tu_usuario
DATABASE_PASSWORD=tu_password
JWT_SECRET=una_clave_secreta_larga_y_segura
JWT_EXPIRATION=3600000
```

---

## ▶️ Instalación y ejecución

### Requisitos

- Java 17
- Maven o Maven Wrapper
- Una base de datos PostgreSQL accesible (por ejemplo, un proyecto en Supabase)
- Las variables de entorno necesarias (ver `.env` de ejemplo arriba)

### Pasos

1. Clona el repositorio:
   ```bash
   git clone https://github.com/handrymoran1/TerraViva.git
   cd TerraViva
   ```
2. Configura tus variables de entorno (`.env` o variables del sistema).
3. Ejecuta el proyecto con Maven Wrapper:

   En macOS/Linux:
   ```bash
   ./mvnw spring-boot:run
   ```

   En Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```

4. El backend quedará disponible en:
   ```text
   http://localhost:8081
   ```

### Tests

```bash
./mvnw test
```

---

## 🔗 Integración con TerraViva Frontend

El frontend de TerraViva consume los endpoints expuestos por este backend mediante solicitudes HTTP, evitando que tenga acceso directo a la lógica de negocio o a las credenciales de la base de datos.

```text
TerraViva Frontend → localhost:8081 → TerraViva Backend → Supabase PostgreSQL
```

---

## 🧩 Principios utilizados

El proyecto mantiene una separación clara de responsabilidades (`Controller → Service → Repository → Database`), lo que permite:

- Mantener el código organizado.
- Separar la lógica de negocio del acceso a datos.
- Facilitar el mantenimiento y las futuras ampliaciones.
- Reducir el acoplamiento entre componentes.
- Permitir que el frontend y backend evolucionen de manera independiente.

---

## 🗺️ Roadmap

- [ ] Completar documentación de endpoints en Swagger
- [ ] Agregar pruebas unitarias e integración
- [ ] Manejo de roles y permisos (admin / recepcionista / cliente)
- [ ] Despliegue en un entorno de producción

---

## 📌 Estado del proyecto

TerraViva se encuentra en desarrollo como proyecto Full Stack, con un backend construido sobre Java + Spring Boot, persistencia mediante JPA/Hibernate, base de datos PostgreSQL alojada en Supabase y autenticación mediante JWT.

---

## 👥 Créditos

Proyecto desarrollado en colaboración por:

- [BrayanVelasquez](https://github.com/brayanvelasquez728-byte)
- [handrymoran](https://github.com/handrymoran1)
- [Fasalpa](https://github.com/Fasalpa)
- [JoanTriana](https://github.com/joantriana7-afk)

---
