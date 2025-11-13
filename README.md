# Mutant Detector API

## Descripción

Esta API REST permite detectar si un humano es mutante en base a su secuencia de ADN. También proporciona estadísticas sobre las verificaciones de ADN realizadas.

El proyecto está desarrollado en Java 17 con Spring Boot 3 y utiliza una base de datos en memoria H2.

## Características

- API REST para la detección de mutantes.
- Almacenamiento de ADN verificado en una base de datos.
- Estadísticas de verificaciones de ADN.
- Documentación de la API con Swagger/OpenAPI.
- Soporte para Docker.

## Requisitos Previos

- Java 17 o superior
- Gradle 8 o superior
- Docker (opcional, para ejecución en contenedor)

## Ejecución Local

1.  **Clonar el repositorio:**

    ```bash
    git clone https://github.com/your-username/examenmercado.git
    cd examenmercado
    ```

2.  **Construir el proyecto:**

    -   En Windows:
        ```bash
        gradlew build
        ```
    -   En Linux/macOS:
        ```bash
        ./gradlew build
        ```

3.  **Ejecutar la aplicación:**

    -   En Windows:
        ```bash
        gradlew bootRun
        ```
    -   En Linux/macOS:
        ```bash
        ./gradlew bootRun
        ```

La aplicación estará disponible en `http://localhost:8080`.

## Ejecución con Docker

1.  **Construir la imagen de Docker:**

    ```bash
    docker build -t examenmercado .
    ```

2.  **Ejecutar el contenedor:**

    ```bash
    docker run -p 8080:8080 examenmercado
    ```

La aplicación estará disponible en `http://localhost:8080`.

## Guía de la API

### Documentación Swagger

La documentación completa de la API, junto con una interfaz de usuario para probar los endpoints, está disponible en:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Endpoints

#### `POST /mutant`

Detecta si un humano es mutante.

-   **Request Body:**

    ```json
    {
        "dna": [
            "ATGCGA",
            "CAGTGC",
            "TTATGT",
            "AGAAGG",
            "CCCCTA",
            "TCACTG"
        ]
    }
    ```

-   **Responses:**
    -   `200 OK`: Si el ADN es de un mutante.
    -   `403 Forbidden`: Si el ADN es de un humano.

#### `GET /stats`

Obtiene las estadísticas de las verificaciones de ADN.

-   **Response Body:**

    ```json
    {
        "count_mutant_dna": 40,
        "count_human_dna": 100,
        "ratio": 0.4
    }
    ```

## Pruebas

Para ejecutar las pruebas unitarias y de integración:

-   En Windows:
    ```bash
    gradlew test
    ```
-   En Linux/macOS:
    ```bash
    ./gradlew test
    ```
