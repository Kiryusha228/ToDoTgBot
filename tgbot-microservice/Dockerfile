# Базовый образ с Java и Gradle
FROM gradle:jdk17-focal AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы, необходимые для сборки
COPY build.gradle.kts build.gradle.kts
COPY settings.gradle.kts settings.gradle.kts

# Копируем исходный код
COPY tgbot-microservice/src tgbot-microservice/src
COPY tgbot-microservice/build.gradle.kts tgbot-microservice/build.gradle.kts

# Собираем проект
RUN gradle build --no-daemon

# Используем официальный образ Java для запуска приложения
FROM openjdk:17-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR из предыдущего этапа
COPY --from=build /app/build/libs/*.jar app.jar

# Команда для запуска приложения
CMD ["java", "-jar", "app.jar"]