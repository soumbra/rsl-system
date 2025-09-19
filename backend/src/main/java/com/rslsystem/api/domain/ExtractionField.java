package com.rslsystem.api.domain;

import com.rslsystem.api.domain.shared.AuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidade ExtractionField - representa um campo individual configurável de um formulário de
 * extração. Define tipo, validações, comportamento e dependências de cada campo.
 */
@Entity
@Table(name = "extraction_fields",
    indexes = {@Index(name = "idx_field_form", columnList = "extraction_form_id"),
        @Index(name = "idx_field_type", columnList = "field_type"),
        @Index(name = "idx_field_order", columnList = "display_order"),
        @Index(name = "idx_field_required", columnList = "is_required"),
        @Index(name = "idx_field_section", columnList = "section_name"),
        @Index(name = "idx_field_created", columnList = "created_at")})
@Getter
@Setter
@NoArgsConstructor
public class ExtractionField extends AuditableEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "extraction_form_id", nullable = false)
  @NotNull(message = "Extraction form is required")
  private ExtractionForm extractionForm;

  // === CONFIGURAÇÃO BÁSICA ===
  @Column(name = "field_name", length = 100, nullable = false)
  @Size(max = 100, message = "Field name must be less than 100 characters")
  @NotNull(message = "Field name is required")
  private String fieldName;

  @Column(name = "field_label", length = 200, nullable = false)
  @Size(max = 200, message = "Field label must be less than 200 characters")
  @NotNull(message = "Field label is required")
  private String fieldLabel;

  @Column(name = "field_description", columnDefinition = "TEXT")
  @Size(max = 1000, message = "Field description must be less than 1000 characters")
  private String fieldDescription;

  @Column(name = "field_type", length = 30, nullable = false)
  @Size(max = 30, message = "Field type must be less than 30 characters")
  @NotNull(message = "Field type is required")
  private String fieldType; // TEXT, NUMBER, DATE, BOOLEAN, SELECT, MULTISELECT, TEXTAREA, URL,
                            // EMAIL, SCALE, MATRIX

  @Column(name = "data_type", length = 20)
  @Size(max = 20, message = "Data type must be less than 20 characters")
  private String dataType = "STRING"; // STRING, INTEGER, DECIMAL, DATE, BOOLEAN, JSON

  // === ORGANIZAÇÃO E LAYOUT ===
  @Column(name = "section_name", length = 100)
  @Size(max = 100, message = "Section name must be less than 100 characters")
  private String sectionName;

  @Column(name = "section_order")
  @Min(value = 1, message = "Section order must be at least 1")
  private Integer sectionOrder = 1;

  @Column(name = "display_order", nullable = false)
  @Min(value = 1, message = "Display order must be at least 1")
  @NotNull(message = "Display order is required")
  private Integer displayOrder;

  @Column(name = "column_span")
  @Min(value = 1, message = "Column span must be at least 1")
  private Integer columnSpan = 1;

  @Column(name = "row_span")
  @Min(value = 1, message = "Row span must be at least 1")
  private Integer rowSpan = 1;

  @Column(name = "width_percentage")
  @Min(value = 1, message = "Width percentage must be between 1 and 100")
  private Integer widthPercentage = 100;

  // === CONFIGURAÇÕES DE COMPORTAMENTO ===
  @Column(name = "is_required", nullable = false)
  private Boolean isRequired = false;

  @Column(name = "is_readonly", nullable = false)
  private Boolean isReadonly = false;

  @Column(name = "is_hidden", nullable = false)
  private Boolean isHidden = false;

  @Column(name = "is_disabled", nullable = false)
  private Boolean isDisabled = false;

  @Column(name = "allow_multiple_values", nullable = false)
  private Boolean allowMultipleValues = false;

  @Column(name = "is_searchable", nullable = false)
  private Boolean isSearchable = true;

  @Column(name = "is_exportable", nullable = false)
  private Boolean isExportable = true;

  // === VALORES E OPÇÕES ===
  @Column(name = "default_value", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Default value must be less than 2000 characters")
  private String defaultValue;

  @Column(name = "placeholder_text", length = 200)
  @Size(max = 200, message = "Placeholder text must be less than 200 characters")
  private String placeholderText;

  @Column(name = "help_text", columnDefinition = "TEXT")
  @Size(max = 1000, message = "Help text must be less than 1000 characters")
  private String helpText;

  @Column(name = "options_list", columnDefinition = "TEXT")
  @Size(max = 5000, message = "Options list must be less than 5000 characters")
  private String optionsList; // JSON array para SELECT/MULTISELECT

  @Column(name = "allowed_values", columnDefinition = "TEXT")
  @Size(max = 3000, message = "Allowed values must be less than 3000 characters")
  private String allowedValues; // Valores permitidos separados por vírgula

  // === VALIDAÇÕES ===
  @Column(name = "validation_rules", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Validation rules must be less than 2000 characters")
  private String validationRules; // JSON com regras de validação

  @Column(name = "min_length")
  @Min(value = 0, message = "Min length cannot be negative")
  private Integer minLength;

  @Column(name = "max_length")
  @Min(value = 1, message = "Max length must be at least 1")
  private Integer maxLength;

  @Column(name = "min_value")
  private Double minValue;

  @Column(name = "max_value")
  private Double maxValue;

  @Column(name = "regex_pattern", length = 500)
  @Size(max = 500, message = "Regex pattern must be less than 500 characters")
  private String regexPattern;

  @Column(name = "error_message", length = 300)
  @Size(max = 300, message = "Error message must be less than 300 characters")
  private String errorMessage;

  // === CONFIGURAÇÕES ESPECÍFICAS POR TIPO ===

  // Para campos de ESCALA (SCALE)
  @Column(name = "scale_min")
  private Integer scaleMin = 1;

  @Column(name = "scale_max")
  private Integer scaleMax = 10;

  @Column(name = "scale_step")
  private Integer scaleStep = 1;

  @Column(name = "scale_labels", columnDefinition = "TEXT")
  @Size(max = 1000, message = "Scale labels must be less than 1000 characters")
  private String scaleLabels; // JSON com labels para cada valor da escala

  // Para campos de DATA (DATE)
  @Column(name = "date_format", length = 20)
  @Size(max = 20, message = "Date format must be less than 20 characters")
  private String dateFormat = "yyyy-MM-dd";

  @Column(name = "allow_future_dates", nullable = false)
  private Boolean allowFutureDates = true;

  @Column(name = "allow_past_dates", nullable = false)
  private Boolean allowPastDates = true;

  // Para campos de ARQUIVO (FILE)
  @Column(name = "allowed_file_types", length = 200)
  @Size(max = 200, message = "Allowed file types must be less than 200 characters")
  private String allowedFileTypes; // .pdf,.doc,.docx

  @Column(name = "max_file_size") // em KB
  @Min(value = 1, message = "Max file size must be at least 1 KB")
  private Integer maxFileSize = 5120; // 5MB

  // === CONFIGURAÇÕES DE DEPENDÊNCIA ===
  @Column(name = "depends_on_field_id")
  private Long dependsOnFieldId; // ID do campo que controla este campo

  @Column(name = "dependency_condition", columnDefinition = "TEXT")
  @Size(max = 1000, message = "Dependency condition must be less than 1000 characters")
  private String dependencyCondition; // JSON com condições para mostrar/ocultar

  @Column(name = "conditional_logic", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Conditional logic must be less than 2000 characters")
  private String conditionalLogic; // Lógica complexa de exibição

  // === CONFIGURAÇÕES DE QUALIDADE ===
  @Column(name = "importance_level")
  @Min(value = 1, message = "Importance level must be between 1 and 5")
  private Integer importanceLevel = 3; // 1-5 (1=baixa, 5=crítica)

  @Column(name = "quality_weight")
  @Min(value = 0, message = "Quality weight cannot be negative")
  private Double qualityWeight = 1.0; // Peso para cálculo de qualidade

  @Column(name = "extraction_difficulty")
  @Min(value = 1, message = "Extraction difficulty must be between 1 and 5")
  private Integer extractionDifficulty = 3; // 1-5

  @Column(name = "requires_consensus", nullable = false)
  private Boolean requiresConsensus = false;

  @Column(name = "auto_extract_enabled", nullable = false)
  private Boolean autoExtractEnabled = false; // Para IA/ML

  @Column(name = "extraction_hints", columnDefinition = "TEXT")
  @Size(max = 1500, message = "Extraction hints must be less than 1500 characters")
  private String extractionHints;

  // === ESTATÍSTICAS E MÉTRICAS ===
  @Column(name = "times_filled")
  @Min(value = 0, message = "Times filled cannot be negative")
  private Integer timesFilled = 0;

  @Column(name = "completion_rate") // porcentagem
  private Double completionRate = 0.0;

  @Column(name = "average_fill_time") // segundos
  private Double averageFillTime = 0.0;

  @Column(name = "validation_error_count")
  @Min(value = 0, message = "Validation error count cannot be negative")
  private Integer validationErrorCount = 0;

  @Column(name = "conflict_rate") // porcentagem de conflitos entre revisores
  private Double conflictRate = 0.0;

  // === OBSERVAÇÕES ===
  @Column(name = "field_notes", columnDefinition = "TEXT")
  @Size(max = 1000, message = "Field notes must be less than 1000 characters")
  private String fieldNotes;

  @Column(name = "usage_feedback", columnDefinition = "TEXT")
  @Size(max = 1000, message = "Usage feedback must be less than 1000 characters")
  private String usageFeedback;

  // === RELACIONAMENTOS ===
  @OneToMany(mappedBy = "dependsOnFieldId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ExtractionField> dependentFields = new ArrayList<>();

  // Construtor customizado
  public ExtractionField(ExtractionForm extractionForm, String fieldName, String fieldLabel,
      String fieldType) {
    this.extractionForm = extractionForm;
    this.fieldName = fieldName;
    this.fieldLabel = fieldLabel;
    this.fieldType = fieldType;
    this.dataType = determineDataType(fieldType);
    this.isRequired = false;
    this.isReadonly = false;
    this.isHidden = false;
    this.isDisabled = false;
    this.allowMultipleValues = false;
    this.isSearchable = true;
    this.isExportable = true;
    this.sectionOrder = 1;
    this.columnSpan = 1;
    this.rowSpan = 1;
    this.widthPercentage = 100;
    this.importanceLevel = 3;
    this.qualityWeight = 1.0;
    this.extractionDifficulty = 3;
    this.requiresConsensus = false;
    this.autoExtractEnabled = false;
    this.scaleMin = 1;
    this.scaleMax = 10;
    this.scaleStep = 1;
    this.dateFormat = "yyyy-MM-dd";
    this.allowFutureDates = true;
    this.allowPastDates = true;
    this.maxFileSize = 5120;
  }

  // === MÉTODOS DE NEGÓCIO ===

  // Determina o tipo de dados baseado no tipo de campo
  private String determineDataType(String fieldType) {
    return switch (fieldType.toUpperCase()) {
      case "NUMBER", "SCALE" -> "DECIMAL";
      case "DATE" -> "DATE";
      case "BOOLEAN" -> "BOOLEAN";
      case "MULTISELECT", "MATRIX" -> "JSON";
      default -> "STRING";
    };
  }

  // Valida se o campo está configurado corretamente
  public boolean isValid() {
    return hasValidName() && hasValidType() && hasValidValidations() && hasValidDependencies();
  }

  // Verifica se tem condições de dependência
  public boolean hasConditions() {
    return dependsOnFieldId != null
        || (conditionalLogic != null && !conditionalLogic.trim().isEmpty());
  }

  // Verifica se é um campo obrigatório
  public boolean isRequired() {
    return Boolean.TRUE.equals(isRequired);
  }

  // Atualiza estatísticas de uso
  public void recordUsage(boolean completed, Integer fillTimeSeconds, boolean hasValidationError,
      boolean hasConflict) {
    this.timesFilled = (this.timesFilled != null ? this.timesFilled : 0) + 1;

    if (completed) {
      updateCompletionRate();
    }

    if (fillTimeSeconds != null && fillTimeSeconds > 0) {
      updateAverageFillTime(fillTimeSeconds);
    }

    if (hasValidationError) {
      this.validationErrorCount =
          (this.validationErrorCount != null ? this.validationErrorCount : 0) + 1;
    }

    if (hasConflict) {
      // ✅ DEPOIS - Extraído para método independente:
      updateConflictRate();
    }
  }

  // === MÉTODOS AUXILIARES PARA CÁLCULOS ===

  /**
   * Atualiza a taxa de conclusão baseada no número total de usos
   */
  private void updateCompletionRate() {
    if (timesFilled == null || timesFilled == 0) {
      this.completionRate = 100.0;
      return;
    }

    double currentRate = (this.completionRate != null) ? this.completionRate : 0.0;
    double currentCompletionValue = currentRate * (timesFilled - 1) / 100.0;
    double newCompletionValue = currentCompletionValue + 1;

    this.completionRate = (newCompletionValue * 100.0) / timesFilled;
  }

  /**
   * Atualiza o tempo médio de preenchimento
   */
  private void updateAverageFillTime(Integer fillTimeSeconds) {
    if (timesFilled == null || timesFilled == 0) {
      this.averageFillTime = fillTimeSeconds.doubleValue();
      return;
    }

    double currentAverage = (this.averageFillTime != null) ? this.averageFillTime : 0.0;
    double totalPreviousTime = currentAverage * (timesFilled - 1);

    this.averageFillTime = (totalPreviousTime + fillTimeSeconds) / timesFilled;
  }

  /**
   * Atualiza a taxa de conflitos baseada no número total de usos
   */
  private void updateConflictRate() {
    if (timesFilled == null || timesFilled == 0) {
      this.conflictRate = 100.0;
      return;
    }

    double currentRate = (this.conflictRate != null) ? this.conflictRate : 0.0;
    double currentConflictValue = currentRate * (timesFilled - 1) / 100.0;
    double newConflictValue = currentConflictValue + 1;

    this.conflictRate = (newConflictValue * 100.0) / timesFilled;
  }

  // Clona o campo para nova versão do formulário
  public ExtractionField cloneForNewForm(ExtractionForm newForm) {
    ExtractionField cloned =
        new ExtractionField(newForm, this.fieldName, this.fieldLabel, this.fieldType);

    // Copiar todas as configurações (exceto IDs e estatísticas)
    cloned.setFieldDescription(this.fieldDescription);
    cloned.setDataType(this.dataType);
    cloned.setSectionName(this.sectionName);
    cloned.setSectionOrder(this.sectionOrder);
    cloned.setDisplayOrder(this.displayOrder);
    cloned.setColumnSpan(this.columnSpan);
    cloned.setRowSpan(this.rowSpan);
    cloned.setWidthPercentage(this.widthPercentage);
    cloned.setIsRequired(this.isRequired);
    cloned.setIsReadonly(this.isReadonly);
    cloned.setIsHidden(this.isHidden);
    cloned.setIsDisabled(this.isDisabled);
    cloned.setAllowMultipleValues(this.allowMultipleValues);
    cloned.setIsSearchable(this.isSearchable);
    cloned.setIsExportable(this.isExportable);
    cloned.setDefaultValue(this.defaultValue);
    cloned.setPlaceholderText(this.placeholderText);
    cloned.setHelpText(this.helpText);
    cloned.setOptionsList(this.optionsList);
    cloned.setAllowedValues(this.allowedValues);
    cloned.setValidationRules(this.validationRules);
    cloned.setMinLength(this.minLength);
    cloned.setMaxLength(this.maxLength);
    cloned.setMinValue(this.minValue);
    cloned.setMaxValue(this.maxValue);
    cloned.setRegexPattern(this.regexPattern);
    cloned.setErrorMessage(this.errorMessage);
    cloned.setImportanceLevel(this.importanceLevel);
    cloned.setQualityWeight(this.qualityWeight);
    cloned.setExtractionDifficulty(this.extractionDifficulty);
    cloned.setRequiresConsensus(this.requiresConsensus);
    cloned.setAutoExtractEnabled(this.autoExtractEnabled);
    cloned.setExtractionHints(this.extractionHints);
    cloned.setFieldNotes(this.fieldNotes);

    return cloned;
  }

  // === VALIDAÇÕES ESPECÍFICAS ===

  private boolean hasValidName() {
    return fieldName != null && !fieldName.trim().isEmpty()
        && fieldName.matches("^[a-zA-Z][a-zA-Z0-9_]*$"); // Nome válido para campo
  }

  private boolean hasValidType() {
    List<String> validTypes = List.of("TEXT", "NUMBER", "DATE", "BOOLEAN", "SELECT", "MULTISELECT",
        "TEXTAREA", "URL", "EMAIL", "SCALE", "MATRIX", "FILE");
    return fieldType != null && validTypes.contains(fieldType.toUpperCase());
  }

  private boolean hasValidValidations() {
    // Validar consistência das validações
    if (minLength != null && maxLength != null && minLength > maxLength)
      return false;
    if (minValue != null && maxValue != null && minValue > maxValue)
      return false;
    if (scaleMin != null && scaleMax != null && scaleMin >= scaleMax)
      return false;
    return true;
  }

  private boolean hasValidDependencies() {
    // Não pode depender de si mesmo
    return dependsOnFieldId == null || !dependsOnFieldId.equals(this.getId());
  }

  // === CÁLCULOS E MÉTRICAS ===

  // Complexidade do campo (1-10)
  public Integer getFieldComplexity() {
    int complexity = 1;

    // Baseado no tipo
    complexity += switch (fieldType.toUpperCase()) {
      case "TEXT", "BOOLEAN" -> 0;
      case "NUMBER", "DATE", "EMAIL", "URL" -> 1;
      case "SELECT", "TEXTAREA" -> 2;
      case "MULTISELECT", "SCALE" -> 3;
      case "MATRIX", "FILE" -> 4;
      default -> 2;
    };

    // Validações adicionais
    if (validationRules != null && !validationRules.trim().isEmpty())
      complexity += 1;
    if (regexPattern != null && !regexPattern.trim().isEmpty())
      complexity += 2;
    if (hasConditions())
      complexity += 2;
    if (Boolean.TRUE.equals(requiresConsensus))
      complexity += 1;

    return Math.min(10, complexity);
  }

  // Taxa de erro de validação
  public Double getValidationErrorRate() {
    if (timesFilled == null || timesFilled == 0)
      return 0.0;
    if (validationErrorCount == null)
      return 0.0;
    return (validationErrorCount * 100.0) / timesFilled;
  }

  // Dificuldade de extração relativa
  public String getExtractionDifficultyLevel() {
    if (extractionDifficulty == null)
      return "MEDIUM";
    return switch (extractionDifficulty) {
      case 1 -> "VERY_EASY";
      case 2 -> "EASY";
      case 3 -> "MEDIUM";
      case 4 -> "HARD";
      case 5 -> "VERY_HARD";
      default -> "MEDIUM";
    };
  }

  // Nível de importância
  public String getImportanceLevelText() {
    if (importanceLevel == null)
      return "MEDIUM";
    return switch (importanceLevel) {
      case 1 -> "VERY_LOW";
      case 2 -> "LOW";
      case 3 -> "MEDIUM";
      case 4 -> "HIGH";
      case 5 -> "CRITICAL";
      default -> "MEDIUM";
    };
  }

  // === MÉTODOS DE CONSULTA ===

  public boolean isTextType() {
    return "TEXT".equalsIgnoreCase(fieldType) || "TEXTAREA".equalsIgnoreCase(fieldType);
  }

  public boolean isNumberType() {
    return "NUMBER".equalsIgnoreCase(fieldType) || "SCALE".equalsIgnoreCase(fieldType);
  }

  public boolean isSelectType() {
    return "SELECT".equalsIgnoreCase(fieldType) || "MULTISELECT".equalsIgnoreCase(fieldType);
  }

  public boolean isDateType() {
    return "DATE".equalsIgnoreCase(fieldType);
  }

  public boolean isBooleanType() {
    return "BOOLEAN".equalsIgnoreCase(fieldType);
  }

  public boolean isFileType() {
    return "FILE".equalsIgnoreCase(fieldType);
  }

  public boolean hasOptions() {
    return isSelectType() && optionsList != null && !optionsList.trim().isEmpty();
  }

  public boolean hasDefaultValue() {
    return defaultValue != null && !defaultValue.trim().isEmpty();
  }

  public boolean hasValidationRules() {
    return validationRules != null && !validationRules.trim().isEmpty();
  }

  public boolean isConditional() {
    return hasConditions();
  }

  public boolean isCritical() {
    return importanceLevel != null && importanceLevel >= 4;
  }

  public boolean isHighQuality() {
    return qualityWeight != null && qualityWeight >= 2.0;
  }

  // === MÉTODOS DE CONVENIÊNCIA ===

  public String getExtractionFormTitle() {
    return extractionForm != null ? extractionForm.getTitle() : "Unknown";
  }

  public String getFullFieldName() {
    return (sectionName != null ? sectionName + "." : "") + fieldName;
  }

  public String getDisplayLabel() {
    return fieldLabel + (Boolean.TRUE.equals(isRequired) ? " *" : "");
  }

  public Integer getTotalDependentFields() {
    return dependentFields != null ? dependentFields.size() : 0;
  }

  // === MÉTODOS DE ATUALIZAÇÃO ===

  public void incrementTimesFilled() {
    this.timesFilled = (this.timesFilled != null ? this.timesFilled : 0) + 1;
  }

  public void incrementValidationErrors() {
    this.validationErrorCount =
        (this.validationErrorCount != null ? this.validationErrorCount : 0) + 1;
  }

  public void updateDisplayOrder(Integer newOrder) {
    this.displayOrder = newOrder;
  }

  public void toggleRequired() {
    this.isRequired = !Boolean.TRUE.equals(this.isRequired);
  }

  public void toggleHidden() {
    this.isHidden = !Boolean.TRUE.equals(this.isHidden);
  }

  public void addUsageFeedback(String feedback) {
    this.usageFeedback = (this.usageFeedback != null ? this.usageFeedback + "\n" : "") + feedback;
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
    return "ExtractionField{" + "id=" + getId() + ", formId="
        + (extractionForm != null ? extractionForm.getId() : null) + ", fieldName='" + fieldName
        + '\'' + ", fieldLabel='" + fieldLabel + '\'' + ", fieldType='" + fieldType + '\''
        + ", dataType='" + dataType + '\'' + ", section='" + sectionName + '\'' + ", order="
        + displayOrder + ", required=" + isRequired + ", hidden=" + isHidden + ", readonly="
        + isReadonly + ", complexity=" + getFieldComplexity() + ", importance="
        + getImportanceLevelText() + ", difficulty=" + getExtractionDifficultyLevel()
        + ", timesFilled=" + timesFilled + ", completionRate="
        + String.format("%.1f", completionRate != null ? completionRate : 0.0) + "%"
        + ", conflictRate=" + String.format("%.1f", conflictRate != null ? conflictRate : 0.0) + "%"
        + ", validationErrors=" + validationErrorCount + ", hasConditions=" + hasConditions()
        + ", dependentFields=" + getTotalDependentFields() + ", isDeleted=" + getIsDeleted() + '}';
  }
}
