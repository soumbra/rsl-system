package com.rslsystem.api.domain;

import com.rslsystem.api.domain.shared.AuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidade User - representa usuários do sistema RSL. Podem ser revisores, administradores ou
 * proprietários de reviews.
 */
@Entity
@Table(name = "users",
    indexes = {@Index(name = "idx_user_email", columnList = "email", unique = true),
        @Index(name = "idx_user_username", columnList = "username", unique = true),
        @Index(name = "idx_user_active", columnList = "is_active")})
@NoArgsConstructor
@Getter
public class User extends AuditableEntity {

  @NotBlank
  @Size(min = 3, max = 50)
  @Column(name = "username", nullable = false, unique = true, length = 50)
  private String username;

  @NotBlank
  @Email
  @Size(max = 100)
  @Column(name = "email", nullable = false, unique = true, length = 100)
  private String email;

  @NotBlank
  @Size(min = 6, max = 255)
  @Column(name = "password", nullable = false)
  @Setter // Apenas setter, para não expor via getter automaticamente
  private String password;

  @NotBlank
  @Size(max = 100)
  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @NotBlank
  @Size(max = 100)
  @Column(name = "lastname", nullable = false, length = 100)
  private String lastname;

  @Size(max = 255)
  @Column(name = "image", length = 255)
  @Setter
  private String image;

  @Size(max = 200)
  @Column(name = "institution", length = 200)
  @Setter
  private String institution;

  @Size(max = 100)
  @Column(name = "academic_title", length = 100)
  @Setter
  private String academicTitle;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive = true;

  public User(String username, String email, String password, String name, String lastname) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.name = name;
    this.lastname = lastname;
    this.isActive = true;
  }

  // Métodos de negócio
  public String getFullName() {
    return this.name + " " + this.lastname;
  }

  public void activate() {
    this.isActive = true;
  }

  public void deactivate() {
    this.isActive = false;
  }

  @Override
  public boolean isActive() {
    return Boolean.TRUE.equals(this.isActive) && !isDeleted();
  }

  @Override
  public boolean equals(Object o) {
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  public boolean hasImage() {
    return this.image != null && !this.image.trim().isEmpty();
  }

  public boolean hasInstitution() {
    return this.institution != null && !this.institution.trim().isEmpty();
  }

  public boolean hasAcademicTitle() {
    return this.academicTitle != null && !this.academicTitle.trim().isEmpty();
  }

  @Override
  public String toString() {
    return "User{" + "id=" + getId() + ", username='" + username + '\'' + ", email='" + email + '\''
        + ", fullName='" + getFullName() + '\'' + ", institution='" + institution + '\''
        + ", isActive=" + isActive + ", isDeleted=" + getIsDeleted() + '}';
  }
}
