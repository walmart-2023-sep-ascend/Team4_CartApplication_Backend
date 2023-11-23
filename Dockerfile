From docker.prod.walmart.com/strati/zulu:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
RUN pwd
ENTRYPOINT ["java","-jar","/app.jar"]