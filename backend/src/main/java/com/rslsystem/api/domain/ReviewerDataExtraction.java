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
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * Entidade ReviewerDataExtraction - representa os dados extraídos por um revisor específico de um
 * estudo usando um formulário de extração configurado.
 */
@Entity
@Table(name = "reviewer_data_extractions",
    indexes = {@Index(name = "idx_extraction_data_extraction", columnList = "data_extraction_id"),
        @Index(name = "idx_extraction_form", columnList = "extraction_form_id"),
        @Index(name = "idx_extraction_study", columnList = "study_id"),
        @Index(name = "idx_extraction_reviewer", columnList = "reviewer_id"),
        @Index(name = "idx_extraction_status", columnList = "status"),
        @Index(name = "idx_extraction_submitted", columnList = "submitted_at"),
        @Index(name = "idx_extraction_study_reviewer", columnList = "study_id, reviewer_id"),
        @Index(name = "idx_extraction_created", columnList = "created_at")})
@Getter
@Setter
@NoArgsConstructor
public class ReviewerDataExtraction extends AuditableEntity {

  // === CONSTANTES ===
  private static final String UNKNOWN_VALUE = "Unknown";

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "data_extraction_id", nullable = false)
  @NotNull(message = "Data extraction is required")
  private DataExtraction dataExtraction;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "extraction_form_id", nullable = false)
  @NotNull(message = "Extraction form is required")
  private ExtractionForm extractionForm;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "study_id", nullable = false)
  @NotNull(message = "Study is required")
  private Study study;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "reviewer_id", nullable = false)
  @NotNull(message = "Reviewer is required")
  private User reviewer;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private WorkflowStatus status = WorkflowStatus.DRAFT;

  // === DADOS EXTRAÍDOS (JSON Flexível) ===
  @Column(name = "extracted_data", columnDefinition = "JSONB")
  private Map<String, Object> extractedData = new ConcurrentHashMap<>();

  @Column(name = "field_validations", columnDefinition = "JSONB")
  private Map<String, Object> fieldValidations = new ConcurrentHashMap<>();

  @Column(name = "field_conflicts", columnDefinition = "JSONB")
  private Map<String, Object> fieldConflicts = new ConcurrentHashMap<>();

  // === CONTROLE DE TEMPO ===
  @Column(name = "started_at")
  private LocalDateTime startedAt;

  @Column(name = "last_saved_at")
  private LocalDateTime lastSavedAt;

  @Column(name = "submitted_at")
  private LocalDateTime submittedAt;

  @Column(name = "validated_at")
  private LocalDateTime validatedAt;

  @Column(name = "extraction_time_minutes") // Tempo total gasto na extração
  @Min(value = 0, message = "Extraction time cannot be negative")
  private Integer extractionTimeMinutes = 0;

  @Column(name = "session_count") // Número de sessões de trabalho
  @Min(value = 0, message = "Session count cannot be negative")
  private Integer sessionCount = 0;

  @Column(name = "auto_saves_count") // Número de salvamentos automáticos
  @Min(value = 0, message = "Auto saves count cannot be negative")
  private Integer autoSavesCount = 0;

  // === CONTROLE DE QUALIDADE ===
  @Column(name = "completion_percentage")
  @Min(value = 0, message = "Completion percentage cannot be negative")
  private Integer completionPercentage = 0;

  @Column(name = "required_fields_completed")
  @Min(value = 0, message = "Required fields completed cannot be negative")
  private Integer requiredFieldsCompleted = 0;

  @Column(name = "total_required_fields")
  @Min(value = 0, message = "Total required fields cannot be negative")
  private Integer totalRequiredFields = 0;

  @Column(name = "validation_errors_count")
  @Min(value = 0, message = "Validation errors count cannot be negative")
  private Integer validationErrorsCount = 0;

  @Column(name = "quality_score") // Score de qualidade (0-100)
  @Min(value = 0, message = "Quality score cannot be negative")
  private Double qualityScore = 0.0;

  // === CONFIGURAÇÕES DE SESSÃO ===
  @Column(name = "is_blind_extraction", nullable = false)
  private Boolean isBlindExtraction = true;

  @Column(name = "allow_edit_after_submit", nullable = false)
  private Boolean allowEditAfterSubmit = false;

  @Column(name = "requires_validation", nullable = false)
  private Boolean requiresValidation = true;

  @Column(name = "extraction_round") // Primeira extração, segunda extração, etc.
  @Min(value = 1, message = "Extraction round must be at least 1")
  private Integer extractionRound = 1;

  @Column(name = "is_consensus_extraction", nullable = false)
  private Boolean isConsensusExtraction = false;

  // === METADADOS DE CONFLITO ===
  @Column(name = "conflicts_detected")
  @Min(value = 0, message = "Conflicts detected cannot be negative")
  private Integer conflictsDetected = 0;

  @Column(name = "conflicts_resolved")
  @Min(value = 0, message = "Conflicts resolved cannot be negative")
  private Integer conflictsResolved = 0;

  @Column(name = "conflict_resolution_notes", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Conflict resolution notes must be less than 2000 characters")
  private String conflictResolutionNotes;

  @Column(name = "consensus_reached", nullable = false)
  private Boolean consensusReached = false;

  @Column(name = "consensus_reached_at")
  private LocalDateTime consensusReachedAt;

  // === OBSERVAÇÕES E FEEDBACK ===
  @Column(name = "extraction_notes", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Extraction notes must be less than 2000 characters")
  private String extractionNotes;

  @Column(name = "reviewer_comments", columnDefinition = "TEXT")
  @Size(max = 1500, message = "Reviewer comments must be less than 1500 characters")
  private String reviewerComments;

  @Column(name = "quality_concerns", columnDefinition = "TEXT")
  @Size(max = 1000, message = "Quality concerns must be less than 1000 characters")
  private String qualityConcerns;

  @Column(name = "extraction_difficulty_rating")
  @Min(value = 1, message = "Extraction difficulty rating must be between 1 and 5")
  private Integer extractionDifficultyRating = 3; // 1-5

  @Column(name = "form_usability_rating")
  @Min(value = 1, message = "Form usability rating must be between 1 and 5")
  private Integer formUsabilityRating = 3; // 1-5

  // === CONTROLE DE VERSÃO ===
  @Column(name = "form_version", length = 20)
  @Size(max = 20, message = "Form version must be less than 20 characters")
  private String formVersion;

  @Column(name = "data_version")
  @Min(value = 1, message = "Data version must be at least 1")
  private Integer dataVersion = 1;

  @Column(name = "last_modified_field", length = 100)
  @Size(max = 100, message = "Last modified field must be less than 100 characters")
  private String lastModifiedField;

  // === VALIDAÇÃO E APROVAÇÃO ===
  @Column(name = "validated_by_user_id")
  private Long validatedByUserId;

  @Column(name = "validation_comments", columnDefinition = "TEXT")
  @Size(max = 1000, message = "Validation comments must be less than 1000 characters")
  private String validationComments;

  @Column(name = "approved_at")
  private LocalDateTime approvedAt;

  @Column(name = "approved_by_user_id")
  private Long approvedByUserId;

  @Column(name = "rejection_reason", columnDefinition = "TEXT")
  @Size(max = 1000, message = "Rejection reason must be less than 1000 characters")
  private String rejectionReason;

  // Construtor customizado
  public ReviewerDataExtraction(DataExtraction dataExtraction, ExtractionForm extractionForm,
      Study study, User reviewer) {
    this.dataExtraction = dataExtraction;
    this.extractionForm = extractionForm;
    this.study = study;
    this.reviewer = reviewer;
    this.status = WorkflowStatus.DRAFT;
    this.extractedData = new ConcurrentHashMap<>();
    this.fieldValidations = new ConcurrentHashMap<>();
    this.fieldConflicts = new ConcurrentHashMap<>();
    this.completionPercentage = 0;
    this.requiredFieldsCompleted = 0;
    this.totalRequiredFields = extractionForm.getRequiredFieldsCount();
    this.validationErrorsCount = 0;
    this.qualityScore = 0.0;
    this.isBlindExtraction = dataExtraction.getBlindExtractionEnabled();
    this.allowEditAfterSubmit = extractionForm.getAllowEditAfterSubmit();
    this.requiresValidation = dataExtraction.getRequiresConsensus();
    this.extractionRound = 1;
    this.isConsensusExtraction = false;
    this.conflictsDetected = 0;
    this.conflictsResolved = 0;
    this.consensusReached = false;
    this.extractionTimeMinutes = 0;
    this.sessionCount = 0;
    this.autoSavesCount = 0;
    this.extractionDifficultyRating = 3;
    this.formUsabilityRating = 3;
    this.formVersion = extractionForm.getVersion();
    this.dataVersion = 1;
  }

  // === MÉTODOS DE NEGÓCIO ===

  // Inicia a extração
  public void startExtraction() {
    if (this.status == WorkflowStatus.DRAFT && canStart()) {
      this.status = WorkflowStatus.IN_PROGRESS;
      this.startedAt = LocalDateTime.now();
      this.sessionCount = (this.sessionCount != null ? this.sessionCount : 0) + 1;
    }
  }

  // Salva dados da extração
  public void saveExtractionData(Map<String, Object> newData) {
    if (canSaveData()) {
      // Merge dos dados existentes com novos dados
      this.extractedData.putAll(newData);
      this.lastSavedAt = LocalDateTime.now();
      this.dataVersion = (this.dataVersion != null ? this.dataVersion : 0) + 1;

      // Atualizar estatísticas
      updateCompletionPercentage();
      updateQualityScore();
    }
  }

  // Auto-save
  public void autoSave(Map<String, Object> currentData) {
    if (canSaveData()) {
      saveExtractionData(currentData);
      this.autoSavesCount = (this.autoSavesCount != null ? this.autoSavesCount : 0) + 1;
    }
  }

  // Submete a extração
  public void submitExtraction() {
    if (this.status == WorkflowStatus.IN_PROGRESS && canSubmit()) {
      this.status = WorkflowStatus.COMPLETED;
      this.submittedAt = LocalDateTime.now();
      this.completionPercentage = calculateFinalCompletionPercentage();
      this.qualityScore = calculateFinalQualityScore();
    }
  }

  // Valida a extração (por supervisor/coordenador)
  public void validateExtraction(Long validatorUserId, String validationComments) {
    if (this.status == WorkflowStatus.COMPLETED && canValidate()) {
      this.status = WorkflowStatus.VALIDATED;
      this.validatedAt = LocalDateTime.now();
      this.validatedByUserId = validatorUserId;
      this.validationComments = validationComments;
    }
  }

  // Aprova a extração
  public void approveExtraction(Long approverUserId) {
    if (this.status == WorkflowStatus.VALIDATED && canApprove()) {
      this.status = WorkflowStatus.APPROVED;
      this.approvedAt = LocalDateTime.now();
      this.approvedByUserId = approverUserId;
    }
  }

  // Rejeita a extração
  public void rejectExtraction(String rejectionReason) {
    if (canReject()) {
      this.status = WorkflowStatus.REJECTED;
      this.rejectionReason = rejectionReason;
      // Permitir edição novamente para correção
      this.allowEditAfterSubmit = true;
    }
  }

  // Pausa a extração
  public void pauseExtraction(String reason) {
    if (this.status == WorkflowStatus.IN_PROGRESS) {
      this.status = WorkflowStatus.PAUSED;
      this.extractionNotes = (this.extractionNotes != null ? this.extractionNotes + "\n" : "")
          + "Pausado: " + reason + " (" + LocalDateTime.now() + ")";
    }
  }

  // Resume a extração
  public void resumeExtraction() {
    if (this.status == WorkflowStatus.PAUSED) {
      this.status = WorkflowStatus.IN_PROGRESS;
      this.sessionCount = (this.sessionCount != null ? this.sessionCount : 0) + 1;
      this.extractionNotes = (this.extractionNotes != null ? this.extractionNotes + "\n" : "")
          + "Retomado: " + LocalDateTime.now();
    }
  }

  // Detecta conflitos com outras extrações
  public void detectConflicts(ReviewerDataExtraction otherExtraction) {
    if (otherExtraction != null
        && !otherExtraction.getReviewer().getId().equals(this.reviewer.getId())) {
      Map<String, Object> conflicts =
          findDataConflicts(this.extractedData, otherExtraction.getExtractedData());
      this.fieldConflicts.putAll(conflicts);
      this.conflictsDetected = conflicts.size();
    }
  }

  // Resolve conflito específico
  public void resolveConflict(String fieldName, Object resolvedValue, String resolutionNote) {
    if (fieldConflicts.containsKey(fieldName)) {
      // Atualizar dados com valor resolvido
      extractedData.put(fieldName, resolvedValue);

      // Remover conflito
      fieldConflicts.remove(fieldName);

      // Adicionar nota de resolução
      this.conflictResolutionNotes =
          (this.conflictResolutionNotes != null ? this.conflictResolutionNotes + "\n" : "")
              + fieldName + ": " + resolutionNote + " (" + LocalDateTime.now() + ")";

      // Atualizar contadores
      this.conflictsResolved = (this.conflictsResolved != null ? this.conflictsResolved : 0) + 1;
      this.conflictsDetected =
          Math.max(0, (this.conflictsDetected != null ? this.conflictsDetected : 0) - 1);

      // Verificar se todos os conflitos foram resolvidos
      if (this.conflictsDetected == 0) {
        this.consensusReached = true;
        this.consensusReachedAt = LocalDateTime.now();
      }
    }
  }

  // === VALIDAÇÕES DE NEGÓCIO ===

  public boolean canStart() {
    return hasValidForm() && hasValidStudy() && hasValidReviewer();
  }

  public boolean canSaveData() {
    return status == WorkflowStatus.IN_PROGRESS || status == WorkflowStatus.PAUSED
        || (status == WorkflowStatus.REJECTED && allowEditAfterSubmit);
  }

  public boolean canSubmit() {
    return isMinimallyComplete() && hasNoValidationErrors();
  }

  public boolean canValidate() {
    return submittedAt != null && validatedByUserId == null;
  }

  public boolean canApprove() {
    return validatedAt != null && approvedByUserId == null;
  }

  public boolean canReject() {
    return status == WorkflowStatus.COMPLETED || status == WorkflowStatus.VALIDATED;
  }

  private boolean hasValidForm() {
    return extractionForm != null && extractionForm.isActiveStatus();
  }

  private boolean hasValidStudy() {
    return study != null;
  }

  private boolean hasValidReviewer() {
    return reviewer != null;
  }

  private boolean isMinimallyComplete() {
    return completionPercentage != null && completionPercentage >= 80; // 80% mínimo
  }

  private boolean hasNoValidationErrors() {
    return validationErrorsCount == null || validationErrorsCount == 0;
  }

  // === CÁLCULOS E MÉTRICAS (CORRIGIDOS) ===

  // Atualiza percentual de conclusão
  private void updateCompletionPercentage() {
    if (totalRequiredFields == null || totalRequiredFields == 0) {
      this.completionPercentage = 100;
      return;
    }

    int completedFields = 0;
    // ✅ Iterar sobre entrySet (mais eficiente)
    for (Map.Entry<String, Object> entry : extractedData.entrySet()) {
      Object value = entry.getValue();
      if (value != null && !value.toString().trim().isEmpty()) {
        completedFields++;
      }
    }

    this.requiredFieldsCompleted = completedFields;
    this.completionPercentage = Math.min(100, (completedFields * 100) / totalRequiredFields);
  }

  // Atualiza score de qualidade
  private void updateQualityScore() {
    double score = 0.0;

    // Baseado na completude (40%)
    if (completionPercentage != null) {
      score += (completionPercentage * 0.4);
    }

    // Baseado na ausência de erros de validação (30%)
    if (validationErrorsCount != null && totalRequiredFields != null && totalRequiredFields > 0) {
      double errorRate = validationErrorsCount.doubleValue() / totalRequiredFields;
      score += ((1.0 - errorRate) * 30.0);
    } else {
      score += 30.0; // Sem erros
    }

    // Baseado na resolução de conflitos (20%)
    if (conflictsDetected != null && conflictsDetected > 0) {
      double resolutionRate =
          (conflictsResolved != null ? conflictsResolved.doubleValue() : 0.0) / conflictsDetected;
      score += (resolutionRate * 20.0);
    } else {
      score += 20.0; // Sem conflitos
    }

    // Baseado no tempo de extração (10%)
    if (extractionTimeMinutes != null && extractionTimeMinutes > 0) {
      Integer estimatedTime = extractionForm.getEstimatedCompletionTime();
      if (estimatedTime != null && estimatedTime > 0) {
        double timeRatio = estimatedTime.doubleValue() / extractionTimeMinutes;
        score += Math.min(10.0, timeRatio * 10.0);
      } else {
        score += 5.0; // Score médio se não há estimativa
      }
    }

    this.qualityScore = Math.clamp(score, 0.0, 100.0);
  }

  // Encontra conflitos entre duas extrações
  private Map<String, Object> findDataConflicts(Map<String, Object> data1,
      Map<String, Object> data2) {
    Map<String, Object> conflicts = new ConcurrentHashMap<>();

    // ✅ Iterar sobre entrySet (mais eficiente)
    for (Map.Entry<String, Object> entry : data1.entrySet()) {
      String fieldName = entry.getKey();
      Object value1 = entry.getValue();

      if (data2.containsKey(fieldName)) {
        Object value2 = data2.get(fieldName);

        if (value1 != null && value2 != null && !value1.equals(value2)) {
          Map<String, Object> conflictInfo = new ConcurrentHashMap<>();
          conflictInfo.put("reviewer1_value", value1);
          conflictInfo.put("reviewer2_value", value2);
          conflictInfo.put("detected_at", LocalDateTime.now());
          conflicts.put(fieldName, conflictInfo);
        }
      }
    }

    return conflicts;
  }

  // Eficiência temporal
  public Double getTimeEfficiency() {
    Integer estimatedTime =
        extractionForm != null ? extractionForm.getEstimatedCompletionTime() : null;
    if (estimatedTime == null || estimatedTime == 0 || extractionTimeMinutes == null)
      return 100.0;

    double efficiency = (estimatedTime.doubleValue() / extractionTimeMinutes) * 100.0;
    // ✅ Usar Math.clamp
    return Math.clamp(efficiency, 0.0, 100.0);
  }

  // === CÁLCULOS E MÉTRICAS (ADICIONAR MÉTODOS FALTANDO) ===

  // ✅ MÉTODO FALTANDO: Cálculo final da completude
  private Integer calculateFinalCompletionPercentage() {
    updateCompletionPercentage();
    return this.completionPercentage;
  }

  public String getReviewerName() {
    return reviewer != null ? reviewer.getName() : UNKNOWN_VALUE;
  }

  public String getQualityLevel() {
    if (qualityScore == null)
      return UNKNOWN_VALUE;
    if (qualityScore >= 90)
      return "EXCELLENT";
    if (qualityScore >= 80)
      return "GOOD";
    if (qualityScore >= 70)
      return "SATISFACTORY";
    if (qualityScore >= 60)
      return "NEEDS_IMPROVEMENT";
    return "POOR";
  }

  private Double calculateFinalQualityScore() {
    updateQualityScore();
    return this.qualityScore;
  }

  public Double getExtractionTimeHours() {
    if (extractionTimeMinutes == null)
      return 0.0;
    return extractionTimeMinutes / 60.0;
  }

  // Taxa de conflitos
  public Double getConflictRate() {
    if (totalRequiredFields == null || totalRequiredFields == 0)
      return 0.0;
    if (conflictsDetected == null)
      return 0.0;
    return (conflictsDetected * 100.0) / totalRequiredFields;
  }

  // Taxa de resolução de conflitos
  public Double getConflictResolutionRate() {
    if (conflictsDetected == null || conflictsDetected == 0)
      return 100.0;
    if (conflictsResolved == null)
      return 0.0;
    return (conflictsResolved * 100.0) / conflictsDetected;
  }

  // === MÉTODOS DE CONSULTA (ADICIONAR MÉTODOS FALTANDO) ===

  public boolean isDraftStatus() {
    return status == WorkflowStatus.DRAFT;
  }

  public boolean isInProgressStatus() {
    return status == WorkflowStatus.IN_PROGRESS;
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

  public boolean isApprovedStatus() {
    return status == WorkflowStatus.APPROVED;
  }

  public boolean isRejectedStatus() {
    return status == WorkflowStatus.REJECTED;
  }

  public boolean isSubmitted() {
    return submittedAt != null;
  }

  public boolean isValidated() {
    return validatedAt != null && validatedByUserId != null;
  }

  public boolean isApproved() {
    return approvedAt != null && approvedByUserId != null;
  }

  public boolean hasConflicts() {
    return conflictsDetected != null && conflictsDetected > 0;
  }

  public boolean hasUnresolvedConflicts() {
    return hasConflicts() && !consensusReached;
  }

  public boolean isHighQuality() {
    return qualityScore != null && qualityScore >= 80.0;
  }

  public boolean isComplete() {
    return completionPercentage != null && completionPercentage >= 100;
  }

  // === MÉTODOS DE ATUALIZAÇÃO ===

  public void incrementExtractionTime(Integer additionalMinutes) {
    this.extractionTimeMinutes =
        (this.extractionTimeMinutes != null ? this.extractionTimeMinutes : 0) + additionalMinutes;
  }

  public void incrementValidationErrors() {
    this.validationErrorsCount =
        (this.validationErrorsCount != null ? this.validationErrorsCount : 0) + 1;
  }

  public void setFieldValue(String fieldName, Object value) {
    if (canSaveData()) {
      this.extractedData.put(fieldName, value);
      this.lastModifiedField = fieldName;
      this.lastSavedAt = LocalDateTime.now();
      updateCompletionPercentage();
      updateQualityScore();
    }
  }

  public Object getFieldValue(String fieldName) {
    return extractedData != null ? extractedData.get(fieldName) : null;
  }

  public void addReviewerComment(String comment) {
    this.reviewerComments =
        (this.reviewerComments != null ? this.reviewerComments + "\n" : "") + comment;
  }

  public void addQualityConcern(String concern) {
    this.qualityConcerns =
        (this.qualityConcerns != null ? this.qualityConcerns + "\n" : "") + concern;
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
    return "ReviewerDataExtraction{" + "id=" + getId() + ", dataExtractionId="
        + (dataExtraction != null ? dataExtraction.getId() : null) + ", formId="
        + (extractionForm != null ? extractionForm.getId() : null) + ", studyId="
        + (study != null ? study.getId() : null) + ", reviewerId="
        + (reviewer != null ? reviewer.getId() : null) + ", reviewerName='" + getReviewerName()
        + '\'' + ", status=" + status + ", completion=" + completionPercentage + "%" + ", quality="
        + String.format("%.1f", qualityScore != null ? qualityScore : 0.0) + "%" + ", qualityLevel="
        + getQualityLevel() + ", fieldsCompleted=" + requiredFieldsCompleted + "/"
        + totalRequiredFields + ", extractionTime="
        + String.format("%.1f", getExtractionTimeHours()) + "h" + ", conflicts=" + conflictsDetected
        + "/" + conflictsResolved + ", consensusReached=" + consensusReached + ", sessions="
        + sessionCount + ", autoSaves=" + autoSavesCount + ", round=" + extractionRound + ", blind="
        + isBlindExtraction + ", submitted=" + isSubmitted() + ", validated=" + isValidated()
        + ", approved=" + isApproved() + ", timeEfficiency="
        + String.format("%.1f", getTimeEfficiency()) + "%" + ", isDeleted=" + getIsDeleted() + '}';
  }
}
