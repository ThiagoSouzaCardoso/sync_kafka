package com.example.producer.configurations;

import io.confluent.developer.StudentMessageInput;
import io.confluent.developer.StudentMessageOutput;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

@Configuration
public class KafkaConfig {

    @Value("${kafka.topic.requestreply-topic}")
    private String requestReplyTopic;

    @Bean
    public ReplyingKafkaTemplate<String, StudentMessageInput, StudentMessageOutput> replyKafkaTemplate(ProducerFactory<String, StudentMessageInput> pf, KafkaMessageListenerContainer<String, StudentMessageOutput> container){
        return new ReplyingKafkaTemplate(pf, container);
    }

    @Bean
    public KafkaMessageListenerContainer<String, StudentMessageOutput> replyContainer(ConsumerFactory<String, StudentMessageInput> cf) {
        ContainerProperties containerProperties = new ContainerProperties(requestReplyTopic);
        return new KafkaMessageListenerContainer(cf, containerProperties);
    }
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, StudentMessageOutput>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, StudentMessageOutput> factory = new ConcurrentKafkaListenerContainerFactory<>();
        return factory;
    }


}
