package com.example.Library.Book;

import com.example.Library.exception.CustomException;
import com.example.Library.response.ApiResponse;
import com.example.Library.response.PaginatedResponse;

import java.util.List;

public interface BookService {

    ApiResponse<Book> save(BookDto dto);

    ApiResponse<Book> update(BookDto dto);

    void delete(Long userId);

    PaginatedResponse<BookInfo> getAll(String search, String sortBy);

    BookInfo findById(Long userId);

    PaginatedResponse<BookInfo> getList(Integer size,
                                    Integer page,
                                    String sortBy,
                                    String sortDirection,
                                    String search) throws CustomException;

}
