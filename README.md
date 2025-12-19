# SpringWebPro

A Spring Boot REST API application for product management.

## Features

- CRUD operations for products
- REST API endpoints
- H2 in-memory database
- JPA/Hibernate integration

## API Endpoints

- `GET /products` - Get all products
- `POST /addProducts` - Add a new product
- `POST /updateProducts` - Update an existing product
- `DELETE /deleteProducts/{id}` - Delete a product by ID

## Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run: `mvn spring-boot:run`
4. Access the API at `http://localhost:8080`

## H2 Console

Access the H2 database console at: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave empty)

## Technologies Used

- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database
- Lombok
- Maven