package com.rslsystem.api.domain;

import com.rslsystem.api.domain.shared.AuditableEntity;
import com.rslsystem.api.domain.shared.enums.StudyStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidade ReviewerStudyAssessment - representa a avaliação individual de um revisor sobre um
 * estudo. Implementa o diferencial "visões isoladas" onde cada revisor avalia independentemente.
 */
@Entity
@Table(name = "reviewer_study_assessments",
    indexes = {@Index(name = "idx_assessment_study_reviewer", columnList = "study_id, user_id"),
        @Index(name = "idx_assessment_review", columnList = "review_id"),
        @Index(name = "idx_assessment_status", columnList = "status")})
@Getter
@Setter
@NoArgsConstructor
public class ReviewerStudyAssessment extends AuditableEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "study_id", nullable = false)
  @NotNull(message = "Study is required")
  private Study study;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  @NotNull(message = "Reviewer is required")
  private User reviewer;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "review_id", nullable = false)
  @NotNull(message = "Review is required")
  private Review review;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  @NotNull(message = "Assessment status is required")
  private StudyStatus status = StudyStatus.NOT_EVALUATED;

  @Column(name = "inclusion_criteria", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Inclusion criteria must be less than 2000 characters")
  private String inclusionCriteria;

  @Column(name = "exclusion_criteria", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Exclusion criteria must be less than 2000 characters")
  private String exclusionCriteria;

  @Column(columnDefinition = "TEXT")
  @Size(max = 1000, message = "Comments must be less than 1000 characters")
  private String comments;

  @Column(name = "reading_priority")
  private Integer readingPriority;

  // Construtor customizado
  public ReviewerStudyAssessment(Study study, User reviewer, Review review) {
    this.study = study;
    this.reviewer = reviewer;
    this.review = review;
    this.status = StudyStatus.NOT_EVALUATED;
  }

  // Métodos de negócio para mudança de status
  public void markAsIncluded(String inclusionCriteria) {
    this.status = StudyStatus.INCLUDED;
    this.inclusionCriteria = inclusionCriteria;
    this.exclusionCriteria = null; // Limpa critério de exclusão
  }

  public void markAsExcluded(String exclusionCriteria) {
    this.status = StudyStatus.EXCLUDED;
    this.exclusionCriteria = exclusionCriteria;
    this.inclusionCriteria = null; // Limpa critério de inclusão
  }

  public void markAsUncertain(String comments) {
    this.status = StudyStatus.UNCERTAIN;
    this.comments = comments;
  }

  public void resetEvaluation() {
    this.status = StudyStatus.NOT_EVALUATED;
    this.inclusionCriteria = null;
    this.exclusionCriteria = null;
    this.comments = null;
    this.readingPriority = null;
  }

  // Métodos de consulta
  public boolean isEvaluated() {
    return status != StudyStatus.NOT_EVALUATED;
  }

  public boolean isIncluded() {
    return status == StudyStatus.INCLUDED;
  }

  public boolean isExcluded() {
    return status == StudyStatus.EXCLUDED;
  }

  public boolean isUncertain() {
    return status == StudyStatus.UNCERTAIN;
  }

  public boolean needsConsensus() {
    return status == StudyStatus.UNCERTAIN;
  }

  public boolean hasJustification() {
    return (inclusionCriteria != null && !inclusionCriteria.trim().isEmpty())
        || (exclusionCriteria != null && !exclusionCriteria.trim().isEmpty())
        || (comments != null && !comments.trim().isEmpty());
  }

  // Métodos de conveniência para relacionamentos bidirecionais
  public void setStudy(Study study) {
    this.study = study;
    if (study != null) {
      study.addReviewerEvaluation(this);
    }
  }

  public String getReviewerName() {
    return reviewer != null ? reviewer.getFullName() : "Unknown";
  }

  public String getStudyTitle() {
    return study != null ? study.getTitle() : "Unknown";
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
    return "ReviewerStudyAssessment{" + "id=" + getId() + ", reviewerId="
        + (reviewer != null ? reviewer.getId() : null) + ", studyId="
        + (study != null ? study.getId() : null) + ", reviewId="
        + (review != null ? review.getId() : null) + ", status=" + status + ", hasJustification="
        + hasJustification() + ", isDeleted=" + getIsDeleted() + '}';
  }
}
