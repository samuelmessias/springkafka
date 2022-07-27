package com.example.springkafka.producers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.People;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PeopleProducer {

	@Value("${topic.name}")
	private String topicName;

	private final KafkaTemplate<String, People> kafkaTemplate;

	public PeopleProducer(KafkaTemplate<String, People> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage(People people) {
		kafkaTemplate.send(topicName, people).addCallback(
			success -> log.info("Messagem enviada com sucesso!"),
			failure -> log.error("Falha ao enviar mensagem!")
		);
	
	}
}
