package com.example.Library.Writer;

import com.example.Library.Book.Book;
import com.example.Library.response.ApiResponse;
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

    @GetMapping("/book/list")
    public ResponseEntity<ApiResponse<List<Book>>> getBookList(@RequestParam Long writerId) {
        return ResponseEntity.ok(writerService.getBookList(writerId));
    }

}
