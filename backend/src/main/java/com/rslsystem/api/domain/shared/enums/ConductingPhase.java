package com.rslsystem.api.domain.shared.enums;

/**
 * Fases da condução de uma RSL - sequência ordenada de execução.
 */
public enum ConductingPhase {

  /**
   * Fase 1: Preparação da busca - configuração de strings, bases, estratégias
   */
  SEARCH_PREPARATION("Preparação da Busca", "Setting up search strings, databases, and strategies",
      1),

  /**
   * Fase 2: Execução da busca - busca nas bases, importação, deduplicação
   */
  SEARCH_EXECUTION("Execução da Busca", "Searching databases, importing, and deduplication", 2),

  /**
   * Fase 3: Seleção de estudos - screening título/resumo e texto completo
   */
  STUDY_SELECTION("Seleção de Estudos", "Title/abstract and full-text screening", 3),

  /**
   * Fase 4: Avaliação de qualidade - aplicação de critérios de qualidade
   */
  QUALITY_ASSESSMENT("Avaliação de Qualidade", "Applying quality assessment criteria", 4),

  /**
   * Fase 5: Extração de dados - extração sistemática de dados dos estudos
   */
  DATA_EXTRACTION("Extração de Dados", "Systematic data extraction from studies", 5),

  /**
   * Condução concluída
   */
  COMPLETED("Concluído", "Review conducting has been completed", 6);

  private final String displayName;
  private final String description;
  private final int order;

  ConductingPhase(String displayName, String description, int order) {
    this.displayName = displayName;
    this.description = description;
    this.order = order;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getDescription() {
    return description;
  }

  public int getOrder() {
    return order;
  }

  // === MÉTODOS DE NAVEGAÇÃO ===

  public ConductingPhase getNext() {
    return switch (this) {
      case SEARCH_PREPARATION -> SEARCH_EXECUTION;
      case SEARCH_EXECUTION -> STUDY_SELECTION;
      case STUDY_SELECTION -> QUALITY_ASSESSMENT;
      case QUALITY_ASSESSMENT -> DATA_EXTRACTION;
      case DATA_EXTRACTION -> COMPLETED;
      case COMPLETED -> COMPLETED; // Não avança mais
    };
  }

  public ConductingPhase getPrevious() {
    return switch (this) {
      case SEARCH_EXECUTION -> SEARCH_PREPARATION;
      case STUDY_SELECTION -> SEARCH_EXECUTION;
      case QUALITY_ASSESSMENT -> STUDY_SELECTION;
      case DATA_EXTRACTION -> QUALITY_ASSESSMENT;
      case COMPLETED -> DATA_EXTRACTION;
      case SEARCH_PREPARATION -> SEARCH_PREPARATION; // Não retrocede mais
    };
  }

  public boolean canAdvanceTo(ConductingPhase nextPhase) {
    return nextPhase == this.getNext();
  }

  public boolean isAfter(ConductingPhase other) {
    return this.order > other.order;
  }

  public boolean isBefore(ConductingPhase other) {
    return this.order < other.order;
  }

  public boolean isFirst() {
    return this == SEARCH_PREPARATION;
  }

  public boolean isLast() {
    return this == COMPLETED;
  }

  // === CÁLCULO DE PESO ===

  /**
   * Peso da fase para cálculo de progresso geral (0-100%)
   */
  public int getProgressWeight() {
    return switch (this) {
      case SEARCH_PREPARATION -> 0; // 0%
      case SEARCH_EXECUTION -> 20; // 20%
      case STUDY_SELECTION -> 40; // 40%
      case QUALITY_ASSESSMENT -> 60; // 60%
      case DATA_EXTRACTION -> 80; // 80%
      case COMPLETED -> 100; // 100%
    };
  }

  // === CONVERSÃO DE STRING (compatibilidade) ===

  public static ConductingPhase fromString(String phase) {
    if (phase == null)
      return SEARCH_PREPARATION;

    return switch (phase.toUpperCase()) {
      case "SEARCH_PREPARATION" -> SEARCH_PREPARATION;
      case "SEARCH_EXECUTION" -> SEARCH_EXECUTION;
      case "STUDY_SELECTION" -> STUDY_SELECTION;
      case "QUALITY_ASSESSMENT" -> QUALITY_ASSESSMENT;
      case "DATA_EXTRACTION" -> DATA_EXTRACTION;
      case "COMPLETED" -> COMPLETED;
      default -> SEARCH_PREPARATION;
    };
  }

  @Override
  public String toString() {
    return name(); // Retorna o nome do enum para compatibilidade
  }

  public String toDisplayString() {
    return displayName;
  }
}
