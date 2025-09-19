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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade ExtractionForm - representa um formulário dinâmico para extração de dados. Define a
 * estrutura, layout e configurações de um formulário de extração específico.
 */
@Entity
@Table(name = "extraction_forms",
    indexes = {@Index(name = "idx_form_data_extraction", columnList = "data_extraction_id"),
        @Index(name = "idx_form_status", columnList = "status"),
        @Index(name = "idx_form_version", columnList = "version"),
        @Index(name = "idx_form_active", columnList = "is_active"),
        @Index(name = "idx_form_created", columnList = "created_at")})
@Getter
@Setter
@NoArgsConstructor
public class ExtractionForm extends AuditableEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "data_extraction_id", nullable = false)
  @NotNull(message = "Data extraction is required")
  private DataExtraction dataExtraction;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private WorkflowStatus status = WorkflowStatus.DRAFT;

  // === CONFIGURAÇÃO BÁSICA ===
  @Column(name = "title", length = 200, nullable = false)
  @Size(max = 200, message = "Title must be less than 200 characters")
  @NotNull(message = "Title is required")
  private String title;

  @Column(name = "description", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Description must be less than 2000 characters")
  private String description;

  @Column(name = "version", length = 20, nullable = false)
  @Size(max = 20, message = "Version must be less than 20 characters")
  @NotNull(message = "Version is required")
  private String version = "1.0";

  @Column(name = "is_active", nullable = false)
  private Boolean isActive = false;

  @Column(name = "is_template", nullable = false)
  private Boolean isTemplate = false;

  // === CONFIGURAÇÕES DE LAYOUT ===
  @Column(name = "layout_type", length = 30)
  @Size(max = 30, message = "Layout type must be less than 30 characters")
  private String layoutType = "VERTICAL"; // VERTICAL, HORIZONTAL, TABBED, WIZARD

  @Column(name = "columns_per_row")
  @Min(value = 1, message = "Columns per row must be at least 1")
  private Integer columnsPerRow = 1;

  @Column(name = "show_progress_indicator", nullable = false)
  private Boolean showProgressIndicator = true;

  @Column(name = "show_field_numbers", nullable = false)
  private Boolean showFieldNumbers = true;

  @Column(name = "compact_mode", nullable = false)
  private Boolean compactMode = false;

  // === CONFIGURAÇÕES DE COMPORTAMENTO ===
  @Column(name = "allow_partial_save", nullable = false)
  private Boolean allowPartialSave = true;

  @Column(name = "auto_save_enabled", nullable = false)
  private Boolean autoSaveEnabled = true;

  @Column(name = "auto_save_interval") // minutos
  @Min(value = 1, message = "Auto save interval must be at least 1 minute")
  private Integer autoSaveInterval = 5;

  @Column(name = "require_all_fields", nullable = false)
  private Boolean requireAllFields = false;

  @Column(name = "validation_on_save", nullable = false)
  private Boolean validationOnSave = true;

  @Column(name = "confirmation_before_submit", nullable = false)
  private Boolean confirmationBeforeSubmit = true;

  // === CONFIGURAÇÕES DE ACESSO ===
  @Column(name = "max_time_minutes") // tempo máximo para preencher
  @Min(value = 5, message = "Max time must be at least 5 minutes")
  private Integer maxTimeMinutes = 180; // 3 horas

  @Column(name = "allow_edit_after_submit", nullable = false)
  private Boolean allowEditAfterSubmit = false;

  @Column(name = "lock_after_consensus", nullable = false)
  private Boolean lockAfterConsensus = true;

  @Column(name = "anonymous_mode", nullable = false)
  private Boolean anonymousMode = false;

  // === INSTRUÇÕES E ORIENTAÇÕES ===
  @Column(name = "instructions", columnDefinition = "TEXT")
  @Size(max = 3000, message = "Instructions must be less than 3000 characters")
  private String instructions;

  @Column(name = "completion_message", columnDefinition = "TEXT")
  @Size(max = 1000, message = "Completion message must be less than 1000 characters")
  private String completionMessage;

  @Column(name = "help_text", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Help text must be less than 2000 characters")
  private String helpText;

  @Column(name = "quality_tips", columnDefinition = "TEXT")
  @Size(max = 1500, message = "Quality tips must be less than 1500 characters")
  private String qualityTips;

  // === CONFIGURAÇÕES DE QUALIDADE ===
  @Column(name = "estimated_completion_time") // minutos
  @Min(value = 1, message = "Estimated completion time must be at least 1 minute")
  private Integer estimatedCompletionTime = 30;

  @Column(name = "difficulty_level")
  @Min(value = 1, message = "Difficulty level must be between 1 and 5")
  private Integer difficultyLevel = 3; // 1-5

  @Column(name = "required_expertise_level", length = 20)
  @Size(max = 20, message = "Required expertise level must be less than 20 characters")
  private String requiredExpertiseLevel = "INTERMEDIATE"; // BEGINNER, INTERMEDIATE, ADVANCED,
                                                          // EXPERT

  // === MÉTRICAS E ESTATÍSTICAS ===
  @Column(name = "total_fields")
  @Min(value = 0, message = "Total fields cannot be negative")
  private Integer totalFields = 0;

  @Column(name = "required_fields")
  @Min(value = 0, message = "Required fields cannot be negative")
  private Integer requiredFields = 0;

  @Column(name = "conditional_fields")
  @Min(value = 0, message = "Conditional fields cannot be negative")
  private Integer conditionalFields = 0;

  @Column(name = "times_used")
  @Min(value = 0, message = "Times used cannot be negative")
  private Integer timesUsed = 0;

  @Column(name = "average_completion_time") // minutos (calculado)
  private Double averageCompletionTime = 0.0;

  @Column(name = "completion_rate") // porcentagem
  private Double completionRate = 0.0;

  // === CONTROLE DE VERSÃO ===
  @Column(name = "parent_form_id")
  private Long parentFormId; // Referência para versionamento

  @Column(name = "major_version")
  @Min(value = 1, message = "Major version must be at least 1")
  private Integer majorVersion = 1;

  @Column(name = "minor_version")
  @Min(value = 0, message = "Minor version cannot be negative")
  private Integer minorVersion = 0;

  @Column(name = "revision_notes", columnDefinition = "TEXT")
  @Size(max = 1000, message = "Revision notes must be less than 1000 characters")
  private String revisionNotes;

  @Column(name = "approved_at")
  private LocalDateTime approvedAt;

  @Column(name = "approved_by_user_id")
  private Long approvedByUserId;

  // === OBSERVAÇÕES ===
  @Column(name = "design_notes", columnDefinition = "TEXT")
  @Size(max = 1500, message = "Design notes must be less than 1500 characters")
  private String designNotes;

  @Column(name = "usage_notes", columnDefinition = "TEXT")
  @Size(max = 1500, message = "Usage notes must be less than 1500 characters")
  private String usageNotes;

  @Column(name = "validation_errors", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Validation errors must be less than 2000 characters")
  private String validationErrors;

  // === RELACIONAMENTOS ===
  @OneToMany(mappedBy = "extractionForm", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ExtractionField> fields = new ArrayList<>();

  @OneToMany(mappedBy = "extractionForm", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ReviewerDataExtraction> reviewerExtractions = new ArrayList<>();

  // Construtor customizado
  public ExtractionForm(DataExtraction dataExtraction, String title) {
    this.dataExtraction = dataExtraction;
    this.title = title;
    this.status = WorkflowStatus.DRAFT;
    this.version = "1.0";
    this.isActive = false;
    this.isTemplate = false;
    this.layoutType = "VERTICAL";
    this.columnsPerRow = 1;
    this.allowPartialSave = true;
    this.autoSaveEnabled = true;
    this.autoSaveInterval = 5;
    this.maxTimeMinutes = 180;
    this.estimatedCompletionTime = 30;
    this.difficultyLevel = 3;
    this.requiredExpertiseLevel = "INTERMEDIATE";
    this.majorVersion = 1;
    this.minorVersion = 0;
  }

  // === MÉTODOS DE NEGÓCIO ===

  // Ativa o formulário para uso
  public void activateForm() {
    if (this.status == WorkflowStatus.COMPLETED && canActivate()) {
      this.isActive = true;
      this.approvedAt = LocalDateTime.now();
      this.status = WorkflowStatus.ACTIVE;
    }
  }

  // Desativa o formulário
  public void deactivateForm(String reason) {
    if (this.isActive) {
      this.isActive = false;
      this.status = WorkflowStatus.ARCHIVED;
      this.usageNotes = (this.usageNotes != null ? this.usageNotes + "\n" : "") + "Desativado: "
          + reason + " (" + LocalDateTime.now() + ")";
    }
  }

  // Completa o design do formulário
  public void completeFormDesign() {
    if (this.status == WorkflowStatus.DRAFT && hasRequiredFields()) {
      this.status = WorkflowStatus.COMPLETED;
      this.totalFields = fields.size();
      this.requiredFields = (int) fields.stream().filter(ExtractionField::isRequired).count();
      this.conditionalFields = (int) fields.stream().filter(ExtractionField::hasConditions).count();
    }
  }

  // Cria nova versão do formulário
  public ExtractionForm createNewVersion(String revisionNotes) {
    ExtractionForm newVersion =
        new ExtractionForm(this.dataExtraction, this.title + " v" + getNextVersion());
    newVersion.setParentFormId(this.getId());
    newVersion.setMajorVersion(this.majorVersion);
    newVersion.setMinorVersion(this.minorVersion + 1);
    newVersion.setVersion(this.majorVersion + "." + (this.minorVersion + 1));
    newVersion.setRevisionNotes(revisionNotes);

    // Copiar configurações básicas
    newVersion.setDescription(this.description);
    newVersion.setLayoutType(this.layoutType);
    newVersion.setColumnsPerRow(this.columnsPerRow);
    newVersion.setInstructions(this.instructions);
    newVersion.setEstimatedCompletionTime(this.estimatedCompletionTime);
    newVersion.setDifficultyLevel(this.difficultyLevel);
    newVersion.setRequiredExpertiseLevel(this.requiredExpertiseLevel);

    return newVersion;
  }

  // Registra uso do formulário
  public void recordUsage(Integer completionTimeMinutes, boolean completed) {
    this.timesUsed = (this.timesUsed != null ? this.timesUsed : 0) + 1;

    if (completed && completionTimeMinutes != null) {
      // Calcular nova média de tempo de conclusão
      double currentAverage = this.averageCompletionTime != null ? this.averageCompletionTime : 0.0;
      int completedUsages = Math.max(1, (int) (this.timesUsed
          * (this.completionRate != null ? this.completionRate / 100.0 : 0.5)));

      this.averageCompletionTime =
          ((currentAverage * (completedUsages - 1)) + completionTimeMinutes) / completedUsages;

      // Calcular nova taxa de conclusão
      this.completionRate = (completedUsages * 100.0) / this.timesUsed;
    }
  }

  // === VALIDAÇÕES DE NEGÓCIO ===

  public boolean canActivate() {
    return hasRequiredFields() && isFormValid() && !hasValidationErrors();
  }

  public boolean hasRequiredFields() {
    return !fields.isEmpty() && fields.stream().anyMatch(ExtractionField::isRequired);
  }

  public boolean isFormValid() {
    return fields.stream().allMatch(ExtractionField::isValid);
  }

  public boolean hasValidationErrors() {
    return validationErrors != null && !validationErrors.trim().isEmpty();
  }

  public boolean isCurrentVersion() {
    return isActive && status == WorkflowStatus.ACTIVE;
  }

  public boolean canCreateNewVersion() {
    return status == WorkflowStatus.ACTIVE || status == WorkflowStatus.ARCHIVED;
  }

  public boolean isExpired() {
    return !isActive && status == WorkflowStatus.ARCHIVED;
  }

  // === CÁLCULOS E MÉTRICAS ===

  // Complexidade do formulário (1-10)
  public Integer getFormComplexity() {
    if (totalFields == null || totalFields == 0)
      return 1;

    int complexity = 1;
    complexity += Math.min(3, totalFields / 5); // +1 para cada 5 campos
    complexity += Math.min(2, conditionalFields != null ? conditionalFields / 2 : 0); // +1 para
                                                                                      // cada 2
                                                                                      // campos
                                                                                      // condicionais
    complexity += difficultyLevel != null ? Math.min(3, difficultyLevel - 1) : 0; // baseado na
                                                                                  // dificuldade

    return Math.min(10, complexity);
  }

  // Eficiência do formulário
  public Double getFormEfficiency() {
    if (averageCompletionTime == null || averageCompletionTime == 0
        || estimatedCompletionTime == null) {
      return 100.0;
    }

    double efficiency = (estimatedCompletionTime / averageCompletionTime) * 100.0;
    return Math.min(100.0, Math.max(0.0, efficiency));
  }

  // Taxa de campos obrigatórios
  public Double getRequiredFieldsRate() {
    if (totalFields == null || totalFields == 0)
      return 0.0;
    if (requiredFields == null)
      return 0.0;
    return (requiredFields * 100.0) / totalFields;
  }

  // Próxima versão
  public String getNextVersion() {
    return majorVersion + "." + (minorVersion + 1);
  }

  // Status de aprovação
  public boolean isApproved() {
    return approvedAt != null && approvedByUserId != null;
  }

  // === MÉTODOS DE CONSULTA ===

  public boolean isDraftStatus() {
    return status == WorkflowStatus.DRAFT;
  }

  public boolean isActiveStatus() {
    return status == WorkflowStatus.ACTIVE;
  }

  public boolean isCompletedStatus() {
    return status == WorkflowStatus.COMPLETED;
  }

  public boolean isArchivedStatus() {
    return status == WorkflowStatus.ARCHIVED;
  }

  // === MÉTODOS DE CONVENIÊNCIA ===

  public String getDataExtractionTitle() {
    return dataExtraction != null ? dataExtraction.getTitle() : "Unknown";
  }

  public String getFullVersion() {
    return majorVersion + "." + minorVersion;
  }

  public Integer getTotalFieldsCount() {
    return fields != null ? fields.size() : 0;
  }

  public Integer getRequiredFieldsCount() {
    return fields != null ? (int) fields.stream().filter(ExtractionField::isRequired).count() : 0;
  }

  public Integer getOptionalFieldsCount() {
    return getTotalFieldsCount() - getRequiredFieldsCount();
  }

  public String getComplexityLevel() {
    int complexity = getFormComplexity();
    if (complexity <= 3)
      return "SIMPLE";
    if (complexity <= 6)
      return "MODERATE";
    if (complexity <= 8)
      return "COMPLEX";
    return "VERY_COMPLEX";
  }

  // === MÉTODOS DE ATUALIZAÇÃO ===

  public void updateFieldCounts() {
    this.totalFields = getTotalFieldsCount();
    this.requiredFields = getRequiredFieldsCount();
    this.conditionalFields =
        fields != null ? (int) fields.stream().filter(ExtractionField::hasConditions).count() : 0;
  }

  public void addValidationError(String error) {
    this.validationErrors =
        (this.validationErrors != null ? this.validationErrors + "\n" : "") + error;
  }

  public void clearValidationErrors() {
    this.validationErrors = null;
  }

  public void incrementUsage() {
    this.timesUsed = (this.timesUsed != null ? this.timesUsed : 0) + 1;
  }

  public void setApproval(Long approvedByUserId) {
    this.approvedAt = LocalDateTime.now();
    this.approvedByUserId = approvedByUserId;
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
    return "ExtractionForm{" + "id=" + getId() + ", dataExtractionId="
        + (dataExtraction != null ? dataExtraction.getId() : null) + ", title='" + title + '\''
        + ", version='" + version + '\'' + ", status=" + status + ", isActive=" + isActive
        + ", totalFields=" + totalFields + ", requiredFields=" + requiredFields + ", complexity="
        + getFormComplexity() + ", timesUsed=" + timesUsed + ", completionRate="
        + String.format("%.1f", completionRate != null ? completionRate : 0.0) + "%"
        + ", averageTime="
        + (averageCompletionTime != null ? String.format("%.1f", averageCompletionTime) : "0.0")
        + " min" + ", efficiency=" + String.format("%.1f", getFormEfficiency()) + "%"
        + ", isApproved=" + isApproved() + ", isDeleted=" + getIsDeleted() + '}';
  }
}
