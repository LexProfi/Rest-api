package com.lex.infomatix.controller;

import com.lex.infomatix.exception.MyEntityNotFoundException;
import com.lex.infomatix.model.Author;
import com.lex.infomatix.model.Book;
import com.lex.infomatix.model.Publisher;
import com.lex.infomatix.repository.AuthorRepository;
import com.lex.infomatix.repository.BookRepository;
import com.lex.infomatix.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/book-management", produces = { MediaType.APPLICATION_JSON_VALUE })
public class BookController {


    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @PostMapping("/post")
    public Book createBook(@Validated @RequestBody Book book) {

        if(!book.getAuthors().isEmpty()){
            for (int i = 0; i < book.getAuthors().size(); i++) {
                final int d = i;
                authorRepository.findById(book.getAuthors().get(i).getId()).orElseThrow(() -> new MyEntityNotFoundException("Author not found for this id: " +book.getAuthors().get(d).getId()));
            }
        }

        publisherRepository.findById(book.getPublisher().getId()).orElseThrow(() -> new MyEntityNotFoundException("Publisher not found for this id: " + book.getPublisher().getId()));

        return bookRepository.save(book);
    }

    @GetMapping("/get/{id}")
    public Book getBookById(@PathVariable(value = "id") Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("Book not found for this id: " + id));
    }

    @GetMapping("/by-author/{id}")
    public List<Book> getBookByAuthor(@PathVariable(value = "id") Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("Author not found for this id: " + id));
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        List<Book> books = bookRepository.findAllByAuthorsIn(authors).orElseThrow(() -> new MyEntityNotFoundException("Books not found from the author with this id: " + id));

        return books;
    }

    @GetMapping("/by-publisher/{id}")
    public List<Book> getBookByPublisher(@PathVariable(value = "id") Long id) {
        Publisher publisher = publisherRepository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("Publisher not found for this id: " + id));
        List<Book> books = bookRepository.findAllByPublisher(publisher).orElseThrow(() -> new MyEntityNotFoundException("Books not found from the publisher with this id: " + id));

        return books;
    }

    @PutMapping("/put/{id}")
    public Book updateBook(@PathVariable(value = "id") Long id, @RequestBody Book editedBook) {
      Book book = bookRepository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("Book not found for this id: " + id));
        if(!editedBook.getAuthors().isEmpty()){
            for (int i = 0; i < editedBook.getAuthors().size(); i++) {
                final int d = i;
                authorRepository.findById(book.getAuthors().get(i).getId()).orElseThrow(() -> new MyEntityNotFoundException("Author not found for this id: " +editedBook.getAuthors().get(d).getId()));
            }
        }
        Publisher publisher = publisherRepository.findById(editedBook.getPublisher().getId()).orElseThrow(() -> new MyEntityNotFoundException("Publisher not found for this id:" + editedBook.getPublisher().getId()));
        book.setTitle(editedBook.getTitle());
        book.setAnnotation(editedBook.getAnnotation());
        book.setAuthors(editedBook.getAuthors());
        book.setPublisher(editedBook.getPublisher());
        book.setPublishingYear(editedBook.getPublishingYear());
        return bookRepository.save(book);

    }

    @DeleteMapping("/dell/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable(value = "id") Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("Book not found for this id: " + id));
        bookRepository.deleteById(book.getId());
        return ResponseEntity.ok().build();
    }
}
