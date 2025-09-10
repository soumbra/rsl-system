package com.rslsystem.api.domain.shared.enums;

/**
 * Status do protocolo de uma RSL - controla o workflow de aprovação científica.
 */
public enum ProtocolStatus {

  /**
   * Protocolo em rascunho - ainda sendo elaborado
   */
  DRAFT("Rascunho", "Protocol is in draft state"),

  /**
   * Protocolo submetido para revisão
   */
  UNDER_REVIEW("Em Revisão", "Protocol is under review"),

  /**
   * Protocolo aprovado para execução
   */
  APPROVED("Aprovado", "Protocol has been approved"),

  /**
   * Protocolo rejeitado - precisa revisão
   */
  REJECTED("Rejeitado", "Protocol has been rejected"),

  /**
   * Protocolo publicado (registrado oficialmente)
   */
  PUBLISHED("Publicado", "Protocol has been published/registered");

  private final String displayName;
  private final String description;

  ProtocolStatus(String displayName, String description) {
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
  public boolean canSubmit() {
    return this == DRAFT;
  }

  public boolean canApprove() {
    return this == UNDER_REVIEW;
  }

  public boolean canReject() {
    return this == UNDER_REVIEW;
  }

  public boolean canRevise() {
    return this == REJECTED;
  }

  public boolean canPublish() {
    return this == APPROVED;
  }

  public boolean isEditable() {
    return this == DRAFT || this == REJECTED;
  }

  public boolean canStartConducting() {
    return this == APPROVED || this == PUBLISHED;
  }

  @Override
  public String toString() {
    return displayName;
  }
}
