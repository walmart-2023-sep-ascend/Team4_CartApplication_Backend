FROM docker.prod.walmart.com/strati/zulu:11-jdk-alpine-main
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]