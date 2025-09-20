### 🚪 API Gateway Service
The API Gateway Service is the single entry point for all client requests in the Project Site Monitoring system. Built on Spring Cloud Gateway, it handles routing, filtering, cross-cutting concerns, and security enforcement for the entire microservices ecosystem.

---
## 🚀 Features

- Dynamic Routing: Intelligent routing based on service discovery
- Load Balancing: Client-side load balancing across service instances
- Authentication & Authorization: JWT validation and security enforcement
- Rate Limiting: Request throttling and rate limiting capabilities
- Circuit Breaker: Resilience patterns with circuit breaker integration
- Request/Response Transformation: Modify requests and responses on the fly
- CORS Management: Centralized Cross-Origin Resource Sharing configuration
- Logging & Monitoring: Centralized request logging and metrics collection
- SSL Termination: HTTPS termination at the gateway level

---
## 🛠 Tech Stack
- Language: Java 17+
- Framework: Spring Boot 3, Spring Cloud Gateway
- Service Discovery: Spring Cloud Netflix Eureka Client
- Load Balancer: Spring Cloud LoadBalancer
- Security: Spring Security, JWT validation
- Resilience: Spring Cloud Circuit Breaker (Resilience4j)
- Containerization: Docker, Docker Compose

---

## 📖 API Endpoints
The Gateway Service acts as a reverse proxy for these downstream services:

