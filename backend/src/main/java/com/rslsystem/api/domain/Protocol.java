package com.rslsystem.api.domain;

import com.rslsystem.api.domain.shared.AuditableEntity;
import com.rslsystem.api.domain.shared.enums.ProtocolStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade Protocol - representa o protocolo metodológico detalhado de uma RSL. Complementa
 * ReviewPlanning com aspectos científicos e metodológicos específicos.
 */
@Entity
@Table(name = "protocols",
    indexes = {@Index(name = "idx_protocol_review", columnList = "review_id"),
        @Index(name = "idx_protocol_status", columnList = "status"),
        @Index(name = "idx_protocol_version", columnList = "version")})
@Getter
@Setter
@NoArgsConstructor
public class Protocol extends AuditableEntity {

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "review_id", nullable = false, unique = true)
  @NotNull(message = "Review is required")
  private Review review;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  @NotNull(message = "Protocol status is required")
  private ProtocolStatus status = ProtocolStatus.DRAFT;

  @Column(nullable = false, length = 10)
  @Size(max = 10, message = "Version must be less than 10 characters")
  private String version = "1.0";

  // === METODOLOGIA ===
  @Column(name = "research_methodology", columnDefinition = "TEXT")
  @Size(max = 3000, message = "Research methodology must be less than 3000 characters")
  private String researchMethodology;

  @Column(name = "research_design", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Research design must be less than 2000 characters")
  private String researchDesign;

  @Column(name = "study_types", columnDefinition = "TEXT")
  @Size(max = 1500, message = "Study types must be less than 1500 characters")
  private String studyTypes;

  @Column(name = "data_analysis_method", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Data analysis method must be less than 2000 characters")
  private String dataAnalysisMethod;

  // === FRAMEWORK DE QUALIDADE ===
  @Column(name = "quality_framework", length = 100)
  @Size(max = 100, message = "Quality framework must be less than 100 characters")
  private String qualityFramework; // Ex: "Cochrane", "CASP", "Custom"

  @Column(name = "quality_questions", columnDefinition = "TEXT")
  @Size(max = 2500, message = "Quality questions must be less than 2500 characters")
  private String qualityQuestions;

  @Column(name = "quality_scoring", columnDefinition = "TEXT")
  @Size(max = 1000, message = "Quality scoring must be less than 1000 characters")
  private String qualityScoring;

  @Column(name = "quality_threshold")
  private Double qualityThreshold; // Score mínimo para inclusão

  // === EXTRAÇÃO DE DADOS ===
  @Column(name = "extraction_fields", columnDefinition = "TEXT")
  @Size(max = 3000, message = "Extraction fields must be less than 3000 characters")
  private String extractionFields;

  @Column(name = "extraction_method", columnDefinition = "TEXT")
  @Size(max = 1500, message = "Extraction method must be less than 1500 characters")
  private String extractionMethod;

  @Column(name = "pilot_extraction", columnDefinition = "TEXT")
  @Size(max = 1000, message = "Pilot extraction must be less than 1000 characters")
  private String pilotExtraction;

  // === DOCUMENTAÇÃO CIENTÍFICA ===
  @Column(name = "ethics_statement", columnDefinition = "TEXT")
  @Size(max = 1500, message = "Ethics statement must be less than 1500 characters")
  private String ethicsStatement;

  @Column(name = "limitations_known", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Known limitations must be less than 2000 characters")
  private String limitationsKnown;

  @Column(name = "bias_mitigation", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Bias mitigation must be less than 2000 characters")
  private String biasMitigation;

  @Column(name = "reporting_guidelines", columnDefinition = "TEXT")
  @Size(max = 1000, message = "Reporting guidelines must be less than 1000 characters")
  private String reportingGuidelines; // Ex: "PRISMA", "PROSPERO"

  // === REFERÊNCIAS E VALIDAÇÕES ===
  @Column(name = "references_list", columnDefinition = "TEXT")
  @Size(max = 3000, message = "References must be less than 3000 characters")
  private String references;

  @Column(name = "validation_method", columnDefinition = "TEXT")
  @Size(max = 1500, message = "Validation method must be less than 1500 characters")
  private String validationMethod;

  @Column(name = "inter_rater_reliability", columnDefinition = "TEXT")
  @Size(max = 1000, message = "Inter-rater reliability must be less than 1000 characters")
  private String interRaterReliability;

  // === CRONOGRAMA E APROVAÇÃO ===
  @Column(name = "submission_date")
  private LocalDate submissionDate;

  @Column(name = "approval_date")
  private LocalDate approvalDate;

  @Column(name = "registration_id", length = 100)
  @Size(max = 100, message = "Registration ID must be less than 100 characters")
  private String registrationId; // PROSPERO, OSF, etc.

  @Column(name = "registration_url", length = 500)
  @Size(max = 500, message = "Registration URL must be less than 500 characters")
  private String registrationUrl;

  // === OBSERVAÇÕES ===
  @Column(name = "protocol_notes", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Protocol notes must be less than 2000 characters")
  private String protocolNotes;

  @Column(name = "review_comments", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Review comments must be less than 2000 characters")
  private String reviewComments;

  // Construtor customizado
  public Protocol(Review review) {
    this.review = review;
    this.status = ProtocolStatus.DRAFT;
    this.version = "1.0";
  }

  // === MÉTODOS DE NEGÓCIO ===

  // Controle do status do protocolo
  public void submitForReview() {
    if (this.status == ProtocolStatus.DRAFT && canSubmit()) {
      this.status = ProtocolStatus.UNDER_REVIEW;
      this.submissionDate = LocalDate.now();
    }
  }

  public void approveProtocol() {
    if (this.status == ProtocolStatus.UNDER_REVIEW) {
      this.status = ProtocolStatus.APPROVED;
      this.approvalDate = LocalDate.now();
    }
  }

  public void rejectProtocol(String comments) {
    if (this.status == ProtocolStatus.UNDER_REVIEW) {
      this.status = ProtocolStatus.REJECTED;
      this.reviewComments = (this.reviewComments != null ? this.reviewComments + "\n" : "")
          + "Rejeitado: " + comments;
    }
  }

  public void reviseProtocol() {
    if (this.status == ProtocolStatus.REJECTED) {
      this.status = ProtocolStatus.DRAFT;
      // Incrementa versão minor
      incrementVersion();
    }
  }

  public void publishProtocol() {
    if (this.status == ProtocolStatus.APPROVED) {
      this.status = ProtocolStatus.PUBLISHED;
    }
  }

  // === VALIDAÇÕES DE NEGÓCIO ===

  // Verifica se o protocolo pode ser submetido
  public boolean canSubmit() {
    return hasMethodology() && hasQualityFramework() && hasExtractionPlan() && hasEthicsStatement();
  }

  public boolean hasMethodology() {
    return researchMethodology != null && !researchMethodology.trim().isEmpty()
        && researchDesign != null && !researchDesign.trim().isEmpty();
  }

  public boolean hasQualityFramework() {
    return qualityFramework != null && !qualityFramework.trim().isEmpty()
        && qualityQuestions != null && !qualityQuestions.trim().isEmpty();
  }

  public boolean hasExtractionPlan() {
    return extractionFields != null && !extractionFields.trim().isEmpty()
        && extractionMethod != null && !extractionMethod.trim().isEmpty();
  }

  public boolean hasEthicsStatement() {
    return ethicsStatement != null && !ethicsStatement.trim().isEmpty();
  }

  // === MÉTODOS DE CONSULTA ===

  public boolean isDraft() {
    return status == ProtocolStatus.DRAFT;
  }

  public boolean isUnderReview() {
    return status == ProtocolStatus.UNDER_REVIEW;
  }

  public boolean isApproved() {
    return status == ProtocolStatus.APPROVED;
  }

  public boolean isRejected() {
    return status == ProtocolStatus.REJECTED;
  }

  public boolean isPublished() {
    return status == ProtocolStatus.PUBLISHED;
  }

  public boolean canStartConducting() {
    return status == ProtocolStatus.APPROVED || status == ProtocolStatus.PUBLISHED;
  }

  public boolean isEditable() {
    return status == ProtocolStatus.DRAFT || status == ProtocolStatus.REJECTED;
  }

  // Calcula completude do protocolo (0-100%)
  public Integer getCompletionPercentage() {
    int completed = 0;
    int total = 4;

    if (hasMethodology())
      completed++;
    if (hasQualityFramework())
      completed++;
    if (hasExtractionPlan())
      completed++;
    if (hasEthicsStatement())
      completed++;

    return (completed * 100) / total;
  }

  // === MÉTODOS DE CONVENIÊNCIA ===

  public String getReviewTitle() {
    return review != null ? review.getTitle() : "Unknown";
  }

  // Incrementa versão do protocolo
  private void incrementVersion() {
    if (version != null && version.contains(".")) {
      String[] parts = version.split("\\.");
      if (parts.length == 2) {
        try {
          int major = Integer.parseInt(parts[0]);
          int minor = Integer.parseInt(parts[1]) + 1;
          this.version = major + "." + minor;
        } catch (NumberFormatException e) {
          this.version = "1.1";
        }
      }
    } else {
      this.version = "1.1";
    }
  }

  // Lista de campos de extração como lista
  public List<String> getExtractionFieldsList() {
    if (extractionFields == null || extractionFields.trim().isEmpty()) {
      return new ArrayList<>();
    }
    return List.of(extractionFields.split("\n"));
  }

  // Lista de questões de qualidade como lista
  public List<String> getQualityQuestionsList() {
    if (qualityQuestions == null || qualityQuestions.trim().isEmpty()) {
      return new ArrayList<>();
    }
    return List.of(qualityQuestions.split("\n"));
  }

  // Formatar período de desenvolvimento
  public String getDevelopmentPeriod() {
    if (submissionDate != null && approvalDate != null) {
      return submissionDate + " até " + approvalDate;
    }
    if (submissionDate != null) {
      return "Submetido em " + submissionDate;
    }
    return "Em desenvolvimento";
  }

  @Override
  public boolean equals(Object o) {
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  // toString seguro (evita recursão infinita)
  @Override
  public String toString() {
    return "Protocol{" + "id=" + getId() + ", reviewId=" + (review != null ? review.getId() : null)
        + ", status=" + status + ", version='" + version + '\'' + ", completion="
        + getCompletionPercentage() + "%" + ", hasMethodology=" + hasMethodology()
        + ", hasQualityFramework=" + hasQualityFramework() + ", hasExtractionPlan="
        + hasExtractionPlan() + ", hasEthicsStatement=" + hasEthicsStatement() + ", canSubmit="
        + canSubmit() + ", isEditable=" + isEditable() + ", isDeleted=" + getIsDeleted() + '}';
  }
}
