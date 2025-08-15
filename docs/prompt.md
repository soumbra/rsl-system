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