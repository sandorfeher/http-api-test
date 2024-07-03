# Spring Boot based REST API

Created using [Spring Initializr](http://start.spring.io/). Dependencies:
1. Spring Web dependencies
2. JPA as data persistence
3. H2 as in-memory database
4. Swagger 3 as OpenApi documentation

## Getting Started

### Requirements

Be sure to have the following items installed:

- JDK 17
- Maven 3.6.1+

## Running the app

### Using maven
Perform `mvn clean install` then execute:
`mvn spring-boot:run`. 

By default, app is running on `localhost:8081`.

## Endpoints

Supported endpoints are documented with Swagger under `/api`. For instance `http://localhost:8081/api`.
