## Testing approach

This project uses slice tests:

- `@WebMvcTest` for controller behavior and validation error handling.
- `@DataJpaTest` for repository persistence with in-memory H2.
- Bean Validation unit tests for field validation rules.

### How to run tests

- `./mvnw test` (or `mvn test`)
