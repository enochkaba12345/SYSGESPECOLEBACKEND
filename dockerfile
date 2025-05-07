# Étape de build
FROM openjdk:17-alpine AS build
RUN apk add --no-cache maven curl

WORKDIR /app

# Précharger les dépendances Maven pour optimiser le cache Docker
COPY pom.xml .
RUN mvn dependency:go-offline 

# Copier le code source et construire le projet
COPY src/ src/
RUN mvn clean package -DskipTests 

# Étape de déploiement
FROM openjdk:17
WORKDIR /app
COPY --from=build /app/target/SYSGESPECOLE1-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
