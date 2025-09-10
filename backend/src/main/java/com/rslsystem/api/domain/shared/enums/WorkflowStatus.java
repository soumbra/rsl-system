package com.rslsystem.api.domain.shared.enums;

/**
 * Status genérico para workflows de RSL (planejamento, condução, extração, etc). Substitui:
 * PlanningStatus, ConductingStatus, QualityAssessmentStatus, ExtractionStatus,
 * StudySelectionStatus.
 */
public enum WorkflowStatus {

  // Estados iniciais
  NOT_STARTED("Não Iniciado", "Workflow has not started yet"), DRAFT("Rascunho",
      "Workflow is being prepared or configured"),

  // Estados ativos
  IN_PROGRESS("Em Progresso", "Workflow is actively running"), ACTIVE("Ativo",
      "Workflow is configured and being used"),

  // Estados de pausa/interrupção
  PAUSED("Pausado", "Workflow has been paused temporarily"),

  // Estados finais
  COMPLETED("Concluído", "Workflow has been completed successfully"), VALIDATED("Validado",
      "Workflow has been validated or approved"), ARCHIVED("Arquivado",
          "Workflow has been archived"),

  // Estados de aprovação (para planning)
  APPROVED("Aprovado", "Workflow has been approved for execution"), REJECTED("Rejeitado",
      "Workflow has been rejected and needs revision");

  private final String displayName;
  private final String description;

  WorkflowStatus(String displayName, String description) {
    this.displayName = displayName;
    this.description = description;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getDescription() {
    return description;
  }

  // === MÉTODOS DE WORKFLOW GENÉRICO ===

  public boolean canStart() {
    return this == NOT_STARTED || this == DRAFT;
  }

  public boolean canPause() {
    return this == IN_PROGRESS || this == ACTIVE;
  }

  public boolean canResume() {
    return this == PAUSED;
  }

  public boolean canComplete() {
    return this == IN_PROGRESS || this == ACTIVE || this == PAUSED;
  }

  public boolean canValidate() {
    return this == COMPLETED;
  }

  public boolean canArchive() {
    return this == COMPLETED || this == VALIDATED;
  }

  public boolean canApprove() {
    return this == COMPLETED;
  }

  public boolean canReject() {
    return this == COMPLETED;
  }

  // === ESTADOS LÓGICOS ===

  public boolean isActive() {
    return this == IN_PROGRESS || this == ACTIVE;
  }

  public boolean isEditable() {
    return this == NOT_STARTED || this == DRAFT || this == PAUSED || this == REJECTED;
  }

  public boolean isFinished() {
    return this == COMPLETED || this == VALIDATED || this == ARCHIVED || this == APPROVED;
  }

  public boolean isPending() {
    return this == NOT_STARTED || this == DRAFT;
  }

  // === MAPEAMENTOS PARA COMPATIBILIDADE ===

  // Para PlanningStatus
  public static WorkflowStatus fromPlanningStatus(String status) {
    return switch (status.toUpperCase()) {
      case "DRAFT" -> DRAFT;
      case "IN_PROGRESS" -> IN_PROGRESS;
      case "COMPLETED" -> COMPLETED;
      case "APPROVED" -> APPROVED;
      case "REJECTED" -> REJECTED;
      default -> DRAFT;
    };
  }

  // Para ConductingStatus
  public static WorkflowStatus fromConductingStatus(String status) {
    return switch (status.toUpperCase()) {
      case "NOT_STARTED" -> NOT_STARTED;
      case "IN_PROGRESS" -> IN_PROGRESS;
      case "PAUSED" -> PAUSED;
      case "COMPLETED" -> COMPLETED;
      default -> NOT_STARTED;
    };
  }

  // Para QualityAssessmentStatus
  public static WorkflowStatus fromQualityStatus(String status) {
    return switch (status.toUpperCase()) {
      case "DRAFT" -> DRAFT;
      case "ACTIVE" -> ACTIVE;
      case "PAUSED" -> PAUSED;
      case "COMPLETED" -> COMPLETED;
      case "ARCHIVED" -> ARCHIVED;
      default -> DRAFT;
    };
  }

  // Para ExtractionStatus
  public static WorkflowStatus fromExtractionStatus(String status) {
    return switch (status.toUpperCase()) {
      case "PENDING" -> NOT_STARTED;
      case "EXTRACTED" -> IN_PROGRESS;
      case "REVIEWED" -> VALIDATED;
      default -> NOT_STARTED;
    };
  }

  @Override
  public String toString() {
    return displayName;
  }
}
