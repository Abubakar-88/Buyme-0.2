# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file to the container
# Replace `target/your-application.jar` with the actual path to your JAR file
COPY target/buyme-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot application runs on (default is 8080)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
