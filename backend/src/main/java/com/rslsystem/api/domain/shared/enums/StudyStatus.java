package com.rslsystem.api.domain.shared.enums;

/**
 * Status de avaliação de um estudo por um revisor. Usado em ReviewerStudyAssessment para
 * implementar "Visões Isoladas".
 */
public enum StudyStatus {

  /**
   * Estudo ainda não foi avaliado pelo revisor
   */
  NOT_EVALUATED("Não Avaliado", "Study has not been evaluated yet"),

  /**
   * Estudo foi incluído na revisão sistemática
   */
  INCLUDED("Incluído", "Study is included in the systematic review"),

  /**
   * Estudo foi excluído da revisão sistemática
   */
  EXCLUDED("Excluído", "Study is excluded from the systematic review"),

  /**
   * Revisor tem dúvidas sobre incluir/excluir - precisa discussão
   */
  UNCERTAIN("Incerto", "Reviewer is uncertain about inclusion/exclusion");

  private final String displayName;
  private final String description;

  StudyStatus(String displayName, String description) {
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
  public boolean isDecided() {
    return this == INCLUDED || this == EXCLUDED;
  }

  public boolean needsConsensus() {
    return this == UNCERTAIN;
  }

  public boolean isPositive() {
    return this == INCLUDED;
  }

  public boolean isNegative() {
    return this == EXCLUDED;
  }

  // Para conversão de strings (útil em APIs/imports)
  public static StudyStatus fromString(String value) {
    if (value == null || value.trim().isEmpty()) {
      return NOT_EVALUATED;
    }

    String normalizedValue = value.trim().toUpperCase();

    try {
      return StudyStatus.valueOf(normalizedValue);
    } catch (IllegalArgumentException e) {
      switch (normalizedValue) {
        case "INCLUDE", "ACCEPT", "YES":
          return INCLUDED;
        case "EXCLUDE", "REJECT", "NO":
          return EXCLUDED;
        case "MAYBE", "DOUBT", "UNCLEAR":
          return UNCERTAIN;
        default:
          return NOT_EVALUATED;
      }
    }
  }

  @Override
  public String toString() {
    return displayName;
  }
}
