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

---

# Core

Frontend Core:
Vuetify + tema base
Axios configurado apontando para backend
Pinia store básico
Roteamento básico (login, dashboard)

Backend Core:
Spring Security básico (sem JWT ainda)
CORS configurado para frontend
Health check endpoint
Perfis de ambiente (dev, prod)

Docker Core:
Multi-stage build
docker-compose com frontend + backend + postgres
Variáveis de ambiente organizadas

CI/CD - Melhor momento:
DEPOIS de ter o core funcionando! Recomendo:

Agora: Core funcionando local
Próxima semana: CI básico (build + test)
2ª semana: CD para ambiente de dev
3ª semana: CD para produção

✅ FASE 1 - Core "Visões Isoladas" (CONCLUÍDA)
├── User, Review, Study, Source (base)
├── ReviewerStudyAssessment (avaliações isoladas) ✅
├── StudyConsensus (resolução de conflitos) ✅
└── StudySelection (orquestração) ✅

🎯 FASE 2 - Estrutura de Planejamento (PRÓXIMA)
├── ReviewPlanning (primeira fase RSL) ← AGORA
├── Protocol (keywords, critérios) ← AGORA
└── QualityAssessment (config avaliação) ← DEPOIS

📅 FASE 3 - Estrutura de Condução (FUTURO)
├── ReviewConducting (segunda fase RSL)
├── Search (configuração buscas)
└── ImportStudies (importação)

🔮 FASE 4 - Extração e Qualidade (FUTURO)
├── DataExtraction + ExtractionForm + ExtractionField
├── QualityStudy + ReviewerQualityAssessment
└── DataAnalyses + ReportData

---

1. 📊 COMPLETAR FASE 4 (4 entidades DataExtraction)
   └── DataExtraction, ExtractionForm, ExtractionField, ReviewerDataExtraction

2. 📈 IMPLEMENTAR FASE 5 (3 entidades Análise/Relatórios)
   └── DataAnalyses, ReportData, ReviewResults

3. 🔧 SERVICES LAYER (Lógica de Negócio)
   └── Planning, Protocol, Conducting, Extraction Services

4. 🌐 CONTROLLERS REST (APIs para Frontend)
   └── Reviews, Planning, Conducting, Extraction APIs

5. 🧪 TESTES UNITÁRIOS E INTEGRAÇÃO
   └── Cobertura completa do domínio

---

Novo roadmap:

## 📅 Novo Roadmap

| Sprint   | Semanas | Foco                    | Entregas                                                 |
| -------- | ------- | ----------------------- | -------------------------------------------------------- |
| Sprint 1 | 1-2     | 🔍 Busca + Importação   | Search, ImportStudies, DuplicateGroup                    |
| Sprint 2 | 3-4     | 📈 Análise + Relatórios | DataAnalyses, ReviewResults, ReviewReporting, ReportData |
| Sprint 3 | 5       | 🎯 Qualidade Avançada   | QualityQuestion, Answer                                  |
| Sprint 4 | 6-7     | 🔧 Services Layer       | Core + Workflow Services                                 |
| Sprint 5 | 8-9     | 🌐 Controllers REST     | APIs completas                                           |
| Sprint 6 | 10-11   | 🧪 Testing Suite        | Cobertura 80%+                                           |
