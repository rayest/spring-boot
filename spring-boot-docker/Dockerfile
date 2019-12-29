FROM openjdk:8-jre-alpine
COPY build/libs/spring-boot-docker.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
