package com.lex.infomatix.controller;

import com.lex.infomatix.exception.AlreadyExistException;
import com.lex.infomatix.exception.MyEntityNotFoundException;
import com.lex.infomatix.model.Author;
import com.lex.infomatix.repository.AuthorRepository;
import com.lex.infomatix.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/author-management", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AuthorController {


    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @PostMapping("/post")
    public Author createAuthor(@Validated @RequestBody Author author){
        return authorRepository.save(author);
    }

    @GetMapping("/get/{id}")
    public Author getAuthorById(@PathVariable(value = "id") Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("Author not found for this id: " + id));
    }

    @PutMapping("/put/{id}")
    public Author updateAuthor(@PathVariable(value = "id") Long id, @RequestBody Author editedAuthor) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("Author not found for this id: " + id));
        author.setName(editedAuthor.getName());
        author.setSurname(editedAuthor.getSurname());
        return authorRepository.save(author);
    }

    @DeleteMapping("/dell/{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable(value = "id") Long id) throws AlreadyExistException {
        Author author = authorRepository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("Author not found for this id: " + id));
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        if(!bookRepository.findAllByAuthorsIn(authors).isEmpty()){
            throw new AlreadyExistException("There are already books by the author with this id: " +id);
        }
        authorRepository.deleteById(author.getId());
        return ResponseEntity.ok().build();
    }
}