# Tienda API

API REST para una tienda virtual, desarrollada con Spring Boot y PostgreSQL.

## Tecnologias

- Java 21
- Spring Boot 4.1.0
- Spring Data JPA
- PostgreSQL
- Maven
- Lombok

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
