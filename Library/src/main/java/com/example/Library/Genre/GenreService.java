package com.example.Library.Genre;

import com.example.Library.Book.Book;
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

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class GenreService {

    private final ModelMapper mapper;

    private final GenreRepository genreRepository;


    public ApiResponse<Genre> save(GenreDto dto) {
        Genre genre = mapper.map(dto, Genre.class);
        genre = genreRepository.save(genre);
        return ApiResponse.success("Genre created successfully", genre);
    }

    public ApiResponse<Genre> update(GenreDto dto) {

        if (dto.getId() == null) {
            throw new IllegalArgumentException("ID is required for update");
        }

        Optional<Genre> optionalGenre = genreRepository.findById(dto.getId());

        if (optionalGenre.isEmpty()) {
            throw new RuntimeException("Genre not found with ID: " + dto.getId());
        }

        Genre existingGenre = optionalGenre.get();

        // Update fields only if provided in DTO, otherwise keep existing
        existingGenre.setName(dto.getName() != null ? dto.getName() : existingGenre.getName());

        // Similarly, preserve recordStatus from BaseEntity if not provided in DTO (if DTO contains it)
        if (dto.getRecordStatus() != null) {
            existingGenre.setRecordStatus(dto.getRecordStatus());
        }

        // Save and return response
        Genre updatedGenre = genreRepository.save(existingGenre);

        return ApiResponse.success("Genre info updated", updatedGenre);
    }


    public ApiResponse<Genre> findById(Long id) {
        Optional<Genre> optionalGenre = genreRepository.findById(id);
        if (optionalGenre.isPresent()) {
            return ApiResponse.success("Genre found !", optionalGenre.get());
        }
        throw new CustomException("Genre not found with id " + id);
    }

    public ApiResponse<Genre> delete(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new CustomException("Genre not found!"));

        try {
            genreRepository.delete(genre);
        } catch (DataIntegrityViolationException e) {
            throw new CustomException("Cannot delete genre due to related data.");
        }

        return ApiResponse.success("Genre Deleted!");
    }


    public PaginatedResponse<Genre> getList(Integer size, Integer page, String sortBy, String sortDirection, String search) throws CustomException {

        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Genre> genrePage;

        if (search != null && !search.isEmpty()) {
            genrePage = genreRepository.findByNameContainingIgnoreCase(search, pageable);
        } else {
            genrePage = genreRepository.findAll(pageable);
        }

        return new PaginatedResponse<>(
                genrePage.getContent(),
                genrePage.getNumber(),
                genrePage.getSize(),
                genrePage.getTotalElements(),
                genrePage.getTotalPages(),
                genrePage.isLast()
        );

    }

    public List<Genre> getAll(String search) throws CustomException {
        List<Genre> allGenreList;

        if (search != null && !search.isEmpty()) {
            allGenreList = genreRepository.findByNameContainingIgnoreCase(search);
        } else {
            allGenreList = genreRepository.findAll();
        }
        return allGenreList;

    }
}
