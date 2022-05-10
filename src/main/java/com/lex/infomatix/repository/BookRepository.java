package com.lex.infomatix.repository;

import com.lex.infomatix.model.Author;
import com.lex.infomatix.model.Book;
import com.lex.infomatix.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<List<Book>> findAllByAuthorsIn(List<Author> authors);

    Optional<List<Book>> findAllByPublisher(Publisher publisher);
}
