# Use a Maven image to build the Spring Boot app
FROM maven:3.8.8-eclipse-temurin-17 AS build

# Set the working directory
WORKDIR /tms-backend

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the app
RUN mvn clean package -DskipTests

# Use a lightweight JDK image to run the built app
FROM eclipse-temurin:17-jre-alpine

# Set the working directory
WORKDIR /tms-backend

# Copy the built JAR file
COPY --from=build /tms-backend/target/*.jar app.jar

# Expose the backend port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
