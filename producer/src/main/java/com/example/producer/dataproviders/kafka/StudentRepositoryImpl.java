package com.example.producer.dataproviders.kafka;

import com.example.producer.core.model.Student;
import com.example.producer.core.ports.StudentRepository;
import io.confluent.developer.StudentMessageInput;
import io.confluent.developer.StudentMessageOutput;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@AllArgsConstructor
public class StudentRepositoryImpl implements StudentRepository {

    private final ReplyingKafkaTemplate<String, StudentMessageInput, StudentMessageOutput> kafkaTemplate;
    private final String requestTopic;
    private final String requestReplyTopic;

    @Override
    public UUID save(Student student) {

        StudentMessageInput studentInput = StudentMessageInput.newBuilder()
                .setName(student.getName())
                .setSurname(student.getSurname())
                .build();

        // create producer record
        ProducerRecord<String, StudentMessageInput> record = new ProducerRecord<>(requestTopic, studentInput);
        // set reply topic in header
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));
        // post in kafka topic
        RequestReplyFuture<String, StudentMessageInput, StudentMessageOutput> sendAndReceive = kafkaTemplate.sendAndReceive(record);

        try {
            // confirm if producer produced successfully
            SendResult<String, StudentMessageInput> sendResult = sendAndReceive.getSendFuture().get();

            //print all headers
            sendResult.getProducerRecord().headers().forEach(header -> System.out.println(header.key() + ":" + header.value().toString()));

            // get consumer record
            ConsumerRecord<String, StudentMessageOutput> consumerRecord = sendAndReceive.get();
            // return consumer value
            return UUID.fromString(consumerRecord.value().getUuid());

        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalArgumentException(e);
        }

    }
}
