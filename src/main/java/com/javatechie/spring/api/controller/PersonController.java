package com.javatechie.spring.api.controller;

import com.javatechie.spring.api.entity.Person;
import com.javatechie.spring.api.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService service;

    @GetMapping
    public List<Person> getPersons() {
        System.out.println("Controller getPersons() method called...");
        return service.findAllPersons();
    }

    @PostMapping
    public Person savePerson(@RequestBody Person person) {
        System.out.println("Controller savePerson() method called...");
        return service.savePerson(person);
    }
}
