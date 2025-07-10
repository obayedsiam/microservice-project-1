package com.example.Library.Writer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WriterRepository extends JpaRepository<Writer, Long> {

    Page<Writer> findByNameContainingIgnoreCase(String bookName, Pageable pageable);

    List<Writer> findByNameContainingIgnoreCase(String bookName);
}
