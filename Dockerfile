FROM openjdk:8-jdk-alpine
MAINTAINER Bossini Gustavo
VOLUME /tmp
ARG JAR_FILE
ADD target/comparador-planilhas-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]