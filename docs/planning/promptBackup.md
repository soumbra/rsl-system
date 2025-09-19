Estou desenvolvendo o RSL System - sistema de revisões sistemáticas da literatura para competir com Parsifal.

**STACK TECNOLÓGICA:**

- Backend: Spring Boot 3 + Java 21 + PostgreSQL + JPA/Hibernate + Lombok
- Frontend: Vue 3 + TypeScript + Vuetify + Pinia
- Infra: Docker Compose
- Qualidade: SonarQube compliance

**DIFERENCIAL COMPETITIVO:**

- ✅ Visões isoladas (blind review) - cada revisor vê apenas seus dados
- ✅ Resolução de conflitos automatizada com consenso
- ✅ Formulários de extração dinâmicos e configuráveis
- ✅ Workflow acadêmico rigoroso seguindo metodologia científica
- ✅ Métricas avançadas de qualidade e eficiência

**ESTADO ATUAL - FASE 4 COMPLETA (64% do sistema):**

- ✅ **16/25 entidades** implementadas e funcionais
- ✅ **4 fases completas**: Base, Core, Planejamento, Condução, Extração
- ✅ **500+ campos especializados** com validações robustas
- ✅ **200+ métodos de negócio** com lógica complexa
- ✅ **SonarQube compliance** 100% aplicado

**ENTIDADES JÁ IMPLEMENTADAS (16):**

**FASE BASE (5/5):**

1. **User** - Sistema de usuários (ADMIN, RESEARCHER, REVIEWER)
2. **Review** - Aggregate root - Projeto RSL principal
3. **Source** - Bases bibliográficas (PubMed, Scopus, IEEE, ACM)
4. **Study** - Artigos científicos importados para revisão
5. **ReviewAuthor** - Relacionamento M:N (via @JoinTable em Review)

**FASE 1 - CORE (3/3):** 6. **ReviewerStudyAssessment** - Avaliações cegas individuais (DIFERENCIAL) 7. **StudyConsensus** - Resolução automatizada de conflitos 8. **StudySelection** - Orquestração do processo de seleção

**FASE 2 - PLANEJAMENTO (3/3):** 9. **ReviewPlanning** - Primeira fase RSL (questões, estratégia, critérios) 10. **Protocol** - Protocolo metodológico detalhado 11. **QualityAssessment** - Configuração de avaliação de qualidade

**FASE 3 - CONDUÇÃO (1/1):** 12. **ReviewConducting** - Segunda fase RSL (execução do workflow)

**FASE 4 - EXTRAÇÃO (4/4):** 13. **DataExtraction** - Configuração geral de extração de dados 14. **ExtractionForm** - Formulários dinâmicos configuráveis 15. **ExtractionField** - Campos individuais com tipos e validações 16. **ReviewerDataExtraction** - Dados extraídos por cada revisor

**ENTIDADES FALTANDO (9/25) - PRÓXIMAS PRIORIDADES:**

**FASE 5 - BUSCA E IMPORTAÇÃO (3 entidades - ALTA PRIORIDADE):** 17. **Search** - Configuração de busca em bases bibliográficas 18. **ImportStudies** - Processo de importação de estudos 19. **DuplicateGroup** - Agrupamento de estudos duplicados

**FASE 6 - ANÁLISE E RELATÓRIOS (4 entidades - MÉDIA PRIORIDADE):** 20. **DataAnalyses** - Análises estatísticas dos dados extraídos 21. **ReviewResults** - Resultados consolidados da RSL 22. **ReviewReporting** - Relatórios exportáveis (PDF, DOCX, CSV) 23. **ReportData** - DTO para dados de relatórios

**FASE 7 - QUALIDADE AVANÇADA (2 entidades - BAIXA PRIORIDADE):** 24. **QualityQuestion** - Perguntas detalhadas de qualidade 25. **Answer** - Respostas configuráveis para avaliação

**ESTRUTURA DE PASTAS ATUAL:**

