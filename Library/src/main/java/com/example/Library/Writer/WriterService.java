package com.example.Library.Writer;

import com.example.Library.Book.Book;
import com.example.Library.Book.BookRepository;
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

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class WriterService {

    private final ModelMapper mapper;

    private final WriterRepository writerRepository;

    private final BookRepository bookRepository;

    public ApiResponse<Writer> save(WriterDto dto) {
        Writer writer = mapper.map(dto, Writer.class);
        writer = writerRepository.save(writer);
        return ApiResponse.success("Writer created successfully", writer);
    }

    public ApiResponse<Writer> update(WriterDto dto) {
        Writer writer = new Writer();
        if (dto.getId() != null) {
            writer.setId(dto.getId());
            Optional<Writer> optionalWriter = writerRepository.findById(dto.getId());
            if (optionalWriter.isPresent()) {
                if (optionalWriter.get().getName() != null) {
                    writer.setName(optionalWriter.get().getName());
                }
                writer = writerRepository.save(writer);
                return ApiResponse.success("Writer Created Successfully", writer);
            }

        }
        return ApiResponse.success("Writer info updated", writer);
    }

    public ApiResponse<Writer> findById(Long id) {
        Optional<Writer> optionalWriter = writerRepository.findById(id);
        if (optionalWriter.isPresent()) {
            return ApiResponse.success("Writer found !", optionalWriter.get());
        }
        throw new CustomException("Writer not found with id " + id);
    }

    public ApiResponse<Writer> addBook(Long bookId, Long writerId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Optional<Writer> optionalWriter = writerRepository.findById(writerId);

        if (optionalWriter.isPresent()) {

            if (optionalBook.isPresent()) {
                Writer writer = optionalWriter.get();

                Book book = optionalBook.get();

                Set<Book> bookSet = writer.getBooks();

                bookSet.add(book);

                writer.setBooks(bookSet);

                return ApiResponse.success("Book added for writer", writer);
            } else throw new CustomException("Book not found with id " + bookId);
        } else throw new CustomException("Writer not found with id " + writerId);
    }

    public ApiResponse<List<Book>> getBookList(Long writerId) {
        Optional<Writer> optionalWriter = writerRepository.findById(writerId);
        if (optionalWriter.isPresent()) {
            Writer writer = optionalWriter.get();
            return ApiResponse.success("All book list of writer " + writer.getBooks());
        }
        throw new CustomException("Writer not found !");
    }

    public PaginatedResponse<Writer> getList(Integer size, Integer page, String sortBy, String sortDirection, String search) throws CustomException {

        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Writer> writerPage;

//        if (search != null && !search.isEmpty()) {
//            writerPage = writerRepository.findByBookNameContainingIgnoreCase(search, pageable);
//        } else {
//        writerPage = writerRepository.findAll(pageable);
//        }

        writerPage = writerRepository.findAll(pageable);

        return new PaginatedResponse<>(
                writerPage.getContent(),
                writerPage.getNumber(),
                writerPage.getSize(),
                writerPage.getTotalElements(),
                writerPage.getTotalPages(),
                writerPage.isLast()
        );

    }
}
