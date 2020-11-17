FROM java:8-alpine
VOLUME /tmp
EXPOSE 8080
ADD /target/payment-service-1.0.0-SNAPSHOT.jar payment-service-1.0.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar",  "payment-service-1.0.0-SNAPSHOT.jar"]