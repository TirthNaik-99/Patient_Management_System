#we define this in creating the Docker file
FROM maven:3.9.9-eclipse-temurin-21 AS builder

#Making our own custom working directory keeps our own custom code in a single location
WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

#changes made and to make a note of it
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk AS runner

WORKDIR /app

COPY --from=builder ./app/target/patient-service-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 4000

ENTRYPOINT ["java", "-jar", "app.jar"]