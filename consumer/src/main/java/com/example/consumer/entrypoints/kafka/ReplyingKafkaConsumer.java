package com.example.consumer.entrypoints.kafka;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import io.confluent.developer.StudentInput;
import io.confluent.developer.StudentOutput;

import java.util.UUID;


@Component
public class ReplyingKafkaConsumer {
	 
	 @KafkaListener(topics = "${kafka.topic.request-topic}")
	 @SendTo
	  public StudentOutput listen(ConsumerRecord<String, StudentInput> payload) throws InterruptedException, JsonProcessingException {
		 System.out.println(payload.value());

		 StudentOutput studentOutput = StudentOutput.newBuilder().setUuid(UUID.randomUUID().toString()).build();

		 return studentOutput;
	  }

}
