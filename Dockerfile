
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
RUN mkdir -p /tmp/s3-files
EXPOSE 9494
ENTRYPOINT ["java","-jar","app.jar"]