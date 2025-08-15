# PRÓXIMOS PASSOS - ROADMAP COMPLETO

## 1. Backend - Estrutura Base (Próximos 2-3 dias)

- Criar entidades JPA: `User`, `Review`, `Article`, `ReviewEvaluation`  
- Configurar Spring Security + JWT  
- Criar repositórios (`UserRepository`, `ReviewRepository`, etc.)  
- Criar controllers básicos (`AuthController`, `ReviewController`)  
- Implementar autenticação/login  
- Criar DTOs e validações  

Vamos voltar e fazer a configuração completa:
O que temos até agora:

✅ Spring Boot rodando
✅ PostgreSQL funcionando
✅ Lombok adicionado

O que FALTA configurar antes das entidades:

Spring Security + JWT (autenticação)
Estrutura de pacotes organizada
Configurações adicionais (CORS, etc.)
Perfis de ambiente (dev, prod)

Por onde você quer começar?

Opção 1: Spring Security + JWT (mais complexo, mas essencial)
Opção 2: Organizar estrutura de pacotes primeiro
Opção 3: Configurações adicionais (CORS, perfis)

Qual você prefere atacar primeiro? Assim fazemos direito, sem pular etapas!

## 2. Frontend - Setup Inicial (1-2 dias)

- Configurar Vue 3 + TypeScript + Vuetify  
- Configurar roteamento (Vue Router)  
- Configurar gerenciamento de estado (Pinia)  
- Criar interceptor HTTP (Axios) para JWT  
- Criar layout base e componentes de autenticação  

## 3. Funcionalidades Core (1-2 semanas)

- Tela de login/registro  
- Dashboard principal  
- CRUD de revisões sistemáticas  
- Sistema de convites para revisores  
- Upload/import de artigos (CSV, BibTeX)  
- Tela de seleção individual (blind screening)  

## 4. Sistema de Revisão por Pares (1-2 semanas)

- Implementar fases da revisão (individual → consenso → final)  
- Controle de permissões por fase  
- Interface isolada para cada revisor  
- Tela de resolução de conflitos  
- Sistema de comentários/discussão  

## 5. Funcionalidades Avançadas (2-3 semanas)

- Métricas e relatórios (Cohen's Kappa, etc.)  
- Exportação de resultados (CSV, PDF)  
- Sistema de notificações  
- Busca textual avançada  
- Versionamento de revisões  

## 6. Integrações Futuras

- APIs bibliográficas (PubMed, Scopus)  
- Elasticsearch para busca  
- Sistema de backup automático  
- Deploy em produção (Docker Swarm/Kubernetes)  
