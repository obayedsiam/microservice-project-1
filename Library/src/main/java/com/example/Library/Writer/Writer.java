package com.example.Library.Writer;

import com.example.Library.Book.Book;
import com.example.Library.Utils.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Writer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "writers")
    @JsonBackReference
    private Set<Book> books = new HashSet<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void addBookSet(Set<Book> bookSet) {
        books.addAll(bookSet);
    }


}