```
backend/src/main/java/com/rslsystem/api/
├── domain/
│   ├── ✅ User.java (authentication + roles)
│   ├── ✅ Review.java (aggregate root)
│   ├── ✅ Source.java (bibliographic databases)
│   ├── ✅ Study.java (scientific articles)
│   ├── ✅ ReviewerStudyAssessment.java (blind assessments)
│   ├── ✅ StudyConsensus.java (conflict resolution)
│   ├── ✅ StudySelection.java (selection orchestration)
│   ├── ✅ ReviewPlanning.java (planning phase)
│   ├── ✅ Protocol.java (methodology)
│   ├── ✅ QualityAssessment.java (quality config)
│   ├── ✅ ReviewConducting.java (conducting phase)
│   ├── ✅ DataExtraction.java (extraction config)
│   ├── ✅ ExtractionForm.java (dynamic forms)
│   ├── ✅ ExtractionField.java (configurable fields)
│   ├── ✅ ReviewerDataExtraction.java (extracted data)
│   └── shared/
│       ├── ✅ BaseEntity.java
│       ├── ✅ AuditableEntity.java
│       └── enums/
│           ├── ✅ WorkflowStatus.java
│           ├── ✅ ProtocolStatus.java
│           ├── ✅ ConductingPhase.java
│           ├── ✅ QualityRecommendation.java
│           └── [outros enums]
├── constants/
│   └── ✅ ApiConstants.java
└── [services/, controllers/, repositories/ - AINDA NÃO IMPLEMENTADOS]

frontend/src/
├── components/ (básico implementado)
├── views/ (Login, Dashboard, Profile)
├── stores/ (Pinia - appStore, backendStore)
├── services/ (httpClient.ts)
├── types/ (api-types.ts)
└── router/ (rotas básicas)

docs/
├── planning/
│   ├── ✅ roadmap.md (roadmap atualizado)
│   └── ✅ ROADMAP_UPDATED.md (novo roadmap detalhado)
└── analysis/
    └── ✅ uml.md (diagrama UML completo)
```

**RELACIONAMENTOS COMENTADOS PARA DESCOMENTAR:**

- Review.java linha ~45: ReviewReporting (aguardando entidade 22)
- Source.java: Search (aguardando entidade 17)
- ReviewConducting.java: Search + ImportStudies (aguardando entidades 17-18)
- Study.java: DuplicateGroup (aguardando entidade 19)

**WORKFLOW IMPLEMENTADO:**

1. **Planejamento** → ReviewPlanning + Protocol + QualityAssessment
2. **Condução** → ReviewConducting (busca, seleção, qualidade, extração)
3. **Seleção** → ReviewerStudyAssessment (blind) → StudyConsensus → StudySelection
4. **Extração** → DataExtraction + ExtractionForm + ExtractionField → ReviewerDataExtraction
5. **Análise** → [FALTANDO: DataAnalyses + ReviewResults]
6. **Relatório** → [FALTANDO: ReviewReporting + ReportData]

**FEATURES AVANÇADAS IMPLEMENTADAS:**

- ✅ Blind review system (visões isoladas)
- ✅ Conflict detection e resolution automática
- ✅ Dynamic form configuration
- ✅ Quality scoring com múltiplos fatores
- ✅ Time tracking e efficiency metrics
- ✅ Session management e auto-save
- ✅ Field-level usage statistics
- ✅ Comprehensive validation system
- ✅ Thread-safe operations (ConcurrentHashMap)
- ✅ Performance optimized (índices estratégicos)

**PRÓXIMOS PASSOS PLANEJADOS:**

1. **SPRINT 1**: Implementar FASE 5 (Search + ImportStudies + DuplicateGroup)
2. **SPRINT 2**: Implementar FASE 6 (DataAnalyses + ReviewResults + Reporting)
3. **SPRINT 3**: Implementar FASE 7 (QualityQuestion + Answer)
4. **SPRINT 4**: Services Layer (business logic)
5. **SPRINT 5**: Controllers REST (APIs)
6. **SPRINT 6**: Testing Suite (cobertura 80%+)

**ÚLTIMO COMMIT REALIZADO:**

- Fase 4 completa com todas as 4 entidades de extração
- SonarQube compliance aplicado
- Performance optimizations
- Business logic robusta implementada

**CONTEXTO DE TRABALHO:**
Estávamos implementando entidade por entidade seguindo arquitetura DDD. Acabamos de completar a Fase 4 (Extração) e estamos prontos para atacar a Fase 5 (Search + ImportStudies + DuplicateGroup) ou qualquer outra necessidade específica.

**COMO CONTINUAR:**
Preciso de ajuda para [DESCREVA SUA NECESSIDADE]:

- Implementar próximas entidades (Fase 5, 6 ou 7)
- Criar Services Layer para business logic
- Desenvolver Controllers REST para APIs
- Implementar testes unitários e integração
- Resolver problemas específicos de relacionamento
- Otimizar performance ou corrigir bugs
- [outro objetivo específico]
