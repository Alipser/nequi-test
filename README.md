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
# 🧪 LocalStack + Terraform para DynamoDB

Este entorno levanta una tabla DynamoDB local llamada `nequi-test-registries` usando [LocalStack](https://github.com/localstack/localstack) y [Terraform](https://www.terraform.io/). Es ideal para entornos de desarrollo o pruebas offline sin necesidad de acceder a AWS real.

---

## ✅ Cómo levantar La base de datos

### 1. Levantar LocalStack con Docker Compose

```bash
cd LocalStack
docker-compose up -d
```

Esto iniciará LocalStack en `http://localhost:4566`, con DynamoDB simulado localmente.

---

### 2. Aplicar Terraform

```bash
cd terraform
terraform init
terraform plan
terraform apply
```

Confirma con `yes` cuando se solicite.

---

## 🗂️ Resultado

Se crea una tabla llamada `nequi-test-registries` con:

- 📌 **Partition key (PK)**: `franchise`
- 📌 **Sort key (SK)**: `compositeKey`
- ⭐ **GSI `ix-franchise-stock`**:
    - PK: `franchise`
    - SK: `stock`

Esto permite buscar todos los productos de una franquicia ordenados por stock.



### ✅ Índices Secundarios Globales (GSI)

Se definieron GSIs para casos de uso específicos:

| Índice                        | Uso principal                                                                                          |
|------------------------------|--------------------------------------------------------------------------------------------------------|
| `ix-franchise-stock`         | Obtener productos ordenados por stock dentro de una franquicia                                         |
| `ix-sucursal-entityType`     | Obtener todas las entidades por sucursal y tipo  (No incluida porque no se desrrollo la funcionalidad) |
| `ix-uniqueId-entityType`     | Búsqueda puntual por ID y tipo  (No incluida porque no se desrrollo la funcionalidad)                  |

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
| POST   | `/api/franquicias/sucursal`                                       | Crear sucursal                                      |
| POST   | `/api/franquicias/sucursal/producto`                        | Crear producto                                      |
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

### Dockerizacion

desde la raíz del proyecto:

```bash
docker build -f applications/app-service/Dockerfile -t nequi-app .
```

---

```bash
cd LocalStack
docker-compose up -d
```

Esto iniciará LocalStack en `http://localhost:4566`, con DynamoDB simulado localmente.

---

volver  a la raiz del proyecto para luego lanzar

```bash
docker run --rm -p 8080:8080 \
  -e AWS_ACCESS_KEY_ID=test \
  -e AWS_SECRET_ACCESS_KEY=test \
  -e AWS_REGION=us-east-1 \
  -e AWS_DYNAMO_ENDPOINT=http://host.docker.internal:4566 \
  nequi-app
```

---


## 🧪 Pruebas

No pudieron ser adleantadas pero se implento Junit5 en el proyecto por cuestion de tiempo no se genraron los test


## 👨‍💻 Autor

Romario Julio — Backend Developer | Arquitectura Clean + Reactiva 

---