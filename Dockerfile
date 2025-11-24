# Stage 1: Build the application
FROM openjdk:17-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew bootJar --no-daemon

# Stage 2: Create the final, smaller image
FROM openjdk:17-jdk-alpine
WORKDIR /app
EXPOSE 8080
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
