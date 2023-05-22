# Player Service API
This is a RESTful API service that provides access to player data stored in a CSV file.
The service is implemented using Java 17 and Spring Boot 3.0.0.

### Features
The API provides the following endpoints:

- GET /api/players: Retrieves a list of all players.
- GET /api/players/{playerID}: Retrieves a single player by ID.
### Setup and Usage
- Clone the repository to your local machine.
- Ensure you have Java 17 and Gradle installed.
- Build the Docker image using the following command:
```shell
docker build -t intuit-app .
```
- Start the application using the following command:
``` shell
docker run -p 8080:8080 intuit-app
```
- Once the application is running, you can access the API endpoints:

``` shell
Swagger UI: http://localhost:8080/swagger-ui/index.html
```

### Additional (with more time)

If I had more time to work on this project, I would consider implementing the following additional features and improvements:

**Performance Optimization:** Consider performance issues for large CSV files and use more efficient storage than H2.
This can improve memory utilization and processing speed. 

**Caching**: Implement a caching mechanism to reduce the load.
Use a caching library like Spring Cache or Redis to cache frequently accessed player data.

**Security:** Implement authentication and authorization to secure the API endpoints.

**Tests:** I would add more tests.

**Database management:** Consider using liquibase/flyway to manage database changes and migrations.

If there would be a requirement to keep working with CSV file updates i would create a sync mechanism to update the database with the new data.
Maybe i would consider using debezium to keep the database updated with the CSV file.

### Conclusion
The Player Service API provides a simple and efficient way to access player data from a CSV file through RESTful endpoints.
It can be further enhanced with additional features and improvements as described above.
