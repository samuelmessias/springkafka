package com.example.springkafka.controllers;

import java.awt.PageAttributes.MediaType;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.People;
import com.example.springkafka.dto.PeopleDTO;
import com.example.springkafka.producers.PeopleProducer;

@RestController
@RequestMapping(value = "/peoples")
public class PeopleController {
	
	@Autowired
	private PeopleProducer peopleProducer;
	
	@PostMapping(consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> sendMessage(@RequestBody PeopleDTO dto){
		String id = UUID.randomUUID().toString();
		
		var message = People.newBuilder()
				.setId(id)
				.setName(dto.getName())
				.setCpf(dto.getCpf())
				.setBooks(dto.getBooks().stream().map(x -> (CharSequence)x).collect(Collectors.toList())).build();
		peopleProducer.sendMessage(message);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
