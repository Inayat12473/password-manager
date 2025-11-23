# ===== Build stage =====
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# copy project files
COPY pom.xml .
COPY src ./src

# build jar
RUN mvn -q -DskipTests clean package

# ===== Run stage =====
FROM eclipse-temurin:21-jre
WORKDIR /app

# copy built jar from first stage
COPY --from=build /app/target/*.jar app.jar

# Render will set $PORT environment variable
CMD ["sh", "-c", "java -Dserver.port=$PORT -jar app.jar"]
