#Build stage
FROM maven:4.0.0-rc-5-amazoncorretto-25-al2023
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests