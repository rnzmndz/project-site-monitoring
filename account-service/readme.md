# 📦 Account Service

The Account Service is a Spring Boot microservice responsible for managing user account data within the Project Site Monitoring system. It provides secure APIs to create, update, delete, search, and manage account statuses with comprehensive validations and exception handling.

---

## 🚀 Features
- **CRUD Operations:** Create, read, update, and delete user accounts
- **Account Management:** Enable, disable, suspend, or soft-delete accounts
- **Advanced Search:** Search accounts with pagination and multiple filters
- **Data Validation:** Comprehensive validation for email, phone number, and data integrity
- **Exception Handling:** Custom exceptions with proper error responses
- **Audit Logging:** Automatic tracking of creation and modification timestamps
- **DTO Mapping:** Efficient data transfer using MapStruct mappers

---

## 🛠 Tech Stack
- **Language:** Java 17+
- **Frameworks:** Spring Boot (Web, Data JPA, Validation)
- **Database:** PostgreSQL
- **API Documentation:** Swagger / OpenAPI 3
- **Mapping:** MapStruct
- **Utilities:** Lombok
- **Containerization:** Docker, Docker Compose

---

## 📖 API Documentation
The service exposes REST APIs under `/api/v1/accounts`.

- **Local:** http://localhost:8082/swagger-ui.html
- **Deployed (via gateway):**  https://api.renzoproject.site/swagger-ui.html

---

## ▶️ Running with Docker Compose

### 1. Build and start services
From the project root:
```shell
docker compose up --build account-service
```

### 2. Access services
- Account Service → http://localhost:8080
- Swagger UI (via gateway) → http://localhost:8080/swagger-ui.html
- PostgreSQL DB → localhost:5432, database: account_database

----

## 🔄 Example Flows
### Create Account

**POST /api/v1/accounts** → ValidationService checks uniqueness → AccountService creates entity → Mappers convert to DTO → returns AccountResponseDto

### Get Account By ID

**GET /api/v1/accounts/{id}** → AccountRepository.findById() → throws AccountNotFoundException if not found → returns AccountResponseDto

### Update Account

**PUT /api/v1/accounts/{id}** → validates input → checks for duplicate email/phone → updates entity → returns updated AccountResponseDto

### Search Accounts

**GET /api/v1/accounts/search** → accepts multiple filters → returns AccountPage with pagination info

## 📂 Service Architecture & Code Structure

```bash
/account-service
│
├── src/main/java/site/renzoproject/auth_service/
│   ├── config/
│   │   ├── OpenApiConfig.java           # Swagger/OpenAPI configuration
│   │   └── SecurityConfig.java          # Security configuration
│   ├── controller/
│   │   ├── GlobalExceptionHandler.java  # Centralized exception handling
│   │   └── AccountController.java       # REST API endpoints
│   ├── dto/
│   │   ├── AccountCreateDto.java        # Request DTO for creation
│   │   ├── AccountListDto.java          # DTO for listing operations
│   │   ├── AccountPage.java             # Pagination response wrapper
│   │   ├── AccountResponseDto.java      # Response DTO for single account
│   │   ├── AccountUpdateDto.java        # Request DTO for updates
│   │   ├── AddressDto.java              # Embedded address DTO
│   │   ├── ContactInformationDto.java   # Contact info DTO
│   │   └── EmergencyContactDto.java     # Emergency contact DTO
│   ├── exception/
│   │   ├── AccountDeletionException.java# Exception for deletion issues
│   │   ├── AccountNotFoundException.java# When account is not found
│   │   ├── DuplicateEmailException.java # Email uniqueness violation
│   │   ├── DuplicatePhoneException.java # Phone uniqueness violation
│   │   └── ValidationException.java     # General validation errors
│   ├── mapper/
│   │   ├── AccountMapper.java           # MapStruct mapper for Account
│   │   ├── AddressMapper.java           # Mapper for Address
│   │   ├── ContactInformationMapper.java# Mapper for ContactInfo
│   │   └── EmergencyContactMapper.java  # Mapper for EmergencyContact
│   ├── model/
│   │   ├── Account.java                 # Main Account entity
│   │   ├── AccountStatus.java           # Status enum (ACTIVE, INACTIVE, etc.)
│   │   ├── Address.java                 # Embedded address entity
│   │   ├── ContactInformation.java      # Embedded contact info entity
│   │   └── EmergencyContact.java        # Embedded emergency contact entity
│   ├── repository/
│   │   └── AccountRepository.java       # JPA Repository for Account
│   └── service/
│       ├── AccountService.java          # Main business logic
│       ├── AuditorAwareImpl.java        # Audit awareness for created/modified
│       └── ValidationService.java       # Validation and uniqueness checks
│
├── src/main/resources/
│   └── application.yml                  # Application configuration
│
├── Dockerfile
└── README.md
```

### Key Components:
- **AccountController:** Handles all HTTP requests and responses
- **AccountService:** Contains core business logic and orchestration
- **ValidationService:** Handles data validation and uniqueness checks
- **GlobalExceptionHandler:** Centralized handling of all custom exceptions
- **Mappers:** Efficient conversion between entities and DTOs using MapStruct
- **AccountRepository:** Data access layer using Spring Data JPA

---

## 🛡️ Exception Handling

The service includes comprehensive exception handling with appropriate HTTP status codes:

- **AccountNotFoundException** → 404 Not Found
- **DuplicateEmailException** → 409 Conflict
- **DuplicatePhoneException** → 409 Conflict
- **ValidationException** → 400 Bad Request
- **AccountDeletionException** → 423 Locked

All exceptions are handled centrally by GlobalExceptionHandler for consistent error responses.