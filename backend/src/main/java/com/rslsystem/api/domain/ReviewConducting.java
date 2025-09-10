package com.rslsystem.api.domain;

import com.rslsystem.api.domain.shared.AuditableEntity;
import com.rslsystem.api.domain.shared.enums.WorkflowStatus;
import com.rslsystem.api.domain.shared.enums.ConductingPhase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entidade ReviewConducting - representa a segunda fase de uma RSL (Condução). Gerencia a execução
 * do protocolo: busca, importação, seleção e avaliação de estudos.
 */
@Entity
@Table(name = "review_conducting",
    indexes = {@Index(name = "idx_conducting_review", columnList = "review_id"),
        @Index(name = "idx_conducting_status", columnList = "status"),
        @Index(name = "idx_conducting_start_date", columnList = "actual_start_date"),
        @Index(name = "idx_conducting_phase", columnList = "current_phase")})
@Getter
@Setter
@NoArgsConstructor
public class ReviewConducting extends AuditableEntity {

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "review_id", nullable = false, unique = true)
  @NotNull(message = "Review is required")
  private Review review;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private WorkflowStatus status = WorkflowStatus.NOT_STARTED;

  // === CONTROLE DE FASES ===
  @Enumerated(EnumType.STRING)
  @Column(name = "current_phase", length = 30, nullable = false)
  @Size(max = 30, message = "Current phase must be less than 30 characters")
  @NotNull(message = "Current phase is required")
  private ConductingPhase currentPhase = ConductingPhase.SEARCH_PREPARATION;

  @Column(name = "phase_progress")
  @Min(value = 0, message = "Phase progress cannot be negative")
  private Integer phaseProgress = 0; // Progresso da fase atual (0-100%)

  // === CRONOGRAMA EXECUTADO ===
  @Column(name = "actual_start_date")
  private LocalDate actualStartDate;

  @Column(name = "actual_end_date")
  private LocalDate actualEndDate;

  @Column(name = "estimated_completion_date")
  private LocalDate estimatedCompletionDate;

  // === MÉTRICAS DE BUSCA ===
  @Column(name = "total_searches_planned")
  @Min(value = 0, message = "Total searches planned cannot be negative")
  private Integer totalSearchesPlanned = 0;

  @Column(name = "searches_completed")
  @Min(value = 0, message = "Searches completed cannot be negative")
  private Integer searchesCompleted = 0;

  @Column(name = "total_studies_found")
  @Min(value = 0, message = "Total studies found cannot be negative")
  private Integer totalStudiesFound = 0;

  @Column(name = "studies_after_deduplication")
  @Min(value = 0, message = "Studies after deduplication cannot be negative")
  private Integer studiesAfterDeduplication = 0;

  // === MÉTRICAS DE SELEÇÃO ===
  @Column(name = "title_abstract_screening_total")
  @Min(value = 0, message = "Title abstract screening total cannot be negative")
  private Integer titleAbstractScreeningTotal = 0;

  @Column(name = "title_abstract_screening_completed")
  @Min(value = 0, message = "Title abstract screening completed cannot be negative")
  private Integer titleAbstractScreeningCompleted = 0;

  @Column(name = "full_text_screening_total")
  @Min(value = 0, message = "Full text screening total cannot be negative")
  private Integer fullTextScreeningTotal = 0;

  @Column(name = "full_text_screening_completed")
  @Min(value = 0, message = "Full text screening completed cannot be negative")
  private Integer fullTextScreeningCompleted = 0;

  @Column(name = "studies_included_final")
  @Min(value = 0, message = "Studies included final cannot be negative")
  private Integer studiesIncludedFinal = 0;

  // === MÉTRICAS DE QUALIDADE ===
  @Column(name = "quality_assessments_total")
  @Min(value = 0, message = "Quality assessments total cannot be negative")
  private Integer qualityAssessmentsTotal = 0;

  @Column(name = "quality_assessments_completed")
  @Min(value = 0, message = "Quality assessments completed cannot be negative")
  private Integer qualityAssessmentsCompleted = 0;

  @Column(name = "studies_meeting_quality_criteria")
  @Min(value = 0, message = "Studies meeting quality criteria cannot be negative")
  private Integer studiesMeetingQualityCriteria = 0;

  // === MÉTRICAS DE EXTRAÇÃO ===
  @Column(name = "data_extractions_total")
  @Min(value = 0, message = "Data extractions total cannot be negative")
  private Integer dataExtractionsTotal = 0;

  @Column(name = "data_extractions_completed")
  @Min(value = 0, message = "Data extractions completed cannot be negative")
  private Integer dataExtractionsCompleted = 0;

  // === CONTROLE DE CONSENSO ===
  @Column(name = "conflicts_detected")
  @Min(value = 0, message = "Conflicts detected cannot be negative")
  private Integer conflictsDetected = 0;

  @Column(name = "conflicts_resolved")
  @Min(value = 0, message = "Conflicts resolved cannot be negative")
  private Integer conflictsResolved = 0;

  @Column(name = "consensus_sessions_held")
  @Min(value = 0, message = "Consensus sessions held cannot be negative")
  private Integer consensusSessionsHeld = 0;

  // === CONFIGURAÇÕES DE EXECUÇÃO ===
  @Column(name = "inter_rater_reliability_target")
  @Min(value = 50, message = "Inter-rater reliability target must be at least 50%")
  private Integer interRaterReliabilityTarget = 80;

  @Column(name = "inter_rater_reliability_achieved")
  @Min(value = 0, message = "Inter-rater reliability achieved cannot be negative")
  private Integer interRaterReliabilityAchieved;

  @Column(name = "blind_selection_enabled", nullable = false)
  private Boolean blindSelectionEnabled = true; // Seleção cega (visões isoladas)

  @Column(name = "automatic_conflict_detection", nullable = false)
  private Boolean automaticConflictDetection = true;

  // === OBSERVAÇÕES E NOTAS ===
  @Column(name = "execution_notes", columnDefinition = "TEXT")
  @Size(max = 3000, message = "Execution notes must be less than 3000 characters")
  private String executionNotes;

  @Column(name = "deviation_from_protocol", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Deviation from protocol must be less than 2000 characters")
  private String deviationFromProtocol;

  @Column(name = "lessons_learned", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Lessons learned must be less than 2000 characters")
  private String lessonsLearned;

  @Column(name = "team_feedback", columnDefinition = "TEXT")
  @Size(max = 1500, message = "Team feedback must be less than 1500 characters")
  private String teamFeedback;

  // Construtor customizado
  public ReviewConducting(Review review) {
    this.review = review;
    this.status = WorkflowStatus.NOT_STARTED;
    this.currentPhase = ConductingPhase.SEARCH_PREPARATION;
    this.phaseProgress = 0;
    this.blindSelectionEnabled = true;
    this.automaticConflictDetection = true;
    this.interRaterReliabilityTarget = 80;
  }

  // === MÉTODOS DE NEGÓCIO - CONTROLE DE FASES ===

  // Inicia a condução da RSL
  public void startConducting() {
    if (this.status == WorkflowStatus.NOT_STARTED && canStart()) {
      this.status = WorkflowStatus.IN_PROGRESS;
      this.actualStartDate = LocalDate.now();
      this.currentPhase = ConductingPhase.SEARCH_PREPARATION;
      this.phaseProgress = 0;
    }
  }

  // Avança para próxima fase
  public void advanceToNextPhase() {
    if (this.status == WorkflowStatus.IN_PROGRESS && canAdvancePhase()) {
      ConductingPhase nextPhase = currentPhase.getNext();

      if (nextPhase == ConductingPhase.COMPLETED) {
        completeConducting();
      } else {
        this.currentPhase = nextPhase;
        this.phaseProgress = 0;
      }
    }
  }

  // Pausa a condução
  public void pauseConducting(String reason) {
    if (this.status == WorkflowStatus.IN_PROGRESS) {
      this.status = WorkflowStatus.PAUSED;
      if (reason != null) {
        this.executionNotes = (this.executionNotes != null ? this.executionNotes + "\n" : "")
            + "Pausado: " + reason + " (" + LocalDate.now() + ")";
      }
    }
  }

  // Resume a condução
  public void resumeConducting() {
    if (this.status == WorkflowStatus.PAUSED) {
      this.status = WorkflowStatus.IN_PROGRESS;
      this.executionNotes = (this.executionNotes != null ? this.executionNotes + "\n" : "")
          + "Retomado: " + LocalDate.now();
    }
  }

  // Completa a condução
  public void completeConducting() {
    if ((this.status == WorkflowStatus.IN_PROGRESS || this.status == WorkflowStatus.PAUSED)
        && canComplete()) {
      this.status = WorkflowStatus.COMPLETED;
      this.actualEndDate = LocalDate.now();
      this.currentPhase = ConductingPhase.COMPLETED;
      this.phaseProgress = 100;
    }
  }

  // === VALIDAÇÕES DE NEGÓCIO ===

  // Verifica se pode iniciar a condução
  public boolean canStart() {
    return review != null && hasApprovedProtocol() && hasRequiredTeamMembers();
  }

  // Verifica se pode avançar para próxima fase
  public boolean canAdvancePhase() {
    return getCurrentPhaseProgress() >= 100;
  }

  // Verifica se pode completar a condução
  public boolean canComplete() {
    return currentPhase == ConductingPhase.DATA_EXTRACTION && dataExtractionsCompleted != null
        && dataExtractionsTotal != null && dataExtractionsCompleted >= dataExtractionsTotal;
  }

  // Verifica se tem protocolo aprovado
  public boolean hasApprovedProtocol() {
    // Implementação via service layer para verificar Protocol relacionado
    return true; // Placeholder
  }

  // Verifica se tem membros necessários na equipe
  public boolean hasRequiredTeamMembers() {
    // Implementação via service layer para verificar ReviewAuthors
    return true; // Placeholder
  }

  // === CÁLCULOS DE PROGRESSO ===

  // Calcula progresso geral da condução (0-100%)
  public Integer getOverallProgress() {
    if (status == WorkflowStatus.NOT_STARTED)
      return 0;
    if (status == WorkflowStatus.COMPLETED)
      return 100;

    // Usa o peso da fase + progresso interno
    int phaseWeight = currentPhase.getProgressWeight();
    return phaseWeight + (phaseProgress * 20 / 100);
  }

  // Calcula progresso da fase atual
  public Integer getCurrentPhaseProgress() {
    return switch (currentPhase) {
      case SEARCH_PREPARATION -> calculateSearchPreparationProgress();
      case SEARCH_EXECUTION -> calculateSearchExecutionProgress();
      case STUDY_SELECTION -> calculateStudySelectionProgress();
      case QUALITY_ASSESSMENT -> calculateQualityAssessmentProgress();
      case DATA_EXTRACTION -> calculateDataExtractionProgress();
      case COMPLETED -> 100;
    };
  }

  // Cálculos específicos por fase
  private Integer calculateSearchPreparationProgress() {
    if (totalSearchesPlanned == null || totalSearchesPlanned == 0)
      return 0;
    return Math.min(100, (searchesCompleted * 100) / totalSearchesPlanned);
  }

  private Integer calculateSearchExecutionProgress() {
    return studiesAfterDeduplication != null && studiesAfterDeduplication > 0 ? 100 : 0;
  }

  private Integer calculateStudySelectionProgress() {
    int titleProgress = titleAbstractScreeningTotal != null && titleAbstractScreeningTotal > 0
        ? (titleAbstractScreeningCompleted * 50) / titleAbstractScreeningTotal
        : 0;
    int fullTextProgress = fullTextScreeningTotal != null && fullTextScreeningTotal > 0
        ? (fullTextScreeningCompleted * 50) / fullTextScreeningTotal
        : 0;
    return Math.min(100, titleProgress + fullTextProgress);
  }

  private Integer calculateQualityAssessmentProgress() {
    if (qualityAssessmentsTotal == null || qualityAssessmentsTotal == 0)
      return 100;
    return Math.min(100, (qualityAssessmentsCompleted * 100) / qualityAssessmentsTotal);
  }

  private Integer calculateDataExtractionProgress() {
    if (dataExtractionsTotal == null || dataExtractionsTotal == 0)
      return 100;
    return Math.min(100, (dataExtractionsCompleted * 100) / dataExtractionsTotal);
  }

  // === MÉTODOS DE CONSULTA ===

  public boolean isNotStarted() {
    return status == WorkflowStatus.NOT_STARTED;
  }

  public boolean isInProgress() {
    return status == WorkflowStatus.IN_PROGRESS;
  }

  public boolean isPaused() {
    return status == WorkflowStatus.PAUSED;
  }

  public boolean isCompleted() {
    return status == WorkflowStatus.COMPLETED;
  }

  @Override
  public boolean isActive() {
    return status == WorkflowStatus.IN_PROGRESS;
  }

  // === MÉTRICAS E RELATÓRIOS ===

  // Taxa de exclusão por fase
  public Double getTitleAbstractExclusionRate() {
    if (totalStudiesFound == null || totalStudiesFound == 0 || titleAbstractScreeningTotal == null)
      return 0.0;
    int excluded = totalStudiesFound - titleAbstractScreeningTotal;
    return (excluded * 100.0) / totalStudiesFound;
  }

  public Double getFullTextExclusionRate() {
    if (titleAbstractScreeningTotal == null || titleAbstractScreeningTotal == 0
        || fullTextScreeningTotal == null)
      return 0.0;
    int excluded = titleAbstractScreeningTotal - fullTextScreeningTotal;
    return (excluded * 100.0) / titleAbstractScreeningTotal;
  }

  // Taxa de resolução de conflitos
  public Double getConflictResolutionRate() {
    if (conflictsDetected == null || conflictsDetected == 0)
      return 100.0;
    if (conflictsResolved == null)
      return 0.0;
    return (conflictsResolved * 100.0) / conflictsDetected;
  }

  // Duração da condução em dias
  public Integer getConductingDurationDays() {
    if (actualStartDate == null)
      return 0;
    LocalDate endDate = actualEndDate != null ? actualEndDate : LocalDate.now();
    return (int) java.time.temporal.ChronoUnit.DAYS.between(actualStartDate, endDate);
  }

  // === MÉTODOS DE CONVENIÊNCIA ===

  public String getReviewTitle() {
    return review != null ? review.getTitle() : "Unknown";
  }

  public String getCurrentPhaseDisplayName() {
    return currentPhase.getDisplayName();
  }

  public String getCurrentPhaseDescription() {
    return currentPhase.getDescription();
  }

  public boolean isInPhase(ConductingPhase phase) {
    return this.currentPhase == phase;
  }

  public boolean hasPassedPhase(ConductingPhase phase) {
    return this.currentPhase.isAfter(phase);
  }

  public boolean canAdvanceToPhase(ConductingPhase phase) {
    return currentPhase.canAdvanceTo(phase);
  }

  // Atualiza progresso da fase automaticamente
  @PreUpdate
  private void updatePhaseProgress() {
    this.phaseProgress = getCurrentPhaseProgress();
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
    return "ReviewConducting{" + "id=" + getId() + ", reviewId="
        + (review != null ? review.getId() : null) + ", status=" + status + ", currentPhase="
        + currentPhase + ", phaseProgress=" + phaseProgress + "%" + ", overallProgress="
        + getOverallProgress() + "%" + ", studiesFound=" + totalStudiesFound + ", studiesIncluded="
        + studiesIncludedFinal + ", conflictsDetected=" + conflictsDetected + ", conflictsResolved="
        + conflictsResolved + ", duration=" + getConductingDurationDays() + " days" + ", isDeleted="
        + getIsDeleted() + '}';
  }
}
