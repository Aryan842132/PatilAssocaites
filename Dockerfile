# Use OpenJDK 17 slim image as base
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven files
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Make mvnw executable
RUN chmod +x ./mvnw

# Download dependencies (to cache them separately)
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose port (Railway will set PORT environment variable)
EXPOSE 8080

# Set environment variable for production profile
ENV SPRING_PROFILES_ACTIVE=prod

# Run the application
ENTRYPOINT ["java", "-jar", "target/barAndRestaurants-0.0.1-SNAPSHOT.jar"]