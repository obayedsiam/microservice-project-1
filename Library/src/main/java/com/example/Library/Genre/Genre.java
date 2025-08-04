package com.example.Library.Genre;

import com.example.Library.Book.Book;
import com.example.Library.Utils.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Genre extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnoreProperties("genres")
    private Set<Book> books = new HashSet<>();

    // Optional helper methods (used in Book)
    public void addBook(Book book) {
        this.books.add(book);
        book.getGenres().add(this);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.getGenres().remove(this);
    }
}
