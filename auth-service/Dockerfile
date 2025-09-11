# Step 1: Use lightweight Java 17 runtime
FROM openjdk:17-jdk-slim

# Step 2: Set working directory inside the container
WORKDIR /app

# Step 3: Copy the JAR file from target/ into the container
# (Make sure you first run `mvn clean package -DskipTests`)
COPY target/auth-service-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Expose the port (matches application.yml -> 8081)
EXPOSE 8081

# Step 5: Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
