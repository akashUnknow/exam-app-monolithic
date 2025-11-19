# -----------------------------
# BUILD STAGE
# -----------------------------
FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /app

# 1️⃣ Copy pom.xml first to cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 2️⃣ Copy source code
COPY src ./src

# 3️⃣ Build the Spring Boot JAR (skip tests for faster builds)
RUN mvn clean package -DskipTests -B

# -----------------------------
# RUNTIME STAGE
# -----------------------------
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/exam-app-monolithic-1.0.0.jar .

# Expose application port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "exam-app-monolithic-1.0.0.jar"]
