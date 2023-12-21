package com.example.demo.kafkalistner;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.example.demo.entities.TrakingEntity;

@Component
@KafkaListener(id = "paytmConsumerClient", topics = "17235", groupId = "makemytrip.consumer.group", containerFactory = "kafkaListerContainerFactory")
public class TrainTrackingHandler {
	@KafkaHandler
	public void listner(@Header(KafkaHeaders.RECEIVED_PARTITION) Integer partitions,
			@Header(KafkaHeaders.RECEIVED_KEY) String key,@Payload TrakingEntity trakingEntity) {
		System.out.println("Message Received " + trakingEntity.toString());
		System.out.println("Key,Partitions Received " + trakingEntity.toString());
	}

}
