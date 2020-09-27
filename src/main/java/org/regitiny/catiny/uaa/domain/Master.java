package org.regitiny.catiny.uaa.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * A Master.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "master")
public class Master implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @org.springframework.data.annotation.Id
  @NotNull
  @Type(type = "uuid-char")
  @Column(name = "master_id", length = 36, nullable = false)
  private UUID masterId;

  @NotNull
  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "group_id")
  private Long groupId;

  @Column(name = "company_id")
  private Long companyId;

  @Column(name = "master_user_name")
  private String masterUserName;

  @Column(name = "user_name")
  private String userName;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "email")
  private String email;

  @Column(name = "group_name")
  private String groupName;

  @Column(name = "company_name")
  private String companyName;

  @Column(name = "created_date")
  private Instant createdDate;

  @Column(name = "modified_date")
  private Instant modifiedDate;

  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Master masterId(UUID masterId)
  {
    this.masterId = masterId;
    return this;
  }

  public Master userId(Long userId)
  {
    this.userId = userId;
    return this;
  }

  public Master groupId(Long groupId)
  {
    this.groupId = groupId;
    return this;
  }

  public Master companyId(Long companyId)
  {
    this.companyId = companyId;
    return this;
  }

  public Master masterUserName(String masterUserName)
  {
    this.masterUserName = masterUserName;
    return this;
  }

  public Master userName(String userName)
  {
    this.userName = userName;
    return this;
  }

  public Master firstName(String firstName)
  {
    this.firstName = firstName;
    return this;
  }

  public Master lastName(String lastName)
  {
    this.lastName = lastName;
    return this;
  }

  public Master imageUrl(String imageUrl)
  {
    this.imageUrl = imageUrl;
    return this;
  }

  public Master email(String email)
  {
    this.email = email;
    return this;
  }

  public Master groupName(String groupName)
  {
    this.groupName = groupName;
    return this;
  }

  public Master companyName(String companyName)
  {
    this.companyName = companyName;
    return this;
  }

  public Master createdDate(Instant createdDate)
  {
    this.createdDate = createdDate;
    return this;
  }

  public Master modifiedDate(Instant modifiedDate)
  {
    this.modifiedDate = modifiedDate;
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (!(o instanceof Master))
    {
      return false;
    }
    return masterId != null && masterId.equals(((Master) o).masterId);
  }

  @Override
  public int hashCode()
  {
    return 31;
  }

  // prettier-ignore
  @Override
  public String toString()
  {
    return "Master{" +
      "  masterId='" + getMasterId() + "'" +
      ", userId=" + getUserId() +
      ", groupId=" + getGroupId() +
      ", companyId=" + getCompanyId() +
      ", masterUserName='" + getMasterUserName() + "'" +
      ", userName='" + getUserName() + "'" +
      ", firstName='" + getFirstName() + "'" +
      ", lastName='" + getLastName() + "'" +
      ", imageUrl='" + getImageUrl() + "'" +
      ", email='" + getEmail() + "'" +
      ", groupName='" + getGroupName() + "'" +
      ", companyName='" + getCompanyName() + "'" +
      ", createdDate='" + getCreatedDate() + "'" +
      ", modifiedDate='" + getModifiedDate() + "'" +
      "}";
  }
}
