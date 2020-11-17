FROM java:8-alpine
VOLUME /tmp
EXPOSE 8086
ADD /target/notification-1.0.0-SNAPSHOT.jar notification-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar",  "notification-1.0.0-SNAPSHOT.jar"]