package com.javatechie.spring.api.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javatechie.spring.api.entity.Person;
import com.javatechie.spring.api.repository.PersonRepository;

@Service
@RequiredArgsConstructor
public class PersonService {

	private final PersonRepository repository;

	public Person savePerson(Person person) {
		return repository.save(person);
	}
	
	public List<Person> findAllPersons() {
		return repository.findAll();
	}
}
