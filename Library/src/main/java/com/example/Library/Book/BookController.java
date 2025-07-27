package com.example.Library.Book;


import com.example.Library.exception.CustomException;
import com.example.Library.response.ApiResponse;
import com.example.Library.response.PaginatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Book", description = "Book related API's")
@RequestMapping("api/v1/book")
@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;

    @PostMapping("/save")
    @Operation(description = "Adding Book")
    public ResponseEntity<ApiResponse<Book>> save(@RequestBody BookDto dto) {
        return ResponseEntity.ok(bookService.save(dto));
    }

    @PutMapping("/update")
    @Operation(description = "Updating Book")
    public ResponseEntity<ApiResponse<Book>> update(@RequestBody BookDto dto) {
        return ResponseEntity.ok(bookService.update(dto));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(description = "Deleting Book")
    public ResponseEntity<ApiResponse<Book>> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/find/{id}")
    @Operation(description = "Finding Book")
    public ResponseEntity<Book> findById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @GetMapping("/all")
    @Operation(description = "Getting All Book")
    public ResponseEntity<List<Book>> getAll(@RequestParam(value = "sortBy", defaultValue = "") String sortBy,
                                                          @RequestParam(value = "search", defaultValue = "") String search) {
        return ResponseEntity.ok(bookService.getAll(search, sortBy));
    }

    @GetMapping("/list")
    @Operation(description = "Getting Paginated Book")
    public PaginatedResponse<Book> getList(@RequestParam(value = "sortBy", defaultValue = "") String sortBy,
                                                 @RequestParam(value = "search", defaultValue = "") String search,
                                                 @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                 @RequestParam(value = "sortDirection", defaultValue = "") String sortDirection,
                                                 @RequestParam(value = "size", defaultValue = "10") Integer size) throws CustomException {
        return bookService.getList(size, page, sortBy, sortDirection, search);
    }
}
