FROM openjdk:17-ea-6-jdk

EXPOSE 8080

RUN mkdir -p /app
COPY build/libs/app.jar /app

ENTRYPOINT ["java", "-jar", "/app/app.jar"]