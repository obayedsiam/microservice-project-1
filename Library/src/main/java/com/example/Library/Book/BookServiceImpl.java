package com.example.Library.Book;

import com.example.Library.Writer.Writer;
import com.example.Library.Writer.WriterRepository;
import com.example.Library.exception.CustomException;
import com.example.Library.response.ApiResponse;
import com.example.Library.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final WriterRepository writerRepository;
    private final ModelMapper modelMapper;


    @Override
    public ApiResponse<Book> save(BookDto dto) {

        Book book = modelMapper.map(dto, Book.class);

        Set<Writer> writers = new HashSet<>();

        if (dto.getWriterSet() != null) {
            writers = getWriterList(dto.getWriterSet());
        }

        book.addWriterSet(writers);
        book = bookRepository.save(book);
        writerRepository.saveAll(writers);

        return ApiResponse.success("Book saved successfully", book);
    }

    private Set<Writer> getWriterList(Set<Long> writerSet) {
        Set<Writer> writerSetResponse = new HashSet<>();
        for (Long writerId : writerSet) {
            Optional<Writer> optionalWriter = writerRepository.findById(writerId);
            optionalWriter.ifPresent(writerSetResponse::add);
        }
        return writerSetResponse;
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

        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Book> bookPage;

//        if (search != null && !search.isEmpty()) {
//            bookPage = bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(search, search, pageable);
//        } else {
//            bookPage = bookRepository.findAll(pageable);
//        }

        bookPage = bookRepository.findAll(pageable);

        return new PaginatedResponse<>(
                bookPage.getContent(),
                bookPage.getNumber(),
                bookPage.getSize(),
                bookPage.getTotalElements(),
                bookPage.getTotalPages(),
                bookPage.isLast()
        );
    }
}
