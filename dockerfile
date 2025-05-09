# 🛠️ Étape 1 : Build avec Maven
FROM eclipse-temurin:17-alpine AS build

# Installer Maven et curl
RUN apk add --no-cache maven curl

# Créer le dossier de travail
WORKDIR /app

# Précharger les dépendances Maven pour améliorer le cache Docker
COPY pom.xml .
RUN mvn dependency:go-offline

# Copier tout le projet en une seule étape pour mieux gérer les modifications de fichiers
COPY . .
RUN mvn clean package -DskipTests

# 🏗️ Étape 2 : Image minimale pour l'exécution
FROM eclipse-temurin:17-alpine

WORKDIR /app

# Copier uniquement le JAR compilé depuis l'image précédente
COPY --from=build /app/target/SYSGESPECOLE1-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port attendu par Render
EXPOSE 8080

# ✅ Utiliser la variable `PORT` fournie par Render au démarrage
CMD ["java", "-jar", "app.jar", "--server.address=0.0.0.0", "--server.port=${PORT:-8080}"]
