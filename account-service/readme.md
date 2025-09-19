# 📦 Account Service

The Account Service is a Spring Boot microservice responsible for managing user account data within the Project Site Monitoring system.
It provides secure APIs to create, update, delete, search, and manage account statuses with validations.

---

## 🚀 Features
- Create, update, delete, and retrieve accounts
- Search accounts with pagination and filters
- Enable, disable, suspend, or soft-delete accounts
- Check email and phone number uniqueness
- Verify account existence and status
- Integrated with service discovery and API gateway

---

## 🛠 Tech Stack
- Language: Java 17+
- Frameworks: Spring Boot (Web, Data JPA, Validation)
- Database: PostgreSQL
- API Documentation: Swagger / OpenAPI
- Utilities: Lombok
- Containerization: Docker, Docker Compose

---

## 📖 API Documentation
The service exposes REST APIs under `/api/v1/accounts`.

- Local: http://localhost:8082/swagger-ui.html
- Deployed (via gateway): https://api.renzoproject.site/swagger-ui.html

---

## ▶️ Running with Docker Compose

### 1. Build and start services
From the project root:
```bash
docker compose up --build account-service
```

### 2. Access services
- Account Service → http://localhost:8080
- Swagger UI → http://localhost:8080/swagger-ui.html
- PostgreSQL DB → localhost:5432, database: account_database

----

## 🔄 Example Flows
- Create Account

  POST /api/v1/accounts → validates request → persists to DB → returns new account
- Get Account By ID

  GET /api/v1/accounts/{id} → fetches from DB → returns account details

👉 See [flow-diagram.puml](docs/flow-diagram.puml) for sequence diagrams.

## 📂 Repository Structure (Service Level)

```bash
/account-service
│── src/main/java/...     # Source code
│── src/main/resources/   # Config files (application.yml)
│── docs/                 # Diagrams and docs
│── Dockerfile            # Service Dockerfile
│── README.md             # This file
```