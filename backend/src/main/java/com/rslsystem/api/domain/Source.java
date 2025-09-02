package com.rslsystem.api.domain;

import com.rslsystem.api.domain.shared.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidade Source - representa uma base de dados ou fonte de busca (IEEE, ACM, Scopus, etc.).
 */
@Entity
@Table(name = "sources",
    indexes = {@Index(name = "idx_source_name", columnList = "name"),
        @Index(name = "idx_source_active", columnList = "is_active")})
@Getter
@Setter
@NoArgsConstructor
public class Source extends BaseEntity {

  @Column(nullable = false, length = 255)
  @NotBlank(message = "Source name is required")
  @Size(max = 255, message = "Source name must be less than 255 characters")
  private String name;

  @Column(columnDefinition = "TEXT")
  @Size(max = 1000, message = "Description must be less than 1000 characters")
  private String description;

  @Column(name = "base_url", length = 500)
  @Size(max = 500, message = "Base URL must be less than 500 characters")
  private String baseUrl;

  @Column(name = "search_string", columnDefinition = "TEXT")
  @Size(max = 2000, message = "Search string must be less than 2000 characters")
  private String searchString;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive = true;

  // Relacionamentos
  @OneToMany(mappedBy = "originatedFrom", fetch = FetchType.LAZY)
  private List<Study> studies = new ArrayList<>();

  // @ManyToMany(mappedBy = "sources", fetch = FetchType.LAZY)
  // private List<Search> searches = new ArrayList<>();

  // Construtor customizado
  public Source(String name, String description) {
    this.name = name;
    this.description = description;
  }

  // Métodos de conveniência
  public void addStudy(Study study) {
    if (studies == null) {
      studies = new ArrayList<>();
    }
    studies.add(study);
    study.setOriginatedFrom(this);
  }

  public void removeStudy(Study study) {
    if (studies != null) {
      studies.remove(study);
      study.setOriginatedFrom(null);
    }
  }

  // Métodos de negócio
  public boolean isEnabled() {
    return Boolean.TRUE.equals(isActive);
  }

  public void activate() {
    this.isActive = true;
  }

  public void deactivate() {
    this.isActive = false;
  }

  public int getStudiesCount() {
    return studies != null ? studies.size() : 0;
  }

  public boolean hasSearchString() {
    return searchString != null && !searchString.trim().isEmpty();
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
    return "Source{" + "id=" + getId() + ", name='" + name + '\'' + ", baseUrl='" + baseUrl + '\''
        + ", isActive=" + isActive + ", studiesCount=" + getStudiesCount() + '}';
  }
}
