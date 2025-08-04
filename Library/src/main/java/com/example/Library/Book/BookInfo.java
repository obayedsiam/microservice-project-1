package com.example.Library.Book;

import java.time.LocalDate;
import java.util.Set;

public interface BookInfo {

    Long getId();

    String getName();

    Double getRating();

    Boolean getIsRead();

    Integer getReadingPercentage();

    String getGiftedBy();

    LocalDate getBuyingDate();

    String getBuyingLocation();

    String getCurrentBookLocation();

    Boolean getBorrowed();

    String getBorrowerName();

    String getBorrowerPhone();

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


