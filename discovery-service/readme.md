# 🌐 Discovery Service (Eureka Server)

The Discovery Service is the central nervous system of the Project Site Monitoring microservices ecosystem. Built on Spring Cloud Netflix Eureka, it provides service discovery and registration capabilities, enabling microservices to dynamically locate and communicate with each other without hard-coded URLs.

----

## 🚀 Features
- Service Registration: Automatic registration of microservices upon startup
- Service Discovery: Dynamic discovery of service instances by name
- Health Monitoring: Continuous health checking and automatic de-registration of unhealthy instances
- Load Balancing: Client-side load balancing through service instance rotation
- High Availability: Support for multiple Eureka server nodes for redundancy
- Dashboard: Web-based UI for monitoring registered services and instances
- Zone Awareness: Support for availability zones and regions in distributed deployments

---

## 🛠 Tech Stack
- Language: Java 17+
- Framework: Spring Boot 3, Spring Cloud Netflix Eureka Server
- Service Discovery: Netflix Eureka
- Monitoring: Spring Boot Actuator
- Security: Spring Security (optional)
- Containerization: Docker, Docker Compose

---

## 📖 API Endpoints

The Discovery Service exposes these main endpoints:

- Eureka Dashboard: http://localhost:8761
- REST API: http://localhost:8761/eureka/api
- Actuator Health: http://localhost:8761/actuator/health
- Actuator Info: http://localhost:8761/actuator/info
- Metrics: http://localhost:8761/actuator/metrics

Service Registration Endpoint: http://localhost:8761/eureka/apps/{service-name}

---

## ▶️ Running with Docker Compose

### 1. Build and start the discovery service

From the project root:
```shell
docker compose up --build discovery-service
```
### 2. Access the service
- Eureka Dashboard → http://localhost:8761
- Health Check → http://localhost:8761/actuator/health

### 3. Verify operation
Wait for services to register, then check the Eureka dashboard to see all registered instances.
---
### 🔧 Configuration

### Discovery Service Configuration (application.yml)
```yaml
server:
  port: 8761

spring:
  application:
    name: discovery-service

# Eureka Server Configuration
eureka:
  client:
    register-with-eureka: false    # This server doesn't register with itself
    fetch-registry: false          # This server doesn't need to fetch registry
    service-url:
      defaultZone: http://localhost:8761/eureka/
  
  server:
    enable-self-preservation: true  # Prevents removal of instances during network issues
    eviction-interval-timer-in-ms: 60000  # Cleanup interval
    wait-time-in-ms-when-sync-empty: 0

# Actuator endpoints
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
  endpoint:
    health:
      show-details: always
```

### Client Service Configuration (example)
Client Service Configuration (example)
```yaml
# bootstrap.yml or application.yml in client services
spring:
  application:
    name: account-service  # This becomes the service ID

eureka:
  client:
    service-url:
      defaultZone: http://discovery-service:8761/eureka/
    register-with-eureka: true
    fetch-registry: true
    healthcheck:
      enabled: true
  
  instance:
    instance-id: ${spring.application.name}:${spring.application.instance-id:${random.value}}
    prefer-ip-address: true
    lease-renewal-interval-in-seconds: 30
    lease-expiration-duration-in-seconds: 90
```

---

## 📂 Service Architecture & Code Structure
```text
/discovery-service
│
├── src/main/java/site/renzoproject/discoveryservice/
│   ├── config/
│   │   ├── SecurityConfig.java          # Optional security configuration
│   │   └── EurekaServerConfig.java      # Custom Eureka configuration
│   └── DiscoveryServiceApplication.java # Main application class with @EnableEurekaServer
│
├── src/main/resources/
│   └── application.yml                  # Eureka server configuration
│
├── Dockerfile
├── docker-compose.yml
└── README.md
```

### Minimal Application Class:
```java
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServiceApplication.class, args);
    }
}
```

---
## 🎯 How It Works
### Service Registration Flow:
1. Microservice Starts: Service with Eureka client dependency starts up 
2. Registration Request: Service sends POST request to /eureka/apps/{service-name} 
3. Heartbeats: Service sends periodic heartbeats every 30 seconds 
4. Registry Update: Eureka server updates its registry with healthy instances
### Service Discovery Flow:
1. Client Needs Service: Microservice needs to call another service (e.g., auth-service)
2. Query Eureka: Client asks Eureka for all instances of "auth-service"
3. Get Instances: Eureka returns list of available auth-service instances 
4. Load Balance: Client-side load balancer (Ribbon) selects an instance 
5. Make Call: Client calls the selected instance directly
---
## 🔐 Security Configuration (Optional)
### Secure Eureka Server:
```yaml
# application.yml with security
spring:
  security:
    user:
      name: eureka-admin
      password: ${EUREKA_ADMIN_PASSWORD}

eureka:
  client:
    service-url:
      defaultZone: http://${spring.security.user.name}:${spring.security.user.password}@localhost:8761/eureka/
```

### Client Configuration with Security:
```yaml
eureka:
  client:
    service-url:
      defaultZone: http://eureka-admin:password@discovery-service:8761/eureka/
```
---
## 🚨 Troubleshooting
### Common Issues:
1. Services Not Registering
    - Check Eureka server URL in client configuration
    - Verify network connectivity between services and Eureka
    - Check if client has @EnableEurekaClient annotation
2. Services Showing as DOWN
    - Verify health check endpoints are accessible
    - Check if service can reach Eureka server
3. Eureka Server Not Starting
    - Check port 8761 is available
    - Verify configuration files
### Health Check:
```shell
curl http://localhost:8761/actuator/health
```

### Check Registered Services:
```shell
curl http://localhost:8761/eureka/apps
```