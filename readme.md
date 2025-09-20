# Project site monitoring repo

This repository contains a collection of **Spring Boot microservices** that together form the Project site monitoring system.
It demonstrates a **modern cloud-native architecture** with centralized configuration, service discovery, API gateway, and containerized deployment.

🌍 **Live Demo:** [https://api.renzoproject.site](https://api.renzoproject.site)

---

## ✨ Features

* **Microservices Architecture** – loosely coupled, independently deployable services.
* **Centralized Configuration** – managed by Spring Cloud Config Server.
* **Service Discovery** – Eureka registry for dynamic service lookup.
* **API Gateway** – single entry point with routing and security.
* **Dockerized Deployment** – run the entire system with one command.
* **Hosted on Custom Domain** – available at `https://api.renzoproject.site`.
* **Scalable Design** – ready for Kubernetes or cloud deployment.

---

## 🛠️ Technology Stack

* **Backend Frameworks:** Spring Boot, Spring Cloud (Config, Eureka, Gateway)
* **Languages:** Java 17
* **Build Tools:** Maven
* **Containerization:** Docker, Docker Compose
* **Service Communication:** REST (HTTP, JSON)
* **Configuration Management:** Git-based Spring Cloud Config
* **API Gateway & Routing:** Spring Cloud Gateway
* **Service Discovery:** Netflix Eureka
* **Monitoring (Optional):** Spring Boot Actuator, Prometheus, Grafana
* **Deployment:** Nginx reverse proxy + SSL (Let’s Encrypt), Docker on cloud server

---

## 🏗️ System Architecture

**Services in this monorepo:**

* **Config Server (config-server)** → Centralized externalized configuration for all microservices (Spring Cloud Config).
* **Discovery Service (discovery-service)** → Service registry (Eureka) that enables dynamic discovery of microservices.
* **API Gateway (gateway-service)** → Single entry point for clients. Handles request routing, security, and rate limiting.
* **Account Service (account-service)** → Handles user account data, profile management, and related operations.
* **Business Service (business-service)** → Core domain logic — manages business entities, workflows, and main application features.

**Architecture Diagram:**

![Microservices Architecture](diagrams\microservices.png)

---

## 🚀 Getting Started (Local)

To run the project locally with Docker:

```bash
docker-compose up --build
```

Default ports:

* Config Server → `http://localhost:8888`
* Discovery Service → `http://localhost:8761`
* Keycloak → `http://localhost:8180`
* Gateway Service → `http://localhost:8080`
* Auth Service → `http://localhost:8081`
* Account Service → `http://localhost:8082`
* Business Service → `http://localhost:8083`

👉 For the **live running version**, visit: [https://api.renzoproject.site](https://api.renzoproject.site)

---

## 📂 Repository Structure

```
/microservices-mono-repo
│── config-server/        # Centralized configuration
│── discovery-service/    # Service discovery
│── gateway-service/      # Gateway service
│── auth-service/         # Authentication service
│── account-service/      # Account management
│── business-service/     # Business service
│── diagrams/             # Diagrams
│── init/                 # Initial import
│── .env                  # Environment variables
│── docker-compose.yml    # Multi-service Docker setup
│── README.md             # This file
```

---

## 📑 Microservice Documentation

Each microservice includes its own `README.md` with details (purpose, endpoints, configuration, run/debug/test instructions):
- [Config Server](config-server/README.md)
- [Discovery Service](discovery-service/README.md)
- [Gateway Service](gateway-service/README.md)
- [Auth Service](auth-service/README.md)
- [Account Service](account-service/README.md)
- [Business Service](business-service/README.md)
---

## ☸️ Deployment

* Local development: `docker-compose up --build`
* Production deployment: hosted on **[https://api.renzoproject.site](https://api.renzoproject.site)** using

  * Docker + Nginx reverse proxy
  * SSL via Let’s Encrypt

---

## 📈 Scalability & Future Improvements

* Centralized logging with ELK (Elasticsearch, Logstash, Kibana).
* Distributed tracing with Zipkin or Jaeger.
* Container orchestration with Kubernetes.
* CI/CD pipeline (GitHub Actions, Jenkins, or GitLab CI).
* Secure API Gateway with OAuth2 / Keycloak.

---

## 📚 References

* [Spring Cloud Config Docs](https://spring.io/projects/spring-cloud-config)
* [Spring Cloud Netflix (Eureka)](https://spring.io/projects/spring-cloud-netflix)
* [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)
* [Docker Compose](https://docs.docker.com/compose/)
