FROM openjdk:17-oracle

WORKDIR /usr/src/app

COPY build/libs/ReviewZIP-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]