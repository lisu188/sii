# Architecture

## Runtime

The application is a Spring Boot MVC application started by `ProductApplication`. Spring Boot configures an embedded Tomcat server and discovers the controller, service, repository, and exception handler through component scanning.

## Layers

- `controller` owns HTTP routing, request bodies, path variables, response statuses, and REST semantics.
- `service` owns product business rules and is independent of HTTP.
- `repository` defines storage operations.
- `repository.implementation` provides a thread-safe in-memory implementation.
- `entity` contains the product model and Bean Validation constraints.
- `exception` maps domain/input failures to consistent HTTP `ProblemDetail` responses.

## Dependency injection

Runtime objects use constructor injection: `ProductController -> ProductService -> ProductRepository`. The package-private no-argument `ProductService` constructor exists only to preserve compatibility with the original Task 1 unit-test style; Spring uses the explicitly autowired repository constructor.

## Tests

`ProductServiceTest` is a unit test that constructs the service and repository directly.

`ProductControllerTest` is an integration test that loads the Spring application context and exercises the REST endpoints through `MockMvc`.

## Persistence

`ProductRepositoryImpl` uses a `ConcurrentHashMap`. This makes the demo self-contained, but data is not durable and is reset on restart.
