# Étape de build
FROM maven:3.4.2-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline 
COPY src/ src/
RUN mvn clean package -DskipTests 

# Étape de déploiement
FROM openjdk:17
WORKDIR /app
COPY --from=build /app/target/SYSGESPECOLE1-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
