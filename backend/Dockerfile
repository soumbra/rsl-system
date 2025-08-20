# ===== BUILD STAGE =====
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

COPY pom.xml mvnw ./
COPY .mvn .mvn

RUN chmod +x ./mvnw

RUN ./mvnw dependency:go-offline -B

COPY src src

RUN ./mvnw clean package -DskipTests

# ===== RUNTIME STAGE =====
FROM eclipse-temurin:21-jre-alpine

# Instalar wget, criar usuário e ajustar permissões em um RUN só
RUN apk add --no-cache wget && \
    addgroup -S appgroup && \
    adduser -S appuser -G appgroup

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

# Ajustar permissões
RUN chown appuser:appgroup app.jar

USER appuser

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
    CMD wget -qO- http://localhost:8080/api/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
