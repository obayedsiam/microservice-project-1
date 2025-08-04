package com.example.Library.Book;

import com.example.Library.exception.CustomException;
import com.example.Library.response.ApiResponse;
import com.example.Library.response.PaginatedResponse;

public interface BookService {

    ApiResponse<BookDto> save(BookDto dto);

    ApiResponse<BookDto> update(BookDto dto);

    void delete(Long userId);

    PaginatedResponse<BookInfo> getAll(String search, String sortBy);

    BookInfo findById(Long userId);

    PaginatedResponse<BookInfo> getList(Integer size,
                                        Integer page,
                                        String sortBy,
                                        String sortDirection,
                                        String search) throws CustomException;

}
