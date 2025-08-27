Estou desenvolvendo um sistema de revisões sistemáticas da literatura (RSL System) para competir com o Parsifal.

Stack:
- Backend: Spring Boot 3 + Java 21 + PostgreSQL + Lombok
- Frontend: Vue 3 + TypeScript + Vuetify + Pinia
- Infra: Docker Compose

Diferencial: Cada revisor tem visões isoladas durante revisão por pares para evitar viés (problema do Parsifal).

Principais entidades:
- User (ADMIN, RESEARCHER, REVIEWER)
- Review (systematic review project)
- Article (papers being reviewed)
- ReviewEvaluation (individual decisions per reviewer)

Fases da revisão:
1. Individual screening (blind)
2. Consensus phase
3. Finalized

Ajude-me a implementar [descreva o que precisa: controller, component, entity, etc.]

-------------------------------------------------
Estou desenvolvendo o RSL System - sistema de revisões sistemáticas da literatura para competir com Parsifal.

CONTEXTO ATUAL:
- Stack: Spring Boot 3 + Java 21 + PostgreSQL + Lombok + Vue 3 + TypeScript + Vuetify
- Branch atual: 2-configurar-spring-security-básico-para-ambiente-dev
- Objetivo: Configurar backend core completo (SEM criar entidades ainda)

ESTRUTURA DE PASTAS:
src/main/java/com/rslsystem/api/
├── config/         # Configurações
├── controller/     # REST Controllers
├── service/        # Lógica de negócio
├── repository/     # Acesso a dados
├── entity/         # Entidades JPA (criar depois)
├── dto/           # DTOs (criar depois)
├── enums/         # Enumerações
└── exception/     # Tratamento de exceções

DIFERENCIAL DO SISTEMA:
Cada revisor tem visões ISOLADAS durante revisão por pares (problema do Parsifal = viés quando revisores veem decisões uns dos outros).

PRÓXIMOS PASSOS (backend core):
1. SecurityConfig completo (CORS + endpoints liberados, SEM JWT ainda)
2. GlobalExceptionHandler
3. ResponseWrapper para padronizar JSON
4. Controllers básicos (Health, Test, Info)
5. Profiles de ambiente (dev, prod)
6. Configurações completas para só implementar features depois

IMPORTANTE:
- Usar arquitetura em camadas (Controller → Service → Repository → Entity)
- Lombok para reduzir boilerplate
- Só configurações, SEM entidades User/Review ainda
- Preparar para JWT no futuro
- CORS configurado para Vue.js frontend

Voce vai me ajudar a implementar, por enquanto so absorva isso.