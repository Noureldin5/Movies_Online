# Movie Management System

## Overview

The Movie Management System is a Spring Boot application that allows users to manage movies, including adding, buying, and retrieving movie details. The application uses Hibernate Validator for data validation and integrates with a PostgreSQL database.

## Features

- Add new movies
- Buy movies
- Retrieve all movies
- Retrieve movies by customer

## Technologies Used

- Java
- Spring Boot
- Hibernate Validator
- Maven
- PostgreSQL
- H2 Database (for development)
- JUnit 5
- Mockito
- Swagger/OpenAPI

## Prerequisites

- Java 23 or higher
- Maven 3.6.0 or higher
- PostgreSQL 13 or higher

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/Noureldin5/movie-management-system.git
cd movie-management-system
```
```bash
mvn clean install
mvn spring-boot:run
```
```
Configure the Database
Development Profile (H2 Database)
The application is configured to use an in-memory H2 database for the development profile. No additional setup is required.


Production Profile (PostgreSQL)
Create a PostgreSQL database named moviesdb and update the application.yml file with your database credentials.

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/moviesdb
    driver-class-name: org.postgresql.Driver
    username: postgres
    password: 1234
  jpa:
    hibernate:
      ddl-auto: update
```
```
API Endpoints
Add a Movie
URL: /movie/add
Method: POST
Request Body:
{
  "title": "Inception",
  "author_full_name": "Christopher Nolan",
  "transcript": "A mind-bending thriller",
  "created_at": "2010-07-16T00:00:00",
  "price": 10,
  "ageAccess": 13,
  "exist": true,
  "type": {
    "name": "Action"
  },
  "customer": {
    "name": "John Doe"
  }
}
```

```
Buy a Movie
URL: /movie/buy
Method: POST
Request Body:
{
  "title": "Inception",
  "customer": {
    "name": "John Doe"
  }
}
```

```bash

```
Running Tests

Unit Tests
```bash
mvn test
```
```
Integration Tests
```
```bash
mvn verify
```
```
Project Structure

src
├── main
│   ├── java
│   │   └── org.example.midterm
│   │       ├── controller
│   │       ├── dto
│   │       ├── entities
│   │       ├── exception
│   │       ├── mapper
│   │       ├── repositories
│   │       ├── service
│   │       └── MovieManagementApplication.java
│   └── resources
│       ├── application.yml
└── test
    ├── java
    │   └── org.example.midterm
    │       ├── controller
    │       ├── exception
    │       ├── integration
    │       ├── mapper
    │       └── service
    └── resources

