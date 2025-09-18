# Step 1: Use Maven to build the application
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Run the JAR with a lightweight JDK
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/User-service-0.0.1-SNAPSHOT.jar user-service.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "user-service.jar"]
