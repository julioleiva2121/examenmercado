# Stage 1: Build the application
FROM gradle:jdk17 AS build
WORKDIR /app
COPY . .
RUN ./gradlew build --no-daemon

# Stage 2: Create the final image
FROM openjdk:17-jre-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
