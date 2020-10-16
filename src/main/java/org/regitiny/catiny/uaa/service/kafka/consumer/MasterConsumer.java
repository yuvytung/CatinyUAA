package org.regitiny.catiny.uaa.service.kafka.consumer;


import lombok.extern.log4j.Log4j2;
import org.regitiny.catiny.uaa.service.MasterService;
import org.regitiny.catiny.uaa.service.dto.MasterDTO;
import org.regitiny.catiny.uaa.service.exception.MasterConsumerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Log4j2
@Service
public class MasterConsumer
{
//  đây là mẫu tùy biến
//  final Consumer<String, String> consumer;
//
//  public MasterConsumer(Consumer<String, String> consumer)
//  {
//    this.consumer = consumer;
//  }
//
//  @Bean(name = "requestKafkaListener_CatinyUAA_Master")
//  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> requestKafkaListenerContainerFactory()
//  {
//    return consumer.requestListenerContainerFactory();
//  }
//
//  @KafkaListener(topics = "topic", containerFactory = "requestKafkaListener_CatinyUAA_Master")
//  @SendTo
//  public String receive(String request)
//  {
//    log.debug(request);
//    return request;
//  }

  // reply mặc định gửi tin nhắn là: String

  @Autowired
  private MasterService masterService;

  @KafkaListener(topics = "GET_Request-Json_Entity-CatinyUAA.Master", containerFactory = "requestListenerContainerFactory")
  @SendTo
  public String receiveGetMasterByUserName_G_C(String request) throws Exception
  {
    log.debug("request : json = {} " + request);
    MasterDTO masterDTO = new MasterDTO().fromJson(request);
    log.debug("request convert to master = {}" + masterDTO.toString());
    if (Objects.isNull(masterDTO) || masterDTO.getUserName() == null)
      throw new MasterConsumerException("Input is null or userName is null");

    if (Objects.isNull(masterDTO.getCompanyId()))
      masterDTO.setCompanyId(0L);
    if (Objects.isNull(masterDTO.getGroupId()))
      masterDTO.setGroupId(0L);

    MasterDTO result = masterService.fetchOneByUserNameGroupIdCompanyId(masterDTO.getUserName(), masterDTO.getGroupId(), masterDTO.getCompanyId());
    if (Objects.nonNull(result))
      return result.toJsonString();
    throw new Exception();
  }

  @KafkaListener(topics = "GET_Request-String_Field-CatinyUAA.Master", containerFactory = "requestListenerContainerFactory")
  @SendTo
  public String receiveGetMasterByMasterId(UUID masterId) throws Exception
  {
    log.debug("request : json = {} " , masterId);
    if (Objects.isNull(masterId) )
      throw new MasterConsumerException("Input is null or userName is null");

    MasterDTO result = masterService.fetchOne(masterId);
    if (Objects.nonNull(result))
      return result.toJsonString();
    throw new Exception();
  }
}
