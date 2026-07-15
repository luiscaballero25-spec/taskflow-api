# TaskFlow API

API REST para gestión de proyectos y tareas, desarrollada como práctica de backend con Spring Boot. Permite administrar usuarios, sus proyectos y las tareas asociadas a cada proyecto, con estados de seguimiento (pendiente, en progreso, terminada).

## Stack tecnológico

- **Java 17**
- **Spring Boot 4.1.0** (Spring Web, Spring Data JPA, Validation)
- **Gradle** como gestor de dependencias
- **H2 Database** (modo archivo, persistente)
- **Lombok** para reducir código repetitivo
- **JUnit 5 + Mockito** para tests unitarios

## Arquitectura

El proyecto sigue una arquitectura en capas clásica:

```
Controller → Service → Repository → Base de datos
```

- **Controllers**: exponen los endpoints REST y validan las peticiones de entrada.
- **Services**: contienen la lógica de negocio y hacen de puente entre controllers y repositorios.
- **Repositories**: interfaces de Spring Data JPA que gestionan el acceso a datos.
- **DTOs**: separan lo que la API expone (`ResponseDTO`) y recibe (`RequestDTO`) de las entidades JPA internas.
- **GlobalExceptionHandler**: centraliza el manejo de errores, devolviendo respuestas HTTP consistentes.

## Modelo de datos

```
Usuario (1) ──── (*) Proyecto (1) ──── (*) Tarea
```

- Un **Usuario** puede tener varios **Proyectos**.
- Un **Proyecto** puede tener varias **Tareas**.
- Cada **Tarea** tiene un estado: `PENDIENTE`, `EN_PROGRESO` o `TERMINADA`.

## Puesta en marcha

### Requisitos

- Java 17 o superior
- No hace falta instalar nada más: el proyecto usa H2 como base de datos embebida.

### Ejecutar el proyecto

```bash
./gradlew bootRun
```

La aplicación arranca en `http://localhost:8080`.

### Consola de la base de datos

Disponible en `http://localhost:8080/h2-console`

- **JDBC URL**: `jdbc:h2:file:./data/taskflowdb`
- **Usuario**: `sa`
- **Contraseña**: (en blanco)

### Ejecutar los tests

```bash
./gradlew test
```

## Endpoints

Todas las respuestas y peticiones usan formato JSON. Los campos marcados como obligatorios están validados por la API; enviarlos vacíos o incorrectos devuelve un error `400`.

### Usuarios — `/api/usuarios`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/usuarios` | Lista todos los usuarios |
| GET | `/api/usuarios/{id}` | Busca un usuario por id |
| POST | `/api/usuarios` | Crea un usuario |
| PUT | `/api/usuarios/{id}` | Actualiza un usuario |
| DELETE | `/api/usuarios/{id}` | Elimina un usuario |

**Crear un usuario**

```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Ana García",
    "email": "ana@taskflow.com"
  }'
```

Respuesta (`200 OK`):
```json
{
    "id": 1,
    "nombre": "Ana García",
    "email": "ana@taskflow.com"
}
```

**Campos obligatorios**: `nombre` (no vacío), `email` (no vacío, formato válido).

### Proyectos — `/api/proyectos`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/proyectos` | Lista todos los proyectos |
| GET | `/api/proyectos/{id}` | Busca un proyecto por id |
| POST | `/api/proyectos` | Crea un proyecto |
| PUT | `/api/proyectos/{id}` | Actualiza un proyecto |
| DELETE | `/api/proyectos/{id}` | Elimina un proyecto |

**Crear un proyecto**

```bash
curl -X POST http://localhost:8080/api/proyectos \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Rediseño web",
    "descripcion": "Renovar la página corporativa",
    "usuarioId": 1
  }'
```

Respuesta (`200 OK`):
```json
{
    "id": 1,
    "nombre": "Rediseño web",
    "descripcion": "Renovar la página corporativa",
    "usuario": {
        "id": 1,
        "nombre": "Ana García",
        "email": "ana@taskflow.com"
    }
}
```

**Campos obligatorios**: `nombre` (no vacío), `usuarioId` (debe existir un usuario con ese id). `descripcion` es opcional.

### Tareas — `/api/tareas`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/tareas` | Lista todas las tareas |
| GET | `/api/tareas/{id}` | Busca una tarea por id |
| POST | `/api/tareas` | Crea una tarea |
| PUT | `/api/tareas/{id}` | Actualiza una tarea |
| DELETE | `/api/tareas/{id}` | Elimina una tarea |

**Crear una tarea**

```bash
curl -X POST http://localhost:8080/api/tareas \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Diseñar wireframes",
    "descripcion": "Crear los wireframes de las páginas principales",
    "estado": "PENDIENTE",
    "proyectoId": 1
  }'
```

Respuesta (`200 OK`):
```json
{
    "id": 1,
    "titulo": "Diseñar wireframes",
    "descripcion": "Crear los wireframes de las páginas principales",
    "estado": "PENDIENTE",
    "proyecto": {
        "id": 1,
        "nombre": "Rediseño web",
        "descripcion": "Renovar la página corporativa",
        "usuario": {
            "id": 1,
            "nombre": "Ana García",
            "email": "ana@taskflow.com"
        }
    }
}
```

**Campos obligatorios**: `titulo` (no vacío), `proyectoId` (debe existir un proyecto con ese id). `descripcion` y `estado` son opcionales. Valores válidos de `estado`: `PENDIENTE`, `EN_PROGRESO`, `TERMINADA`.

## Manejo de errores

La API devuelve errores estructurados en JSON, con el código HTTP apropiado.

**Recurso no encontrado (`404`)**

```bash
curl http://localhost:8080/api/usuarios/999
```

```json
{
    "timestamp": "2026-07-15T10:00:00",
    "status": 404,
    "error": "No encontrado",
    "mensaje": "Usuario no encontrado con id 999"
}
```

**Datos inválidos (`400`)**

```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nombre": "", "email": "no-es-un-email"}'
```

```json
{
    "timestamp": "2026-07-15T10:00:00",
    "status": 400,
    "error": "Datos inválidos",
    "errores": {
        "nombre": "El nombre no puede estar vacío",
        "email": "El email debe tener un formato válido"
    }
}
```

## Estructura del proyecto

```
src/main/java/com/taskflow/taskflowapi/
├── controller/     Controllers REST
├── service/        Lógica de negocio
├── repository/     Interfaces de acceso a datos (Spring Data JPA)
├── model/          Entidades JPA
├── dto/            Request y Response DTOs
└── exception/      Excepciones personalizadas y manejo global de errores
```

## Documentación y capturas
![Taskflow API](docs/readme_collage_taskflow.png)

Puedes probar la API interactivamente en Swagger UI una vez levantado el proyecto:
`http://localhost:8080/swagger-ui/index.html`
