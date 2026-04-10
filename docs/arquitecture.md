# High Level Design - DOSW Library API

## Descripción General
Sistema de gestión de biblioteca con arquitectura por capas, persistencia dual (relacional y NoSQL), seguridad JWT y pipeline CI/CD.

## Diagrama de Arquitectura
[Cliente] → [Spring Security] → [Controllers] → [Services] → [IRepository] →
[[JPA/PostgreSQL]  [Mongo/Atlas]]

## Capas del Sistema

### Controller
Expone los endpoints REST. Maneja autenticación con `@PreAuthorize` por roles (USER/LIBRARIAN).

### Core (Servicios y Modelos)
Contiene la lógica de negocio independiente de la persistencia. Los servicios usan interfaces `IRepository` para desacoplarse de la implementación.

### Persistence
Dividida en dos subcapas:
- **relational**: Entidades JPA, repositorios Spring Data JPA, mappers MapStruct → PostgreSQL
- **nonrelational**: Documentos MongoDB, repositorios MongoRepository, mappers MapStruct → MongoDB Atlas

### Security
- Spring Security stateless (sin sesiones)
- JWT para autenticación y autorización
- Roles: `USER` y `LIBRARIAN`
- CORS configurado

## Tecnologías
| Componente | Tecnología |
|---|---|
| Framework | Spring Boot 3.4.3 |
| Lenguaje | Java 21 |
| BD Relacional | PostgreSQL 17 |
| BD NoSQL | MongoDB Atlas |
| Seguridad | Spring Security + JWT |
| ORM | Hibernate / JPA |
| Mappers | MapStruct |
| Documentación | Swagger / OpenAPI |
| CI/CD | GitHub Actions |
| Cobertura | JaCoCo |
| Calidad | SonarCloud |

## Perfiles
- `relational`: usa PostgreSQL con JPA
- `mongo`: usa MongoDB Atlas (activo por defecto)