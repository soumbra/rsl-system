package com.rslsystem.api.domain;

import com.rslsystem.api.domain.shared.AuditableEntity;
import com.rslsystem.api.domain.shared.enums.StudySelectionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidade StudySelection - orquestra o processo de seleção de estudos com "visões isoladas".
 * Gerencia avaliações independentes dos revisores e detecção automática de conflitos.
 */
@Entity
@Table(name = "study_selections",
    indexes = {@Index(name = "idx_selection_review", columnList = "review_id"),
        @Index(name = "idx_selection_status", columnList = "status"),
        @Index(name = "idx_selection_phase", columnList = "selection_phase")})
@Getter
@Setter
@NoArgsConstructor
public class StudySelection extends AuditableEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "review_id", nullable = false)
  @NotNull(message = "Review is required")
  private Review review;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  @NotNull(message = "Selection status is required")
  private StudySelectionStatus status = StudySelectionStatus.NOT_STARTED;

  @Column(name = "selection_phase", length = 50)
  @Size(max = 50, message = "Selection phase must be less than 50 characters")
  private String selectionPhase = "TITLE_ABSTRACT";

  @Column(name = "total_studies")
  private Integer totalStudies = 0;

  @Column(name = "evaluated_studies")
  private Integer evaluatedStudies = 0;

  @Column(name = "consensus_needed")
  private Integer consensusNeeded = 0;

  @Column(name = "consensus_resolved")
  private Integer consensusResolved = 0;

  @Column(name = "included_studies")
  private Integer includedStudies = 0;

  @Column(name = "excluded_studies")
  private Integer excludedStudies = 0;

  @Column(columnDefinition = "TEXT")
  @Size(max = 2000, message = "Selection notes must be less than 2000 characters")
  private String selectionNotes;

  // Construtor customizado
  public StudySelection(Review review) {
    this.review = review;
    this.status = StudySelectionStatus.NOT_STARTED;
    this.selectionPhase = "TITLE_ABSTRACT";
  }

  // Métodos de controle do processo
  public void startSelection() {
    if (this.status == StudySelectionStatus.NOT_STARTED) {
      this.status = StudySelectionStatus.IN_PROGRESS;
      updateMetrics();
    }
  }

  public void completeSelection() {
    if (this.status == StudySelectionStatus.IN_PROGRESS
        && consensusNeeded.equals(consensusResolved)) {
      this.status = StudySelectionStatus.COMPLETED;
    }
  }

  public void resetSelection() {
    this.status = StudySelectionStatus.NOT_STARTED;
    this.evaluatedStudies = 0;
    this.consensusNeeded = 0;
    this.consensusResolved = 0;
    this.includedStudies = 0;
    this.excludedStudies = 0;
  }

  // Métodos para atualização de métricas (implementação via service layer)
  public void updateMetrics() {
    // TODO: Implementar via StudySelectionService quando necessário
    // Por enquanto, mantém valores atuais para não quebrar
  }

  public void updateMetrics(Integer totalStudies, Integer evaluatedStudies, Integer consensusNeeded,
      Integer consensusResolved, Integer includedStudies, Integer excludedStudies) {
    this.totalStudies = totalStudies;
    this.evaluatedStudies = evaluatedStudies;
    this.consensusNeeded = consensusNeeded;
    this.consensusResolved = consensusResolved;
    this.includedStudies = includedStudies;
    this.excludedStudies = excludedStudies;
  }

  // Métodos de consulta e métricas
  public boolean isCompleted() {
    return status == StudySelectionStatus.COMPLETED;
  }

  public boolean isInProgress() {
    return status == StudySelectionStatus.IN_PROGRESS;
  }

  public boolean isNotStarted() {
    return status == StudySelectionStatus.NOT_STARTED;
  }

  public boolean hasPendingConsensus() {
    return consensusNeeded > consensusResolved;
  }

  public Integer getProgressPercentage() {
    if (totalStudies == null || totalStudies == 0)
      return 0;
    return (evaluatedStudies * 100) / totalStudies;
  }

  public Integer getConsensusProgressPercentage() {
    if (consensusNeeded == null || consensusNeeded == 0)
      return 100;
    return (consensusResolved * 100) / consensusNeeded;
  }

  // Métodos de conveniência
  public String getReviewTitle() {
    return review != null ? review.getTitle() : "Unknown";
  }

  public boolean canComplete() {
    return isInProgress() && evaluatedStudies.equals(totalStudies)
        && consensusNeeded.equals(consensusResolved);
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
    return "StudySelection{" + "id=" + getId() + ", reviewId="
        + (review != null ? review.getId() : null) + ", status=" + status + ", phase='"
        + selectionPhase + '\'' + ", progress=" + getProgressPercentage() + "%" + ", totalStudies="
        + totalStudies + ", evaluatedStudies=" + evaluatedStudies + ", consensusNeeded="
        + consensusNeeded + ", consensusResolved=" + consensusResolved + ", includedStudies="
        + includedStudies + ", excludedStudies=" + excludedStudies + ", canComplete="
        + canComplete() + ", isDeleted=" + getIsDeleted() + '}';
  }
}
