```mermaid
classDiagram
    class User {
        -Long id
        -String username
        -String email
        -String password
        -String name
        -String lastname
        -String image
        -String institution
        -String academicTitle
        -Boolean isActive
        -LocalDateTime createdAt
        -LocalDateTime updatedAt
        +getFullName() String
    }

    class Review {
        -Long id
        -String title
        -String description
        -User[] authors
        -User owner
        -ReviewPlanning planning
        -ReviewConducting conducting
        -ReviewReporting reporting
        -LocalDateTime createdAt
        -LocalDateTime updatedAt
    }

    class ReviewPlanning {
        -Long id
        -Protocol protocol
        -QualityAssessment qualityAssessment
        -ExtractionForm extractionForm
    }

    class ReviewConducting {
        -Long id
    }

    class ReviewReporting {
        -Long id
    }

    class Protocol {
        -Long id
        -String objectives
        -String population
        -String intervention
        -String comparison
        -String outcome
        -String context
        -String[] researchQuestions
        -Keyword[] keywords
        -String baseSearchString
        -Source[] sources
        -String[] inclusionCriteria
        -String[] exclusionCriteria
    }

    class QualityAssessment {
        -Long id
        -String[] questions
        -Answer[] answers
        -Float maxScore
        -Float cutoffScore
    }

    class Answer {
        -Long id
        -String description
        -Float weight
    }

    class ExtractionForm {
        -Long id
        -ExtractionField[] fields
    }

    class ExtractionField {
        -Long id
        -String description
        -String type
        -String values
    }

    class Keyword {
        -Long id
        -String term
        -String[] synonyms
    }

    class Source {
        -Long id
        -String name
        -String url
        -String searchString
    }

    User "1" -- "0..*" Review : owns
    User "0..*" -- "0..*" Review : participates
    Review "1" -- "1" ReviewPlanning : has
    Review "1" -- "1" ReviewConducting : has
    Review "1" -- "1" ReviewReporting : has
    ReviewPlanning "1" -- "1" Protocol : has
    ReviewPlanning "1" -- "1" QualityAssessment : has
    ReviewPlanning "1" -- "1" ExtractionForm : has
    QualityAssessment "1" -- "0..*" Answer : contains
    ExtractionForm "1" -- "0..*" ExtractionField : contains
    Protocol "1" -- "0..*" Keyword : contains
    Protocol "1" -- "0..*" Source : contains
```
