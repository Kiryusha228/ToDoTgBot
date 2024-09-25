# Базовый образ с Java и Gradle
FROM gradle:jdk17-focal AS build


WORKDIR /app

COPY build.gradle.kts build.gradle.kts
COPY settings.gradle.kts settings.gradle.kts


COPY crud-microservice/src crud-microservice/src
COPY crud-microservice/build.gradle.kts crud-microservice/build.gradle.kts

RUN gradle crud-microservice:build #--no-deamon

FROM openjdk:17-slim

WORKDIR /app

# Копируем собранный JAR из предыдущего этапа
COPY --from=build /app/crud-microservice/build/libs/*.jar app/crud-microservice/app.jar

# Команда для запуска приложения
CMD ["java", "-jar", "app/crud-microservice/app.jar"]
