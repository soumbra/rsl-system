package com.rslsystem.api.domain;

import com.rslsystem.api.domain.shared.AuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidade Study - representa um artigo/estudo científico na revisão sistemática.
 */
@Entity
@Table(name = "studies",
    indexes = {@Index(name = "idx_study_title", columnList = "title"),
        @Index(name = "idx_study_year", columnList = "year"),
        @Index(name = "idx_study_doi", columnList = "doi"),
        @Index(name = "idx_study_title_authors", columnList = "title, authors")})
@Getter
@Setter
@NoArgsConstructor
public class Study extends AuditableEntity {

  @Column(nullable = false, columnDefinition = "TEXT")
  @NotBlank(message = "Title is required")
  @Size(max = 1000, message = "Title must be less than 1000 characters")
  private String title;

  @Column(name = "study_abstract", columnDefinition = "TEXT")
  @Size(max = 5000, message = "Abstract must be less than 5000 characters")
  private String abstractText;

  @Column
  @Min(value = 1900, message = "Year must be greater than 1900")
  private Integer year;

  @Column(columnDefinition = "TEXT")
  @Size(max = 2000, message = "Authors must be less than 2000 characters")
  private String authors;

  @Column(columnDefinition = "TEXT")
  @Size(max = 1000, message = "Keywords must be less than 1000 characters")
  private String keywords;

  @Column(name = "document_type", length = 100)
  @Size(max = 100, message = "Document type must be less than 100 characters")
  private String documentType;

  @Column(length = 50)
  @Size(max = 50, message = "Pages must be less than 50 characters")
  private String pages;

  @Column(length = 50)
  @Size(max = 50, message = "Volume must be less than 50 characters")
  private String volume;

  @Column(length = 100)
  @Size(max = 100, message = "DOI must be less than 100 characters")
  private String doi;

  @Column(length = 255)
  @Size(max = 255, message = "Journal must be less than 255 characters")
  private String journal;

  @Column(length = 255)
  @Size(max = 255, message = "Conference must be less than 255 characters")
  private String conference;

  @Column(length = 255)
  @Size(max = 255, message = "Publisher must be less than 255 characters")
  private String publisher;

  @Column(length = 50)
  @Size(max = 50, message = "ISBN must be less than 50 characters")
  private String isbn;

  @Column(length = 50)
  @Size(max = 50, message = "Number must be less than 50 characters")
  private String number;

  // Relacionamento com Source (base de dados de origem)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "source_id")
  private Source originatedFrom;

  // Relacionamentos com avaliações dos revisores
  @OneToMany(mappedBy = "study", cascade = CascadeType.ALL, fetch = FetchType.LAZY,
      orphanRemoval = true)
  private List<ReviewerStudyAssessment> reviewerEvaluations = new ArrayList<>();

  // Relacionamentos com extrações de dados
  @OneToMany(mappedBy = "study", cascade = CascadeType.ALL, fetch = FetchType.LAZY,
      orphanRemoval = true)
  private List<DataExtraction> dataExtractions = new ArrayList<>();

  // Relacionamentos com avaliações de qualidade
  @OneToMany(mappedBy = "study", cascade = CascadeType.ALL, fetch = FetchType.LAZY,
      orphanRemoval = true)
  private List<ReviewerQualityAssessment> qualityAssessments = new ArrayList<>();

  // Construtor customizado
  public Study(String title, String abstractText, Integer year, String authors) {
    this.title = title;
    this.abstractText = abstractText;
    this.year = year;
    this.authors = authors;
  }

  // Métodos de conveniência
  public void addReviewerEvaluation(ReviewerStudyAssessment evaluation) {
    if (reviewerEvaluations == null) {
      reviewerEvaluations = new ArrayList<>();
    }
    reviewerEvaluations.add(evaluation);
    evaluation.setStudy(this);
  }

  public void removeReviewerEvaluation(ReviewerStudyAssessment evaluation) {
    if (reviewerEvaluations != null) {
      reviewerEvaluations.remove(evaluation);
      evaluation.setStudy(null);
    }
  }

  public void addDataExtraction(DataExtraction extraction) {
    if (dataExtractions == null) {
      dataExtractions = new ArrayList<>();
    }
    dataExtractions.add(extraction);
    extraction.setStudy(this);
  }

  public void addQualityAssessment(ReviewerQualityAssessment assessment) {
    if (qualityAssessments == null) {
      qualityAssessments = new ArrayList<>();
    }
    qualityAssessments.add(assessment);
    assessment.setStudy(this);
  }

  // Métodos de negócio
  public boolean hasValidDoi() {
    return doi != null && !doi.trim().isEmpty();
  }

  public String getPublicationVenue() {
    if (journal != null && !journal.trim().isEmpty()) {
      return journal;
    }
    if (conference != null && !conference.trim().isEmpty()) {
      return conference;
    }
    return "Unknown";
  }

  public String getShortTitle() {
    if (title != null && title.length() > 100) {
      return title.substring(0, 97) + "...";
    }
    return title;
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
    return "Study{" + "id=" + getId() + ", title='" + getShortTitle() + '\'' + ", year=" + year
        + ", authors='"
        + (authors != null && authors.length() > 50 ? authors.substring(0, 47) + "..." : authors)
        + '\'' + ", doi='" + doi + '\'' + ", venue='" + getPublicationVenue() + '\''
        + ", evaluationsCount=" + (reviewerEvaluations != null ? reviewerEvaluations.size() : 0)
        + ", isDeleted=" + getIsDeleted() + '}';
  }
}
