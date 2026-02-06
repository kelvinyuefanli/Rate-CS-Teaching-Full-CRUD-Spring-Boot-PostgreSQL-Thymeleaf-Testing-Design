## Rate CS Teaching Staff

Full CRUD web application to manage ratings for CS teaching staff.

### How to run locally

1. Create a local Postgres database named `staff_rating`.
2. Set environment variables (or use defaults in `application.properties`):
   - `DATABASE_URL` (example: `jdbc:postgresql://localhost:5432/staff_rating`)
   - `DATABASE_USERNAME`
   - `DATABASE_PASSWORD`
3. Run the app:
   - `./mvnw spring-boot:run` (or `mvn spring-boot:run`)
4. Visit `http://localhost:8080/ratings`

### How to deploy (Render)

1. Create a Render Postgres database and copy the connection info.
2. Set environment variables in Render:
   - `DATABASE_URL`
   - `DATABASE_USERNAME`
   - `DATABASE_PASSWORD`
3. Deploy the service (Java + Maven). Render will run `mvn clean package`.
4. Set start command: `java -jar target/staff-rating-0.0.1-SNAPSHOT.jar`

### Known Issues / Future Work

- Add aggregated statistics per instructor (average across multiple ratings).
- Improve UI styling and add delete confirmation modal.
