package com.example.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.model.User;

public interface Data499Repository extends MongoRepository<User, Integer>{
	List<User> findByFirstName(String name);

}
