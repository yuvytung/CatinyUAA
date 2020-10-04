package org.regitiny.catiny.uaa.repository;

import org.regitiny.catiny.uaa.domain.Master;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data  repository for the Master entity.
 */
@Repository
public interface MasterRepository extends JpaRepository<Master, UUID>, JpaSpecificationExecutor<Master>
{
  Master findOneByMasterId(UUID masterId);


  List<Master> findByUserId(Long userId);


  Boolean deleteOneByMasterId(UUID masterId);


  Master findOneByUserIdAndGroupIdAndCompanyId(Long userId, Long groupId, Long companyId);


  Master findOneByUserNameAndGroupIdAndCompanyId(String userId, Long groupId, Long companyId);


  List<Master> findByUserName(String UserName);
}
