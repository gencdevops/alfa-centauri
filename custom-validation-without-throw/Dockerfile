FROM openjdk:17-slim
WORKDIR /app
ADD build/libs/custom-validation-without-throw-0.0.1-SNAPSHOT.jar custom-validation-without-throw.jar
ENTRYPOINT ["java", "-jar", "custom-validation-without-throw.jar"]