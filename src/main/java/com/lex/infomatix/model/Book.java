package com.lex.infomatix.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "annotation")
    private String annotation;

    @Column(name = "publishing_year", nullable = false)
    private int publishingYear;

    @ManyToOne()
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    @ManyToMany()
    @JoinColumn(name = "author_id")
    private List<Author> authors;

    public Book() {}

    public long getId(){ return id; }

    public void setId(long id){ this.id = id; }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getAnnotation(){
        return annotation;
    }

    public void setAnnotation(String annotation){
        this.annotation = annotation;
    }

    public int getPublishingYear() { return publishingYear; }

    public void setPublishingYear(int publishingYear) {
        this.publishingYear = publishingYear;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Author> getAuthors() { return authors; }

    public void setAuthors( List<Author> authors) {this.authors = authors;}

}
