package com.rslsystem.api.domain.shared;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class TrackableEntity extends BaseEntity {

  @Column(name = "last_modified_at", nullable = true)
  private LocalDateTime lastModifiedAt;

  @Column(name = "last_modified_by_id", nullable = true)
  private Long lastModifiedByUserId;

  protected TrackableEntity() {
    super();
  }

  public LocalDateTime getLastModifiedAt() {
    return lastModifiedAt;
  }

  public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
    this.lastModifiedAt = lastModifiedAt;
  }

  public Long getLastModifiedByUserId() {
    return lastModifiedByUserId;
  }

  public void setLastModifiedByUserId(Long lastModifiedByUserId) {
    this.lastModifiedByUserId = lastModifiedByUserId;
  }

  public void updateModification(Long userId) {
    this.lastModifiedAt = LocalDateTime.now();
    this.lastModifiedByUserId = userId;
  }

  public boolean wasModified() {
    return this.lastModifiedAt != null;
  }

  public boolean wasModifiedBy(Long userId) {
    return userId != null && userId.equals(this.lastModifiedByUserId);
  }

  @PreUpdate
  @Override
  protected void onUpdate() {
    super.onUpdate();
  }

  @Override
  public boolean equals(Object o) {
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" + "id=" + getId() + ", lastModifiedAt=" + lastModifiedAt
        + ", lastModifiedByUserId=" + lastModifiedByUserId + ", createdAt=" + getCreatedAt()
        + ", updatedAt=" + getUpdatedAt() + '}';
  }
}
