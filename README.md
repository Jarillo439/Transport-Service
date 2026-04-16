# Transport API - Microservicios

API REST para gestión de órdenes de transporte desarrollada con Java 21 y Spring Boot 3.5.x

## Microservicios

| Servicio | Puerto | Descripción |
|---|---|---|
| drivers-service | 8082 | Gestión de conductores |
| orders-service | 8081 | Gestión de órdenes |
| assignment-service | 8083 | Asignación de conductores |

## Requisitos
- Java 21
- Maven 3.8+
- Docker Desktop
- MySQL 8 corriendo en Docker en puerto 3306

## Base de datos
Antes de correr el proyecto crea las bases de datos en MySQL:
```sql
CREATE DATABASE db_orders;
CREATE DATABASE db_drivers;
CREATE DATABASE db_assignments;
```

## Cómo ejecutar

### Opción 1 — Local con STS4
Corre cada microservicio en este orden:
1. drivers-service
2. orders-service
3. assignment-service

### Opción 2 — Docker
Primero genera los jars de cada proyecto:
```bash
cd drivers-service
mvn clean package -DskipTests

cd ../orders-service
mvn clean package -DskipTests

cd ../Assignment-Service-1
mvn clean package -DskipTests
```

Luego levanta todo:
```bash
docker-compose up --build
```

## Documentación API (Swagger)
- Drivers: http://localhost:8082/swagger-ui/index.html
- Orders: http://localhost:8081/swagger-ui/index.html
- Assignment: http://localhost:8083/swagger-ui/index.html

## Endpoints principales

### Drivers
- POST /api/drivers — Crear conductor
- GET /api/drivers/active — Listar conductores activos
- GET /api/drivers/{id} — Buscar conductor por ID

### Orders
- POST /api/orders — Crear orden
- GET /api/orders/{id} — Buscar orden por ID
- PATCH /api/orders/{id}/status — Cambiar estado
- GET /api/orders — Listar con filtros

### Assignment
- POST /api/assignments — Asignar conductor a orden
- POST /api/assignments/{id}/file — Subir PDF
- POST /api/assignments/{id}/image — Subir imagen

## Seguridad
JWT Bearer Token requerido en todos los endpoints.

## Tecnologías
- Java 21
- Spring Boot 3.5.x
- Spring Security + JWT
- Spring Data JPA
- MySQL 8
- OpenFeign
- Swagger/OpenAPI
- JUnit 5 + Mockito
- Docker