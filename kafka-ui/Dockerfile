FROM openjdk:17-alpine
WORKDIR /app
ADD /build/libs/kafka-ui-0.0.1.jar spring-kafka-ui.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker","spring-kafka-ui.jar"]