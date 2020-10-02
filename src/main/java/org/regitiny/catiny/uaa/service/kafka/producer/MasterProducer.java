package org.regitiny.catiny.uaa.service.kafka.producer;

import lombok.extern.log4j.Log4j2;
import org.regitiny.catiny.uaa.service.kafka.Producer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MasterProducer
{

//  @Value("${kafka.producer.services.catiny-uaa.reply-topic}")
//  private String replyTopic;
//
//  private final Producer<String,String> producer;
//
//  public MasterProducer(Producer<String, String> producer)
//  {
//    this.producer = producer;
//  }
//
//  @SuppressWarnings("rawtypes")
//  @Bean(name = "ReplyingKafkaTemplate_CatinyUAA_Master")
//  public ReplyingKafkaTemplate replyKafkaTemplateImplementation()
//  {
//    return producer.replyKafkaTemplate(replyTopic);
//  }

  @Value("${kafka.producer.services.catiny-uaa.reply-topic}")
  private String replyTopic;

//  quy tắc đặt tên : replyKafka_<service>_<Entity>_<Other>
  @SuppressWarnings("rawtypes")
  @Bean
  public ReplyingKafkaTemplate replyKafka_CatinyUAA_Master(Producer<String,String> producer)
  {
    return producer.replyKafkaTemplate(replyTopic);
  }



}
