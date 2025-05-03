
# Movies Online Platform

## Overview

The Movies Online Platform is a comprehensive Spring Boot application that allows users to manage movies, including adding, buying, and retrieving movie details. The application features robust authentication with JWT tokens, OAuth2 integration with multiple providers, and email verification for user registration.

## Features

- User authentication and authorization with JWT
- OAuth2 integration with Google, Facebook, and GitHub
- Email verification for user registration
- Add new movies
- Buy movies
- Retrieve all movies
- Retrieve movies by customer
- User profile management

## Technologies Used

- Java 23
- Spring Boot 3.4
- Spring Security with JWT
- OAuth2 for social login
- Spring Mail for email services
- Hibernate/JPA
- Maven
- PostgreSQL (Production)
- H2 Database (Development)
- JUnit 5 & Mockito
- Swagger/OpenAPI

## Prerequisites

- Java 23 or higher
- Maven 3.6.0 or higher
- PostgreSQL 13 or higher (for production)
- Gmail account (for email services)

## Getting Started

### Clone the Repository
```
bash git clone [https://github.com/Noureldin5/movie-management-system.git](https://github.com/Noureldin5/movie-management-system.git) cd movie-management-system
``` 

### Build and Run
```
bash mvn clean install mvn spring-boot:run
``` 

### Configuration

#### Database Configuration

**Development Profile (H2 Database)**
- The application is configured to use an in-memory H2 database for the development profile.
- No additional setup is required.

**Production Profile (PostgreSQL)**
- Create a PostgreSQL database named `moviesdb`
- Update the database credentials in `application.yml`

#### Email Configuration

The application uses Gmail SMTP for sending verification emails:
```
yaml spring: mail: host: smtp.gmail.com port: 587 username: your-email@gmail.com password: your-app-password properties: mail: smtp: auth: true starttls: enable: true
``` 

**Note:** For Gmail, you need to use an app password. Create one at [Google Account Security](https://myaccount.google.com/security).

#### OAuth2 Configuration

Configure your OAuth2 credentials for social login:
```
yaml spring: security: oauth2: client: registration: google: client-id: YOUR_GOOGLE_CLIENT_ID client-secret: YOUR_GOOGLE_CLIENT_SECRET facebook: client-id: YOUR_FACEBOOK_CLIENT_ID client-secret: YOUR_FACEBOOK_CLIENT_SECRET github: client-id: YOUR_GITHUB_CLIENT_ID client-secret: YOUR_GITHUB_CLIENT_SECRET
``` 

## API Endpoints

### Authentication

**Register a User**
- URL: `/api/auth/register`
- Method: POST
- Request Body:
```
json { "email": "user@example.com", "password": "password123", "firstName": "John", "lastName": "Doe" }
``` 

**Login**
- URL: `/api/auth/login`
- Method: POST
- Request Body:
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```
```
**Refresh Token**
- URL: `/api/auth/refresh`
- Method: POST
- Request Body:
``` json
{
  "refreshToken": "your-refresh-token"
}
```
### Movies
**Add a Movie**
- URL: `/movie/add`
- Method: POST
- Request Body:
``` json
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
**Buy a Movie**
- URL: `/movie/buy`
- Method: POST
- Request Body:
``` json
{
  "title": "Inception",
  "customer": {
    "name": "John Doe"
  }
}
```
## Running Tests
### Unit Tests
``` bash
mvn test
```
### Integration Tests
``` bash
mvn verify
```
## Project Structure
``` 
src
├── main
│   ├── java
│   │   └── org.example.midterm
│   │       ├── config
│   │       ├── controller
│   │       ├── dto
│   │       ├── entities
│   │       ├── exception
│   │       ├── mapper
│   │       ├── repositories
│   │       ├── service
│   │       │   └── impl
│   │       └── MoviesonlineApplication.java
│   └── resources
│       ├── application.yml
│       ├── static
│       └── templates
└── test
    ├── java
    │   └── org.example.midterm
    │       ├── controller
    │       ├── exception
    │       ├── integration
    │       ├── mapper
    │       └── service
```
## API Documentation
Run the application and open: 👉 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
## Security Notes
- For production environments, always use environment variables or a secure vault for sensitive information
- JWT tokens are configured to expire after 15 minutes (access token) and 24 hours (refresh token)
- Password storage uses secure hashing algorithms
- Email verification is required for new accounts
