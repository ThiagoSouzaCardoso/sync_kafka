package com.example.consumer.entrypoints.kafka;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.confluent.developer.StudentMessageInput;
import io.confluent.developer.StudentMessageOutput;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class ReplyingKafkaConsumer {
	 
	 @KafkaListener(topics = "${kafka.topic.request-topic}")
	 @SendTo
	  public StudentMessageOutput listen(ConsumerRecord<String, StudentMessageInput> payload) throws InterruptedException, JsonProcessingException {
		 System.out.println(payload.value());

		 StudentMessageOutput studentOutput = StudentMessageOutput.newBuilder().setUuid(UUID.randomUUID().toString()).build();

		 return studentOutput;
	  }

}
