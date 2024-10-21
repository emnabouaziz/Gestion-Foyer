FROM openjdk:17-jdk-alpine
EXPOSE 8089
ARG NEXUS_REPO
ADD target/tp-foyer-5.0.0.jar tp-foyer-5.0.0.jar
CMD ["java", "-jar", "/tp-foyer-5.0.0.jar "]
