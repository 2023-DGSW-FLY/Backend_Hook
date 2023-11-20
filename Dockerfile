FROM openjdk:17-jdk
ARG JAR_FILE=build/libs/hook-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/bootsecurity.p12 /app/src/main/resources/
ENTRYPOINT ["java", "-jar", "app.jar"]