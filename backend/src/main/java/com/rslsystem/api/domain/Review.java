package com.rslsystem.api.domain;

import com.rslsystem.api.domain.shared.AuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Entidade Review - representa uma revisão sistemática da literatura.
 */
@Entity
@Table(name = "reviews",
    indexes = {@Index(name = "idx_review_title", columnList = "title"),
        @Index(name = "idx_review_owner", columnList = "owner_id")})
@Getter
@Setter
@NoArgsConstructor
public class Review extends AuditableEntity {

  @Column(nullable = false, length = 255)
  @NotBlank(message = "Title is required")
  @Size(max = 255, message = "Title must be less than 255 characters")
  private String title;

  @Column(columnDefinition = "TEXT")
  @Size(max = 2000, message = "Description must be less than 2000 characters")
  private String description;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "owner_id", nullable = false)
  private User owner;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "review_authors", joinColumns = @JoinColumn(name = "review_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  private Set<User> authors = new HashSet<>();

  @Column(name = "registration_number", length = 100)
  @Size(max = 100, message = "Registration number must be less than 100 characters")
  private String registrationNumber;

  @Column(name = "registration_platform", length = 100)
  @Size(max = 100, message = "Registration platform must be less than 100 characters")
  private String registrationPlatform;

  // Relacionamentos com as 3 fases da RSL
  @OneToOne(mappedBy = "review", cascade = CascadeType.ALL, fetch = FetchType.LAZY,
      orphanRemoval = true)
  private ReviewPlanning planning;

  @OneToOne(mappedBy = "review", cascade = CascadeType.ALL, fetch = FetchType.LAZY,
      orphanRemoval = true)
  private ReviewConducting conducting;

  @OneToOne(mappedBy = "review", cascade = CascadeType.ALL, fetch = FetchType.LAZY,
      orphanRemoval = true)
  private ReviewReporting reporting;

  public Review(String title, String description, User owner) {
    this.title = title;
    this.description = description;
    this.owner = owner;
  }

  // Métodos de conveniência para autores
  public void addAuthor(User author) {
    if (authors == null) {
      authors = new HashSet<>();
    }
    authors.add(author);
  }

  public void removeAuthor(User author) {
    if (authors != null) {
      authors.remove(author);
    }
  }

  public boolean isOwner(User user) {
    return owner != null && owner.equals(user);
  }

  public boolean isAuthor(User user) {
    return authors != null && authors.contains(user);
  }

  public boolean isParticipant(User user) {
    return isOwner(user) || isAuthor(user);
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
    return "Review{" + "id=" + getId() + ", title='" + title + '\'' + ", ownerId="
        + (owner != null ? owner.getId() : null) + ", authorsCount="
        + (authors != null ? authors.size() : 0) + ", registrationNumber='" + registrationNumber
        + '\'' + ", registrationPlatform='" + registrationPlatform + '\'' + ", isDeleted="
        + getIsDeleted() + '}';
  }
}