| Route             | Destination Service | Column 3            | Description                        |
|-------------------|---------------------|---------------------|------------------------------------|
| Auth Routes       | Auth Service        | /auth/**            | Authentication and user management |
| Account Routes    | Account Service     | /api/accounts/**	   | User account management            |
| Monitoring Routes | Monitoring Service  | /api/monitoring/**	 | Site monitoring data               |
| Admin Routes      | Admin Service       | /api/admin/**	      | Administrative functions           |

Gateway Endpoints:
- Gateway Health: http://localhost:8080/actuator/health
- Gateway Routes: http://localhost:8080/actuator/gateway/routes
- Refresh Routes: POST http://localhost:8080/actuator/gateway/refresh
---
## ▶️ Running with Docker Compose
### 1. Build and start the gateway service
From the project root:
```shell
docker compose up --build gateway-service
```
### 2. Access the gateway
- API Gateway → http://localhost:8080
- Health Check → http://localhost:8080/actuator/health
- Eureka Dashboard → http://localhost:8761 (to verify registration)
### 3. Test routing
```shell
# Test auth service routing
curl http://localhost:8080/auth/health

# Test account service routing  
curl http://localhost:8080/api/accounts/health
```
---
## 🔧 Configuration
### Gateway Configuration (application.yml)
```yaml
server:
  port: 8080

spring:
  application:
    name: gateway-service
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
      routes:
        # Authentication Service Route
        - id: auth-service
          uri: lb://auth-service
          predicates:
            - Path=/auth/**
          filters:
            - name: CircuitBreaker
              args:
                name: authService
                fallbackUri: forward:/fallback/auth
            - StripPrefix=1

        # Account Service Route  
        - id: account-service
          uri: lb://account-service
          predicates:
            - Path=/api/accounts/**
          filters:
            - name: RequestRateLimiter
              args:
                redis-rate-limiter.replenishRate: 10
                redis-rate-limiter.burstCapacity: 20
            - StripPrefix=2

        # Monitoring Service Route
        - id: monitoring-service
          uri: lb://monitoring-service
          predicates:
            - Path=/api/monitoring/**
          filters:
            - StripPrefix=2

      globalcors:
        cors-configurations:
          '[/**]':
            allowed-origins: "http://localhost:3000,https://renzoproject.site"
            allowed-methods: GET,POST,PUT,DELETE,OPTIONS
            allowed-headers: "*"
            allow-credentials: true

# Eureka Client Configuration
eureka:
  client:
    service-url:
      defaultZone: http://discovery-service:8761/eureka/
    register-with-eureka: true
    fetch-registry: true

# Resilience4j Circuit Breaker
resilience4j:
  circuitbreaker:
    instances:
      authService:
        register-health-indicator: true
        sliding-window-size: 10
        minimum-number-of-calls: 5
        wait-duration-in-open-state: 10s

# Rate Limiting (Redis)
spring:
  redis:
    host: redis-service
    port: 6379

# Actuator
management:
  endpoints:
    web:
      exposure:
        include: health,info,gateway
  endpoint:
    health:
      show-details: always
```

---
## 📂 Service Architecture & Code Structure
```text
/gateway-service
│
├── src/main/java/site/renzoproject/gateway/
│   ├── config/
│   │   ├── GatewayConfig.java           # Gateway configuration beans
│   │   ├── SecurityConfig.java          # Security configuration
│   │   ├── CorsConfig.java              # CORS configuration
│   │   └── RateLimiterConfig.java       # Rate limiting configuration
│   ├── filter/
│   │   ├── AuthenticationFilter.java    # JWT validation filter
│   │   ├── LoggingFilter.java           # Request/response logging
│   │   └── RateLimitingFilter.java      # Custom rate limiting
│   ├── fallback/
│   │   └── FallbackController.java      # Circuit breaker fallbacks
│   └── GatewayServiceApplication.java   # Main application class
│
├── src/main/resources/
│   └── application.yml                  # Gateway configuration
│
├── Dockerfile
├── docker-compose.yml
└── README.md
```

### Main Application Class:
```java
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
}
```
---
## 🎯 How It Works
### Request Flow:
1. Client Request: Client sends request to http://gateway:8080/api/accounts/123
2. Route Matching: Gateway matches path /api/accounts/** to account-service route
3. Service Discovery: Gateway queries Eureka for account-service instances
4. Load Balancing: LoadBalancer selects healthy instance
5. Request Filtering: Authentication, logging, and other filters applied
6. Forward Request: Request forwarded to http://account-service:8082/123
7. Response Processing: Response filters applied before returning to client

### Security Flow:
1. JWT Validation: AuthenticationFilter validates JWT token from Authorization header
2. Role Extraction: Roles extracted from JWT for authorization checks
3. Request Enrichment: User information added to downstream request headers
4. Access Control: Route-based access control enforced

---
## 🔐 Security Configuration
### JWT Authentication Filter:
```java
@Component
public class AuthenticationFilter implements GlobalFilter {

    private final JwtValidator jwtValidator;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = extractToken(exchange.getRequest());

        if (token != null && jwtValidator.validateToken(token)) {
            String username = jwtValidator.extractUsername(token);
            exchange = exchange.mutate()
                    .request(builder -> builder.header("X-User-Id", username))
                    .build();
            return chain.filter(exchange);
        }

        return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token"));
    }
}
```

### Route-based Security:

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: secure-api
          uri: lb://secure-service
          predicates:
            - Path=/api/secure/**
          filters:
            - name: AuthenticationFilter
            - name: AuthorizationFilter
              args:
                required-roles: ADMIN,MANAGER
```
---
## ⚡ Performance Features
### Rate Limiting:
```yaml
filters:
  - name: RequestRateLimiter
    args:
      redis-rate-limiter.replenishRate: 100
      redis-rate-limiter.burstCapacity: 200
      redis-rate-limiter.requestedTokens: 1
```
### Circuit Breaker:
```yaml
filters:
  - name: CircuitBreaker
    args:
      name: inventoryService
      fallbackUri: forward:/fallback/inventory
```
### Caching:
```java
@Bean
public RouteLocator cachedRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
        .route("cached-route", r -> r.path("/api/cache/**")
        .filters(f -> f.dedupeResponseHeader("Cache-Control", "RETAIN_FIRST"))
        .uri("lb://cache-service"))
        .build();
        }
```
---
## 🚨 Troubleshooting
### Common Issues:
1. Routes Not Working
    - Check Eureka registration of target services
    - Verify route predicates match incoming paths
    - Check service discovery configuration
2. 504 Gateway Timeout
    - Increase timeout settings for slow services
    - Verify downstream services are healthy
3. Authentication Failures
    - Check JWT token validation configuration
    - Verify token signing key matches auth service

### Debug Endpoints:
```shell
# List all routes
curl http://localhost:8080/actuator/gateway/routes

# Refresh route configuration
curl -X POST http://localhost:8080/actuator/gateway/refresh

# Check health
curl http://localhost:8080/actuator/health
```
--- 

## 📊 Monitoring & Metrics
The gateway provides extensive metrics through Spring Boot Actuator:
- Request rates and response times
- Circuit breaker states
- Rate limiting statistics
- Route-specific metrics
- JVM and system metrics

```shell
# Access metrics
curl http://localhost:8080/actuator/metrics
curl http://localhost:8080/actuator/metrics/gateway.requests
```