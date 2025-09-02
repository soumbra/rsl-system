package com.rslsystem.api.domain;

import com.rslsystem.api.domain.shared.TrackableEntity;
import com.rslsystem.api.domain.shared.enums.ConsensusDecision;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidade StudyConsensus - representa o consenso entre revisores para um estudo com avaliações
 * conflitantes. Utilizada quando diferentes revisores têm opiniões divergentes sobre
 * inclusão/exclusão.
 */
@Entity
@Table(name = "study_consensus",
    indexes = {@Index(name = "idx_consensus_study", columnList = "study_id"),
        @Index(name = "idx_consensus_review", columnList = "review_id"),
        @Index(name = "idx_consensus_decision", columnList = "final_decision"),
        @Index(name = "idx_consensus_moderator", columnList = "last_modified_by")})
@Getter
@Setter
@NoArgsConstructor
public class StudyConsensus extends TrackableEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "study_id", nullable = false)
  @NotNull(message = "Study is required")
  private Study study;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "review_id", nullable = false)
  @NotNull(message = "Review is required")
  private Review review;

  @Enumerated(EnumType.STRING)
  @Column(name = "final_decision", nullable = false, length = 20)
  @NotNull(message = "Final decision is required")
  private ConsensusDecision finalDecision;

  @Column(name = "consensus_notes", columnDefinition = "TEXT")
  @Size(max = 3000, message = "Consensus notes must be less than 3000 characters")
  private String consensusNotes;

  @Column(name = "conflict_summary", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Conflict summary must be less than 2000 characters")
  private String conflictSummary;

  @Column(name = "requires_discussion", nullable = false)
  private Boolean requiresDiscussion = false;

  @Column(name = "discussion_resolved", nullable = false)
  private Boolean discussionResolved = false;

  // Relacionamento com as avaliações que geraram o conflito
  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "consensus_id")
  private List<ReviewerStudyAssessment> conflictingAssessments = new ArrayList<>();

  // Construtor customizado
  public StudyConsensus(Study study, Review review, User moderator) {
    this.study = study;
    this.review = review;
    this.finalDecision = ConsensusDecision.NEEDS_DISCUSSION;
    this.requiresDiscussion = true;
    updateModification(moderator.getId());
  }

  // Métodos de negócio para resolução de consenso
  public void resolveAsIncluded(String consensusNotes, User moderator) {
    this.finalDecision = ConsensusDecision.INCLUDED;
    this.consensusNotes = consensusNotes;
    this.requiresDiscussion = false;
    this.discussionResolved = true;
    updateModification(moderator.getId());
  }

  public void resolveAsExcluded(String consensusNotes, User moderator) {
    this.finalDecision = ConsensusDecision.EXCLUDED;
    this.consensusNotes = consensusNotes;
    this.requiresDiscussion = false;
    this.discussionResolved = true;
    updateModification(moderator.getId());
  }

  public void markForDiscussion(String conflictSummary, User moderator) {
    this.finalDecision = ConsensusDecision.NEEDS_DISCUSSION;
    this.conflictSummary = conflictSummary;
    this.requiresDiscussion = true;
    this.discussionResolved = false;
    updateModification(moderator.getId());
  }

  public void reopenDiscussion(String reason, User moderator) {
    this.requiresDiscussion = true;
    this.discussionResolved = false;
    this.consensusNotes =
        (consensusNotes != null ? consensusNotes + "\n\n" : "") + "REABERTURA: " + reason;
    updateModification(moderator.getId());
  }

  // Métodos de consulta
  public boolean isResolved() {
    return finalDecision != ConsensusDecision.NEEDS_DISCUSSION && discussionResolved;
  }

  public boolean needsDiscussion() {
    return requiresDiscussion && !discussionResolved;
  }

  public boolean isIncluded() {
    return finalDecision == ConsensusDecision.INCLUDED;
  }

  public boolean isExcluded() {
    return finalDecision == ConsensusDecision.EXCLUDED;
  }

  public int getConflictingAssessmentsCount() {
    return conflictingAssessments != null ? conflictingAssessments.size() : 0;
  }

  // Método para adicionar avaliações conflitantes
  public void addConflictingAssessment(ReviewerStudyAssessment assessment) {
    if (conflictingAssessments == null) {
      conflictingAssessments = new ArrayList<>();
    }
    conflictingAssessments.add(assessment);
  }

  // Método para gerar resumo do conflito automaticamente
  public void generateConflictSummary() {
    if (conflictingAssessments == null || conflictingAssessments.isEmpty()) {
      return;
    }

    StringBuilder summary = new StringBuilder("Conflito entre avaliações:\n");

    for (ReviewerStudyAssessment assessment : conflictingAssessments) {
      summary.append("- ").append(assessment.getReviewerName()).append(": ")
          .append(assessment.getStatus()).append("\n");
    }

    this.conflictSummary = summary.toString();
  }

  // Métodos de conveniência
  public String getStudyTitle() {
    return study != null ? study.getTitle() : "Unknown";
  }

  public String getModeratorName() {
    // Como TrackableEntity só tem o ID, precisamos buscar o User separadamente
    // Por enquanto, vamos retornar apenas o ID
    return getLastModifiedByUserId() != null ? "User ID: " + getLastModifiedByUserId() : "Unknown";
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
    return "StudyConsensus{" + "id=" + getId() + ", studyId="
        + (study != null ? study.getId() : null) + ", reviewId="
        + (review != null ? review.getId() : null) + ", finalDecision=" + finalDecision
        + ", isResolved=" + isResolved() + ", needsDiscussion=" + needsDiscussion()
        + ", conflictingCount=" + getConflictingAssessmentsCount() + ", lastModifiedBy="
        + getLastModifiedByUserId() + ", lastModified=" + getLastModifiedAt() + '}';
  }
}
