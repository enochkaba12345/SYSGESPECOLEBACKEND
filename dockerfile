# Étape de build
FROM openjdk:17 AS build
RUN apt-get update && apt-get install -y maven
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
