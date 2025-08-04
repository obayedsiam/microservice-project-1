package com.example.Library.Book;

import com.example.Library.Genre.Genre;
import com.example.Library.Utils.BaseEntity;
import com.example.Library.Writer.Writer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Book extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double rating;
    private Boolean isRead;
    private Integer readingPercentage;
    private String giftedBy;
    private LocalDate buyingDate;
    private String buyingLocation;
    private String currentBookLocation;
    private Boolean borrowed;
    private String borrowerName;
    private String borrowerPhone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    @ToString.Exclude
    @JsonBackReference
    private Writer writer;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @ToString.Exclude
    @JsonIgnoreProperties("books")
    private Set<Genre> genres = new HashSet<>();

    // Helper methods
    public void addGenre(Genre genre) {
        this.genres.add(genre);
        genre.getBooks().add(this);
    }

    public void removeGenre(Genre genre) {
        this.genres.remove(genre);
        genre.getBooks().remove(this);
    }
}
