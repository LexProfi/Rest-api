package com.lex.infomatix.controller;

import com.lex.infomatix.exception.MyEntityNotFoundException;
import com.lex.infomatix.model.Publisher;
import com.lex.infomatix.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/publisher-management", produces = {MediaType.APPLICATION_JSON_VALUE })
public class PublisherController {


    @Autowired
    private PublisherRepository publisherRepository;

    @PostMapping("/post")
    public Publisher createPublisher(@Validated @RequestBody Publisher publisher){
        return publisherRepository.save(publisher);
    }
    
    @GetMapping("/get/{id}")
    public Publisher getPublisherById(@PathVariable(value = "id") Long id) {
        return publisherRepository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("Publisher not found for this id: " + id));
    }

    @PutMapping("/put/{id}")
    public Publisher updatePublisher(@PathVariable(value = "id") Long id, @RequestBody Publisher editedPublisher) {
        Publisher publisher = publisherRepository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("Publisher not found for this id: " + id));
        publisher.setName(editedPublisher.getName());
        return publisherRepository.save(publisher);
    }

    @DeleteMapping("/dell/{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable(value = "id") Long id) {
        Publisher publisher = publisherRepository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("Publisher not found for this id: " + id));
        publisherRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}