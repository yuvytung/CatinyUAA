package org.regitiny.catiny.uaa.business.impl;

import org.regitiny.catiny.uaa.business.MasterBusiness;
import org.regitiny.catiny.uaa.domain.User;
import org.regitiny.catiny.uaa.service.MasterService;
import org.regitiny.catiny.uaa.service.dto.MasterDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MasterBusinessImpl implements MasterBusiness
{
  private final MasterService masterService;

  private final KafkaTemplate<String, String> kafkaTemplate;

  public MasterBusinessImpl(MasterService masterService, KafkaTemplate<String, String> kafkaTemplate)
  {
    this.masterService = masterService;
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public MasterDTO updateMasterWhenUpdateUser(User user) throws NullPointerException
  {
    MasterDTO masterDTO = masterService.updateMasterWhenUpdateUser(user);
    kafkaTemplate.send("updateCache_CatinyUAA_Master", masterDTO.toJsonString());
    return masterDTO;
  }
}
