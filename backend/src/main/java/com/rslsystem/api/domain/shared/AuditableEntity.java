package com.rslsystem.api.domain.shared;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AuditableEntity extends BaseEntity {

  @Column(name = "is_deleted", nullable = false)
  private Boolean isDeleted = false;

  @Column(name = "deleted_at", nullable = true)
  private LocalDateTime deletedAt;

  @Column(name = "deleted_by_id", nullable = true)
  private Long deletedByUserId;

  protected AuditableEntity() {
    super();
  }

  // Getters e Setters
  public Boolean getIsDeleted() {
    return isDeleted;
  }

  public void setIsDeleted(Boolean isDeleted) {
    this.isDeleted = isDeleted;
  }

  public LocalDateTime getDeletedAt() {
    return deletedAt;
  }

  public void setDeletedAt(LocalDateTime deletedAt) {
    this.deletedAt = deletedAt;
  }

  public Long getDeletedByUserId() {
    return deletedByUserId;
  }

  public void setDeletedByUserId(Long deletedByUserId) {
    this.deletedByUserId = deletedByUserId;
  }

  // Método para soft delete
  public void softDelete(Long userId) {
    this.isDeleted = true;
    this.deletedAt = LocalDateTime.now();
    this.deletedByUserId = userId;
  }

  // Método para restaurar
  public void restore() {
    this.isDeleted = false;
    this.deletedAt = null;
    this.deletedByUserId = null;
  }

  // Métodos de verificação
  public boolean isDeleted() {
    return Boolean.TRUE.equals(this.isDeleted);
  }

  public boolean isActive() {
    return !isDeleted();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" + "id=" + getId() + ", isDeleted=" + isDeleted
        + ", deletedAt=" + deletedAt + ", createdAt=" + getCreatedAt() + ", updatedAt="
        + getUpdatedAt() + '}';
  }

  @Override
  public boolean equals(Object o) {
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
