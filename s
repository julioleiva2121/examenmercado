ROM gradle:jdk17 AS build

WORKDIR /app
COPY . .
RUN ./gradlew build --no-daemon
FROM alpine:latest as build
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]