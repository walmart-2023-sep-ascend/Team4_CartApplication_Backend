FROM openjdk:17
EXPOSE 9301
ADD target/cartapp-service.jar cartservice.jar
ENTRYPOINT ["java", "-jar", "/cartservice.jar"]
