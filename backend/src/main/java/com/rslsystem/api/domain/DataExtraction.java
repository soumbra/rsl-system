package com.rslsystem.api.domain;

import com.rslsystem.api.domain.shared.AuditableEntity;
import com.rslsystem.api.domain.shared.enums.WorkflowStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade DataExtraction - representa a configuração de extração de dados de uma RSL. Define como
 * os dados serão extraídos dos estudos selecionados.
 */
@Entity
@Table(name = "data_extractions",
    indexes = {@Index(name = "idx_extraction_review", columnList = "review_id"),
        @Index(name = "idx_extraction_status", columnList = "status"),
        @Index(name = "idx_extraction_start_date", columnList = "planned_start_date"),
        @Index(name = "idx_extraction_created", columnList = "created_at")})
@Getter
@Setter
@NoArgsConstructor
public class DataExtraction extends AuditableEntity {

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "review_id", nullable = false, unique = true)
  @NotNull(message = "Review is required")
  private Review review;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private WorkflowStatus status = WorkflowStatus.DRAFT;

  // === CONFIGURAÇÃO GERAL ===
  @Column(name = "title", length = 200, nullable = false)
  @Size(max = 200, message = "Title must be less than 200 characters")
  @NotNull(message = "Title is required")
  private String title;

  @Column(name = "description", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Description must be less than 2000 characters")
  private String description;

  @Column(name = "extraction_instructions", columnDefinition = "TEXT")
  @Size(max = 3000, message = "Extraction instructions must be less than 3000 characters")
  private String extractionInstructions;

  // === CRONOGRAMA ===
  @Column(name = "planned_start_date")
  private LocalDate plannedStartDate;

  @Column(name = "planned_end_date")
  private LocalDate plannedEndDate;

  @Column(name = "actual_start_date")
  private LocalDate actualStartDate;

  @Column(name = "actual_end_date")
  private LocalDate actualEndDate;

  @Column(name = "estimated_hours_per_study")
  @Min(value = 1, message = "Estimated hours per study must be at least 1")
  private Integer estimatedHoursPerStudy = 2;

  // === CONFIGURAÇÕES DE VALIDAÇÃO ===
  @Column(name = "requires_double_extraction", nullable = false)
  private Boolean requiresDoubleExtraction = true;

  @Column(name = "requires_consensus", nullable = false)
  private Boolean requiresConsensus = true;

  @Column(name = "blind_extraction_enabled", nullable = false)
  private Boolean blindExtractionEnabled = true;

  @Column(name = "automatic_conflict_detection", nullable = false)
  private Boolean automaticConflictDetection = true;

  @Column(name = "inter_rater_reliability_target")
  @Min(value = 50, message = "Inter-rater reliability target must be at least 50%")
  private Integer interRaterReliabilityTarget = 80;

  // === CONTROLE DE QUALIDADE ===
  @Column(name = "pilot_extraction_size")
  @Min(value = 1, message = "Pilot extraction size must be at least 1")
  private Integer pilotExtractionSize = 5; // Número de estudos para teste piloto

  @Column(name = "pilot_completed", nullable = false)
  private Boolean pilotCompleted = false;

  @Column(name = "calibration_sessions_held")
  @Min(value = 0, message = "Calibration sessions held cannot be negative")
  private Integer calibrationSessionsHeld = 0;

  @Column(name = "training_completed", nullable = false)
  private Boolean trainingCompleted = false;

  // === MÉTRICAS DE PROGRESSO ===
  @Column(name = "total_studies_for_extraction")
  @Min(value = 0, message = "Total studies for extraction cannot be negative")
  private Integer totalStudiesForExtraction = 0;

  @Column(name = "extractions_completed")
  @Min(value = 0, message = "Extractions completed cannot be negative")
  private Integer extractionsCompleted = 0;

  @Column(name = "extractions_validated")
  @Min(value = 0, message = "Extractions validated cannot be negative")
  private Integer extractionsValidated = 0;

  @Column(name = "conflicts_detected")
  @Min(value = 0, message = "Conflicts detected cannot be negative")
  private Integer conflictsDetected = 0;

  @Column(name = "conflicts_resolved")
  @Min(value = 0, message = "Conflicts resolved cannot be negative")
  private Integer conflictsResolved = 0;

  // === CONFIGURAÇÕES TÉCNICAS ===
  @Column(name = "form_version", length = 10)
  @Size(max = 10, message = "Form version must be less than 10 characters")
  private String formVersion = "1.0";

  @Column(name = "allow_partial_save", nullable = false)
  private Boolean allowPartialSave = true;

  @Column(name = "auto_save_interval") // minutos
  @Min(value = 1, message = "Auto save interval must be at least 1 minute")
  private Integer autoSaveInterval = 5;

  @Column(name = "session_timeout") // minutos
  @Min(value = 30, message = "Session timeout must be at least 30 minutes")
  private Integer sessionTimeout = 120;

  // === OBSERVAÇÕES ===
  @Column(name = "extraction_notes", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Extraction notes must be less than 2000 characters")
  private String extractionNotes;

  @Column(name = "quality_issues", columnDefinition = "TEXT")
  @Size(max = 1500, message = "Quality issues must be less than 1500 characters")
  private String qualityIssues;

  @Column(name = "team_feedback", columnDefinition = "TEXT")
  @Size(max = 1500, message = "Team feedback must be less than 1500 characters")
  private String teamFeedback;

  // === RELACIONAMENTOS ===
  @OneToMany(mappedBy = "dataExtraction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ExtractionForm> forms = new ArrayList<>();

  @OneToMany(mappedBy = "dataExtraction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ReviewerDataExtraction> reviewerExtractions = new ArrayList<>();

  // Construtor customizado
  public DataExtraction(Review review, String title) {
    this.review = review;
    this.title = title;
    this.status = WorkflowStatus.DRAFT;
    this.requiresDoubleExtraction = true;
    this.requiresConsensus = true;
    this.blindExtractionEnabled = true;
    this.automaticConflictDetection = true;
    this.interRaterReliabilityTarget = 80;
    this.estimatedHoursPerStudy = 2;
    this.pilotExtractionSize = 5;
    this.allowPartialSave = true;
    this.autoSaveInterval = 5;
    this.sessionTimeout = 120;
    this.formVersion = "1.0";
  }

  // === MÉTODOS DE NEGÓCIO ===

  // Inicia a extração
  public void startExtraction() {
    if (this.status == WorkflowStatus.DRAFT && canStart()) {
      this.status = WorkflowStatus.ACTIVE;
      this.actualStartDate = LocalDate.now();
    }
  }

  // Completa a extração
  public void completeExtraction() {
    if (this.status == WorkflowStatus.ACTIVE && canComplete()) {
      this.status = WorkflowStatus.COMPLETED;
      this.actualEndDate = LocalDate.now();
    }
  }

  // Valida extração (após revisão)
  public void validateExtraction() {
    if (this.status == WorkflowStatus.COMPLETED && canValidate()) {
      this.status = WorkflowStatus.VALIDATED;
    }
  }

  // Pausa extração
  public void pauseExtraction(String reason) {
    if (this.status == WorkflowStatus.ACTIVE) {
      this.status = WorkflowStatus.PAUSED;
      if (reason != null) {
        this.extractionNotes = (this.extractionNotes != null ? this.extractionNotes + "\n" : "")
            + "Pausado: " + reason + " (" + LocalDate.now() + ")";
      }
    }
  }

  // Resume extração
  public void resumeExtraction() {
    if (this.status == WorkflowStatus.PAUSED) {
      this.status = WorkflowStatus.ACTIVE;
      this.extractionNotes = (this.extractionNotes != null ? this.extractionNotes + "\n" : "")
          + "Retomado: " + LocalDate.now();
    }
  }

  // === VALIDAÇÕES DE NEGÓCIO ===

  public boolean canStart() {
    return hasValidForm() && hasTrainedReviewers() && pilotCompleted;
  }

  public boolean canComplete() {
    return extractionsCompleted != null && totalStudiesForExtraction != null
        && extractionsCompleted >= totalStudiesForExtraction;
  }

  public boolean canValidate() {
    return extractionsValidated != null && extractionsCompleted != null
        && extractionsValidated >= extractionsCompleted;
  }

  public boolean hasValidForm() {
    return !forms.isEmpty(); // Verificação básica - pode ser expandida
  }

  public boolean hasTrainedReviewers() {
    return trainingCompleted;
  }

  public boolean isPilotRequired() {
    return totalStudiesForExtraction != null && totalStudiesForExtraction > pilotExtractionSize;
  }

  // === CÁLCULOS E MÉTRICAS ===

  // Progresso geral da extração (0-100%)
  public Integer getExtractionProgress() {
    if (totalStudiesForExtraction == null || totalStudiesForExtraction == 0)
      return 0;
    if (extractionsCompleted == null)
      return 0;
    return Math.min(100, (extractionsCompleted * 100) / totalStudiesForExtraction);
  }

  // Progresso da validação (0-100%)
  public Integer getValidationProgress() {
    if (extractionsCompleted == null || extractionsCompleted == 0)
      return 0;
    if (extractionsValidated == null)
      return 0;
    return Math.min(100, (extractionsValidated * 100) / extractionsCompleted);
  }

  // Taxa de conflitos
  public Double getConflictRate() {
    if (extractionsCompleted == null || extractionsCompleted == 0)
      return 0.0;
    if (conflictsDetected == null)
      return 0.0;
    return (conflictsDetected * 100.0) / extractionsCompleted;
  }

  // Taxa de resolução de conflitos
  public Double getConflictResolutionRate() {
    if (conflictsDetected == null || conflictsDetected == 0)
      return 100.0;
    if (conflictsResolved == null)
      return 0.0;
    return (conflictsResolved * 100.0) / conflictsDetected;
  }

  // Estimativa de tempo restante em horas
  public Integer getEstimatedRemainingHours() {
    if (totalStudiesForExtraction == null || extractionsCompleted == null
        || estimatedHoursPerStudy == null) {
      return 0;
    }
    int remainingStudies = totalStudiesForExtraction - extractionsCompleted;
    return Math.max(0, remainingStudies * estimatedHoursPerStudy);
  }

  // Duração da extração em dias
  public Integer getExtractionDurationDays() {
    if (actualStartDate == null)
      return 0;
    LocalDate endDate = actualEndDate != null ? actualEndDate : LocalDate.now();
    return (int) java.time.temporal.ChronoUnit.DAYS.between(actualStartDate, endDate);
  }

  // === MÉTODOS DE CONSULTA ===

  public boolean isDraftStatus() {
    return status == WorkflowStatus.DRAFT;
  }

  public boolean isActiveStatus() {
    return status == WorkflowStatus.ACTIVE;
  }

  public boolean isPausedStatus() {
    return status == WorkflowStatus.PAUSED;
  }

  public boolean isCompletedStatus() {
    return status == WorkflowStatus.COMPLETED;
  }

  public boolean isValidatedStatus() {
    return status == WorkflowStatus.VALIDATED;
  }

  public boolean isExtractionActive() {
    return status == WorkflowStatus.ACTIVE;
  }

  public boolean requiresDoubleValidation() {
    return requiresDoubleExtraction && requiresConsensus;
  }

  // === MÉTODOS DE CONVENIÊNCIA ===

  public String getReviewTitle() {
    return review != null ? review.getTitle() : "Unknown";
  }

  public Integer getTotalForms() {
    return forms != null ? forms.size() : 0;
  }

  public Integer getTotalReviewerExtractions() {
    return reviewerExtractions != null ? reviewerExtractions.size() : 0;
  }

  // === MÉTODOS DE ATUALIZAÇÃO ===

  public void incrementExtractionsCompleted() {
    this.extractionsCompleted =
        (this.extractionsCompleted != null ? this.extractionsCompleted : 0) + 1;
  }

  public void incrementExtractionsValidated() {
    this.extractionsValidated =
        (this.extractionsValidated != null ? this.extractionsValidated : 0) + 1;
  }

  public void incrementConflictsDetected() {
    this.conflictsDetected = (this.conflictsDetected != null ? this.conflictsDetected : 0) + 1;
  }

  public void incrementConflictsResolved() {
    this.conflictsResolved = (this.conflictsResolved != null ? this.conflictsResolved : 0) + 1;
  }

  public void completePilotExtraction() {
    this.pilotCompleted = true;
    this.extractionNotes = (this.extractionNotes != null ? this.extractionNotes + "\n" : "")
        + "Extração piloto concluída: " + LocalDate.now();
  }

  public void completeTraining() {
    this.trainingCompleted = true;
    this.extractionNotes = (this.extractionNotes != null ? this.extractionNotes + "\n" : "")
        + "Treinamento concluído: " + LocalDate.now();
  }

  @Override
  public boolean equals(Object o) {
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return "DataExtraction{" + "id=" + getId() + ", reviewId="
        + (review != null ? review.getId() : null) + ", title='" + title + '\'' + ", status="
        + status + ", progress=" + getExtractionProgress() + "%" + ", extractionsCompleted="
        + extractionsCompleted + "/" + totalStudiesForExtraction + ", validationProgress="
        + getValidationProgress() + "%" + ", conflictRate="
        + String.format("%.1f", getConflictRate()) + "%" + ", forms=" + getTotalForms()
        + ", reviewerExtractions=" + getTotalReviewerExtractions() + ", duration="
        + getExtractionDurationDays() + " days" + ", requiresDoubleExtraction="
        + requiresDoubleExtraction + ", pilotCompleted=" + pilotCompleted + ", trainingCompleted="
        + trainingCompleted + ", isDeleted=" + getIsDeleted() + '}';
  }
}
