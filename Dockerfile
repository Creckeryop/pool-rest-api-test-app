FROM maven:3.8.5-openjdk-18 AS build
COPY src /src
COPY pom.xml ./
RUN mvn -e install

FROM openjdk:18-jdk
COPY --from=build /target/pool-rest-api-test-app-0.0.1-SNAPSHOT.war /usr/local/lib/pool-rest-api-test-app.war
