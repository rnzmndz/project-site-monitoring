# 🔐 Authentication Service
The Authentication Service is a Spring Boot microservice responsible for user authentication, authorization, and identity management within the Project Site Monitoring system. It acts as a secure gateway, integrating with Keycloak to handle OAuth2/OIDC flows, JWT token management, and user registration.

---

## 🚀 Features
- **User Login & Logout:** Secure password-based authentication with HTTP-only cookie session management.
- **User Registration:** Creates users in both Keycloak and the downstream Account Service.
- **Token Management:** Issues, refreshes, and validates JWT access and refresh tokens.
- **Session Validation:** Provides endpoints to check active user sessions.
- **Role Management:** Fetches user roles from Keycloak for authorization purposes.
- **Secure Cookies:** Uses HTTP-only, Secure, and SameSite cookies for enhanced security.

---

## 🛠 Tech Stack
- **Language:** Java 17+
- **Framework:** Spring Boot 3 (Web, Security OAuth2 Resource Server)
- **Identity & Access Management:** Keycloak
- **API Documentation:** Swagger / OpenAPI 3
- **Utilities:** Lombok, Spring REST Client
- **Containerization:** Docker, Docker Compose

---
## 📖 API Documentation
- The service exposes REST APIs under the /auth endpoint.
- **Local:** http://localhost:8081/swagger-ui.html
- **Deployed (via gateway):** https://api.renzoproject.site/swagger-ui.html

---

## ▶️ Running with Docker Compose
### 1. Build and start the service
   From the project root:
```shell
docker compose up --build auth-service
```

### 2. Access the service
- **Authentication Service** → http://localhost:8081
- **Swagger UI (via gateway)** → http://localhost:8080/swagger-ui.html
- **Keycloak Admin Console** → http://localhost:8180

---

## 🔄 Key Flows
### User Login
1. **Client** → POST /auth/login (with username/password)
2. **Auth Service** → Forwards credentials to Keycloak's token endpoint.
3. **Keycloak** → Validates credentials and returns JWT tokens.
4. **Auth Service** → Sets ACCESS_TOKEN and REFRESH_TOKEN as secure HTTP-only cookies.
4. **Client** → Stores cookies automatically; includes them in subsequent requests.

### User Registration
1. **Auth Service** → Creates user in Keycloak.
2. **Auth Service** → Uses the AccountClient (a Feign client) to create the account.
3. **FeignOAuth2Interceptor** → Automatically intercepts this call to the Account Service.
4. **Interceptor** → Contacts Keycloak to obtain a client access token using the credentials configured in OAuth2ClientConfig.
5. **Interceptor** → Adds the token to the request header (Authorization: Bearer <token>).
6. **Account Service** → Receives the request and validates the token, ensuring the request is authorized.

### Token Refresh
1. **Client** → POST /auth/refresh (with REFRESH_TOKEN cookie)
2. **Auth Service** → Sends the refresh token to Keycloak.
3. **Keycloak** → Validates the refresh token and issues a new access token.
4. **Auth Service** → Sets the new ACCESS_TOKEN as a secure HTTP-only cookie.

### User Logout
1. **Client** → POST /auth/logout
2. **Auth Service** → Returns response instructions to clear the ACCESS_TOKEN and REFRESH_TOKEN cookies, effectively ending the session.

---
## 📂 Service Architecture & Code Structure

```bash
/auth-service
│
├── src/main/java/site/renzoproject/auth_service/
│   ├── controller/
│   │   └── AuthController.java          # Main REST API endpoints
│   ├── service/
│   │   ├── AuthService.java             # Orchestrates registration logic
│   │   └── KeycloakService.java         # Admin client for Keycloak operations
│   ├── client/
│   │   └── AccountClient.java           # Feign client for Account Service
│   ├── config
│   │   ├── FeignOAuth2Interceptor.java  # A crucial integration component. This interceptor automatically acquires and attaches an OAuth2 access token to every request made by the AccountClient to the Account Service. This enables secure, authenticated service-to-service communication.
│   │   ├── OAuth2ClientConfig.java      # Configures the service as an OAuth2 Client, allowing it to obtain credentials (e.g., a client credentials grant) for itself.
│   │   ├── OpenApiConfig.java           # Configures the OpenAPI (Swagger) documentation, typically adding a security scheme to document the Bearer Token (JWT) requirement for endpoints.
│   │   └── SecurityConfig.java          # Configures the application as an OAuth2 Resource Server, enabling JWT validation for protecting its own endpoints.
│   └── dto/                             # Data Transfer Objects
│       ├── LoginRequest.java
│       ├── RegisterRequest.java
│       ├── RegisterResponse.java
│       ├── TokenResponse.java
│       └── account/
│           ├── AccountCreateDto
│           ├── AccountResponseDto
│           ├── AccountStatus
│           ├── AddressDto
│           ├── ContactInformationDto
│           └── EmergencyContactDto
│
├── src/main/resources/
│   └── application.yml                  # Configuration
│
├── Dockerfile
└── README.md
```
### Key Components:
- **AuthController:** Handles all HTTP requests for login, logout, register, refresh, and session checks.
- **AuthService:** Contains the business logic for user registration, ensuring consistency between Keycloak and the Account Service.
- **KeycloakService:** Uses the Keycloak Admin REST API to create users, check emails, and assign roles.
- **AccountClient:** A Feign client used to call the Account Service API during registration.