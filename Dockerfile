FROM openjdk:11-jdk-slim

COPY /build/libs/*.jar app.jar

CMD ["java", "-jar", "app.jar"]