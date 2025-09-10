package com.rslsystem.api.domain.shared.enums;

/**
 * Decisão de avaliação para estudos, qualidade e consenso. Substitui: StudyStatus,
 * ConsensusDecision, QualityRecommendation.
 */
public enum AssessmentDecision {

  /**
   * Não avaliado ainda
   */
  NOT_EVALUATED("Não Avaliado", "Item has not been evaluated yet"),

  /**
   * Incluído/Aceito
   */
  INCLUDED("Incluído", "Item is included/accepted"),

  /**
   * Excluído/Rejeitado
   */
  EXCLUDED("Excluído", "Item is excluded/rejected"),

  /**
   * Aceito condicionalmente (precisa revisão)
   */
  CONDITIONAL("Condicional", "Item is conditionally accepted"),

  /**
   * Incerto/Precisa discussão
   */
  UNCERTAIN("Incerto", "Item is uncertain and needs discussion"),

  /**
   * Precisa mais discussão (para consenso)
   */
  NEEDS_DISCUSSION("Precisa Discussão", "Item needs further discussion or analysis");

  private final String displayName;
  private final String description;

  AssessmentDecision(String displayName, String description) {
    this.displayName = displayName;
    this.description = description;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getDescription() {
    return description;
  }

  // === MÉTODOS LÓGICOS ===

  public boolean isPositive() {
    return this == INCLUDED;
  }

  public boolean isNegative() {
    return this == EXCLUDED;
  }

  public boolean isDecided() {
    return this == INCLUDED || this == EXCLUDED;
  }

  public boolean needsConsensus() {
    return this == UNCERTAIN || this == CONDITIONAL || this == NEEDS_DISCUSSION;
  }

  public boolean isPending() {
    return this == NOT_EVALUATED;
  }

  // === MAPEAMENTOS PARA COMPATIBILIDADE ===

  // Para StudyStatus
  public static AssessmentDecision fromStudyStatus(String status) {
    return switch (status.toUpperCase()) {
      case "INCLUDED" -> INCLUDED;
      case "EXCLUDED" -> EXCLUDED;
      case "UNCERTAIN" -> UNCERTAIN;
      case "NOT_EVALUATED" -> NOT_EVALUATED;
      default -> NOT_EVALUATED;
    };
  }

  // Para ConsensusDecision
  public static AssessmentDecision fromConsensusDecision(String decision) {
    return switch (decision.toUpperCase()) {
      case "INCLUDED" -> INCLUDED;
      case "EXCLUDED" -> EXCLUDED;
      case "NEEDS_DISCUSSION" -> NEEDS_DISCUSSION;
      default -> UNCERTAIN;
    };
  }

  @Override
  public String toString() {
    return displayName;
  }
}
