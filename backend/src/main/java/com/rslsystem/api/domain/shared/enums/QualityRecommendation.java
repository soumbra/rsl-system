package com.rslsystem.api.domain.shared.enums;

/**
 * Recomendação do revisor sobre a qualidade de um estudo. Usado em ReviewerQualityAssessment
 * baseado no score de qualidade calculado.
 */
public enum QualityRecommendation {

  /**
   * Aceitar o estudo - qualidade suficiente
   */
  ACCEPT("Aceitar", "Study meets quality standards and should be accepted"),

  /**
   * Rejeitar o estudo - qualidade insuficiente
   */
  REJECT("Rejeitar", "Study does not meet quality standards and should be rejected"),

  /**
   * Aceitar condicionalmente - qualidade limítrofe, precisa análise
   */
  CONDITIONAL_ACCEPT("Aceitar Condicionalmente",
      "Study has borderline quality and needs further analysis");

  private final String displayName;
  private final String description;

  QualityRecommendation(String displayName, String description) {
    this.displayName = displayName;
    this.description = description;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getDescription() {
    return description;
  }

  // Métodos utilitários para lógica de qualidade
  public boolean isPositive() {
    return this == ACCEPT;
  }

  public boolean isNegative() {
    return this == REJECT;
  }

  public boolean isConditional() {
    return this == CONDITIONAL_ACCEPT;
  }

  public boolean needsConsensus() {
    return this == CONDITIONAL_ACCEPT;
  }

  public boolean isDefinitive() {
    return this == ACCEPT || this == REJECT;
  }

  // Converter score em recomendação (baseado em thresholds)
  public static QualityRecommendation fromScore(float score, float acceptThreshold,
      float rejectThreshold) {
    if (score >= acceptThreshold) {
      return ACCEPT;
    } else if (score <= rejectThreshold) {
      return REJECT;
    } else {
      return CONDITIONAL_ACCEPT; // Score entre os thresholds
    }
  }

  // Converter para ConsensusDecision (para processos de consenso)
  public ConsensusDecision toConsensusDecision() {
    switch (this) {
      case ACCEPT:
        return ConsensusDecision.INCLUDED;
      case REJECT:
        return ConsensusDecision.EXCLUDED;
      case CONDITIONAL_ACCEPT:
        return ConsensusDecision.NEEDS_DISCUSSION;
      default:
        return ConsensusDecision.NEEDS_DISCUSSION;
    }
  }

  // Obter cor/estilo para UI (útil para frontend)
  public String getStyleClass() {
    switch (this) {
      case ACCEPT:
        return "success"; // Verde
      case REJECT:
        return "danger"; // Vermelho
      case CONDITIONAL_ACCEPT:
        return "warning"; // Amarelo
      default:
        return "secondary";
    }
  }

  // Prioridade para ordenação (maior prioridade = mais urgente)
  public int getPriority() {
    switch (this) {
      case CONDITIONAL_ACCEPT:
        return 3; // Mais urgente - precisa decisão
      case REJECT:
        return 2;
      case ACCEPT:
        return 1; // Menos urgente - já decidido
      default:
        return 0;
    }
  }

  @Override
  public String toString() {
    return displayName;
  }
}
