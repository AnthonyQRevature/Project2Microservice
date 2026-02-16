#Build stage
FROM util:latest AS utility
WORKDIR /app
RUN cp ./target/*.jar ./util.jar

FROM maven:4.0.0-rc-5-amazoncorretto-25-al2023 AS build
WORKDIR /app
COPY --from=utility /app/util.jar ./util-1.0-SNAPSHOT.jar
COPY pom.xml .
RUN mvn install:install-file -DgroupId=com.example -DartifactId=util -Dversion=1.0-SNAPSHOT -Dpackaging=jar -Dfile=./util-1.0-SNAPSHOT.jar
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Run Stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]