package com.example.Library.Book;

import com.example.Library.Genre.Genre;
import com.example.Library.Genre.GenreRepository;
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

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final WriterRepository writerRepository;
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;


    @Override
    public ApiResponse<BookDto> save(BookDto dto) {

        Book book = new Book();

        // Map simple DTO fields to entity
        book.setName(dto.getName());
        book.setRating(dto.getRating());
        book.setIsRead(dto.getIsRead());
        book.setReadingPercentage(dto.getReadingPercentage());
        book.setGiftedBy(dto.getGiftedBy());
        book.setBuyingDate(dto.getBuyingDate());
        book.setBuyingLocation(dto.getBuyingLocation());
        book.setCurrentBookLocation(dto.getCurrentBookLocation());
        book.setBorrowed(dto.getBorrowed());
        book.setBorrowerName(dto.getBorrowerName());
        book.setBorrowerPhone(dto.getBorrowerPhone());

        // Handle Writer relationship
        if (dto.getWriterId() != null) {
            Optional<Writer> writer = writerRepository.findById(dto.getWriterId());

            // Directly set the writer on the book
            writer.ifPresent(book::setWriter);

        }

        // Handle Genre relationship
        if (dto.getGenreIds() != null && !dto.getGenreIds().isEmpty()) {
            Set<Genre> genres = dto.getGenreIds().stream()
                    .map(genreId -> genreRepository.findById(genreId)
                            .orElseThrow(() -> new IllegalArgumentException("Genre not found with ID: " + genreId)))
                    .collect(Collectors.toSet());
            book.setGenres(genres); // Set the genres on the book
        }

        // Save the book and the changes will cascade to the relationships
        book = bookRepository.save(book);
        BookDto bookDto = convertToDto(book);

        return ApiResponse.success("Book saved successfully", bookDto);
    }


    public BookDto convertToDto(Book book) {
        if (book == null) {
            return null;
        }

        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setName(book.getName());
        dto.setRating(book.getRating());
        dto.setIsRead(book.getIsRead());
        dto.setReadingPercentage(book.getReadingPercentage());
        dto.setGiftedBy(book.getGiftedBy());
        dto.setBuyingDate(book.getBuyingDate());
        dto.setBuyingLocation(book.getBuyingLocation());
        dto.setCurrentBookLocation(book.getCurrentBookLocation());
        dto.setBorrowed(book.getBorrowed());
        dto.setBorrowerName(book.getBorrowerName());
        dto.setBorrowerPhone(book.getBorrowerPhone());

        // Set the writerId from the Book's writer entity
        if (book.getWriter() != null) {
            dto.setWriterId(book.getWriter().getId());
        }

        // Set the list of genreIds from the Book's genres set
        if (book.getGenres() != null && !book.getGenres().isEmpty()) {
            List<Long> genreIds = book.getGenres().stream()
                    .map(Genre::getId)
                    .collect(Collectors.toList());
            dto.setGenreIds(genreIds);
        }

        return dto;
    }

    @Override
    @Transactional
    public ApiResponse<BookDto> update(BookDto dto) {
        // Find the existing book or throw an exception if not found
        Book existingBook = bookRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + dto.getId()));

        // Update simple fields from the DTO
        existingBook.setName(dto.getName());
        existingBook.setRating(dto.getRating());
        existingBook.setIsRead(dto.getIsRead());
        existingBook.setReadingPercentage(dto.getReadingPercentage());
        existingBook.setGiftedBy(dto.getGiftedBy());
        existingBook.setBuyingDate(dto.getBuyingDate());
        existingBook.setBuyingLocation(dto.getBuyingLocation());
        existingBook.setCurrentBookLocation(dto.getCurrentBookLocation());
        existingBook.setBorrowed(dto.getBorrowed());
        existingBook.setBorrowerName(dto.getBorrowerName());
        existingBook.setBorrowerPhone(dto.getBorrowerPhone());

        // Update Writer relationship
        if (dto.getWriterId() != null) {
            Writer writer = writerRepository.findById(dto.getWriterId())
                    .orElseThrow(() -> new IllegalArgumentException("Writer not found with ID: " + dto.getWriterId()));
            existingBook.setWriter(writer);
        } else {
            existingBook.setWriter(null); // Allow removing the writer
        }

        // Update Genre relationship
        if (dto.getGenreIds() != null && !dto.getGenreIds().isEmpty()) {
            Set<Genre> genres = dto.getGenreIds().stream()
                    .map(genreId -> genreRepository.findById(genreId)
                            .orElseThrow(() -> new IllegalArgumentException("Genre not found with ID: " + genreId)))
                    .collect(Collectors.toSet());
            existingBook.setGenres(genres);
        } else {
            existingBook.setGenres(new HashSet<>()); // Allow removing all genres
        }

        // Save the updated book
        Book updatedBook = bookRepository.save(existingBook);

        // Convert the updated entity to a DTO and return
        return ApiResponse.success("Book updated successfully", convertToDto(updatedBook));
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public PaginatedResponse<BookInfo> getAll(String search, String sortBy) {
        List<BookInfo> bookoInfoList = bookRepository.findAllBy();
        return new PaginatedResponse<>(bookoInfoList);
    }

    @Override
    public BookInfo findById(Long bookId) {
        return bookRepository.findBookById(bookId);
    }

    @Override
    public PaginatedResponse<BookInfo> getList(Integer size, Integer page, String sortBy, String sortDirection, String search) throws CustomException {

        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<BookInfo> bookPage;

        if (search != null && !search.isEmpty()) {
            bookPage = bookRepository.findByNameContainingIgnoreCaseOrWriter_NameContainingIgnoreCase(search, search, pageable);
        } else {
            bookPage = bookRepository.findAllBy(pageable);
        }

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
