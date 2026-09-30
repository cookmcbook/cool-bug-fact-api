FROM maven:3.9.9-eclipse-temurin-23 AS build

WORKDIR /build
COPY pom.xml .
RUN mvn -q dependency:go-offline
COPY src ./src
RUN mvn -q -DskipTests package

FROM eclipse-temurin:23-jre

WORKDIR /app
COPY --from=build /build/target/*.jar app.jar
COPY Cool-Bug-Facts-Meme.jpg .

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
