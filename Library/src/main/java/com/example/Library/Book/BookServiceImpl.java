package com.example.Library.Book;

import com.example.Library.Genre.Genre;
import com.example.Library.Genre.GenreRepository;
import com.example.Library.Utils.RecordStatus;
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
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;


    @Override
    public ApiResponse<Book> save(BookDto dto) {

        Book book = modelMapper.map(dto, Book.class);

        Optional<Writer> writerOptional = writerRepository.findById(dto.getWriterId() != null ? dto.getWriterId() : 0L);
        if(writerOptional.isPresent()){
            Writer writer = writerOptional.get();
            book.setWriter(writer);
        }

        Set<Genre> genreSet = new HashSet<>();
        for(Long genreId : dto.getGenreIds()){
            Optional<Genre> genreOptional = genreRepository.findById(genreId != null ? genreId : 0L);
            genreOptional.ifPresent(genreSet::add);
        }
        book.setGenres(genreSet);

        book = bookRepository.save(book);

        return ApiResponse.success("Book saved successfully", book);
    }


    @Override
    public ApiResponse<Book> update(BookDto dto) {
        Optional<Book> bookOptional = bookRepository.findById(dto.getId());
        if (bookOptional.isPresent()) {
            ModelMapper mapper = new ModelMapper();
            Book book = mapper.map(dto, Book.class);
            book.setRecordStatus(RecordStatus.ACTIVE);
            return ApiResponse.success("Book information updated", bookRepository.save(book));
        }
        throw new CustomException("Book not found !");
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
