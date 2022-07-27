package com.example.springkafka.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springkafka.entity.PeopleEntity;

public interface PeopleRepository extends JpaRepository<PeopleEntity, String>{

}
