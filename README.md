# Tienda API

API REST para una tienda virtual, desarrollada con Spring Boot y PostgreSQL. Permite gestionar clientes, productos y registrar compras.

## Tecnologias

- Java 21
- Spring Boot 4.1.0
- Spring Data JPA
- PostgreSQL
- Maven
- Lombok

## Requisitos previos

- JDK 21+
- Docker (para levantar PostgreSQL) o una instancia de PostgreSQL local

## Configuracion de la base de datos

El proyecto incluye un `docker-compose.yml` para levantar PostgreSQL rapidamente:

```bash
docker compose up -d
```

Esto crea una base de datos `tienda_db` en `localhost:5432` con usuario `postgres` y contrasena `postgres`.

Si prefieres usar tu propia instancia de PostgreSQL, ajusta las siguientes propiedades en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tienda_db
spring.datasource.username=postgres
spring.datasource.password=postgres
```

## Ejecutar la aplicacion

```bash
./mvnw spring-boot:run
```

La aplicacion queda disponible en `http://localhost:8080`.

## Modelo de datos

- **Cliente**: nombre, email, telefono, direccion.
- **Producto**: nombre, descripcion, precio, stock.
- **Compra**: asocia un cliente con un producto, calcula el total y descuenta el stock disponible.

## Endpoints

### Clientes

| Metodo | Endpoint             | Descripcion                |
|--------|-----------------------|-----------------------------|
| POST   | /api/clientes          | Crear un cliente            |
| GET    | /api/clientes          | Listar todos los clientes   |
| GET    | /api/clientes/{id}     | Obtener un cliente por id   |
| PUT    | /api/clientes/{id}     | Actualizar un cliente       |
| DELETE | /api/clientes/{id}     | Eliminar un cliente         |

### Productos

| Metodo | Endpoint             | Descripcion                |
|--------|-----------------------|-----------------------------|
| POST   | /api/productos         | Crear un producto           |
| GET    | /api/productos         | Listar todos los productos  |
| GET    | /api/productos/{id}    | Obtener un producto por id  |
| PUT    | /api/productos/{id}    | Actualizar un producto      |
| DELETE | /api/productos/{id}    | Eliminar un producto        |

### Compras

| Metodo | Endpoint                        | Descripcion                          |
|--------|----------------------------------|----------------------------------------|
| POST   | /api/compras                     | Registrar una compra                   |
| GET    | /api/compras                     | Listar todas las compras               |
| GET    | /api/compras/{id}                | Obtener una compra por id              |
| GET    | /api/compras/cliente/{clienteId} | Listar las compras de un cliente       |

## Ejemplos de payloads

**POST /api/clientes**
```json
{
  "nombre": "Juan Perez",
  "email": "juan.perez@example.com",
  "telefono": "3001234567",
  "direccion": "Calle 123 #45-67"
}
```

**POST /api/productos**
```json
{
  "nombre": "Teclado mecanico",
  "descripcion": "Teclado mecanico switches rojos",
  "precio": 250000,
  "stock": 20
}
```

**POST /api/compras**
```json
{
  "clienteId": 1,
  "productoId": 1,
  "cantidad": 2
}
```
