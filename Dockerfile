FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew build -x test

Ejecuta automáticamente el jar correcto (no el plain)
CMD ["sh", "-c", "java -jar build/libs/$(ls build/libs | grep -v plain | head -n 1)"]
