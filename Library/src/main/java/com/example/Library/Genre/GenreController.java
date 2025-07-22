package com.example.Library.Genre;

import com.example.Library.response.ApiResponse;
import com.example.Library.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/genre")
public class GenreController {
    private final GenreService genreService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Genre>> save(@RequestBody GenreDto dto) {
        return ResponseEntity.ok(genreService.save(dto));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Genre>> update(@RequestBody GenreDto dto) {
        return ResponseEntity.ok(genreService.update(dto));
    }

    @GetMapping("/findBy/{id}")
    public ResponseEntity<ApiResponse<Genre>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(genreService.findById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Genre>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(genreService.delete(id));
    }

    @GetMapping("/list")
    public ResponseEntity<PaginatedResponse<Genre>> getList(
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) String search
    ) {
        return ResponseEntity.ok(genreService.getList(size, page, sortBy, sortDirection, search));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Genre>> getAll(
            @RequestParam(required = false) String search
    ) {
        return ResponseEntity.ok(genreService.getAll(search));
    }
}
