package org.regitiny.catiny.uaa.web.deepapi.impl;

import lombok.extern.log4j.Log4j2;
import org.regitiny.catiny.uaa.service.MasterService;
import org.regitiny.catiny.uaa.service.dto.MasterDTO;
import org.regitiny.catiny.uaa.service.exception.MasterConsumerException;
import org.regitiny.catiny.uaa.web.deepapi.MasterDeepApi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

@Log4j2
@RestController
public class MasterDeepApiImpl implements MasterDeepApi
{
  private final MasterService masterService;

  public MasterDeepApiImpl(MasterService masterService)
  {
    this.masterService = masterService;
  }

  @Override
  public ResponseEntity<MasterDTO> getMasterByUserName_G_C(String userName, Long groupId, Long companyId)
  {
    log.debug("request userName = {} , groupId = {} , companyId = {} " , userName , groupId , companyId);

    if (Objects.isNull(userName) )
      return ResponseEntity.badRequest().build();

    //default groupId and companyId is 0;
    if (Objects.isNull(groupId))
      groupId = 0L;
    if (Objects.isNull(companyId))
      companyId = 0L;

    MasterDTO result = masterService.fetchOneByUserNameGroupIdCompanyId(userName, groupId, companyId);
    log.debug(result);
    if (Objects.nonNull(result))
      return ResponseEntity.ok(result);
    return ResponseEntity.notFound().build();
  }

  @Override
  public ResponseEntity<MasterDTO> getMasterByMasterId(UUID masterId)
  {
    log.debug("request : json = {} " , masterId);
    if (Objects.isNull(masterId))
      throw new MasterConsumerException("Input is null or userName is null");

    MasterDTO result = masterService.fetchOne(masterId);
    log.debug(result);
    if (Objects.nonNull(result))
      return ResponseEntity.ok(result);
    return ResponseEntity.notFound().build();
  }
}
