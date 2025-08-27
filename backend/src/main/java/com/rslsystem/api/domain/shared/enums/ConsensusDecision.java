package com.rslsystem.api.domain.shared.enums;

/**
 * Decisão final de consenso após resolução de conflitos entre revisores. Usado em StudyConsensus,
 * QualityConsensus e DataExtractionConsensus.
 */
public enum ConsensusDecision {

  /**
   * Consenso: Incluir o item na revisão
   */
  INCLUDED("Incluído", "Item is included after consensus"),

  /**
   * Consenso: Excluir o item da revisão
   */
  EXCLUDED("Excluído", "Item is excluded after consensus"),

  /**
   * Consenso: Precisa de mais discussão/análise
   */
  NEEDS_DISCUSSION("Precisa Discussão", "Item needs further discussion or analysis");

  private final String displayName;
  private final String description;

  ConsensusDecision(String displayName, String description) {
    this.displayName = displayName;
    this.description = description;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getDescription() {
    return description;
  }

  // Métodos utilitários para lógica de negócio
  public boolean isDefinitive() {
    return this == INCLUDED || this == EXCLUDED;
  }

  public boolean isPending() {
    return this == NEEDS_DISCUSSION;
  }

  public boolean isPositive() {
    return this == INCLUDED;
  }

  public boolean isNegative() {
    return this == EXCLUDED;
  }

  // Conversão de StudyStatus para ConsensusDecision
  public static ConsensusDecision fromStudyStatus(StudyStatus status) {
    switch (status) {
      case INCLUDED:
        return INCLUDED;
      case EXCLUDED:
        return EXCLUDED;
      case UNCERTAIN, NOT_EVALUATED:
        return NEEDS_DISCUSSION;
      default:
        return NEEDS_DISCUSSION;
    }
  }

  @Override
  public String toString() {
    return displayName;
  }
}
