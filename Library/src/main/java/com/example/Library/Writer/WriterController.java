package com.example.Library.Writer;

import com.example.Library.Book.Book;
import com.example.Library.response.ApiResponse;
import com.example.Library.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/writer")
public class WriterController {
    private final WriterService writerService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Writer>> save(@RequestBody WriterDto dto) {
        return ResponseEntity.ok(writerService.save(dto));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Writer>> update(@RequestBody WriterDto dto) {
        return ResponseEntity.ok(writerService.update(dto));
    }

    @GetMapping("/findBy/{id}")
    public ResponseEntity<ApiResponse<Writer>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(writerService.findById(id));
    }

    @GetMapping("/add/book")
    public ResponseEntity<ApiResponse<Writer>> addBook(@RequestParam Long bookId,
                                                       @RequestParam Long writerId) {
        return ResponseEntity.ok(writerService.addBook(bookId, writerId));
    }

    @GetMapping("/book/list")
    public ResponseEntity<ApiResponse<List<Book>>> getBookList(@RequestParam Long writerId) {
        return ResponseEntity.ok(writerService.getBookList(writerId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Writer>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(writerService.delete(id));
    }





    @GetMapping("/list")
    public ResponseEntity<PaginatedResponse<Writer>> getList(
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) String search
    ) {
        return ResponseEntity.ok(writerService.getList(size, page, sortBy, sortDirection, search));
    }
}
