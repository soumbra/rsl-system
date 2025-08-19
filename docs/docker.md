# 🐳 Ambiente Docker

## Pré-requisitos

- [Docker](https://docs.docker.com/get-docker/) 20.10+
- [Docker Compose](https://docs.docker.com/compose/install/) 2.0+

## Estrutura dos Containers

```
RSL System Docker Environment
├── 🗄️  PostgreSQL 15      → localhost:5432 (Database)
├── ☕ Spring Boot API    → localhost:8080 (Backend)
└── 🌐 Vue.js + Vite      → localhost:5173 (Frontend)
```

## 🚀 Quick Start

### 1. Configuração Inicial

```bash
# Clone o repositório
git clone
cd rsl-system

# Copie o arquivo de configuração
cp .env.example .env
```

### 2. Personalizar Variáveis (Opcional)

Edite o arquivo `.env` se necessário:

```bash
POSTGRES_DB= x
POSTGRES_USER= x
POSTGRES_PASSWORD= x
BACKEND_PORT=8080
FRONTEND_PORT=5173
SPRING_PROFILE=dev
```

### 3. Executar Ambiente

```bash
# Subir todos os serviços (primeira vez)
docker compose up --build

# Subir em background
docker compose up -d --build

# Parar todos os serviços
docker compose down

# Parar e remover volumes (⚠️ perde dados do banco)
docker compose down -v
```

## 🌐 Acessos

| Serviço          | URL                              | Descrição                    |
| ---------------- | -------------------------------- | ---------------------------- |
| **Frontend**     | http://localhost:5173            | Interface Vue.js com Vuetify |
| **Backend API**  | http://localhost:8080/api        | Spring Boot REST API         |
| **Health Check** | http://localhost:8080/api/health | Status do backend            |
| **Database**     | localhost:5432                   | PostgreSQL (acesso interno)  |

## 📊 Monitoramento

### Verificar Status dos Containers

```bash
# Ver containers rodando
docker compose ps

# Ver logs em tempo real
docker compose logs -f

# Logs específicos de um serviço
docker compose logs -f backend
docker compose logs -f frontend
docker compose logs -f postgres
```

### Health Checks

```bash
# Testar backend manualmente
curl http://localhost:8080/api/health

# Verificar saúde dos containers
docker compose ps
# Containers saudáveis mostram: Up (healthy)
```

## 🛠️ Desenvolvimento

### Hot Reload

- **Frontend**: Alterações em `./frontend/src` são refletidas automaticamente
- **Backend**: Rebuild necessário após mudanças (ou usar Spring DevTools)

### Rebuild Após Mudanças

```bash
# Rebuild específico
docker compose up --build backend
docker compose up --build frontend

# Rebuild completo
docker compose up --build
```

### Acesso aos Containers

```bash
# Entrar no container do backend
docker compose exec backend bash

# Entrar no container do frontend
docker compose exec frontend sh

# Acessar PostgreSQL
docker compose exec postgres psql -U rsl_user -d rsl_system
```

## 🐛 Troubleshooting

### Problemas Comuns

**1. Porta já em uso (8080/5173/5432)**

```bash
# Ver qual processo usa a porta
sudo lsof -i :8080

# Parar containers Docker órfãos
docker compose down --remove-orphans
```

**2. Backend não conecta no banco**

```bash
# Verificar logs do backend
docker compose logs backend

# Verificar se postgres está healthy
docker compose ps postgres
```

**3. Frontend não conecta no backend**

- Verifique se backend está `Up (healthy)` no `docker compose ps`
- Confirme CORS configurado para `localhost:5173`
- Teste `curl http://localhost:8080/api/health`

**4. Limpar ambiente completamente**

```bash
# Remove containers, networks e volumes
docker compose down -v --remove-orphans

# Limpa cache do Docker
docker system prune -f

# Rebuild do zero
docker compose up --build
```

### Logs Detalhados

```bash
# Backend: Spring Boot logs
docker compose logs backend --tail 50

# Frontend: Vite dev server logs
docker compose logs frontend --tail 50

# Database: PostgreSQL logs
docker compose logs postgres --tail 50
```

## 📁 Estrutura de Arquivos Docker

```
rsl-system/
├── .env                    # Variáveis de ambiente (não versionado)
├── .env.example           # Template de configuração
├── docker-compose.yml     # Orquestração dos serviços
├── backend/
│   └── Dockerfile        # Multi-stage: builder + runtime
├── frontend/
│   ├── Dockerfile        # Multi-stage: dev + builder + nginx
│   └── nginx.conf        # Configuração Nginx para SPA
└── README.md            # Esta documentação
```

## 🚀 Deploy em Produção

Para deploy em produção, use o target `production` do frontend:

```bash
# Build para produção
docker compose -f docker-compose.prod.yml up --build

# Ou override do target
docker compose up --build frontend
# (altere target: production no docker-compose.yml)
```

---

**✅ Ambiente Docker configurado com sucesso!**
Todos os serviços devem estar acessíveis e comunicando entre si.
