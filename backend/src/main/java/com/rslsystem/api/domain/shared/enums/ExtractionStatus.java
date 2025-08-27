package com.rslsystem.api.domain.shared.enums;

/**
 * Status do processo de extração de dados de um estudo. Usado em DataExtraction para controlar o
 * fluxo de trabalho.
 */
public enum ExtractionStatus {

  /**
   * Extração ainda não foi iniciada pelo revisor
   */
  PENDING("Pendente", "Data extraction is pending"),

  /**
   * Dados foram extraídos pelo revisor
   */
  EXTRACTED("Extraído", "Data has been extracted"),

  /**
   * Extração foi revisada/validada
   */
  REVIEWED("Revisado", "Data extraction has been reviewed and validated");

  private final String displayName;
  private final String description;

  ExtractionStatus(String displayName, String description) {
    this.displayName = displayName;
    this.description = description;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getDescription() {
    return description;
  }

  // Métodos utilitários para fluxo de trabalho
  public boolean isCompleted() {
    return this == EXTRACTED || this == REVIEWED;
  }

  public boolean isPending() {
    return this == PENDING;
  }

  public boolean isValidated() {
    return this == REVIEWED;
  }

  public boolean canBeReviewed() {
    return this == EXTRACTED;
  }

  // Progressão do status
  public ExtractionStatus nextStatus() {
    switch (this) {
      case PENDING:
        return EXTRACTED;
      case EXTRACTED:
        return REVIEWED;
      case REVIEWED:
        return REVIEWED; // Já está no status final
      default:
        return this;
    }
  }

  // Verificar se pode avançar para próximo status
  public boolean canAdvanceTo(ExtractionStatus targetStatus) {
    switch (this) {
      case PENDING:
        return targetStatus == EXTRACTED;
      case EXTRACTED:
        return targetStatus == REVIEWED;
      case REVIEWED:
        return false; // Não pode avançar mais
      default:
        return false;
    }
  }

  @Override
  public String toString() {
    return displayName;
  }
}
