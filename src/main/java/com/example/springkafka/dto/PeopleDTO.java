package com.example.springkafka.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PeopleDTO {
		
	private String name;
	private String cpf;
		
	private List<String> books = new ArrayList<>();
}
