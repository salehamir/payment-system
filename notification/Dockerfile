FROM java:8-alpine
VOLUME /tmp
EXPOSE 8082
ADD /target/notification-1.0.0-SNAPSHOT.jar notification-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar",  "notification-0.0.1-SNAPSHOT.jar"]