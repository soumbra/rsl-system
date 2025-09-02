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
import java.util.Map;
import java.util.stream.Collectors;

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

  // Relacionamentos com consensos criados durante a seleção
  @OneToMany(mappedBy = "review", fetch = FetchType.LAZY)
  private List<StudyConsensus> studyConsensuses = new ArrayList<>();

  // Relacionamento com todas as avaliações da revisão
  @OneToMany(mappedBy = "review", fetch = FetchType.LAZY)
  private List<ReviewerStudyAssessment> allAssessments = new ArrayList<>();

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

  // Métodos para atualização de métricas
  public void updateMetrics() {
    if (allAssessments == null)
      return;

    // Agrupar avaliações por estudo
    Map<Long, List<ReviewerStudyAssessment>> assessmentsByStudy =
        allAssessments.stream().collect(Collectors.groupingBy(a -> a.getStudy().getId()));

    this.totalStudies = assessmentsByStudy.size();
    this.evaluatedStudies = 0;
    this.consensusNeeded = 0;
    this.includedStudies = 0;
    this.excludedStudies = 0;

    for (List<ReviewerStudyAssessment> studyAssessments : assessmentsByStudy.values()) {
      analyzeStudyAssessments(studyAssessments);
    }

    // Contar consensos resolvidos
    this.consensusResolved = studyConsensuses != null
        ? (int) studyConsensuses.stream().filter(StudyConsensus::isResolved).count()
        : 0;
  }

  private void analyzeStudyAssessments(List<ReviewerStudyAssessment> studyAssessments) {
    // Verificar se todos os revisores avaliaram
    long evaluatedCount =
        studyAssessments.stream().filter(ReviewerStudyAssessment::isEvaluated).count();

    if (evaluatedCount == studyAssessments.size()) {
      this.evaluatedStudies++;

      // Verificar se há consenso ou conflito
      if (hasConsensus(studyAssessments)) {
        // Há consenso - contar incluídos/excluídos
        ReviewerStudyAssessment firstEvaluation = studyAssessments.get(0);
        if (firstEvaluation.isIncluded()) {
          this.includedStudies++;
        } else if (firstEvaluation.isExcluded()) {
          this.excludedStudies++;
        }
      } else {
        // Há conflito - necessita consenso
        this.consensusNeeded++;
      }
    }
  }

  // Métodos para detecção de conflitos
  public boolean hasConsensus(List<ReviewerStudyAssessment> studyAssessments) {
    if (studyAssessments == null || studyAssessments.isEmpty()) {
      return false;
    }

    // Verificar se todos têm o mesmo status (exceto UNCERTAIN)
    return studyAssessments.stream().filter(ReviewerStudyAssessment::isEvaluated)
        .map(ReviewerStudyAssessment::getStatus).distinct().count() == 1;
  }

  public List<Study> getStudiesNeedingConsensus() {
    if (allAssessments == null)
      return new ArrayList<>();

    Map<Long, List<ReviewerStudyAssessment>> assessmentsByStudy =
        allAssessments.stream().collect(Collectors.groupingBy(a -> a.getStudy().getId()));

    return assessmentsByStudy.entrySet().stream().filter(entry -> !hasConsensus(entry.getValue()))
        .map(entry -> entry.getValue().get(0).getStudy()).toList();
  }

  public Integer getStudiesNeedingConsensusCount() {
    return getStudiesNeedingConsensus().size();
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
