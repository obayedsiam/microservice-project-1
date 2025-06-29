package com.example.Library.Book;

import com.example.Library.Writer.Writer;
import com.example.Library.Writer.WriterRepository;
import com.example.Library.exception.CustomException;
import com.example.Library.response.ApiResponse;
import com.example.Library.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final WriterRepository writerRepository;
    private final ModelMapper modelMapper;


    @Override
    public ApiResponse<Book> save(BookDto dto) {
        Book book = modelMapper.map(dto, Book.class);


        List<Writer> writerList;
        writerList = getWriterList(dto.getWriterList());
        book.setWriterList(writerList);
        book = bookRepository.save(book);
        writerRepository.saveAll(writerList);

        return ApiResponse.success("Book saved successfully", book);
    }

    private List<Writer> getWriterList(List<Long> writerList) {
        List<Writer> writerListResponse = new ArrayList<>();
        for (Long writerId : writerList) {
         Optional<Writer> optionalWriter = writerRepository.findById(writerId);
            optionalWriter.ifPresent(writerListResponse::add);
        }
        return writerListResponse;
    }

    @Override
    public ApiResponse<Book> update(BookDto dto) {
        Optional<Book> bookOptional = bookRepository.findById(dto.getId());
        if (bookOptional.isPresent()) {
            ModelMapper mapper = new ModelMapper();
            Book book = mapper.map(dto, Book.class);
            return ApiResponse.success("Book information updated", bookRepository.save(book));
        }
        throw new CustomException("Book not found !");
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public ApiResponse<List<Book>> getAll(String search, String sortBy) {
        return ApiResponse.success("", bookRepository.findAll());
    }

    @Override
    public Book findById(Long userId) {
        return null;
    }

    @Override
    public PaginatedResponse<Book> getList(Integer size, Integer page, String sortBy, String sortDirection, String search) throws CustomException {
        return null;
    }
}
