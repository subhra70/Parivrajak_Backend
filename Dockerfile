FROM openjdk:24-jdk-slim

WORKDIR /app

COPY target/parivrajak-backend.jar app.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "app.jar"]
