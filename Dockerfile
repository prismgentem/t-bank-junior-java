# Этап сборки
FROM maven:3.8.5-openjdk-17 AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы проекта
COPY pom.xml .
COPY src ./src

# Собираем проект и создаем JAR файл
RUN mvn clean package -DskipTests

# Этап запуска
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем JAR файл из контейнера сборки
COPY --from=build /app/target/t-bank-junior-java-0.0.1-SNAPSHOT.jar /app/t-bank-junior-java.jar

# Открываем порт, на котором будет работать приложение
EXPOSE 8080

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "/app/t-bank-junior-java.jar"]
