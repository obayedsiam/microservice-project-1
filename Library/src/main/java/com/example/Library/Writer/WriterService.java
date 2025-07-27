package com.example.Library.Writer;

import com.example.Library.Book.Book;
import com.example.Library.Book.BookRepository;
import com.example.Library.exception.CustomException;
import com.example.Library.response.ApiResponse;
import com.example.Library.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        if (dto.getId() == null) {
            throw new IllegalArgumentException("ID is required for update");
        }

        Optional<Writer> optionalWriter = writerRepository.findById(dto.getId());

        if (optionalWriter.isEmpty()) {
            throw new RuntimeException("Writer not found with ID: " + dto.getId());
        }

        Writer existingWriter = optionalWriter.get();

        // Update fields only if provided in DTO, otherwise keep existing
        existingWriter.setName(dto.getName() != null ? dto.getName() : existingWriter.getName());

        // Similarly, preserve recordStatus from BaseEntity if not provided in DTO (if DTO contains it)
        if (dto.getRecordStatus() != null) {
            existingWriter.setRecordStatus(dto.getRecordStatus());
        }

        // Save and return response
        Writer updatedWriter = writerRepository.save(existingWriter);

        return ApiResponse.success("Writer info updated", updatedWriter);
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

                List<Book> bookList = writer.getBooks();

                bookList.add(book);

                writer.setBooks(bookList);

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

    public ApiResponse<Writer> delete(Long id) {
        Writer writer = writerRepository.findById(id)
                .orElseThrow(() -> new CustomException("Writer not found!"));

        try {
            writerRepository.delete(writer);
        } catch (DataIntegrityViolationException e) {
            throw new CustomException("Cannot delete writer due to related data.");
        }

        return ApiResponse.success("Author Deleted!");
    }


    public PaginatedResponse<Writer> getList(Integer size, Integer page, String sortBy, String sortDirection, String search) throws CustomException {

        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Writer> writerPage;

        if (search != null && !search.isEmpty()) {
            writerPage = writerRepository.findByNameContainingIgnoreCase(search, pageable);
        } else {
            writerPage = writerRepository.findAll(pageable);
        }

        return new PaginatedResponse<>(
                writerPage.getContent(),
                writerPage.getNumber(),
                writerPage.getSize(),
                writerPage.getTotalElements(),
                writerPage.getTotalPages(),
                writerPage.isLast()
        );

    }

    public List<Writer> getAll(String search) throws CustomException {
        List<Writer> allWriterList;

        if (search != null && !search.isEmpty()) {
            allWriterList = writerRepository.findByNameContainingIgnoreCase(search);
        } else {
             allWriterList = writerRepository.findAll();
        }
        return allWriterList;

    }
}
