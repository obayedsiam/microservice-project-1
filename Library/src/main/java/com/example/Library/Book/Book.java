package com.example.Library.Book;

import com.example.Library.Utils.BaseEntity;
import com.example.Library.Writer.Writer;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "book_writer",
            joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "writer_id"))
    @JsonManagedReference
    private Set<Writer> writers = new HashSet<>();

    public void addWriter(Writer writer) {
        writers.add(writer);
    }

    public void addWriterSet(Set<Writer> writerList) {
        writers.addAll(writerList);
    }
}
