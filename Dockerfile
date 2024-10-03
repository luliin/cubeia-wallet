FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/cubeia-wallet-0.0.1-SNAPSHOT.jar /app/cubeia-wallet.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Duser.timezone=Europe/Stockholm", "-jar", "/app/cubeia-wallet.jar"]
