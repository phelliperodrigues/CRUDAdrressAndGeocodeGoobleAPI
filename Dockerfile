FROM openjdk:11
MAINTAINER Phellipe Rodrigues
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} address.jar
ENTRYPOINT ["java", "-jar" , "/address.jar"]

