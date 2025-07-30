package com.example.Library.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b from Book b where b.id = :id")
    BookInfo findBookById(Long id);

//    @Query("SELECT b.id AS id, b.name AS name, b.writer AS writer, b.genres AS genres FROM Book b")
    Page<BookInfo> findAllBy(Pageable pageable);

//    @Query("SELECT b.id AS id, b.name AS name, b.writer AS writer, b.genres AS genres FROM Book b")
    List<BookInfo> findAllBy();

    Page<BookInfo> findByNameContainingIgnoreCaseOrWriter_NameContainingIgnoreCase(
            String name, String writerName, Pageable pageable);
}
