package com.example.Library.Book;

import java.util.Set;

public interface BookInfo {

    Long getId();

    String getName();

    BookWriter getWriter();

    Set<BookGenre> getGenres();

    interface BookWriter {
        Long getId();

        String getName();
    }

    interface BookGenre {
        Long getId();

        String getName();
    }
}


