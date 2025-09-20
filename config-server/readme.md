# ⚙️ Config Server

The Config Server is a centralized configuration management service that provides externalized configuration for all microservices in the Project Site Monitoring system. It follows the Spring Cloud Config Server pattern, enabling consistent configuration management across all environments.

---

## 🚀 Features

- Centralized Configuration: Single source of truth for all service configurations
- Environment-Specific Profiles: Supports development, staging, and production configurations
- Multiple Repository Backends: Configurations stored in Git repository (GitHub, GitLab, Bitbucket)
- Encryption/Decryption: Supports encryption of sensitive properties
- Health Checks: Provides health endpoints to monitor config server status
- Auto-Refresh: Integrates with Spring Cloud Bus for dynamic configuration updates
- Security: Secure access to configuration files with authentication

---

## 🛠 Tech Stack

- Language: Java 17+
- Framework: Spring Boot 3, Spring Cloud Config Server
- Configuration Storage: Git Repository
- Security: Spring Security
- Service Discovery: Integration with Eureka Server (optional)
- Containerization: Docker, Docker Compose

---

## 📖 API Endpoints
The Config Server exposes REST APIs under these endpoints:
- Config Server Health: http://localhost:8888/actuator/health
- Configuration Access: http://localhost:8888/{application}/{profile}[/{label}]
- Configuration Access (Specific): http://localhost:8888/{application}-{profile}.yml
- Encryption/Decryption: http://localhost:8888/encrypt & http://localhost:8888/decrypt

Examples:

- http://localhost:8888/account-service/default - Account service default profile
- http://localhost:8888/auth-service/dev - Auth service development profile
- http://localhost:8888/gateway-service/prod - Gateway service production profile

---

## ▶️ Running with Docker Compose

### 1. Build and start the config server
From the project root:
```bash
docker compose up --build config-server
```

### 2. Access the service
- Config Server → http://localhost:8888
- Health Check → http://localhost:8888/actuator/health

---

## 🔧 Configuration Structure
### Git Repository Layout
```text
config-repo/
├── application.yml                      # Shared configuration for all services
├── gateway-service/
│   ├── gateway-service.yml              # Default profile
│   ├── gateway-service-dev.yml          # Development profile
│   └── gateway-service-prod.yml         # Production profile
├── auth-service/
│   ├── auth-service.yml
│   ├── auth-service-dev.yml
│   └── auth-service-prod.yml
├── account-service/
│   ├── account-service.yml
│   ├── account-service-dev.yml
│   └── account-service-prod.yml
└── notification-service/
    ├── notification-service.yml
    └── notification-service-dev.yml
```

### Example Configuration File (auth-service-dev.yml)
```yaml
server:
  port: 8081

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/auth_db
    username: auth_user
    password: '{cipher}AQBv2JjJxyz...encrypted_password...'
  jpa:
    hibernate:
      ddl-auto: update

keycloak:
  auth-server-url: http://localhost:8180
  realm: master
  resource: auth-service
  credentials:
    secret: '{cipher}AQCyz123...encrypted_secret...'

logging:
  level:
    site.renzoproject: DEBUG
```
---
## 📂 Service Architecture & Code Structure
```text
/config-server
│
├── src/main/java/site/renzoproject/configserver/
│   ├── config/
│   │   ├── SecurityConfig.java          # Security configuration
│   │   ├── GitRepositoryConfig.java     # Git repository configuration
│   │   └── EncryptionConfig.java        # Encryption setup
│   ├── controller/
│   │   └── EncryptionController.java    # Custom encryption endpoints
│   └── ConfigServerApplication.java     # Main application class
│
├── src/main/resources/
│   └── application.yml                  # Config server own configuration
│
├── Dockerfile
├── docker-compose.yml
└── README.md
```

---

## 🚨 Troubleshooting

### Common Issues:
1. Config Server Not Starting
   - Check Git repository credentials
   - Verify network connectivity to Git repository
2. Client Cannot Connect
   - Verify config server is running on port 8888
   - Check client bootstrap configuration
3. Encryption/Decryption Fails
   - Verify encryption key is properly set
   - Check keystore configuration
### Health Check:
```shell
curl http://localhost:8888/actuator/health
```
### Test Configuration Access:
```shell
curl -u config-user:password http://localhost:8888/auth-service/dev
```

