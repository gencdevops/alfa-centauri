FROM openjdk:17-slim
WORKDIR /app
ADD build/libs/property-encryption-with-jasypt-0.0.1.jar jasypt-encryptor.jar
ENTRYPOINT ["java", "-jar", "jasypt-encryptor.jar"]