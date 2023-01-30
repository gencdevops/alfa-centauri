FROM openjdk:17-alpine
WORKDIR /app
ADD /build/libs/kafka-logback-xml-v2-0.0.1-SNAPSHOT.jar spring-kafka-logback.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker","spring-kafka-logback.jar"]