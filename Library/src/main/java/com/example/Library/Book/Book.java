package com.example.Library.Book;

import com.example.Library.Genre.Genre;
import com.example.Library.Utils.BaseEntity;
import com.example.Library.Writer.Writer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper = true, exclude = {"writer", "genres"}) // Exclude relationship fields
public class Book extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use IDENTITY for auto-incrementing IDs
    private Long id;

    private String name;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY) // Lazy fetch for ManyToOne to avoid N+1 issues
    private Writer writer;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) // Cascade for persist and merge
    @JoinTable(
            name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @JsonIgnoreProperties("books") // Ignore "books" in Genre to break recursion
    private Set<Genre> genres = new HashSet<>();

    // Helper methods to manage bidirectional relationships
    public void addGenre(Genre genre) {
        this.genres.add(genre);
        genre.getBooks().add(this);
    }

    public void removeGenre(Genre genre) {
        this.genres.remove(genre);
        genre.getBooks().remove(this);
    }

    public void setWriter(Writer writer) {
        // Remove from old writer's books if exists
        if (this.writer != null) {
            this.writer.getBooks().remove(this);
        }
        this.writer = writer;
        // Add to new writer's books
        if (writer != null) {
            writer.getBooks().add(this);
        }
    }
}