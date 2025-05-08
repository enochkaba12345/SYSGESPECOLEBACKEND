# Étape de build
FROM eclipse-temurin:11-alpine AS build
RUN apk add --no-cache maven curl

WORKDIR /app

COPY pom.xml . 
RUN mvn dependency:go-offline

COPY src/ src/
RUN mvn clean package -DskipTests

# Étape de déploiement
FROM eclipse-temurin:11-alpine
WORKDIR /app
COPY --from=build /app/target/SYSGESPECOLE1-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
