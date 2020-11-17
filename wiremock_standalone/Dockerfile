FROM java:8-alpine
VOLUME /tmp
EXPOSE 8088
RUN mkdir config
COPY config config
ADD wiremock-jre8-standalone-2.27.2.jar wiremock.jar
ENTRYPOINT ["java", "-jar", "wiremock.jar","--verbose", "--port", "8088","--root-dir","./config"]
