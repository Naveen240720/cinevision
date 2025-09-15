# Use official JDK runtime
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy built JAR file into container
COPY target/auth-service-0.0.1-SNAPSHOT.jar auth-service.jar

# Expose application port
EXPOSE 8081

# Run the JAR file
ENTRYPOINT ["java", "-jar", "auth-service.jar"]
