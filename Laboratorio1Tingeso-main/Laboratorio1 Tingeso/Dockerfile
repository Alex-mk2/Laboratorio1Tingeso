FROM openjdk:18.0.2
ARG JAR_FILE=target/monolithic-webapp-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} monolithic-webapp-0.0.1-SNAPSHOT.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "/monolithic-webapp-0.0.1-SNAPSHOT.jar"]
