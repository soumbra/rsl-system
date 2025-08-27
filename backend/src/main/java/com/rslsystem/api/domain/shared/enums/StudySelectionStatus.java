package com.rslsystem.api.domain.shared.enums;

/**
 * Status da fase de seleção de estudos na revisão sistemática. Usado em StudySelection para
 * controlar o progresso da fase.
 */
public enum StudySelectionStatus {

  /**
   * Seleção ainda não foi iniciada
   */
  NOT_STARTED("Não Iniciado", "Study selection has not started yet"),

  /**
   * Seleção em progresso - revisores avaliando estudos
   */
  IN_PROGRESS("Em Progresso", "Study selection is in progress"),

  /**
   * Seleção concluída - todos estudos avaliados e consensos resolvidos
   */
  COMPLETED("Concluído", "Study selection has been completed");

  private final String displayName;
  private final String description;

  StudySelectionStatus(String displayName, String description) {
    this.displayName = displayName;
    this.description = description;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getDescription() {
    return description;
  }

  // Métodos utilitários para controle de fluxo
  public boolean isActive() {
    return this == IN_PROGRESS;
  }

  public boolean isFinished() {
    return this == COMPLETED;
  }

  public boolean notStarted() {
    return this == NOT_STARTED;
  }

  public boolean canStart() {
    return this == NOT_STARTED;
  }

  public boolean canComplete() {
    return this == IN_PROGRESS;
  }

  // Progressão do status
  public StudySelectionStatus nextStatus() {
    switch (this) {
      case NOT_STARTED:
        return IN_PROGRESS;
      case IN_PROGRESS:
        return COMPLETED;
      case COMPLETED:
        return COMPLETED; // Já concluído
      default:
        return this;
    }
  }

  // Verificar se pode avançar para próximo status
  public boolean canAdvanceTo(StudySelectionStatus targetStatus) {
    switch (this) {
      case NOT_STARTED:
        return targetStatus == IN_PROGRESS;
      case IN_PROGRESS:
        return targetStatus == COMPLETED;
      case COMPLETED:
        return false; // Não pode voltar
      default:
        return false;
    }
  }

  // Calcular porcentagem de progresso (útil para UI)
  public int getProgressPercentage() {
    switch (this) {
      case NOT_STARTED:
        return 0;
      case IN_PROGRESS:
        return 50; // Pode ser calculado dinamicamente baseado nas avaliações
      case COMPLETED:
        return 100;
      default:
        return 0;
    }
  }

  @Override
  public String toString() {
    return displayName;
  }
}
