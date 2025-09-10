package com.rslsystem.api.domain;

import com.rslsystem.api.domain.shared.AuditableEntity;
import com.rslsystem.api.domain.shared.enums.WorkflowStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade ReviewPlanning - representa a primeira fase de uma RSL (Planejamento). Gerencia questões
 * de pesquisa, estratégia de busca, critérios de seleção e cronograma.
 */
@Entity
@Table(name = "review_planning",
    indexes = {@Index(name = "idx_planning_review", columnList = "review_id"),
        @Index(name = "idx_planning_status", columnList = "status"),
        @Index(name = "idx_planning_start_date", columnList = "planned_start_date")})
@Getter
@Setter
@NoArgsConstructor
public class ReviewPlanning extends AuditableEntity {

  private static final String COMMA_SEPARATOR_PATTERN = ",\\s*";


  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "review_id", nullable = false, unique = true)
  @NotNull(message = "Review is required")
  private Review review;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  @NotNull(message = "Status is required")
  private WorkflowStatus status = WorkflowStatus.DRAFT;

  // === QUESTÕES DE PESQUISA ===
  @Column(name = "research_questions", columnDefinition = "TEXT")
  @Size(max = 3000, message = "Research questions must be less than 3000 characters")
  private String researchQuestions;

  @Column(name = "research_objectives", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Research objectives must be less than 2000 characters")
  private String researchObjectives;

  @Column(name = "research_scope", columnDefinition = "TEXT")
  @Size(max = 1500, message = "Research scope must be less than 1500 characters")
  private String researchScope;

  // === ESTRATÉGIA DE BUSCA ===
  @Column(name = "primary_keywords", columnDefinition = "TEXT")
  @Size(max = 1000, message = "Primary keywords must be less than 1000 characters")
  private String primaryKeywords;

  @Column(name = "secondary_keywords", columnDefinition = "TEXT")
  @Size(max = 1000, message = "Secondary keywords must be less than 1000 characters")
  private String secondaryKeywords;

  @Column(name = "search_string", columnDefinition = "TEXT")
  @Size(max = 3000, message = "Search string must be less than 3000 characters")
  private String searchString;

  @Column(name = "selected_databases", columnDefinition = "TEXT")
  @Size(max = 1000, message = "Selected databases must be less than 1000 characters")
  private String selectedDatabases;

  // === CRITÉRIOS DE SELEÇÃO ===
  @Column(name = "inclusion_criteria", columnDefinition = "TEXT")
  @Size(max = 2500, message = "Inclusion criteria must be less than 2500 characters")
  private String inclusionCriteria;

  @Column(name = "exclusion_criteria", columnDefinition = "TEXT")
  @Size(max = 2500, message = "Exclusion criteria must be less than 2500 characters")
  private String exclusionCriteria;

  @Column(name = "language_restrictions", length = 500)
  @Size(max = 500, message = "Language restrictions must be less than 500 characters")
  private String languageRestrictions;

  @Column(name = "time_period_start")
  private Integer timePeriodStart;

  @Column(name = "time_period_end")
  private Integer timePeriodEnd;

  // === CRONOGRAMA E ORGANIZAÇÃO ===
  @Column(name = "planned_start_date")
  private LocalDate plannedStartDate;

  @Column(name = "planned_end_date")
  private LocalDate plannedEndDate;

  @Column(name = "estimated_studies")
  private Integer estimatedStudies;

  @Column(name = "team_members", columnDefinition = "TEXT")
  @Size(max = 1000, message = "Team members must be less than 1000 characters")
  private String teamMembers;

  // === OBSERVAÇÕES E METODOLOGIA ===
  @Column(name = "methodology_notes", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Methodology notes must be less than 2000 characters")
  private String methodologyNotes;

  @Column(name = "planning_notes", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Planning notes must be less than 2000 characters")
  private String planningNotes;

  // Construtor customizado
  public ReviewPlanning(Review review) {
    this.review = review;
    this.status = WorkflowStatus.DRAFT;
  }

  // === MÉTODOS DE NEGÓCIO ===

  // Controle do status do planejamento
  public void startPlanning() {
    if (this.status == WorkflowStatus.DRAFT) {
      this.status = WorkflowStatus.IN_PROGRESS;
    }
  }

  public void completePlanning() {
    if (this.status == WorkflowStatus.IN_PROGRESS && canComplete()) {
      this.status = WorkflowStatus.COMPLETED;
    }
  }

  public void approvePlanning() {
    if (this.status == WorkflowStatus.COMPLETED) {
      this.status = WorkflowStatus.APPROVED;
    }
  }

  public void rejectPlanning(String reason) {
    if (this.status == WorkflowStatus.COMPLETED) {
      this.status = WorkflowStatus.REJECTED;
      this.planningNotes =
          (this.planningNotes != null ? this.planningNotes + "\n" : "") + "Rejeitado: " + reason;
    }
  }

  public void resetToInProgress() {
    if (this.status == WorkflowStatus.REJECTED) {
      this.status = WorkflowStatus.IN_PROGRESS;
    }
  }

  // === VALIDAÇÕES DE NEGÓCIO ===

  // Verifica se o planejamento pode ser considerado completo
  public boolean canComplete() {
    return hasResearchQuestions() && hasSearchStrategy() && hasSelectionCriteria()
        && hasTimeframe();
  }

  public boolean hasResearchQuestions() {
    return researchQuestions != null && !researchQuestions.trim().isEmpty();
  }

  public boolean hasSearchStrategy() {
    return (primaryKeywords != null && !primaryKeywords.trim().isEmpty())
        && (searchString != null && !searchString.trim().isEmpty())
        && (selectedDatabases != null && !selectedDatabases.trim().isEmpty());
  }

  public boolean hasSelectionCriteria() {
    return (inclusionCriteria != null && !inclusionCriteria.trim().isEmpty())
        && (exclusionCriteria != null && !exclusionCriteria.trim().isEmpty());
  }

  public boolean hasTimeframe() {
    return plannedStartDate != null && plannedEndDate != null;
  }

  // === MÉTODOS DE CONSULTA ===

  public boolean isDraftStatus() {
    return status == WorkflowStatus.DRAFT;
  }

  public boolean isInProgressStatus() {
    return status == WorkflowStatus.IN_PROGRESS;
  }

  public boolean isCompletedStatus() {
    return status == WorkflowStatus.COMPLETED;
  }

  public boolean isApprovedStatus() {
    return status == WorkflowStatus.APPROVED;
  }

  public boolean isRejected() {
    return status == WorkflowStatus.REJECTED;
  }

  public boolean canStartConducting() {
    return status == WorkflowStatus.APPROVED;
  }

  // Calcula progresso do planejamento (0-100%)
  public Integer getCompletionPercentage() {
    int completed = 0;
    int total = 4;

    if (hasResearchQuestions())
      completed++;
    if (hasSearchStrategy())
      completed++;
    if (hasSelectionCriteria())
      completed++;
    if (hasTimeframe())
      completed++;

    return (completed * 100) / total;
  }

  // === MÉTODOS DE CONVENIÊNCIA ===

  public String getReviewTitle() {
    return review != null ? review.getTitle() : "Unknown";
  }

  public List<String> getKeywordsList() {
    List<String> keywords = new ArrayList<>();
    if (primaryKeywords != null && !primaryKeywords.trim().isEmpty()) {
      keywords.addAll(List.of(primaryKeywords.split(COMMA_SEPARATOR_PATTERN)));
    }
    if (secondaryKeywords != null && !secondaryKeywords.trim().isEmpty()) {
      keywords.addAll(List.of(secondaryKeywords.split(COMMA_SEPARATOR_PATTERN)));
    }
    return keywords;
  }

  public List<String> getDatabasesList() {
    if (selectedDatabases == null || selectedDatabases.trim().isEmpty()) {
      return new ArrayList<>();
    }
    return List.of(selectedDatabases.split(COMMA_SEPARATOR_PATTERN));
  }

  // Formata período de tempo para exibição
  public String getTimesPeriodFormatted() {
    if (timePeriodStart != null && timePeriodEnd != null) {
      return timePeriodStart + " - " + timePeriodEnd;
    }
    if (timePeriodStart != null) {
      return "A partir de " + timePeriodStart;
    }
    if (timePeriodEnd != null) {
      return "Até " + timePeriodEnd;
    }
    return "Não especificado";
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
    return "ReviewPlanning{" + "id=" + getId() + ", reviewId="
        + (review != null ? review.getId() : null) + ", status=" + status + ", completion="
        + getCompletionPercentage() + "%" + ", hasQuestions=" + hasResearchQuestions()
        + ", hasStrategy=" + hasSearchStrategy() + ", hasCriteria=" + hasSelectionCriteria()
        + ", hasTimeframe=" + hasTimeframe() + ", canComplete=" + canComplete() + ", isDeleted="
        + getIsDeleted() + '}';
  }
}
