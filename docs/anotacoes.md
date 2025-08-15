# Comandos 

# Testar conexão diretamente no container
docker exec -it rsl-system-db psql -U rsl_user -d rsl_system -c "\dt"

docker docker compose up -d 
docker compose ps

# Subir banco
cd docker && docker compose up -d

# Rodar backend  
cd backend && ./mvnw spring-boot:run

# Futuro frontend
cd frontend && npm run dev

# Para testes do backend usar isto por enquanto
export $(cat backend/.env | xargs)
./mvnw spring-boot:run
