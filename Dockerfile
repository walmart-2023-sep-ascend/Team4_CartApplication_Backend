From docker.prod.walmart.com/strati/zulu:17-jdk-alpine
ARG JAR_FILE=target/*.jar
ARG org_name
ARG file_name
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]