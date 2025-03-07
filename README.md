# Prices API

## Project Description

A comprehensive Price Management API that allows querying of price information for products across different time frames.

## Key Features

- Price querying by product, brand, date, and chain
- Historical price information storage
- RESTful API endpoints
- Swagger/OpenAPI documentation

## Prerequisites

- Java 17+
- Maven 3.8+
- Spring Boot 3.4.x or higher

## Technologies Used

- Spring Boot
- Spring Data JPA
- H2 Database (for development)
- Swagger/OpenAPI
- JUnit 5
- Mockito

## Project Setup

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── rubenbr/
│   │           └── prices/
│   │               ├── application/
│   │               │   └── port/
│   │               │   └── service/
│   │               ├── domain/
│   │               │   └── exception/
│   │               │   └── model/
│   │               ├── infrastructure/
│   │               │   ├── config/
│   │               │   ├── persistence/
│   │               │   │   ├── adapter/
│   │               │   │   ├── entity/
│   │               │   │   ├── mapper/
│   │               │   │   └── repository/
│   │               │   └── rest/
│   │               │       ├── controller/
│   │               │       ├── exception/
│   │               │       └── mapper/
│   └── resources/
│       ├── api/
│       │   └── openapi.yml
│       ├── templates/
│       ├── application.properties
│       ├── data.sql
│       └── schema.sql
└── test/
    └── java/
        └── com/
            └── rubenbr/
                └── prices/
                    ├── application/
                    │   └── service/
                    ├── infrastructure/
                        ├── persistence/
                        │   └── adapter/
                        └── rest/
                            └── controller/
```

## API Documentation

API documentation is available via Swagger:
- Swagger UI URL: `http://localhost:8080/swagger-ui.html`
- OpenAPI Specification: `http://localhost:8080/v3/api-docs`

## Main Endpoints

- `GET /prices`: Price query

## Usage Example

### Query Price

```bash
curl -X GET "http://localhost:8080/api/prices?applicationDate=2020-06-16T21:00:00Z&productId=35455&brandId=1"
```

## Architecture

The project follows a Hexagonal (Ports and Adapters) architecture:
- `domain`: Core business logic
- `application`: Application services
- `infrastructure`: Implementations, adapters, and external concerns

## Licensing

Distributed under the MIT License. See `LICENSE` for more information.