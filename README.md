# SII REST Example

A small but complete REST application built with Spring Boot. It demonstrates a conventional controller/service/repository architecture, JSON request handling, validation, HTTP status codes, centralized error handling, health checks, unit tests, integration tests, and CI.

The repository started as the exercise in `Task1.md`; it now uses real Spring MVC annotations and an embedded Tomcat server instead of the original fake annotation classes.

## Requirements

- Java 17+
- Maven 3.6.3+

## Run

```bash
mvn spring-boot:run
```

The API is available at `http://localhost:8080/products`.

Run tests with:

```bash
mvn verify
```

Build an executable jar with:

```bash
mvn clean package
java -jar target/sii-rest-example-1.0.0-SNAPSHOT.jar
```

## API

| Method | Path | Description | Success |
| --- | --- | --- | --- |
| GET | `/products` | List all products | `200 OK` |
| GET | `/products/{id}` | Get one product | `200 OK` |
| POST | `/products` | Create a product | `201 Created` |
| PUT | `/products/{id}` | Replace an existing product | `200 OK` |
| PUT | `/products` | Bulk save-or-update products | `200 OK` |
| DELETE | `/products/{id}` | Delete a product | `204 No Content` |
| GET | `/actuator/health` | Application health | `200 OK` |

The application starts with three in-memory products: Laptop, PC, and Smartphone. Data is reset whenever the application restarts.

### List products

```bash
curl http://localhost:8080/products
```

### Get a product

```bash
curl http://localhost:8080/products/1
```

### Create a product

The ID is optional for POST. If omitted, the service generates a UUID.

```bash
curl -i -X POST http://localhost:8080/products \
  -H 'Content-Type: application/json' \
  -d '{"productName":"Tablet"}'
```

A client-supplied ID is also accepted if it does not already exist:

```bash
curl -i -X POST http://localhost:8080/products \
  -H 'Content-Type: application/json' \
  -d '{"productId":"4","productName":"Tablet"}'
```

### Replace a product

```bash
curl -i -X PUT http://localhost:8080/products/4 \
  -H 'Content-Type: application/json' \
  -d '{"productName":"Tablet Pro"}'
```

### Bulk upsert

Every product in a bulk request must have an ID.

```bash
curl -i -X PUT http://localhost:8080/products \
  -H 'Content-Type: application/json' \
  -d '[{"productId":"1","productName":"Gaming Laptop"},{"productId":"5","productName":"Monitor"}]'
```

### Delete a product

```bash
curl -i -X DELETE http://localhost:8080/products/2
```

## Validation and errors

`productName` is required, cannot be blank, and is limited to 120 characters. Optional client-provided IDs are limited to letters, digits, `.`, `_`, and `-`, with a maximum of 64 characters.

Errors use Spring's `ProblemDetail` representation. Typical status codes are:

- `400 Bad Request` for invalid input
- `404 Not Found` for an unknown product
- `409 Conflict` when POST tries to create an existing ID

Example:

```json
{
  "type": "about:blank",
  "title": "Product not found",
  "status": 404,
  "detail": "Product with id '999' was not found"
}
```

## Structure

```text
com.task
├── ProductApplication
├── controller
│   └── ProductController
├── entity
│   └── ProductEntity
├── exception
│   ├── ApiExceptionHandler
│   ├── ProductAlreadyExistsException
│   └── ProductNotFoundException
├── repository
│   ├── ProductRepository
│   └── implementation/ProductRepositoryImpl
└── service
    └── ProductService
```

The repository intentionally remains in-memory to keep the example focused on REST and application layering. Replacing `ProductRepositoryImpl` with a database-backed implementation does not require changing the controller contract.
