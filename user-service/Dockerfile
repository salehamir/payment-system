FROM java:8-alpine
VOLUME /tmp
EXPOSE 8084
ADD /target/user-service-1.0.0-SNAPSHOT.jar user-service-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar",  "user-service-1.0.0-SNAPSHOT.jar"]