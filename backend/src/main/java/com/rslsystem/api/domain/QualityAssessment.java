package com.rslsystem.api.domain;

import com.rslsystem.api.domain.shared.AuditableEntity;
import com.rslsystem.api.domain.shared.enums.QualityAssessmentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidade QualityAssessment - representa a configuração de critérios de avaliação de qualidade
 * para uma RSL. Define frameworks, questões e métodos de pontuação.
 */
@Entity
@Table(name = "quality_assessments",
    indexes = {@Index(name = "idx_quality_review", columnList = "review_id"),
        @Index(name = "idx_quality_status", columnList = "status"),
        @Index(name = "idx_quality_framework", columnList = "framework"),
        @Index(name = "idx_quality_mandatory", columnList = "is_mandatory")})
@Getter
@Setter
@NoArgsConstructor
public class QualityAssessment extends AuditableEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "review_id", nullable = false)
  @NotNull(message = "Review is required")
  private Review review;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  @NotNull(message = "Quality assessment status is required")
  private QualityAssessmentStatus status = QualityAssessmentStatus.DRAFT;

  // === CONFIGURAÇÃO DO FRAMEWORK ===
  @Column(nullable = false, length = 50)
  @Size(min = 3, max = 50, message = "Framework name must be between 3 and 50 characters")
  @NotNull(message = "Framework is required")
  private String framework; // Ex: "Cochrane", "CASP", "JBI", "Custom"

  @Column(name = "framework_description", columnDefinition = "TEXT")
  @Size(max = 1000, message = "Framework description must be less than 1000 characters")
  private String frameworkDescription;

  @Column(name = "framework_reference", length = 500)
  @Size(max = 500, message = "Framework reference must be less than 500 characters")
  private String frameworkReference; // URL ou citação do framework

  // === QUESTÕES DE QUALIDADE ===
  @Column(name = "quality_questions", columnDefinition = "TEXT", nullable = false)
  @Size(min = 10, max = 5000, message = "Quality questions must be between 10 and 5000 characters")
  @NotNull(message = "Quality questions are required")
  private String qualityQuestions; // Uma por linha

  @Column(name = "questions_count")
  @Min(value = 1, message = "Must have at least 1 quality question")
  @Max(value = 50, message = "Cannot have more than 50 quality questions")
  private Integer questionsCount = 0;

  // === MÉTODO DE PONTUAÇÃO ===
  @Column(name = "scoring_method", length = 50, nullable = false)
  @Size(max = 50, message = "Scoring method must be less than 50 characters")
  @NotNull(message = "Scoring method is required")
  private String scoringMethod; // "YES_NO", "LIKERT_3", "LIKERT_5", "CUSTOM"

  @Column(name = "scoring_description", columnDefinition = "TEXT")
  @Size(max = 1500, message = "Scoring description must be less than 1500 characters")
  private String scoringDescription;

  @Column(name = "max_score")
  @Min(value = 1, message = "Maximum score must be at least 1")
  @Max(value = 100, message = "Maximum score cannot exceed 100")
  private Integer maxScore = 10;

  @Column(name = "min_score")
  @Min(value = 0, message = "Minimum score cannot be negative")
  private Integer minScore = 0;

  // === CRITÉRIOS DE INCLUSÃO/EXCLUSÃO ===
  @Column(name = "quality_threshold")
  private Double qualityThreshold; // Score mínimo para inclusão

  @Column(name = "threshold_description", columnDefinition = "TEXT")
  @Size(max = 1000, message = "Threshold description must be less than 1000 characters")
  private String thresholdDescription;

  @Column(name = "is_mandatory", nullable = false)
  @NotNull(message = "Mandatory flag is required")
  private Boolean isMandatory = true; // Se avaliação é obrigatória

  @Column(name = "exclusion_criteria", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Exclusion criteria must be less than 2000 characters")
  private String exclusionCriteria;

  // === CONFIGURAÇÃO AVANÇADA ===
  @Column(name = "weight_percentage")
  @Min(value = 1, message = "Weight percentage must be at least 1")
  @Max(value = 100, message = "Weight percentage cannot exceed 100")
  private Integer weightPercentage = 100; // Peso na decisão final

  @Column(name = "inter_rater_required", nullable = false)
  private Boolean interRaterRequired = false; // Se requer múltiplos avaliadores

  @Column(name = "consensus_threshold")
  @Min(value = 50, message = "Consensus threshold must be at least 50%")
  @Max(value = 100, message = "Consensus threshold cannot exceed 100%")
  private Integer consensusThreshold = 80; // % de acordo entre revisores

  // === INSTRUÇÕES E TREINAMENTO ===
  @Column(name = "assessment_instructions", columnDefinition = "TEXT")
  @Size(max = 3000, message = "Assessment instructions must be less than 3000 characters")
  private String assessmentInstructions;

  @Column(name = "training_materials", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Training materials must be less than 2000 characters")
  private String trainingMaterials;

  @Column(name = "pilot_assessment", columnDefinition = "TEXT")
  @Size(max = 1500, message = "Pilot assessment must be less than 1500 characters")
  private String pilotAssessment;

  // === OBSERVAÇÕES ===
  @Column(name = "quality_notes", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Quality notes must be less than 2000 characters")
  private String qualityNotes;

  @Column(name = "reviewer_comments", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Reviewer comments must be less than 2000 characters")
  private String reviewerComments;

  // Construtor customizado
  public QualityAssessment(Review review, String framework) {
    this.review = review;
    this.framework = framework;
    this.status = QualityAssessmentStatus.DRAFT;
    this.scoringMethod = "YES_NO";
    this.isMandatory = true;
    this.interRaterRequired = false;
    this.weightPercentage = 100;
    this.consensusThreshold = 80;
  }

  // === MÉTODOS DE NEGÓCIO ===

  // Controle do status da avaliação de qualidade
  public void activateAssessment() {
    if (this.status == QualityAssessmentStatus.DRAFT && canActivate()) {
      this.status = QualityAssessmentStatus.ACTIVE;
    }
  }

  public void pauseAssessment() {
    if (this.status == QualityAssessmentStatus.ACTIVE) {
      this.status = QualityAssessmentStatus.PAUSED;
    }
  }

  public void resumeAssessment() {
    if (this.status == QualityAssessmentStatus.PAUSED) {
      this.status = QualityAssessmentStatus.ACTIVE;
    }
  }

  public void completeAssessment() {
    if (this.status == QualityAssessmentStatus.ACTIVE
        || this.status == QualityAssessmentStatus.PAUSED) {
      this.status = QualityAssessmentStatus.COMPLETED;
    }
  }

  public void archiveAssessment() {
    if (this.status == QualityAssessmentStatus.COMPLETED) {
      this.status = QualityAssessmentStatus.ARCHIVED;
    }
  }

  // === VALIDAÇÕES DE NEGÓCIO ===

  // Verifica se a avaliação pode ser ativada
  public boolean canActivate() {
    return hasValidQuestions() && hasValidScoringMethod() && hasValidThreshold();
  }

  public boolean hasValidQuestions() {
    return qualityQuestions != null && !qualityQuestions.trim().isEmpty() && questionsCount != null
        && questionsCount > 0;
  }

  public boolean hasValidScoringMethod() {
    return scoringMethod != null && !scoringMethod.trim().isEmpty() && maxScore != null
        && minScore != null && maxScore > minScore;
  }

  public boolean hasValidThreshold() {
    if (!isMandatory)
      return true; // Se não é obrigatória, não precisa threshold

    return qualityThreshold != null && qualityThreshold >= minScore && qualityThreshold <= maxScore;
  }

  // === MÉTODOS DE CONSULTA DE STATUS ===

  public boolean isDraftStatus() {
    return status == QualityAssessmentStatus.DRAFT;
  }

  public boolean isActiveStatus() {
    return status == QualityAssessmentStatus.ACTIVE;
  }

  public boolean isPausedStatus() {
    return status == QualityAssessmentStatus.PAUSED;
  }

  public boolean isCompletedStatus() {
    return status == QualityAssessmentStatus.COMPLETED;
  }

  public boolean isArchivedStatus() {
    return status == QualityAssessmentStatus.ARCHIVED;
  }

  // === MÉTODOS DE CAPACIDADE ===

  public boolean isEditable() {
    return status == QualityAssessmentStatus.DRAFT || status == QualityAssessmentStatus.PAUSED;
  }

  public boolean canBeUsed() {
    return status == QualityAssessmentStatus.ACTIVE;
  }

  public boolean isFinished() {
    return status == QualityAssessmentStatus.COMPLETED
        || status == QualityAssessmentStatus.ARCHIVED;
  }

  // === CÁLCULOS E MÉTRICS ===

  // Calcula completude da configuração (0-100%)
  public Integer getConfigurationCompleteness() {
    int completed = 0;
    int total = 6;

    if (hasValidQuestions())
      completed++;
    if (hasValidScoringMethod())
      completed++;
    if (hasValidThreshold())
      completed++;
    if (assessmentInstructions != null && !assessmentInstructions.trim().isEmpty())
      completed++;
    if (frameworkDescription != null && !frameworkDescription.trim().isEmpty())
      completed++;
    if (exclusionCriteria != null && !exclusionCriteria.trim().isEmpty())
      completed++;

    return (completed * 100) / total;
  }

  // Calcula o range de scores possível
  public String getScoreRange() {
    return minScore + " - " + maxScore + " pontos";
  }

  // Verifica se um score passa no threshold
  public boolean passesThreshold(Double score) {
    if (!isMandatory || qualityThreshold == null)
      return true;
    return score != null && score >= qualityThreshold;
  }

  // === MÉTODOS DE CONVENIÊNCIA ===

  public String getReviewTitle() {
    return review != null ? review.getTitle() : "Unknown";
  }

  // Atualiza contador de questões automaticamente
  @PrePersist
  @PreUpdate
  private void updateQuestionsCount() {
    if (qualityQuestions != null && !qualityQuestions.trim().isEmpty()) {
      this.questionsCount = qualityQuestions.split("\n").length;
    } else {
      this.questionsCount = 0;
    }
  }

  // Lista de questões como lista
  public List<String> getQualityQuestionsList() {
    if (qualityQuestions == null || qualityQuestions.trim().isEmpty()) {
      return new ArrayList<>();
    }
    return List.of(qualityQuestions.split("\n"));
  }

  // Formatar threshold como porcentagem se aplicável
  public String getFormattedThreshold() {
    if (qualityThreshold == null)
      return "N/A";

    if (scoringMethod.equals("YES_NO") && maxScore == 100) {
      return qualityThreshold.intValue() + "%";
    }

    return qualityThreshold + " pontos";
  }

  // Tipo de framework (built-in vs custom)
  public boolean isCustomFramework() {
    return "CUSTOM".equalsIgnoreCase(framework);
  }

  public boolean isStandardFramework() {
    return !isCustomFramework();
  }

  @Override
  public boolean equals(Object o) {
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  // toString seguro (evita recursão infinita)
  @Override
  public String toString() {
    return "QualityAssessment{" + "id=" + getId() + ", reviewId="
        + (review != null ? review.getId() : null) + ", framework='" + framework + '\''
        + ", status=" + status + ", questionsCount=" + questionsCount + ", scoringMethod='"
        + scoringMethod + '\'' + ", scoreRange='" + getScoreRange() + '\'' + ", threshold="
        + qualityThreshold + ", isMandatory=" + isMandatory + ", interRaterRequired="
        + interRaterRequired + ", weight=" + weightPercentage + "%" + ", completeness="
        + getConfigurationCompleteness() + "%" + ", canActivate=" + canActivate() + ", isEditable="
        + isEditable() + ", isDeleted=" + getIsDeleted() + '}';
  }
}
