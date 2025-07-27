package com.example.Library.Genre;

import com.example.Library.Book.Book;
import com.example.Library.Utils.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = "books") // Exclude relationship fields
@Data
@Entity
public class Genre extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use IDENTITY
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "genres")
    @JsonIgnoreProperties("genres") // Ignore "genres" in Book to break recursion
    private Set<Book> books = new HashSet<>();

//     Helper methods to manage bidirectional relationships
    public void addBook(Book book) {
        this.books.add(book);
        book.getGenres().add(this);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.getGenres().remove(this);
    }
}