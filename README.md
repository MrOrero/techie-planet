# Techie Planet Submissions

This repository contains three separate submissions:

## 1. Algorithms (`algorithms/`)
Java algorithms implementation with Maven.

**Build:**
```bash
cd algorithms
mvn clean install
```

**Run Tests:**
```bash
mvn test
```

## 2. Application (`application/`)
Spring Boot REST API application with PostgreSQL database.

**Build (skip tests if DB not running):**
```bash
cd application
./mvnw clean install -DskipTests
# or: mvn clean install -DskipTests
```

**Run Tests:**
```bash
./mvnw test
# or: mvn test
```

**Run with Docker Compose:**
```bash
docker-compose up --build
```

The API will be available at `http://localhost:8080`

Swagger docs: `http://localhost:8080/swagger-ui.html`

**Stop:**
```bash
docker-compose down
```

## 3. Databases (`databases/`)
SQL queries and solutions in text files (question1.txt - question4.txt).
