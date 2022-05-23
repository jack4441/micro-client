FROM eclipse-temurin:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} bootcamp.client-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/bootcamp.client-0.0.1-SNAPSHOT.jar"]