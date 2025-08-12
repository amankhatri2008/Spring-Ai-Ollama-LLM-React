# --- Stage 1: Build the React Frontend ---
FROM node:18 AS frontend-builder
WORKDIR /app/frontend
COPY frontend/package.json ./
RUN npm install
COPY frontend/ .
RUN npm run build

# --- Stage 2: Build the Spring Boot Backend ---
FROM maven:3.9.6-eclipse-temurin-17 AS backend-builder
WORKDIR /app
COPY pom.xml ./
COPY src ./src
COPY .mvn ./.mvn
COPY mvnw ./
RUN chmod +x ./mvnw

# Copy React build into Spring Boot static resources BEFORE packaging
COPY --from=frontend-builder /app/frontend/build ./src/main/resources/static/

# Now package the Spring Boot application
RUN ./mvnw package -DskipTests

# --- Stage 3: The Final, Lightweight Runtime Image ---
FROM eclipse-temurin:17-jre-focal
WORKDIR /app
COPY --from=backend-builder /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

# --Stage 4 Run Ollama
FROM ollama/ollama:latest
ENV OLLAMA_HOST 0.0.0.0:8080
ENV OLLAMA_MODELS /models
ENV OLLAMA_KEEP_ALIVE -1
# Example: Pulling a specific model
ENV MODEL gemma3:latest
RUN ollama serve & sleep 5 && ollama pull $MODEL
ENTRYPOINT ["ollama", "serve"]
