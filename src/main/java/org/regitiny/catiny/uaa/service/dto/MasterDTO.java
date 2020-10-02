package org.regitiny.catiny.uaa.service.dto;

import lombok.*;
import org.regitiny.tools.magic.quick.JsonQuickMagic;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * A DTO for the {@link org.regitiny.catiny.uaa.domain.Master} entity.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MasterDTO extends JsonQuickMagic implements Serializable
{

  @NotNull
  private UUID masterId;

  @NotNull
  private Long userId;

  private Long groupId;

  private Long companyId;

  private String masterUserName;

  private String userName;

  private String firstName;

  private String lastName;

  private String imageUrl;

  private String email;

  private String groupName;

  private String companyName;

  private Instant createdDate;

  private Instant modifiedDate;


  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (!(o instanceof MasterDTO))
    {
      return false;
    }

    return masterId != null && masterId.equals(((MasterDTO) o).masterId);
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
    return "MasterDTO{" +
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
