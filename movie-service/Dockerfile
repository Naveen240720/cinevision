# Step 1: Use lightweight Java 17 runtime
FROM openjdk:17-jdk-slim

# Step 2: Set working directory inside the container
WORKDIR /app

# Step 3: Copy the JAR file from target/ into the container
# (Run: mvn clean package -DskipTests)
COPY target/movie-service-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Expose the port (set this in application.yml -> 8082)
EXPOSE 8082

# Step 5: Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
