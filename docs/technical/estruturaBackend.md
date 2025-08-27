src/main/java/com/rslsystem/api/
├── config/ # Configurações (Security, CORS, etc.)
├── controller/ # REST Controllers (@RestController)
├── service/ # Lógica de negócio (@Service)
├── repository/ # Acesso a dados (@Repository)
├── entity/ # Entidades JPA (@Entity)
├── dto/ # DTOs para transferência
├── enums/ # Enumerações
└── exception/ # Tratamento de exceções

------------------------------------------

Estrutura final

src/main/java/com/rslsystem/api/
├── 📁 domain/                          ← CORE (sem sub-camadas complexas)
│   ├── 📁 shared/
│   │   ├── BaseEntity.java             ← Base para herança
│   │   ├── AuditableEntity.java        ← Soft delete
│   │   └── 📁 enums/                   ← Enums diretos
│   │       ├── StudyStatus.java
│   │       ├── ConsensusDecision.java
│   │       └── ExtractionStatus.java
│   ├── User.java                       ← Entidades diretas no domain
│   ├── Review.java                     ← Aggregate root
│   ├── Study.java
│   ├── Protocol.java
│   ├── QualityAssessment.java
│   ├── DataExtraction.java
│   └── ... (todas as entidades do UML)
├── 📁 repository/                      ← Repositories JPA (interfaces)
│   ├── UserRepository.java
│   ├── ReviewRepository.java
│   └── StudyRepository.java
├── 📁 service/                         ← Services diretos
│   ├── ReviewService.java              ← Regras de negócio
│   ├── StudyService.java
│   └── ConsensusService.java
├── 📁 controller/                      ← REST Controllers
│   ├── ReviewController.java
│   └── StudyController.java
└── 📁 dto/                            ← DTOs para API
    ├── CreateReviewRequest.java
    └── StudyResponse.java

Estrutura de testes


http://localhost:8080/api/health
http://localhost:8080/api/info
http://localhost:8080/api/test
http://localhost:8080/api/test/cors
