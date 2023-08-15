FROM openjdk:17
EXPOSE 8080
ADD target/shorten.jar shorten.jar
ENTRYPOINT ["java", "-jar", "/shorten.jar"]