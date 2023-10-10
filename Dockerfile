FROM openjdk:17
ADD target/hakaton.jar /app/hakaton.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "hakaton.jar"]
