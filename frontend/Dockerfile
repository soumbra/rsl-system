# ===== DESENVOLVIMENTO =====
FROM node:20-alpine AS development

# Diretório de trabalho
WORKDIR /app

# Copiar package files para cache de dependências
COPY package*.json ./

# Instalar dependências (incluindo devDependencies)
RUN npm ci

# Copiar código fonte
COPY . .

# Porta do Vite dev server
EXPOSE 5173

# Comando dev com host 0.0.0.0 (necessário para Docker)
CMD ["npm", "run", "dev", "--", "--host", "0.0.0.0"]

# ===== BUILD PRODUÇÃO =====
FROM node:20-alpine AS builder

WORKDIR /app

# Cache de dependências
COPY package*.json ./
RUN npm ci

# Copiar código e build
COPY . .
RUN npm run build

# ===== PRODUÇÃO =====
FROM nginx:alpine AS production

# Copiar build para nginx
COPY --from=builder /app/dist /usr/share/nginx/html

# Copiar configuração nginx customizada
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Porta nginx
EXPOSE 80

# Comando nginx
CMD ["nginx", "-g", "daemon off;"]
