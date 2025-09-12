FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy only the POM file first to leverage Docker cache
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source files
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the built JAR
COPY --from=build /app/target/auth-service-*.jar app.jar

# Expose the application port (default: 8080)
EXPOSE ${SERVER_PORT}

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]