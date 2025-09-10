package com.rslsystem.api.domain.shared.enums;

/**
 * Status do planejamento de uma RSL - controla o workflow da primeira fase. Diferente de
 * StudyStatus que é para avaliação individual de artigos.
 */
public enum PlanningStatus {

  /**
   * Planejamento em rascunho - ainda sendo elaborado
   */
  DRAFT("Rascunho", "Planning is in draft state"),

  /**
   * Planejamento em desenvolvimento ativo
   */
  IN_PROGRESS("Em Progresso", "Planning is being actively developed"),

  /**
   * Planejamento concluído pelo pesquisador
   */
  COMPLETED("Concluído", "Planning has been completed"),

  /**
   * Planejamento aprovado - pode iniciar condução
   */
  APPROVED("Aprovado", "Planning has been approved for execution"),

  /**
   * Planejamento rejeitado - precisa revisão
   */
  REJECTED("Rejeitado", "Planning has been rejected and needs revision");

  private final String displayName;
  private final String description;

  PlanningStatus(String displayName, String description) {
    this.displayName = displayName;
    this.description = description;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getDescription() {
    return description;
  }

  // Métodos de workflow
  public boolean canProgressTo(PlanningStatus targetStatus) {
    switch (this) {
      case DRAFT:
        return targetStatus == IN_PROGRESS;
      case IN_PROGRESS:
        return targetStatus == COMPLETED;
      case COMPLETED:
        return targetStatus == APPROVED || targetStatus == REJECTED;
      case REJECTED:
        return targetStatus == IN_PROGRESS;
      case APPROVED:
        return false; // Final state
      default:
        return false;
    }
  }

  public boolean isEditable() {
    return this == DRAFT || this == IN_PROGRESS || this == REJECTED;
  }

  public boolean canStartConducting() {
    return this == APPROVED;
  }

  @Override
  public String toString() {
    return displayName;
  }
}
