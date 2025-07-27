package com.example.Library.Book;

import lombok.Data;

import java.util.List;

@Data
public class BookResponse {
    String bookName;
    String writerName;
    List<String> genreList;
}
