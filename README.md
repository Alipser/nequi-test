# Proyecto Backend Reactivo para Gestión de Franquicias

Este proyecto implementa un backend reactivo basado en **Spring WebFlux** y **DynamoDB** para gestionar franquicias, sucursales y productos. Está diseñado para maximizar el rendimiento, la mantenibilidad y la eficiencia operativa utilizando una arquitectura moderna orientada al dominio.

---

## 🧱 Arquitectura del Proyecto

El proyecto está estructurado como un **multi-módulo Gradle** siguiendo principios de **Domain-Driven Design (DDD)** y **Clean Architecture**:

```
nequitest/
├── applications/         # Módulo de arranque de la aplicación (main)
├── domain/
│   ├── model/            # Entidades de dominio y enums
│   └── usecase/          # Lógica de negocio (casos de uso)
├── infrastructure/
│   ├── dynamo-adapter/   # Adaptadores para DynamoDB
│   └── reactive-web/     # Entrypoints HTTP (handlers + routers)
```

---

## ⚙️ Tecnologías Utilizadas

| Herramienta        | Justificación                                                                 |
|--------------------|--------------------------------------------------------------------------------|
| **Spring WebFlux** | Permite construir un backend no bloqueante y escalable usando programación reactiva |
| **DynamoDB**       | NoSQL serverless de alto rendimiento; uso de tabla única para todos los tipos de entidad |
| **Reactor**        | Manejo de flujos `Mono`/`Flux` para máxima eficiencia I/O |
| **MapStruct**      | Mapeo eficiente entre DTOs y objetos de dominio sin boilerplate |
| **Lombok**         | Simplifica la generación de código repetitivo (getters, builders, etc.) |
| **Gradle Multi-Proyecto** | Mejora la modularidad, facilita testing y despliegues independientes |

---

## 🧩 Decisiones Técnicas Clave

### ✅ Spring WebFlux

Usamos **Spring WebFlux** en lugar de Spring MVC porque:

- Permite manejar miles de conexiones concurrentes con pocos recursos
- Compatible con entornos como AWS Lambda, Fargate o API Gateway
- Es la mejor opción para trabajar con drivers no bloqueantes como el de DynamoDB SDK V2

---

### ✅ Tabla Única en DynamoDB

En lugar de tener una tabla por entidad (`Product`, `Branch`, `Franchise`), se decidió utilizar una **única tabla con claves compuestas**:

- **Partition Key**: `franquicia#{franchiseId}`
- **Sort Key**: `ENTITYTYPE#{entityId}` (ej. `PRODUCT#abc123`)

Esto permite:

- Consultas eficientes por franquicia y tipo
- Actualizaciones simples con `putItem`
- Escalabilidad horizontal con una sola tabla

---

### ✅ Índices Secundarios Globales (GSI)

Se definieron GSIs para casos de uso específicos:

| Índice                        | Uso principal                                      |
|------------------------------|----------------------------------------------------|
| `ix-franchise-stock`         | Obtener productos ordenados por stock dentro de una franquicia |
| `ix-sucursal-entityType`     | Obtener todas las entidades por sucursal y tipo    |
| `ix-uniqueId-entityType`     | Búsqueda puntual por ID y tipo                     |

---

### ✅ Estrategia de Actualización

Toda actualización (por ejemplo, `updateStock`, `deactivateProduct`) se realiza mediante:

- **Lectura del registro existente**
- **Modificación con `.toBuilder()`**
- **Sobrescritura completa con `putItem(...)`**

Esto garantiza:

- Trazabilidad (`updatedAt`, `cancelledAt`, etc.)
- Consistencia del objeto
- Simplicidad en el código

---

### ✅ Manejo del Estado

Cada registro contiene un campo `state`, controlado por el sistema:

```java
enum RegisterState {
    ACTV("activo"),
    DLTD("eliminado lógicamente")
}
```

Esto permite **soft deletes**, evitando borrar físicamente los registros.

---

## 🔀 Endpoints Implementados

| Método | Endpoint                                                                 | Descripción                                         |
|--------|--------------------------------------------------------------------------|-----------------------------------------------------|
| POST   | `/api/franquicias`                                                       | Crear franquicia                                    |
| POST   | `/api/franquicias/{id}/sucursales`                                       | Crear sucursal                                      |
| POST   | `/api/franquicias/{id}/sucursales/{id}/productos`                        | Crear producto                                      |
| PUT    | `/api/franquicias/{id}/sucursales/{id}/productos/{id}`                   | Actualizar nombre o stock de un producto            |
| PUT    | `/api/franquicias/{id}/sucursales/{id}/productos/{id}/stock`             | (versión anterior) actualizar solo el stock         |
| DELETE | `/api/franquicias/{id}/sucursales/{id}/productos/{id}`                   | Desactivar producto (soft delete)                   |
| GET    | `/api/franquicias/{id}/productos/mayor-stock`                            | Obtener productos con más stock por sucursal        |

---

## 🚀 Cómo correr el proyecto

### Requisitos

- Java 18 o superior
- Docker + LocalStack (para emular DynamoDB)
- Gradle

### Comando

```bash
./gradlew bootRun --project-dir applications/app-service
```

---

## 🧪 Pruebas

No pudieron ser adleantadas pero se implento Junit5 en el proyecto por cuestion de tiempo no se genraron los test


## 👨‍💻 Autor

Romario Julio — Backend Developer | Arquitectura Clean + Reactiva + Serverless

---