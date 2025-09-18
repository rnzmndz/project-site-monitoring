# Spring Cloud Config Server with Docker

This project provides a Spring Cloud Config Server that loads configuration files from a Git repository and makes them available to microservices.

---

## 🚀 Prerequisites

* [Docker](https://www.docker.com/) installed on your system
* A Git repository containing your configuration files (e.g., `application.yml`, `application-dev.yml`)

---

## 🛠 Build the Docker Image

1. Build the JAR file using Maven or Gradle:

```bash
./mvnw clean package -DskipTests
```

This will create a JAR file in the `target/` directory, e.g. `config-server-0.0.1-SNAPSHOT.jar`.

2. Build the Docker image:

```bash
docker build -t config-server:latest .
```

---

## 🐳 Run the Config Server with Docker

You can start the container by passing environment variables for your Git repo:

```bash
docker run -d \
  -p 8888:8888 \
  -e SPRING_CLOUD_CONFIG_SERVER_GIT_URI=https://github.com/your-org/your-config-repo.git \
  -e SPRING_CLOUD_CONFIG_SERVER_GIT_CLONE_ON_START=true \
  --name config-server \
  config-server:latest
```

---

## 🔍 Verify

Once running, you can check if the server is working:

```bash
curl http://localhost:8888/application/default
```

You should see the JSON response with your configuration.

---

## ⚙️ Example `Dockerfile`

Here’s a simple Dockerfile for this project:

```dockerfile
FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/config-server-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

---

## 📦 Docker Compose (Optional)

If you want to manage this service with Docker Compose:

```yaml
version: "3.8"
services:
  config-server:
    build: .
    ports:
      - "8888:8888"
    environment:
      SPRING_CLOUD_CONFIG_SERVER_GIT_URI: https://github.com/your-org/your-config-repo.git
      SPRING_CLOUD_CONFIG_SERVER_GIT_CLONE_ON_START: "true"
```

Run with:

```bash
docker-compose up -d
```

---

## 📝 Notes

* Make sure your Git repository is accessible (public or with proper credentials).
* Microservices should point to `http://<host>:8888` as their config server URL.
* Profiles (e.g., `dev`, `prod`) will be resolved based on filenames in the Git repo (`application-dev.yml`).

---
