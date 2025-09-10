package com.rslsystem.api.domain.shared.enums;

/**
 * Status da configuração de avaliação de qualidade de uma RSL.
 */
public enum QualityAssessmentStatus {

    /**
     * Configuração em rascunho - ainda sendo definida
     */
    DRAFT("Rascunho", "Quality assessment configuration is in draft state"),

    /**
     * Configuração ativa - sendo usada nas avaliações
     */
    ACTIVE("Ativa", "Quality assessment is active and being used"),

    /**
     * Configuração pausada temporariamente
     */
    PAUSED("Pausada", "Quality assessment has been paused temporarily"),

    /**
     * Avaliação de qualidade concluída
     */
    COMPLETED("Concluída", "Quality assessment has been completed"),

    /**
     * Configuração arquivada (histórico)
     */
    ARCHIVED("Arquivada", "Quality assessment has been archived");

    private final String displayName;
    private final String description;

    QualityAssessmentStatus(String displayName, String description) {
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
    public boolean canActivate() {
        return this == DRAFT;
    }

    public boolean canPause() {
        return this == ACTIVE;
    }

    public boolean canResume() {
        return this == PAUSED;
    }

    public boolean canComplete() {
        return this == ACTIVE || this == PAUSED;
    }

    public boolean canArchive() {
        return this == COMPLETED;
    }

    public boolean isEditable() {
        return this == DRAFT || this == PAUSED;
    }

    public boolean isUsable() {
        return this == ACTIVE;
    }

    public boolean isFinished() {
        return this == COMPLETED || this == ARCHIVED;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
