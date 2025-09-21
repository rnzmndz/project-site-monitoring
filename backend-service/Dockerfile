FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy only the POM file first to leverage Docker cache
COPY pom.xml .
COPY application/pom.xml application/
COPY employee-service/pom.xml employee-service/

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source files
COPY . .

# Build the application
RUN mvn clean package -pl application -am -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the built JAR
COPY --from=build /app/application/target/application-*.jar app.jar

# Expose the application port (default: 8080)
EXPOSE 8083

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]