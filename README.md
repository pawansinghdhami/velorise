# Velorise - Enterprise Mobility Platform

[![Java](https://img.shields.io/badge/Java-25-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.0-green)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Latest-blue)](https://www.postgresql.org/)
[![Redis](https://img.shields.io/badge/Redis-6.x+-red)](https://redis.io/)

---

## Table of Contents
- [Overview](#overview)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Technology Stack](#technology-stack)
- [Features](#features)
- [Workflow & Flowcharts](#workflow--flowcharts)
- [Prerequisites](#prerequisites)
- [Setup & Installation](#setup--installation)
- [Configuration](#configuration)
- [API Endpoints](#api-endpoints)
- [Database Schema](#database-schema)
- [Running the Application](#running-the-application)
- [Testing](#testing)

---

## Overview

**Velorise** is an enterprise-grade mobility platform designed to provide secure authentication, user management, and API access control for enterprise applications. Built on Spring Boot with JWT-based authentication, it offers a scalable and production-ready foundation for mobility services.

### Key Features
- ✅ JWT-based stateless authentication
- ✅ Role-based access control (RBAC)
- ✅ Secure password hashing with BCrypt
- ✅ Token refresh mechanism
- ✅ Redis caching support
- ✅ PostgreSQL with Liquibase migrations
- ✅ Comprehensive API validation

---

## Architecture

### High-Level Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                        Client Applications                      │
└────────────────────────────┬────────────────────────────────────┘
                             │
                    HTTP/HTTPS Requests
                             │
┌────────────────────────────▼────────────────────────────────────┐
│                      API Gateway Layer                          │
│  (/api/v1/auth/signup, /api/v1/auth/login)                      │
└────────────────────────────┬────────────────────────────────────┘
                             │
         ┌───────────────────┼───────────────────┐
         │                   │                   │
         ▼                   ▼                   ▼
    ┌─────────────┐  ┌──────────────┐  ┌──────────────┐
    │ Controller  │  │ JWT Security │  │  Exception   │
    │   Layer     │  │   Filter     │  │ Handler      │
    └─────────────┘  └──────────────┘  └──────────────┘
         │                   │                   │
         └───────────────────┼───────────────────┘
                             │
                    ┌────────▼─────────┐
                    │ Service Layer    │
                    │ (Business Logic) │
                    └────────┬─────────┘
                             │
         ┌───────────────────┼───────────────────┐
         │                   │                   │
         ▼                   ▼                   ▼
    ┌──────────┐      ┌──────────┐      ┌──────────────┐
    │Repository│      │JWT Token │      │User Details  │
    │Layer     │      │Service   │      │Service       │
    └────┬─────┘      └──────────┘      └──────────────┘
         │
    ┌────┴────────────────────┐
    │                         │
    ▼                         ▼
┌─────────────┐        ┌─────────────┐
│ PostgreSQL  │        │    Redis    │
│  Database   │        │   Cache     │
└─────────────┘        └─────────────┘
```

---

## Project Structure

```
velorise/
│
├── src/
│   ├── main/
│   │   ├── java/com/mobility/platform/
│   │   │   ├── VeloriseApplicationMain.java          # Entry point
│   │   │   ├── util/
│   │   │   │   └── AppUtil.java                      # Utility functions
│   │   │   └── authenticationservice/
│   │   │       ├── config/
│   │   │       │   ├── JwtConfiguration.java        # JWT config setup
│   │   │       │   ├── JwtProperties.java           # JWT properties
│   │   │       │   └── SecurityConfig.java          # Spring Security config
│   │   │       ├── controller/
│   │   │       │   └── AuthController.java          # Auth endpoints
│   │   │       ├── service/
│   │   │       │   ├── impl/
│   │   │       │   │   ├── AuthService.java         # Interface
│   │   │       │   │   ├── AuthServiceImpl.java      # Implementation
│   │   │       │   │   ├── AppUserDetails.java      # User details
│   │   │       │   │   └── (continued below)
│   │   │       │   └── ...
│   │   │       ├── security/
│   │   │       │   ├── jwt/
│   │   │       │   │   ├── JwtService.java          # JWT interface
│   │   │       │   │   ├── JwtServiceImpl.java       # JWT implementation
│   │   │       │   │   └── SecurityConstants.java   # Security constants
│   │   │       │   └── filter/
│   │   │       │       ├── JwtAuthenticationFilter.java  # Auth filter
│   │   │       │       └── AppUserDetailsService.java    # User service
│   │   │       ├── repository/
│   │   │       │   └── UserRepository.java          # JPA repository
│   │   │       ├── entity/
│   │   │       │   ├── AppUser.java                 # User entity
│   │   │       │   ├── RefreshToken.java            # Token entity
│   │   │       │   └── Role.java                    # Role enum
│   │   │       ├── dto/
│   │   │       │   ├── request/
│   │   │       │   │   ├── LoginRequest.java
│   │   │       │   │   └── SignupRequest.java
│   │   │       │   └── response/
│   │   │       │       └── LoginResponse.java
│   │   │       └── exception/
│   │   │           └── ExceptionHandling.java       # Exception handler
│   │   └── resources/
│   │       ├── application.yml                      # Configuration
│   │       └── db/
│   │           └── changelog/
│   │               ├── db.changelog-master.yml
│   │               └── changes/
│   │                   ├── 001-create-user-table.yml
│   │                   ├── 002-create-update-trigger-function.yml
│   │                   └── 003-create-app-user-trigger.yml
│   │
│   └── test/
│       └── java/com/mobility/platform/
│           └── VeloriseApplicationMainTests.java
│
├── docker/                                           # Docker configs
├── docs/                                             # Documentation
├── gradle/                                           # Gradle wrapper
├── .gradle/                                          # Gradle cache
├── build/                                            # Build output
├── build.gradle                                      # Gradle build config
├── settings.gradle                                   # Gradle settings
├── gradlew & gradlew.bat                             # Gradle wrapper scripts
└── README.md                                         # This file
```

---

## Technology Stack

| Component | Technology | Version |
|-----------|------------|---------|
| **Language** | Java | 25 |
| **Framework** | Spring Boot | 4.1.0 |
| **Security** | Spring Security | Latest |
| **Authentication** | JWT (JJWT) | 0.12.7 |
| **Database** | PostgreSQL | Latest |
| **ORM** | JPA/Hibernate | Latest |
| **Migrations** | Liquibase | Latest |
| **Caching** | Redis | 6.x+ |
| **Password Encoding** | BCrypt | Latest |
| **Build Tool** | Gradle | Latest |
| **Testing** | JUnit 5 | Latest |
| **Utility** | Lombok | Latest |

---

## Features

### Authentication Module
- **User Signup**: Create new user accounts with email and password
- **User Login**: Authenticate users and issue JWT tokens
- **Token Refresh**: Extend session with refresh tokens
- **JWT Validation**: Automatic token validation on protected endpoints
- **Role-Based Access**: Support for different user roles and permissions

### Security Features
- **Stateless Architecture**: No server-side session management (JWT-based)
- **Password Hashing**: BCrypt with configurable strength
- **CORS Support**: Cross-Origin Resource Sharing enabled
- **CSRF Protection**: Disabled for stateless JWT auth
- **Request Validation**: Comprehensive input validation

### Database Features
- **Version Control**: Liquibase for database schema migrations
- **Triggers**: Automatic timestamp management (created_at, updated_at)
- **Data Integrity**: Constraints and proper indexing
- **PostgreSQL Optimized**: Native PostgreSQL features

### Caching
- **Redis Integration**: In-memory caching for performance
- **Session Data**: Cache user sessions and tokens
- **Token Blacklisting**: Revoke tokens via Redis

---

## Workflow & Flowcharts

### 1. User Registration Flow

```
┌─────────────────────┐
│   Client/Frontend   │
└──────────┬──────────┘
           │
           │ POST /api/v1/auth/signup
           │ {email, password}
           │
           ▼
┌─────────────────────────────┐
│   AuthController.signup()   │
│   - Receive request         │
└──────────┬──────────────────┘
           │
           ▼
┌──────────────────────────────┐
│   AuthServiceImpl.signup()    │
│   - Validate input           │
│   - Check duplicate user     │
│   - Hash password (BCrypt)   │
└──────────┬───────────────────┘
           │
           ▼
┌────────────────────────────────┐
│   UserRepository.save()        │
│   - Create AppUser entity      │
│   - Assign default role        │
└──────────┬─────────────────────┘
           │
           ▼
┌────────────────────────────────┐
│   PostgreSQL Database          │
│   - INSERT user record         │
│   - Trigger: Set timestamps    │
└──────────┬─────────────────────┘
           │
           ▼
┌─────────────────────────────┐
│   Response to Client        │
│   HTTP 201 Created          │
│   {user details}            │
└─────────────────────────────┘
```

### 2. User Login Flow

```
┌─────────────────────┐
│   Client/Frontend   │
└──────────┬──────────┘
           │
           │ POST /api/v1/auth/login
           │ {email, password}
           │
           ▼
┌──────────────────────────────┐
│   AuthController.login()     │
│   - Receive credentials      │
└──────────┬───────────────────┘
           │
           ▼
┌──────────────────────────────┐
│ AuthServiceImpl.login()       │
│ - Authenticate user          │
└──────────┬───────────────────┘
           │
           ▼
┌──────────────────────────────┐
│ AuthenticationManager         │
│ - Load user details          │
│ - Compare password (BCrypt)  │
└──────────┬───────────────────┘
           │
      ┌────┴────────┐
      │              │
   SUCCESS       FAILURE
      │              │
      ▼              ▼
   ┌──────┐    ┌─────────────┐
   │Valid │    │Unauthorized │
   │Creds │    │HTTP 401     │
   └───┬──┘    └─────────────┘
       │
       ▼
┌────────────────────────────────┐
│ JwtServiceImpl.generateToken()  │
│ - Create JWT token             │
│ - Set expiration (1 hour)      │
│ - Sign with secret key         │
└────────┬─────────────────────────┘
         │
         ▼
┌──────────────────────────────┐
│ Create RefreshToken          │
│ - Store in PostgreSQL        │
└────────┬─────────────────────┘
         │
         ▼
┌──────────────────────────┐
│ Cache in Redis           │
│ - Key: userId           │
│ - Value: Token metadata  │
└────────┬─────────────────┘
         │
         ▼
┌──────────────────────────────┐
│ Response to Client           │
│ HTTP 200 OK                  │
│ {                            │
│   accessToken,               │
│   refreshToken,              │
│   expiresIn,                 │
│   tokenType: "Bearer"        │
│ }                            │
└──────────────────────────────┘
```

### 3. JWT Authentication Filter Flow (Protected Endpoints)

```
┌────────────────────────────┐
│   Client Request           │
│   Header: Authorization    │
│   Bearer <JWT_TOKEN>       │
└──────────┬─────────────────┘
           │
           ▼
┌─────────────────────────────────┐
│ JwtAuthenticationFilter          │
│ - Extract Authorization header  │
└──────────┬──────────────────────┘
           │
           ▼
┌──────────────────────────────┐
│ Token Extraction             │
│ - Parse "Bearer <token>"     │
│ - Extract token string       │
└──────────┬───────────────────┘
           │
           ▼
┌──────────────────────────────────┐
│ JwtServiceImpl.validateToken()    │
│ - Verify signature               │
│ - Check expiration               │
│ - Check Redis blacklist          │
└──────────┬───────────────────────┘
           │
      ┌────┴────────────┐
      │                 │
   VALID            INVALID
      │                 │
      ▼                 ▼
  ┌────────┐      ┌──────────────────┐
  │Extract │      │Throw Exception   │
  │Claims  │      │HTTP 401/403      │
  └───┬────┘      └──────────────────┘
      │
      ▼
┌──────────────────────────────┐
│ Load UserDetails             │
│ - From database by username  │
└──────────┬───────────────────┘
           │
           ▼
┌──────────────────────────────┐
│ Set SecurityContext          │
│ - Principal: User details    │
│ - Authorities: Roles         │
└──────────┬───────────────────┘
           │
           ▼
┌──────────────────────────────┐
│ filterChain.doFilter()       │
│ - Continue to endpoint       │
└──────────────────────────────┘
           │
           ▼
┌──────────────────────────────┐
│ Endpoint Handler             │
│ - Process request            │
│ - Return response            │
└──────────────────────────────┘
```

### 4. Authorization & Role-Based Access Control

```
┌──────────────────────────┐
│   Request Received       │
│   Token validated ✓      │
└──────────┬───────────────┘
           │
           ▼
┌────────────────────────────────────┐
│ SecurityConfig.authorizeHttpRequests│
│ - Check URL endpoint               │
└──────────┬───────────────────────────┘
           │
      ┌────┴─────────┬────────────┬────────────┐
      │              │            │            │
      ▼              ▼            ▼            ▼
  /signup        /login        /protected   /admin
  permitAll()  permitAll()   @Authenticated @Admin
      │              │            │            │
      │              │            ▼            ▼
      │              │      ┌──────────────────────┐
      │              │      │ Check User Roles     │
      │              │      │ - ADMIN              │
      │              │      │ - USER               │
      │              │      │ - GUEST              │
      └──────┬───────┴──────┴────┬─────────────────┘
             │                   │
             ▼                   ▼
         ALLOWED            ┌──────────┐
         Continue to     │Forbidden  │
         endpoint        │HTTP 403   │
                        └──────────┘
```

### 5. Error Handling Flow

```
┌──────────────────────┐
│   Request Processing │
└──────────┬───────────┘
           │
      ┌────┴────────────────┬──────────────────┐
      │                     │                  │
   SUCCESS              VALIDATION         SECURITY
      │                 ERROR               ERROR
      │                 │                  │
      ▼                 ▼                  ▼
  ┌───────┐      ┌─────────────┐  ┌──────────────────┐
  │Return │      │Input not    │  │Unauthorized or   │
  │Data   │      │valid format │  │Forbidden Access  │
  └───────┘      └──────┬──────┘  └────────┬─────────┘
                        │                  │
                        ▼                  ▼
                   ┌────────────────────────────┐
                   │ ExceptionHandling.java     │
                   │ - Global exception handler │
                   │ - Map to HTTP status codes │
                   └────────┬───────────────────┘
                            │
                            ▼
                   ┌──────────────────────┐
                   │Response to Client    │
                   │ - Error message      │
                   │ - HTTP status code   │
                   │ - Error timestamp    │
                   └──────────────────────┘
```

---

## Prerequisites

Before running Velorise, ensure you have:

- **Java 25** or higher: [Download Java](https://www.oracle.com/java/technologies/downloads/)
- **Gradle 8.x+**: [Download Gradle](https://gradle.org/releases/) or use the bundled `gradlew`
- **PostgreSQL 12+**: [Download PostgreSQL](https://www.postgresql.org/download/)
- **Redis 6.x+**: [Download Redis](https://redis.io/download) or use Docker
- **Git**: For version control

### Verify Installation

```bash
java -version
gradle --version
psql --version
redis-cli --version
```

---

## Setup & Installation

### Step 1: Clone the Repository

```bash
git clone https://github.com/pawansinghdhami/velorise.git
cd velorise
```

### Step 2: Create PostgreSQL Database

```bash
# Login to PostgreSQL
psql -U postgres

# Create database and user
CREATE DATABASE velorise;
CREATE USER velorise WITH PASSWORD 'velorise';
GRANT ALL PRIVILEGES ON DATABASE velorise TO velorise;
\q
```

### Step 3: Start Redis

**Using Docker:**
```bash
docker run -d -p 6379:6379 redis:latest
```

**Or if Redis is installed locally:**
```bash
redis-server
```

### Step 4: Build the Project

```bash
./gradlew clean build
```

### Step 5: Run Database Migrations

Migrations run automatically on application startup (Liquibase is enabled).

---

## Configuration

### Application Configuration (application.yml)

```yaml
spring:
  application:
    name: velorise
  datasource:
    url: jdbc:postgresql://localhost:5432/velorise
    username: velorise
    password: velorise
    driver-class-name: org.postgresql.Driver
  data:
    redis:
      host: localhost
      port: 6379
  jpa:
    show-sql: true
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    properties:
      hibernate:
        jpa:
          timezone: Asia/Kolkata
        format_sql: true
  liquibase:
    enabled: true
    change-log: classpath:db/changelog/db.changelog-master.yml

server:
  port: 8081

jwt:
  secret: "5v6RrN6qN4P3eY9tL2mJ8wQ1zX7cA4kH9nB5uF0sD3gL6pV2xC9"
  expiration: 3600000  # 1 hour in milliseconds
```

### Environment-Specific Configuration

For production, override settings:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://prod-db:5432/velorise
export SPRING_DATASOURCE_USERNAME=prod_user
export SPRING_DATASOURCE_PASSWORD=secure_password
export JWT_SECRET=your-production-secret-key
```

---

## API Endpoints

### Authentication Endpoints

#### 1. User Registration

```http
POST /api/v1/auth/signup
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "securePassword123",
  "firstName": "John",
  "lastName": "Doe"
}
```

**Success Response (201 Created):**
```json
{
  "id": 1,
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "USER",
  "createdAt": "2026-07-06T20:38:06.461+05:30"
}
```

#### 2. User Login

```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "securePassword123"
}
```

**Success Response (200 OK):**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expiresIn": 3600000,
  "tokenType": "Bearer"
}
```

#### 3. Access Protected Endpoint

```http
GET /api/v1/protected/profile
Authorization: Bearer <accessToken>
```

**Success Response (200 OK):**
```json
{
  "userId": 1,
  "email": "user@example.com",
  "role": "USER"
}
```

### Error Responses

**Invalid Credentials (401 Unauthorized):**
```json
{
  "timestamp": "2026-07-06T20:38:06.461+05:30",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid email or password"
}
```

**Token Expired (403 Forbidden):**
```json
{
  "timestamp": "2026-07-06T20:38:06.461+05:30",
  "status": 403,
  "error": "Forbidden",
  "message": "Token has expired"
}
```

---

## Database Schema

### Users Table

```sql
CREATE TABLE app_user (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    role VARCHAR(50) DEFAULT 'USER',
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_app_user_email ON app_user(email);
CREATE INDEX idx_app_user_active ON app_user(active);
```

### Refresh Tokens Table

```sql
CREATE TABLE refresh_token (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    token TEXT NOT NULL UNIQUE,
    expires_at TIMESTAMP NOT NULL,
    revoked BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE
);

CREATE INDEX idx_refresh_token_user_id ON refresh_token(user_id);
CREATE INDEX idx_refresh_token_token ON refresh_token(token);
```

### Auto-Update Timestamp Trigger

```sql
CREATE OR REPLACE FUNCTION update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER app_user_update_timestamp
BEFORE UPDATE ON app_user
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();
```

---

## Running the Application

### Method 1: Using Gradle

```bash
# Build and run
./gradlew bootRun

# Or build then run
./gradlew clean build
java -jar build/libs/velorise-0.0.1-SNAPSHOT.jar
```

### Method 2: Using IDE

1. Open the project in IntelliJ IDEA or Eclipse
2. Right-click `VeloriseApplicationMain.java`
3. Click "Run"

### Method 3: Docker (Optional)

```bash
docker build -t velorise:latest .
docker run -p 8081:8081 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/velorise \
  -e SPRING_DATASOURCE_USERNAME=velorise \
  -e SPRING_DATASOURCE_PASSWORD=velorise \
  velorise:latest
```

### Application Startup Output

```
  __________ __________________________
  / ____/ __ \\__  / ____/____  / ____/
 / __/ / / / //_ \\ / __/    / / /_
/ /___/ /_/ //_ \\ / /____ / / ___/
/_____/\\____/____/\\____/___/_/

Starting Velorise Application...
2026-07-06 20:38:06 - INFO - Spring Boot Version: 4.1.0
2026-07-06 20:38:06 - INFO - Application started successfully
2026-07-06 20:38:06 - INFO - Liquibase: Running migrations
2026-07-06 20:38:06 - INFO - Application is running on http://localhost:8081
```

### Verify Application is Running

```bash
# Health check endpoint (if enabled)
curl -X GET http://localhost:8081/actuator/health

# Try signup
curl -X POST http://localhost:8081/api/v1/auth/signup \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"Test123@"}'
```

---

## Testing

### Run Unit Tests

```bash
./gradlew test
```

### Run Integration Tests

```bash
./gradlew integrationTest
```

### Run All Tests with Coverage

```bash
./gradlew test jacocoTestReport
```

### View Test Reports

```bash
# Coverage report (after running tests)
open build/reports/jacoco/test/html/index.html
```

---

## Development Guidelines

### Code Structure

- **Entity**: JPA entities in `entity/` package
- **Repository**: Spring Data JPA repositories in `repository/`
- **Service**: Business logic in `service/impl/`
- **Controller**: REST endpoints in `controller/`
- **DTO**: Data transfer objects in `dto/`
- **Config**: Spring configurations in `config/`
- **Security**: Security configurations in `security/`

### Best Practices

1. **Always validate input** at controller level
2. **Use DTOs** for API contracts
3. **Implement exception handling** globally
4. **Use transactions** for multi-step operations
5. **Add logging** for debugging
6. **Write unit tests** for services
7. **Document APIs** with comments

---

## Troubleshooting

### Port Already in Use
```bash
# Find and kill process on port 8081
lsof -ti:8081 | xargs kill -9  # macOS/Linux
netstat -ano | findstr :8081  # Windows
```

### Database Connection Failed
```bash
# Verify PostgreSQL is running
psql -U velorise -d velorise -h localhost

# Check connection string in application.yml
```

### Redis Connection Issues
```bash
# Test Redis connection
redis-cli ping
# Should return: PONG
```

### JWT Token Expired
```bash
# Get a new token by logging in again
POST /api/v1/auth/login
```

---

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## Support & Contact

For issues, questions, or suggestions:
- 📧 Email: support@velorise.com
- 🐛 Issues: [GitHub Issues](https://github.com/pawansinghdhami/velorise/issues)
- 📖 Documentation: [Project Wiki](https://github.com/pawansinghdhami/velorise/wiki)

---

## Changelog

### Version 0.0.1 (Current)
- ✅ Initial project setup
- ✅ JWT authentication module
- ✅ User registration and login
- ✅ Role-based access control
- ✅ PostgreSQL integration
- ✅ Redis caching
- ✅ Database migrations with Liquibase

---

**Last Updated:** July 6, 2026  
**Maintained by:** Enterprise Mobility Platform Team
