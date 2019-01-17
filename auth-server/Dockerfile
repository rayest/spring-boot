FROM doublemine/openjdk:8-jre-alpine

COPY auth-server.jar /app/
CMD  ["java", "-Djava.security.egd=file:/dev/./urandom","-XX:+UnlockExperimentalVMOptions","-XX:+UseCGroupMemoryLimitForHeap","-server","-Xms400m","-Xmx400m","-jar", "/app/auth-server.jar"]
EXPOSE 5001