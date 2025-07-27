package com.example.Library.Writer;

import com.example.Library.Book.Book;
import com.example.Library.Utils.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = "books") // Exclude relationship fields
@Entity
@Data
public class Writer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use IDENTITY
    private Long id;

    private String name;

//    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnoreProperties("writer") // Ignore "writer" in Book to break recursion
//    private Set<Book> books = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "writer")
    private List<Book> books = new ArrayList<>();

    // Helper method to add a book and maintain bidirectional relationship
    public void addBook(Book book) {
        this.books.add(book);
        book.setWriter(this); // Set the writer for the book
    }

    // Helper method to remove a book and maintain bidirectional relationship
    public void removeBook(Book book) {
        this.books.remove(book);
        book.setWriter(null); // Remove the writer from the book
    }
}