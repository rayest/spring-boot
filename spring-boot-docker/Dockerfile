FROM openjdk:8-jre-alpine
COPY build/libs/spring-boot-docker.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
