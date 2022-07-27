package com.example.springkafka.consumers;

import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.example.People;
import com.example.springkafka.entity.Book;
import com.example.springkafka.entity.PeopleEntity;
import com.example.springkafka.repositories.PeopleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PeopleConsumer {
	
	@Autowired
	private PeopleRepository repository;
	
	@KafkaListener(topics="${topic.name}")
	public void consumer(ConsumerRecord<String, People> record, Acknowledgment ack) {
		
		People people = record.value();
		
		log.info("Mensagem consumida: " + people.toString());
				
		PeopleEntity peopleEntity = new PeopleEntity();
		peopleEntity.setId(people.getId().toString());
        peopleEntity.setCpf(people.getCpf().toString());
        peopleEntity.setName(people.getName().toString());
        peopleEntity.setBooks(people.getBooks().stream()
                .map(book -> Book.builder()
                        .people(peopleEntity)
                        .name(book.toString())
                        .build()).collect(Collectors.toList()));
        
        repository.save(peopleEntity);
		
		ack.acknowledge();
		
	}

}
