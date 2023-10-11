FROM openjdk:17
COPY target/hakaton.jar /app/hakaton.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "hakaton.jar"]
