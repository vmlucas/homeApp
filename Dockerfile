FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.war app.jar
ENTRYPOINT ["java","-jar","/app.jar"]