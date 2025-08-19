# ===== BUILD STAGE =====
FROM eclipse-temurin:21-jdk AS builder

# Diretório de trabalho
WORKDIR /app

# Copiar arquivos Maven para cache de dependências
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Dar permissão de execução ao mvnw
RUN chmod +x ./mvnw

# Baixar dependências (cache)
RUN ./mvnw dependency:go-offline -B

# Copiar código fonte
COPY src src

# Build da aplicação (skip testes para agilizar)
RUN ./mvnw clean package -DskipTests

# ===== RUNTIME STAGE =====
FROM eclipse-temurin:21-jre-alpine

# Instalar wget para health check
RUN apk add --no-cache wget

# Criar usuário não-root
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Diretório de trabalho
WORKDIR /app

# Copiar JAR da build
COPY --from=builder /app/target/*.jar app.jar

# Ajustar permissões
RUN chown appuser:appgroup app.jar

# Usar usuário não-root
USER appuser

# Porta da aplicação
EXPOSE 8080

# Health check com endpoint correto
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
    CMD wget -qO- http://localhost:8080/api/health || exit 1

# Executar aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
