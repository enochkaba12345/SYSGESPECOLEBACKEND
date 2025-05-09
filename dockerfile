# 🛠️ Étape 1 : Build avec Maven
FROM eclipse-temurin:17-alpine AS build

# Installer Maven et curl
RUN apk add --no-cache maven curl

# Créer le dossier de travail
WORKDIR /app

# Copier le fichier pom.xml et télécharger les dépendances
COPY pom.xml .
RUN mvn dependency:go-offline

# Copier le code source et compiler l’application
COPY src/ src/
RUN mvn clean package -DskipTests

# 🏗️ Étape 2 : Image finale minimale pour l'exécution
FROM eclipse-temurin:17-alpine

WORKDIR /app

# Copier le jar compilé depuis l'image précédente
COPY --from=build /app/target/SYSGESPECOLE1-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port attendu par Render
EXPOSE 8080

# ✅ Lancer l'application avec le bon host (Render fournit PORT via env var)
CMD ["java", "-jar", "app.jar", "--server.port=8080", "--server.address=0.0.0.0"]
