# Created by Andryev Lemes
FROM maven:3.5-jdk-8-alpine

COPY beblue-0.0.1.jar beblue-api/

WORKDIR beblue-api/

EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=prod", "-jar", "beblue-0.0.1.jar"]
